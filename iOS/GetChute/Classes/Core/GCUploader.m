//
//  GCUploader.m
//
//  Created by Achal Aggarwal on 09/09/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "GCUploader.h"

static GCUploader *sharedUploader = nil;

@interface GCUploader()
- (void) processQueue;
@end

@implementation GCUploader

@synthesize queue;

- (void) parcelCompleted {
    DLog(@"Parcel Completed");
    if ([self.queue count] > 0) {
        [self.queue removeObjectAtIndex:0];
    }
    [self processQueue];
}

- (void) processQueue {
    dispatch_async(dispatch_get_main_queue(), ^(void) {
        if ([self.queue count] > 0) {
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
