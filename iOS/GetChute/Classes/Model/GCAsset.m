//
//  GCAsset.m
//
//  Created by Brandon Coston on 8/31/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "GCAsset.h"
#import "GCAssetUploader.h"
#import "GC_UIImage+Extras.h"

NSString * const GCAssetStatusChanged   = @"GCAssetStatusChanged";
NSString * const GCAssetProgressChanged = @"GCAssetProgressChanged";

@implementation GCAsset

@synthesize alAsset;
@synthesize thumbnail;
@synthesize selected;
@synthesize progress;
@synthesize status;
@synthesize parentID;

#pragma mark - Heart Method
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
        return [UIImage imageWithCGImage:[[self alAsset] thumbnail]];
    }
    else if([self status] == GCAssetStateFinished) {
        return [self imageForWidth:75 andHeight:75];
    }
    return nil;
}

- (void) setProgress:(CGFloat)aProgress {
    progress = aProgress;
    DLog(@"%f", progress*100);
    [[NSNotificationCenter defaultCenter] postNotificationName:GCAssetProgressChanged object:self];
}

- (void) setStatus:(GCAssetStatus)aStatus {
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

@end
