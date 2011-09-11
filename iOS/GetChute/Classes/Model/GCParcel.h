//
//  GCParcel.h
//
//  Created by Achal Aggarwal on 09/09/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "GCResource.h"

NSString * const GCParcelFinishedUploading;

typedef enum {
    GCParcelStatusNew = 0,
    GCParcelStatusUploading = 1,
    GCParcelStatusDone
} GCParcelStatus;

@interface GCParcel : GCResource

@property (nonatomic, assign) GCParcelStatus status;
@property (nonatomic, retain) NSMutableArray *assets;
@property (nonatomic, readonly) NSArray *chutes;

@property (nonatomic, assign) id<NSObject> delegate;
@property (nonatomic, assign) SEL completionSelector;

+ (id) objectWithAssets:(NSArray *) _assets andChutes:(NSArray *) _chutes;
- (void) startUploadWithTarget:(id)_target andSelector:(SEL)_selector;

@end
