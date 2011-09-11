//
//  GCParcel.m
//
//  Created by Achal Aggarwal on 09/09/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "GCParcel.h"
#import "GCAsset.h"
#import "GCChute.h"

NSString * const GCParcelFinishedUploading   = @"GCParcelFinishedUploading";

@implementation GCParcel

@synthesize status;
@synthesize assets;
@synthesize chutes;

@synthesize delegate;
@synthesize completionSelector;

- (void) removeAsset:(GCAsset *)_asset {
    if ([assets indexOfObject:_asset] != NSNotFound) {
        [assets removeObject:_asset];
    }
    
    if ([assets count] == 0) {
        [self setStatus:GCParcelStatusDone];
        [[NSNotificationCenter defaultCenter] postNotificationName:GCParcelFinishedUploading object:self];
        if (delegate && [delegate respondsToSelector:completionSelector]) {
            [delegate performSelector:completionSelector];
        }
    }
}

- (GCResponse *) newParcel {
    
    //Unique Description of Assets
    NSMutableArray *_assetsUniqueDescription = [[NSMutableArray alloc] init];
    for (GCAsset *_asset in assets) {
        [_assetsUniqueDescription addObject:[_asset uniqueRepresentation]];
    }
    
    //Get all Chute IDs
    NSMutableArray *_chuteIDs = [[NSMutableArray alloc] init];
    for (GCChute *_chute in chutes) {
        [_chuteIDs addObject:[NSString stringWithFormat:@"%@",[_chute objectID]]];
    }
    
    //Make Parameters to be sent across with the request
    NSDictionary *params    = [NSDictionary dictionaryWithObjectsAndKeys:
                               [_assetsUniqueDescription JSONRepresentation], @"files", 
                               [_chuteIDs JSONRepresentation], @"chutes", 
                               nil];
    
    [_chuteIDs release];
    [_assetsUniqueDescription release];
    
    NSString *_path = [[NSString alloc] initWithFormat:@"%@%@", API_URL, @"parcels"];
    
    GCRequest *gcRequest = [[GCRequest alloc] init];
    GCResponse *response = [[gcRequest postRequestWithPath:_path andParams:(NSMutableDictionary *)params] retain];
    
    [gcRequest release];
    [_path release];
    return [response autorelease];
}

- (GCResponse *) tokenForAsset:(GCAsset *) anAsset {
    NSString *_path = [[NSString alloc] initWithFormat:@"%@/uploads/%@/token", API_URL, [anAsset objectID]];
    GCRequest *gcRequest = [[GCRequest alloc] init];
    GCResponse *response = [[gcRequest getRequestWithPath:_path] retain];
    [gcRequest release];
    [_path release];
    return [response autorelease];
}

- (BOOL) uploadAssetToS3:(GCAsset *) anAsset withToken:(NSDictionary *) _token {
    NSMutableData *_imageData = [UIImageJPEGRepresentation([UIImage imageWithCGImage:[[anAsset.alAsset defaultRepresentation] fullResolutionImage] scale:1 orientation:[[anAsset.alAsset valueForProperty:ALAssetPropertyOrientation] intValue]], 1.0) mutableCopy];
    
    ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:[NSURL URLWithString:[_token objectForKey:@"upload_url"]]];
    [request setUploadProgressDelegate:anAsset];
	[request setRequestMethod:@"PUT"];
    [request setPostBody:_imageData];
    
	[request addRequestHeader:@"Date" value:[_token objectForKey:@"date"]];
	[request addRequestHeader:@"Authorization" value:[_token objectForKey:@"signature"]];
	[request addRequestHeader:@"Content-Type" value:[_token objectForKey:@"content_type"]];
    [request addRequestHeader:@"x-amz-acl" value:@"public-read"];
    [request startSynchronous];
    GCResponse *_result = [[GCResponse alloc] initWithRequest:request];
    
    BOOL _response = [_result isSuccessful];
    [_result release];
    
    if (_response) {
        [anAsset setStatus:GCAssetStateCompleting];
    }
    else {
        [anAsset setStatus:GCAssetStateUploadingToS3Failed];
    }
    return _response;
}

- (GCResponse *) completionRequestForAsset:(GCAsset *) anAsset {
    NSString *_path = [[NSString alloc] initWithFormat:@"%@/uploads/%@/complete", API_URL, [anAsset objectID]];
    GCRequest *gcRequest = [[GCRequest alloc] init];
    GCResponse *response = [[gcRequest getRequestWithPath:_path] retain];
    
    if ([response isSuccessful]) {
        [anAsset setStatus:GCAssetStateFinished];
    }
    else {
        [anAsset setStatus:GCAssetStateCompletingFailed];
    }
    
    [gcRequest release];
    [_path release];
    return [response autorelease];
}

