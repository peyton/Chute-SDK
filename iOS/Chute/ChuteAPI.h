//
//  ChuteAPI.h
//
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AssetsLibrary/AssetsLibrary.h>
#import <UIKit/UIKit.h>

typedef void (^ErrorBlock)(NSError *);
typedef void (^ResponseBlock)(id);

extern NSString * const ChuteLoginStatusChanged;

typedef enum {
    ChuteAccountStatusLoggedOut,
    ChuteAccountStatusLoggingIn,
    ChuteAccountStatusLoggedIn,
    ChuteAccountStatusLoginFailed
} ChuteAccountStatus;

@interface ChuteAPI : NSObject {
    BOOL loggedIn;
    ChuteAccountStatus accountStatus;
    NSString *_accessToken;
}

@property (nonatomic) ChuteAccountStatus accountStatus;
@property (nonatomic, retain) NSString *accessToken;

+ (ChuteAPI *)shared;

//Authorization Methods
- (void) verifyAuthorizationWithAccessCode:(NSString *) accessCode 
                                   success:(void (^)(void))successBlock 
                                  andError:(ErrorBlock)errorBlock;

- (void)reset;

//Data Wrappers
//Get Data
- (void)getProfileInfoWithResponse:(ResponseBlock)aResponseBlock
                          andError:(ErrorBlock)anErrorBlock;

- (void)getMyChutesWithResponse:(void (^)(NSArray *))aResponseBlock
                       andError:(ErrorBlock)anErrorBlock;

- (void)getPublicChutesWithResponse:(void (^)(NSArray *))aResponseBlock
                           andError:(ErrorBlock)anErrorBlock;

- (void)getChutesForId:(NSString *)Id 
              response:(void (^)(NSArray *))aResponseBlock 
              andError:(ErrorBlock)anErrorBlock;

- (void)getAssetsForChuteId:(NSUInteger)chuteId
                   response:(void (^)(NSArray *))aResponseBlock 
                   andError:(ErrorBlock)anErrorBlock;

//Post Data
- (void)createChute:(NSString *)name 
         withParent:(NSUInteger)parentId
 withPermissionView:(NSUInteger)permissionView
      andAddMembers:(NSUInteger)addMembers
       andAddPhotos:(NSUInteger)addPhotos
        andResponse:(ResponseBlock)responseBlock 
           andError:(ErrorBlock)errorBlock;

//Helper methods for Asset Uploader
- (void)initThumbnail:(UIImage *)thumbnail
           forAssetId:(NSString *)assetId
          andResponse:(ResponseBlock)aResponseBlock
             andError:(ErrorBlock)anErrorBlock;

- (void)getTokenForAssetId:(NSString *)assetId
               andResponse:(ResponseBlock)aResponseBlock
                  andError:(ErrorBlock)anErrorBlock;

- (void)completeForAssetId:(NSString *)assetId
               andResponse:(ResponseBlock)aResponseBlock
                  andError:(ErrorBlock)anErrorBlock;


- (void) test;

- (void)startUploadingAssets:(NSArray *) assets forChutes:(NSArray *) chutes;

- (void)syncWithResponse:(void (^)(void))aResponseBlock
                andError:(ErrorBlock)anErrorBlock;

- (void)createParcelWithFiles:(NSArray *)filesArray
                    andChutes:(NSArray *)chutesArray
                  andResponse:(ResponseBlock)aResponseBlock
                     andError:(ErrorBlock)anErrorBlock;

@end
