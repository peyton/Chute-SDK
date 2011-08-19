//
//  AssetManager.h
//  ChuteSDK
//
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AssetsLibrary/AssetsLibrary.h>
#import <UIKit/UIKit.h>

#import "ChuteConstants.h"

extern NSString * const ChuteAssetManagerAssetsAdded;

@class ChuteAsset;

@interface ChuteAssetManager : NSObject {    
    NSMutableArray *assetsArray;
    NSMutableArray *uploads;
}

@property (nonatomic, retain) NSMutableArray *assetsArray;
@property (nonatomic, readonly) NSMutableArray *uploadingAssets;

@property(copy) void (^responseBlock)(void);
@property(copy) void (^errorBlock)(NSError *);



+ (ChuteAssetManager*)shared;
- (void)loadAssets;
- (void)loadAssetsCompletionBlock:(void (^)(void))aCompletionBlock;

- (void)startUploadingAssets:(NSArray *) assets forChutes:(NSArray *) chutes;
- (ChuteAsset *)assetForURL:(NSString *)url;

- (void)syncWithResponse:(void (^)(void))aResponseBlock
                andError:(void (^)(NSError *))anErrorBlock;
@end
