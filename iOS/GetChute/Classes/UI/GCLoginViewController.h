//
//  LoginViewController.h
//
//  Copyright 2011 NA. All rights reserved.
//

#import "GCUIBaseViewController.h"

#define SERVICES_ARRAY [NSArray arrayWithObjects:@"facebook", @"evernote", @"chute", @"twitter", @"foursquare", nil]

@interface GCLoginViewController : GCUIBaseViewController <UIWebViewDelegate> {
    IBOutlet UIButton *loginButton;
}

@property (nonatomic, retain) IBOutlet UIView *authView;
@property (nonatomic, retain) IBOutlet UIWebView *authWebView;

-(IBAction) login;

+(void)presentInController:(UIViewController *)controller;

@end
