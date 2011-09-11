//
//  GCChute.m
//
//  Created by Achal Aggarwal on 01/09/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "GCChute.h"
#import "GCAsset.h"
#import "GCUser.h"
#import "GCParcel.h"

@implementation GCChute


@synthesize assetsCount;
@synthesize contributersCount;
@synthesize membersCount;

@synthesize moderateComments;
@synthesize moderateMembers;
@synthesize moderatePhotos;

@synthesize name;

@synthesize permissionAddComments;
@synthesize permissionAddMembers;
@synthesize permissionAddPhotos;
@synthesize permissionView;

@synthesize recentCount;
@synthesize recentParcelId;

@synthesize recentThumbnailUrl;

@synthesize recentUserId;

@synthesize shortcut;

#pragma mark - Assets

- (GCResponse *) assets {
    NSString *_path         = [[NSString alloc] initWithFormat:@"%@%@/%@/assets", API_URL, [[self class] elementName], [self objectID]];
    GCRequest *gcRequest    = [[GCRequest alloc] init];
    GCResponse *_response   = [[gcRequest getRequestWithPath:_path] retain];
    
    NSMutableArray *_assetsArray = [[NSMutableArray alloc] init];
    for (NSDictionary *_dic in [_response data]) {
        GCAsset *_asset = [[GCAsset objectWithDictionary:_dic] retain];
        [_asset setParentID:[self objectID]];
        [_assetsArray addObject:_asset];
        [_asset release];
    }
    
    [_response setObject:_assetsArray];
    [_assetsArray release];
    
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

- (void) assetsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock {
    DO_IN_BACKGROUND([self assets], aResponseBlock);
}

- (GCResponse *) contributors {
    NSString *_path         = [[NSString alloc] initWithFormat:@"%@%@/%@/contributors", API_URL, [[self class] elementName], [self objectID]];
    GCRequest *gcRequest    = [[GCRequest alloc] init];
    GCResponse *_response   = [[gcRequest getRequestWithPath:_path] retain];
    
    NSMutableArray *_contributorsArray = [[NSMutableArray alloc] init];
    for (NSDictionary *_dic in [_response data]) {
        [_contributorsArray addObject:[GCUser objectWithDictionary:_dic]];
    }
    
    [_response setObject:_contributorsArray];
    [_contributorsArray release];
    
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

- (void) contributorsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock {
    DO_IN_BACKGROUND([self contributors], aResponseBlock);
}

- (GCResponse *) members {
    NSString *_path         = [[NSString alloc] initWithFormat:@"%@%@/%@/members", API_URL, [[self class] elementName], [self objectID]];
    GCRequest *gcRequest    = [[GCRequest alloc] init];
    GCResponse *_response   = [[gcRequest getRequestWithPath:_path] retain];
    
    NSMutableArray *_membersArray = [[NSMutableArray alloc] init];
    for (NSDictionary *_dic in [_response data]) {
        [_membersArray addObject:[GCUser objectWithDictionary:_dic]];
    }
    
    [_response setObject:_membersArray];
    [_membersArray release];
    
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

- (void) membersInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock {
    DO_IN_BACKGROUND([self members], aResponseBlock);
}

- (BOOL) join{
    if(!self.objectID)
        return NO;
    NSString *_path             = [[NSString alloc] initWithFormat:@"%@%@/%@/join", API_URL, [[self class] elementName], [self objectID]];
    
    GCRequest *gcRequest        = [[GCRequest alloc] init];
    GCResponse *response        = [gcRequest getRequestWithPath:_path];
    BOOL _response              = [response isSuccessful];
    [gcRequest release];
    [_path release];
    return _response;
}

- (void) joinInBackgroundWithBOOLCompletion:(GCBoolBlock) aResponseBlock{
    DO_IN_BACKGROUND_BOOL([self join], aResponseBlock);
}

#pragma mark - Accessors Override
- (NSUInteger)assetsCount
{
    return [[self objectForKey:@"assets_count"] intValue];
}

- (NSUInteger)contributersCount
{
    return [[self objectForKey:@"contributers_count"] intValue];
}

- (NSUInteger)membersCount
{
    return [[self objectForKey:@"members_count"] intValue];
}

- (GCPermissionType)moderateComments
{
    return [[self objectForKey:@"moderate_comments"] intValue];
}

- (void)setModerateComments:(GCPermissionType)aModerateComments
{
    [self setObject:[NSString stringWithFormat:@"%d", aModerateComments] forKey:@"moderate_comments"];
}

- (GCPermissionType)moderateMembers
{
    return [[self objectForKey:@"moderate_members"] intValue];
}
- (void)setModerateMembers:(GCPermissionType)aModerateMembers
{
    [self setObject:[NSString stringWithFormat:@"%d", aModerateMembers] forKey:@"moderate_members"];
}

- (GCPermissionType)moderatePhotos
{
    return [[self objectForKey:@"moderate_photos"] intValue];
}
- (void)setModeratePhotos:(GCPermissionType)aModeratePhotos
{
    [self setObject:[NSString stringWithFormat:@"%d", aModeratePhotos] forKey:@"moderate_photos"];
}

- (NSString *)name
{
    return [[[self objectForKey:@"name"] retain] autorelease]; 
}
- (void)setName:(NSString *)aName
{
    [aName retain];
    [self setObject:aName forKey:@"name"];
    [aName release];
}

- (GCPermissionType)permissionAddComments
{
    return [[self objectForKey:@"permission_add_comments"] intValue];
}
- (void)setPermissionAddComments:(GCPermissionType)aPermissionAddComments
{
    [self setObject:[NSString stringWithFormat:@"%d", aPermissionAddComments] forKey:@"permission_add_comments"];
}

- (GCPermissionType)permissionAddMembers
{
    return [[self objectForKey:@"permission_add_members"] intValue];
}
- (void)setPermissionAddMembers:(GCPermissionType)aPermissionAddMembers
{
    [self setObject:[NSString stringWithFormat:@"%d", aPermissionAddMembers] forKey:@"permission_add_members"];
}

- (GCPermissionType)permissionAddPhotos
{
    return [[self objectForKey:@"permission_add_photos"] intValue];
}
- (void)setPermissionAddPhotos:(GCPermissionType)aPermissionAddPhotos
{
    [self setObject:[NSString stringWithFormat:@"%d", aPermissionAddPhotos] forKey:@"permission_add_photos"];
}

- (GCPermissionType)permissionView
{
    return [[self objectForKey:@"permission_view"] intValue];
}
- (void)setPermissionView:(GCPermissionType)aPermissionView
{
    [self setObject:[NSString stringWithFormat:@"%d", aPermissionView] forKey:@"permission_view"];
}

- (NSUInteger)recentCount
{
    return [[self objectForKey:@"recent_count"] intValue];
}

- (NSUInteger)recentParcelId
{
    return [[self objectForKey:@"recent_parcel_id"] intValue];
}

- (NSString *)recentThumbnailUrl
{
    return [[[self objectForKey:@"recent_thumbnail_url"] retain] autorelease];
}

- (NSUInteger)recentUserId
{
    return [[self objectForKey:@"recent_user_id"] intValue];
}

- (NSString *)shortcut
{
    return [[[self objectForKey:@"shortcut"] retain] autorelease];
}


#pragma mark - JSON Representation Method
- (id)proxyForJson {
    NSMutableDictionary *_temp = [[[NSMutableDictionary alloc] init] autorelease];
    for (NSString *key in [[self content] allKeys]) {
        if ([key isEqualToString:@"user"])
            continue;
        [_temp setObject:[[self content] objectForKey:key] forKey:key];
    }
    return _temp;
}

+ (id) new {
    NSString *_base = @"{\"name\":\"Untitled\",\"permission_view\":0,\"permission_add_members\":0,\"permission_add_photos\":0,\"permission_add_comments\":0,\"moderate_members\":false,\"moderate_photos\":false,\"moderate_comments\":false}";
    return [self objectWithDictionary:[_base JSONValue]];
}

#pragma mark - Super Class Methods
+ (NSString *)elementName {
    return @"chutes";
}

#pragma mark - Public Chutes
+ (GCResponse *)allPublic {
    NSString *_path         = [[NSString alloc] initWithFormat:@"%@/public/%@", API_URL, [self elementName]];
    GCRequest *gcRequest    = [[GCRequest alloc] init];
    GCResponse *_response   = [[gcRequest getRequestWithPath:_path] retain];
    
    NSMutableArray *_result = [[NSMutableArray alloc] init];
    for (NSDictionary *_dic in [_response object]) {
        id _obj = [self objectWithDictionary:_dic];
        [_result addObject:_obj];
    }
    [_response setObject:_result];
    [_result release];
    [gcRequest release];
    [_path release];
    return [_response autorelease];
}

+ (void)allPublicInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock {      
    DO_IN_BACKGROUND([self allPublic], aResponseBlock);
}


@end
