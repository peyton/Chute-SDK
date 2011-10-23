//
//  GCAsset.m
//
//  Created by Brandon Coston on 8/31/11.
//  Copyright 2011 Chute Corporation. All rights reserved.
//

#import "GCAsset.h"
#import "GCAssetUploader.h"
#import "GC_UIImage+Extras.h"

NSString * const GCAssetStatusChanged   = @"GCAssetStatusChanged";
NSString * const GCAssetProgressChanged = @"GCAssetProgressChanged";
NSString * const GCAssetUploadComplete = @"GCAssetUploadComplete";

@implementation GCAsset

@synthesize alAsset;
@synthesize thumbnail;
@synthesize selected;
@synthesize progress;
@synthesize status;
@synthesize parentID;

- (BOOL) isHearted {
    if([[GCAccount sharedManager] heartedAssets])
        return [[[GCAccount sharedManager] heartedAssets] containsObject:self];
    return NO;
}

#pragma mark - Heart Method
- (BOOL) toggleHeart {
    if ([self isHearted]) {
        //unheart
        GCResponse *response = [self unheart];
        if ([response isSuccessful]) {
            [[[GCAccount sharedManager] heartedAssets] removeObject:self];
        }
    }
    else {
        //heart
        GCResponse *response = [self heart];
        if ([response isSuccessful]) {
            [[[GCAccount sharedManager] heartedAssets] addObject:self];
        }
    }
    return [self isHearted];
}

- (void) toggleHeartInBackgroundWithCompletion:(GCBoolBlock) aBoolBlock {
    DO_IN_BACKGROUND_BOOL([self toggleHeart], aBoolBlock);
}

