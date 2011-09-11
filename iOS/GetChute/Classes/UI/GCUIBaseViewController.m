//
//  GCUIBaseViewController.m
//
//  Copyright 2011 NA. All rights reserved.
//

#import "GCUIBaseViewController.h"

@implementation GCUIBaseViewController
- (void) showHUD {
    [self showHUDWithTitle:@"Loading..." andOpacity:0.5f];
}

- (void) showHUDWithTitle:(NSString *) title andOpacity:(CGFloat) opacity {
    HUDCount++;
    
	if (!IS_NULL(HUD))
		return;
    
    HUD = [[MBProgressHUD alloc] initWithView:self.view];
	[self.view addSubview:HUD];
	HUD.labelText = title;
    HUD.opacity = opacity;
	[HUD show:YES];
}

- (void) hideHUD {
	if (IS_NULL(HUD))
		return;
    
	HUDCount--;
    
	if (HUDCount > 0) {
        return;
    }
    
	[HUD hide:YES];
	[HUD removeFromSuperview];
	[HUD release], HUD=nil;
}

-(void) quickAlertWithTitle:(NSString *) title 
                    message:(NSString *) message 
                     button:(NSString *) buttonTitle {
	[self quickAlertViewWithTitle:title message:message button:buttonTitle completionBlock:^(void) {} cancelBlock:^(void) {}];
}

- (void)quickAlertViewWithTitle:(NSString *) title 
                        message:(NSString *)message 
                         button:(NSString *)button 
                completionBlock:(void (^)(void))completionBlock 
                    cancelBlock:(void (^)(void))cancelBlock {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message:message delegate:self cancelButtonTitle:button otherButtonTitles:@"Cancel", nil];
    [alert show];
    [alert release];
    alertCompletionBlock = Block_copy(completionBlock);
    alertCancelBlock = Block_copy(cancelBlock);
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    switch (buttonIndex) {
        case 0:
            alertCompletionBlock();
            break;
        case 1:
            alertCancelBlock();
            break;
        default:
            break;
    }
    Block_release(alertCompletionBlock);
    Block_release(alertCancelBlock);
}

@end
