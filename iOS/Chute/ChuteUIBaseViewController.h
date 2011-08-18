//
//  UIBaseViewController.h
//
//  Copyright 2011 NA. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AssetsLibrary/AssetsLibrary.h>
#import <UIKit/UIKit.h>
#import "MBProgressHUD.h"
#import "SBJson.h"
#import "ChuteConstants.h"
#import "ASIHTTPRequest.h"
#import "ChuteAPI.h"
#import "NSDictionary+QueryString.h"
#import "ChuteUIBaseViewController.h"

@interface ChuteUIBaseViewController : UIViewController <UIAlertViewDelegate> {
    MBProgressHUD *HUD;
    NSUInteger HUDCount;
    
    void (^alertCompletionBlock)(void);
    void (^alertCancelBlock)(void);
}

- (void) showHUD;
- (void) showHUDWithTitle:(NSString *) title andOpacity:(CGFloat) opacity;
- (void) hideHUD;
- (void) quickAlertWithTitle:(NSString *) title message:(NSString *) message button:(NSString *) buttonTitle;
- (void)quickAlertViewWithTitle:(NSString *) title message:(NSString *)message button:(NSString *)button completionBlock:(void (^)(void))completionBlock cancelBlock:(void (^)(void))cancelBlock;

@end
