//
//  GCUploader.m
//
//  Created by Achal Aggarwal on 09/09/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "GCUploader.h"

static GCUploader *sharedUploader = nil;

NSString * const GCUploaderProgressChanged = @"GCUploaderProgressChanged";
NSString * const GCUploaderFinished = @"GCUploaderFinished";

@interface GCUploader()
- (void) processQueue;
@end

@implementation GCUploader

@synthesize queue;
@synthesize progress;

- (void) updateProgress:(NSNotification *) notification {
    float total = 0.0;
    int totalAssets = 0;
    for (GCParcel *_parcel in queue) {
        totalAssets += [_parcel assetCount];
        if (_parcel == [queue objectAtIndex:0]) {
            //calculate asset progress
            for (GCAsset *_asset in [_parcel assets]) {
                total += [_asset progress]; 
            }
            total += [_parcel completedAssetCount];
        }
    }
    [self setProgress:total/totalAssets];
}

- (void) setProgress:(CGFloat)aProgress {
    progress = aProgress;
    [[NSNotificationCenter defaultCenter] postNotificationName:GCUploaderProgressChanged object:nil];
}

- (void) parcelCompleted {
    if ([self.queue count] > 0) {
        [self.queue removeObjectAtIndex:0];
    }
    
    if ([[self queue] count] == 0) {
        [[NSNotificationCenter defaultCenter] postNotificationName:GCUploaderFinished object:nil];
    }
    
    [self processQueue];
}

- (void) processQueue {
    dispatch_async(dispatch_get_main_queue(), ^(void) {
        if ([[self queue] count] > 0) {
            GCParcel *_parcel = [self.queue objectAtIndex:0];
            [_parcel startUploadWithTarget:self andSelector:@selector(parcelCompleted)];
        }
    });
}

- (void) addParcel:(GCParcel *) _parcel {
    [self.queue addObject:_parcel];
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^(void) {
        [self processQueue];
    });
}

- (void) removeParcel:(GCParcel *) _parcel {
    [self.queue removeObject:_parcel];
    [self processQueue];
}

#pragma mark - Methods for Singleton class
+ (GCUploader *)sharedUploader
{
    if (sharedUploader == nil) {
        static dispatch_once_t onceToken;
        dispatch_once(&onceToken, ^{
            sharedUploader = [[super allocWithZone:NULL] init];
        });
    }
    return sharedUploader;
}

+ (id)allocWithZone:(NSZone *)zone
{
    return [[self sharedUploader] retain];
}

- (id) init {
    self = [super init];
    if (self) {
        self.queue = [[NSMutableArray alloc] init];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateProgress:) name:GCAssetProgressChanged object:nil];
    }
    return self;
}

- (id)copyWithZone:(NSZone *)zone
{
    return self;
}

- (id)retain
{
    return self;
}

- (NSUInteger)retainCount
{
    return NSUIntegerMax;
}

- (oneway void)release;
{
    //nothing
}

- (id)autorelease
{
    return self;
}

- (void) dealloc {
    [self.queue release];
    [super dealloc];
}

@end
