//
//  KitchenSinkAppDelegate.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 08/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface KitchenSinkAppDelegate : NSObject <UIApplicationDelegate>

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet UINavigationController *navigationController;

@property (nonatomic, retain) IBOutlet UIView *consoleView;
@property (nonatomic, retain) IBOutlet UITextView *consoleTextView;

- (IBAction)hideConsole:(id)sender;
- (IBAction)clearConsole:(id)sender;

@end
