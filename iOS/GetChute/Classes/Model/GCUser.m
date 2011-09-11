//
//  GCUser.m
//
//  Created by Achal Aggarwal on 07/09/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "GCUser.h"

@implementation GCUser

@synthesize name;
@synthesize avatarURL;

#pragma mark - Accessor Methods
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

- (NSString *)avatarURL
{
    return [[[self objectForKey:@"avatar"] retain] autorelease]; 
}

- (void)setAvatarURL:(NSString *)anAvatarURL
{
    [anAvatarURL retain];
    [self setObject:anAvatarURL forKey:@"avatar"];
    [anAvatarURL release];
}

@end
