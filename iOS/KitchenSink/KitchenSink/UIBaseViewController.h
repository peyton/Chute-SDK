//
//  UIBaseViewController.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 09/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIBaseViewController : UIViewController <UIAlertViewDelegate> {
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
