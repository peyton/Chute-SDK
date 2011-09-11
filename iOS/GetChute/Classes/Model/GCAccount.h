//
//  GCAccount.h
//
//  Created by Achal Aggarwal on 30/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GCRequest.h"
#import "SBJson.h"
#import "GCConstants.h"
#import "ASIHTTPRequest.h"
#import "NSDictionary+QueryString.h"
#import "GCAsset.h"
#import <AssetsLibrary/AssetsLibrary.h>
#import "GCParcel.h"

//Notification which is fired whenever the Account Status is changed
NSString * const GCAccountStatusChanged;

typedef enum {
    GCAccountLoggedOut,
    GCAccountLoggingIn,
    GCAccountLoggedIn,
    GCAccountLoginFailed
} GCAccountStatus;

@interface GCAccount : NSObject {
    GCAccountStatus accountStatus;
    NSString *_accessToken;
    NSMutableArray *assetsArray;
}

@property (nonatomic) GCAccountStatus accountStatus;
@property (nonatomic, retain) NSString *accessToken;
@property (nonatomic, retain) NSMutableArray *assetsArray;

+ (GCAccount *)sharedManager;

- (void)loadAssets;
- (void)loadAssetsCompletionBlock:(void (^)(void))aCompletionBlock;

- (void) verifyAuthorizationWithAccessCode:(NSString *) accessCode 
                                   success:(GCBasicBlock)successBlock 
                                  andError:(GCErrorBlock)errorBlock;
- (void)reset;

- (void)getProfileInfoWithResponse:(GCResponseBlock)aResponseBlock;

- (GCResponse *) getMyMetaData;
- (void) getMyMetaDataInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;

- (BOOL) setMyMetaData:(NSDictionary *) metaData;
- (void) setMyMetaData:(NSDictionary *) metaData inBackgroundWithCompletion:(GCBoolBlock) aBoolBlock;

- (GCResponse *) getInboxParcels;
- (void) getInboxParcelsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;

@end
