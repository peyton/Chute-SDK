//
//  KitchenSinkAppDelegate.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 08/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "KitchenSinkAppDelegate.h"

@implementation KitchenSinkAppDelegate

@synthesize window = _window;
@synthesize navigationController = _navigationController;

@synthesize consoleView;
@synthesize consoleTextView;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    // Add the navigation controller's view to the window and display.
    self.window.rootViewController = self.navigationController;
    [self.window makeKeyAndVisible];
    
    //Add Console and Transform
    [self.window addSubview:consoleView];
    [consoleTextView setText:@"Chute Console\n------------------------"];
    [consoleView setTransform:CGAffineTransformMakeTranslation(0, -460)];
    
    __block UIView *bConsoleView            = consoleView;
    __block UITextView *bConsoleTextView    = consoleTextView;
    
    //Show Console
    [[NSNotificationCenter defaultCenter] addObserverForName:@"ShowConsole" object:nil queue:nil usingBlock:^(NSNotification *notification) {
        [UIView animateWithDuration:0.5f animations:^(void) {
            [bConsoleView setTransform:CGAffineTransformIdentity];
        }];
    }];
    
    //Update Console
    [[NSNotificationCenter defaultCenter] addObserverForName:@"UpdateConsole" object:nil queue:nil usingBlock:^(NSNotification *notification) {
        NSString *data = (NSString *)[notification object];
        [bConsoleTextView setText:[bConsoleTextView.text stringByAppendingFormat:@"\n\n %@> %@", [[NSDate date] description], data]];  
        DLog(@"%@", data);
        //scroll to bottom
        [bConsoleTextView scrollRangeToVisible:NSMakeRange([bConsoleTextView.text length], 0)];
    }];
    return YES;
}

- (IBAction)clearConsole:(id)sender {
    [consoleTextView setText:@"Chute Console\n------------------------"];
}

- (IBAction)hideConsole:(id)sender {
    [UIView animateWithDuration:0.5f animations:^(void) {
        [consoleView setTransform:CGAffineTransformMakeTranslation(0, -460)];
    }];
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
}

- (void)dealloc
{
    [consoleView release];
    [consoleTextView release];
    [_window release];
    [_navigationController release];
    [super dealloc];
}

@end
