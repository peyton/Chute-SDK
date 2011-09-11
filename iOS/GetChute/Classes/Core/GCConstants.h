//
//  GCConstants.h
//
//  Copyright 2011 NA. All rights reserved.
//

////////////////////////////////////////////////////////////////////////////////////////////////////////
// Set which service is to be used
// 0 - Facebook
// 1 - Evernote
// 2 - Chute
// 3 - Twitter
// 4 - Foursquare

#define kSERVICE 3

////////////////////////////////////////////////////////////////////////////////////////////////////////

#define API_URL @"http://api.developer.getchute.com/v1/"
#define SERVER_URL @"http://developer.getchute.com"

#define kChutePathVerifyAssets          @"assets/verify"
#define kChuteParcels                   @"parcels"

////////////////////////////////////////////////////////////////////////////////////////////////////////

#define kUDID               [[UIDevice currentDevice] uniqueIdentifier]
#define kDEVICE_NAME        [[UIDevice currentDevice] name]
#define kDEVICE_OS          [[UIDevice currentDevice] systemName]
#define kDEVICE_VERSION     [[UIDevice currentDevice] systemVersion]

//#error Please remove this line after changing the client id and client secret
#define kOAuthRedirectURL               @"http://getchute.com/oauth/callback"
#define kOAuthRedirectRelativeURL       @"/oauth/callback"
#define kOAuthClientID                  @"4e6a2cc4f3e3bd0bcb000001"
#define kOAuthClientSecret              @"a744516290995009e2aaaf308085cc4e07a6b53057f4e90e8f108df6b8becd4b"

#define kOAuthPermissions               @"resources profile manage_resources all_resources services"

#define kOAuthTokenURL                  @"http://developer.getchute.com/oauth/access_token"