- (GCResponse *) heart {
    NSString *_path              = [[NSString alloc] initWithFormat:@"%@%@/%@/heart", API_URL, [[self class] elementName], [self objectID]];
    GCRequest *gcRequest         = [[GCRequest alloc] init];
    GCResponse *_response        = [[gcRequest postRequestWithPath:_path andParams:nil] retain];
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

- (void) heartInBackgroundWithCompletion:(GCBoolErrorBlock) aBoolErrorBlock {
    DO_IN_BACKGROUND_BOOL_ERROR([self heart], aBoolErrorBlock);
}

- (GCResponse *) unheart {
    NSString *_path              = [[NSString alloc] initWithFormat:@"%@%@/%@/unheart", API_URL, [[self class] elementName], [self objectID]];
    GCRequest *gcRequest         = [[GCRequest alloc] init];
    GCResponse *_response        = [[gcRequest postRequestWithPath:_path andParams:nil] retain];
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

- (void) unheartInBackgroundWithCompletion:(GCBoolErrorBlock) aBoolErrorBlock {
    DO_IN_BACKGROUND_BOOL_ERROR([self unheart], aBoolErrorBlock);
}

#pragma mark - Comment Methods

- (GCResponse *) comments {
    if (IS_NULL([self parentID])) {
        return nil;
    }
    NSString *_path              = [[NSString alloc] initWithFormat:@"%@chutes/%@/assets/%@/comments", API_URL, [self parentID], [self objectID]];
    GCRequest *gcRequest         = [[GCRequest alloc] init];
    GCResponse *_response        = [[gcRequest getRequestWithPath:_path] retain];
    NSMutableArray *_comments    = [[NSMutableArray alloc] init]; 
    for (NSDictionary *_dic in [_response data]) {
        [_comments addObject:[GCComment objectWithDictionary:_dic]];
    }
    [_response setObject:_comments];
    [_comments release];
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

- (void) commentsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock {
    DO_IN_BACKGROUND([self comments], aResponseBlock);
}

- (GCResponse *) addComment:(NSString *) _comment {
    if (IS_NULL([self parentID])) {
        return nil;
    }
    NSMutableDictionary *_params = [[NSMutableDictionary alloc] init];
    [_params setValue:_comment forKey:@"comment"];
    
    NSString *_path             = [[NSString alloc] initWithFormat:@"%@chutes/%@/assets/%@/comments", API_URL, [self parentID], [self objectID]];
    
    GCRequest *gcRequest        = [[GCRequest alloc] init];
    GCResponse *_response       = [[gcRequest postRequestWithPath:_path andParams:_params] retain];
    [gcRequest release];
    [_path release];
    [_params release];
    return [_response autorelease];
}

- (void) addComment:(NSString *) _comment inBackgroundWithCompletion:(GCResponseBlock) aResponseBlock {
    DO_IN_BACKGROUND([self addComment:_comment], aResponseBlock);
}

- (NSDictionary *) uniqueRepresentation {
    ALAssetRepresentation *_representation = [[self alAsset] defaultRepresentation];
    return [NSDictionary dictionaryWithObjectsAndKeys:[[_representation url] absoluteString], @"filename", 
     [NSString stringWithFormat:@"%d", [_representation size]], @"size", 
     [NSString stringWithFormat:@"%d", [_representation size]], @"md5", 
     nil];
}

- (NSString *) uniqueURL {
    return [[[[self alAsset] defaultRepresentation] url] absoluteString];
}

#pragma mark - Upload

- (void) upload {
    [[GCAssetUploader sharedUploader] addAsset:self];
}

#pragma mark - Accessors Override
- (UIImage *) thumbnail {
    if ([self alAsset]) {
            CGImageRef image = [[self alAsset] thumbnail];
            return [UIImage imageWithCGImage:image];
    }
    else if([self status] == GCAssetStateFinished) {
        return [self imageForWidth:75 andHeight:75];
    }
    return nil;
}

- (void) setProgress:(CGFloat)aProgress {
    progress = aProgress;
    [[NSNotificationCenter defaultCenter] postNotificationName:GCAssetProgressChanged object:self];
}

- (void) setStatus:(GCAssetStatus)aStatus {
    if (status == GCAssetStateCompleting && aStatus == GCAssetStateFinished) {
        status = aStatus;
        [[NSNotificationCenter defaultCenter] postNotificationName:GCAssetUploadComplete object:self];
    }
    
    status = aStatus;
    [[NSNotificationCenter defaultCenter] postNotificationName:GCAssetStatusChanged object:self];
}

- (NSString*)urlStringForImageWithWidth:(NSUInteger)width andHeight:(NSUInteger)height{
    if ([self status] == GCAssetStateNew)
        return nil;
    
    NSString *urlString = [self objectForKey:@"url"];
    
    if(urlString)
        urlString   = [urlString stringByAppendingFormat:@"/%dx%d",width,height];
    return urlString;
}

- (UIImage *)imageForWidth:(NSUInteger)width andHeight:(NSUInteger)height{
    if ([self alAsset]) {
        UIImage *fullResolutionImage = [UIImage imageWithCGImage:[[[self alAsset] defaultRepresentation] fullResolutionImage] scale:1 orientation:[[[self alAsset] valueForProperty:ALAssetPropertyOrientation] intValue]];
        return [fullResolutionImage imageByScalingProportionallyToSize:CGSizeMake(width, height)];
    }
    
    NSString *urlString = [self urlStringForImageWithWidth:width andHeight:height];
    
    NSURL *url      = [NSURL URLWithString:urlString];
    NSData *data    = [NSData dataWithContentsOfURL:url];
    UIImage *image  = nil;
    
    if(data)
        image = [UIImage imageWithData:data];
    return image;
}

- (void)imageForWidth:(NSUInteger)width 
            andHeight:(NSUInteger)height 
inBackgroundWithCompletion:(void (^)(UIImage *))aResponseBlock {    
    DO_IN_BACKGROUND([self imageForWidth:width andHeight:height], aResponseBlock);
}

- (NSDate*)createdAt{
    if(self.alAsset){
        return [self.alAsset valueForProperty:ALAssetPropertyDate];
    }
    return [super createdAt];
}

#pragma mark - Memory Management
- (id) init {
    self = [super init];
    if (self) {
        [self setStatus:GCAssetStateNew];
    }
    return  self;
}

- (id) initWithDictionary:(NSDictionary *) dictionary {
    self = [super initWithDictionary:dictionary];
    if (self) {
        [self setStatus:GCAssetStateFinished];
    }
    return self;
}

- (void) dealloc {
    [parentID release];
    [alAsset release];
    [super dealloc];
}

#pragma mark - Super Class Methods
+ (NSString *)elementName {
    return @"assets";
}

- (BOOL) isEqual:(id)object {
    if (IS_NULL([self objectID]) && IS_NULL([object objectID])) {
        return [super isEqual:object];
    }
    
    if ([[self objectID] intValue] == [[object objectID] intValue]) {
        return YES;
    }
    return NO;
}

@end