- (void) removeUploadedAssets {
    NSMutableArray *assetUrls = [[NSMutableArray alloc] init];
    for (NSDictionary *_assetDetails in [self objectForKey:@"uploads"]) {
        [assetUrls addObject:[_assetDetails objectForKey:@"file_path"]];
    }
    
    NSMutableArray *assetsToRemove = [[NSMutableArray alloc] init];
    
    for (GCAsset *_asset in assets) {
        if ([assetUrls indexOfObject:[_asset uniqueURL]] != NSNotFound) {
            NSString *_id = [[[self objectForKey:@"uploads"] objectAtIndex:[assetUrls indexOfObject:[_asset uniqueURL]]] objectForKey:@"asset_id"];
            [_asset setObject:_id forKey:@"id"];
        }
        else {
            [assetsToRemove addObject:_asset];
        }
    }
    
    for (id obj in assetsToRemove) {
        [self removeAsset:obj];
    }
    
    [assetsToRemove release];
    [assetUrls release];
}

- (void) startUpload {
    //Create Parcel with Assets and Chutes
    NSDictionary *_parcel = [[[self newParcel] rawResponse] JSONValue];
    for (NSString *key in [_parcel allKeys]) {
        [self setObject:[_parcel objectForKey:key] forKey:key];
    }
    
    //Remove assets which are already uploaded.
    [self removeUploadedAssets];
    
    //Start loop of assets
    for (GCAsset *_asset in assets) {
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^(void) {
            [_asset setStatus:GCAssetStateGettingToken];
            
            //Generate New token for each asset
            GCResponse *_response = [self tokenForAsset:_asset];
            while (![_response isSuccessful]) {
                [_asset setStatus:GCAssetStateGettingTokenFailed];
                _response = [self tokenForAsset:_asset];
            }
            
            //Upload using the token to S3
            NSDictionary *_token = [_response data];
            [_asset setStatus:GCAssetStateUploadingToS3];
            
            BOOL uploaded = [self uploadAssetToS3:_asset withToken:_token];
            while (!uploaded) {
                uploaded = [self uploadAssetToS3:_asset withToken:_token];
            }
            
            [_asset setStatus:GCAssetStateCompleting];
            //Send completion Request to the asset after uploading to S3
            _response = [self completionRequestForAsset:_asset];
            while (![_response isSuccessful]) {
                _response = [self completionRequestForAsset:_asset];
            }
            
        });
    }
}

- (void) updateUploadQueue:(NSNotification *) notification {
    if ([[notification object] status] == GCAssetStateFinished) {
        [self removeAsset:[notification object]];
    }
}

- (void) startUploadWithTarget:(id)_target andSelector:(SEL)_selector {
    [self setDelegate:_target];
    [self setCompletionSelector:_selector];
    [self setStatus:GCParcelStatusUploading];
    [self performSelector:@selector(startUpload) withObject:nil afterDelay:0.1f];
}

- (id) initWithAssets:(NSArray *) _assets andChutes:(NSArray *) _chutes {
    self = [super init];
    if (self) {
        assets = [[NSMutableArray arrayWithArray:_assets] retain];
        chutes = [[NSArray arrayWithArray:_chutes] retain];
        [self setStatus:GCParcelStatusNew];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateUploadQueue:) name:GCAssetStatusChanged object:nil];
    }
    return self;
}

+ (id) objectWithAssets:(NSArray *) _assets andChutes:(NSArray *) _chutes {
    return [[[self alloc] initWithAssets:_assets andChutes:_chutes] autorelease];
}

+ (id) objectWithDictionary:(NSDictionary *) dictionary {
    return [[[self alloc] initWithDictionary:dictionary] autorelease];
}

- (id) initWithDictionary:(NSDictionary *) dictionary {
    [self init];
    for (NSString *key in [dictionary allKeys]) {
        id _obj;
        if ([key isEqualToString:@"user"]) {
            _obj = [GCUser objectWithDictionary:[dictionary objectForKey:key]];
        }
        else if ([key isEqualToString:@"assets"]) {
            assets = [[NSMutableArray alloc] init];
            for (NSDictionary *_dic in [dictionary objectForKey:key]) {
                [assets addObject:[GCAsset objectWithDictionary:_dic]];
            }
        }
        else if ([key isEqualToString:@"chute"]) {
            chutes = [[NSArray arrayWithObject:[GCChute objectWithDictionary:[dictionary objectForKey:key]]] retain];
        }
        else {
            _obj = IS_NULL([dictionary objectForKey:key])? @"": [dictionary objectForKey:key];
        }
        [self setObject:_obj forKey:key];
    }
    [self setStatus:GCParcelStatusDone];
    return self;
}

- (void) dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    [assets release];
    [chutes release];
    [super dealloc];
}

@end
