//
//  LoginViewController.h
//
//  Copyright 2011 NA. All rights reserved.
//

#import "ChuteUIBaseViewController.h"

#define SERVICES_ARRAY [NSArray arrayWithObjects:@"facebook", @"evernote", @"chute", nil]

@interface ChuteLoginViewController : ChuteUIBaseViewController <UIWebViewDelegate> {
    IBOutlet UIButton *loginButton;
}

@property (nonatomic, retain) IBOutlet UIView *authView;
@property (nonatomic, retain) IBOutlet UIWebView *authWebView;

-(IBAction) login;

+(void)presentInController:(UIViewController *)controller;

@end
