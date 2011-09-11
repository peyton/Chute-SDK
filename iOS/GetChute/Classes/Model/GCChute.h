//
//  GCChute.h
//
//  Created by Achal Aggarwal on 01/09/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "GCResource.h"

typedef enum {
    GCPermissionTypePrivate = 0,
    GCPermissionTypeMembers,
    GCPermissionTypePublic,
    GCPermissionTypeFriends
} GCPermissionType;

@interface GCChute : GCResource

@property (nonatomic, readonly) NSUInteger assetsCount;
@property (nonatomic, readonly) NSUInteger contributersCount;
@property (nonatomic, readonly) NSUInteger membersCount;

@property (nonatomic, assign) GCPermissionType moderateComments;
@property (nonatomic, assign) GCPermissionType moderateMembers;
@property (nonatomic, assign) GCPermissionType moderatePhotos;

@property (nonatomic, assign) NSString *name;

@property (nonatomic, assign) GCPermissionType permissionAddComments;
@property (nonatomic, assign) GCPermissionType permissionAddMembers;
@property (nonatomic, assign) GCPermissionType permissionAddPhotos;
@property (nonatomic, assign) GCPermissionType permissionView;

@property (nonatomic, readonly) NSUInteger recentCount;
@property (nonatomic, readonly) NSUInteger recentParcelId;

@property (nonatomic, readonly) NSString *recentThumbnailUrl;

@property (nonatomic, readonly) NSUInteger recentUserId;

@property (nonatomic, readonly) NSString *shortcut;

- (GCResponse *) assets;
- (void) assetsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;

- (GCResponse *) contributors;
- (void) contributorsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;

- (GCResponse *) members;
- (void) membersInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;

- (BOOL) join;
- (void) joinInBackgroundWithBOOLCompletion:(GCBoolBlock) aResponseBlock;

+ (GCResponse *)allPublic;
+ (void)allPublicInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;

@end
