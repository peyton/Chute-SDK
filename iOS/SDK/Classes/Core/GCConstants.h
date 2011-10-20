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

#define kSERVICE 0

////////////////////////////////////////////////////////////////////////////////////////////////////////

#define API_URL @"http://api.getchute.com/v1/"
#define SERVER_URL @"http://getchute.com"

#define kChutePathVerifyAssets          @"assets/verify"
#define kChuteParcels                   @"parcels"

////////////////////////////////////////////////////////////////////////////////////////////////////////

#define kUDID               [[UIDevice currentDevice] uniqueIdentifier]
#define kDEVICE_NAME        [[UIDevice currentDevice] name]
#define kDEVICE_OS          [[UIDevice currentDevice] systemName]
#define kDEVICE_VERSION     [[UIDevice currentDevice] systemVersion]

//#error Please remove this line after changing the client id and client secret
#define kOAuthRedirectURL               @"http://sharedroll.com/oauth/callback"
#define kOAuthRedirectRelativeURL       @"/oauth/callback"
#define kOAuthClientID                  @"4e79ff9e32fc725e5b000001"
#define kOAuthClientSecret              @"e8036d54e98682a3a2e02562cbc4f77bb922e8bb6cfd13264fe00b5e3b565693"

#define kOAuthPermissions               @"all_resources manage_resources profile resources"

#define kOAuthTokenURL                  @"http://getchute.com/oauth/access_token"