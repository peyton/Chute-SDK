//
//  ChuteConstants.h
//
//  Copyright 2011 NA. All rights reserved.
//

////////////////////////////////////////////////////////////////////////////////////////////////////////
// Set which service is to be used
// 0 - Facebook
// 1 - Evernote
// 2 - Chute

#define kSERVICE 1

////////////////////////////////////////////////////////////////////////////////////////////////////////

#ifdef DEBUG
#define DLog(fmt, ...) NSLog((@"%s [Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__);
#else
#define DLog(...)
#endif

#define ALog(fmt, ...) NSLog((@"%s [Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__);

#define IS_NULL(x)	((nil == x) || ([x isEqual: [NSNull null]]))

////////////////////////////////////////////////////////////////////////////////////////////////////////

#ifdef DEBUG
#ifdef LOCAL_SERVER
#define API_URL @"http://api.getchute.local:8080/v1/"
#define SERVER_URL @"http://getchute.local:8080"
#else
#define API_URL @"http://api.edge.getchute.com/v1/"
#define SERVER_URL @"http://edge.getchute.com"
#endif
#else
//Please change to Production URL before submitting
#define API_URL @"http://api.edge.getchute.com/v1/"
#define SERVER_URL @"http://edge.getchute.com"
#endif

#define kChutePathVerifyAssets          @"assets/verify"
#define kChuteParcels                   @"parcels"

////////////////////////////////////////////////////////////////////////////////////////////////////////

#define kUDID               [[UIDevice currentDevice] uniqueIdentifier]
#define kDEVICE_NAME        [[UIDevice currentDevice] name]
#define kDEVICE_OS          [[UIDevice currentDevice] systemName]
#define kDEVICE_VERSION     [[UIDevice currentDevice] systemVersion]

#define kOAuthRedirectURL               @"http://getchute.com/oauth/callback"
#define kOAuthRedirectRelativeURL       @"/oauth/callback"
#define kOAuthClientID                  @"4e44f307f3e3bd09ac000001"
#define kOAuthClientSecret              @"d6a9f6b219291ded44e763c22599e0d9f8daeef668898d088ab870d60911f642"
#define kOAuthTokenURL                  @"http://edge.getchute.com/oauth/access_token"