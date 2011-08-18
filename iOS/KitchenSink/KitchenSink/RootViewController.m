//
//  RootViewController.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 08/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "RootViewController.h"
#import "ChuteBaseViewController.h"
#import "PhotosBaseViewController.h"
#import "BundlesBaseViewController.h"

@implementation RootViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [ChuteLoginViewController presentInController:self];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)chute:(id)sender {
    ChuteBaseViewController *chuteBaseViewController = [[ChuteBaseViewController alloc] init];
    [chuteBaseViewController setTitle:@"Chutes"];
    [self.navigationController pushViewController:chuteBaseViewController animated:YES];
    [chuteBaseViewController release];
}

- (IBAction)photos:(id)sender {
    PhotosBaseViewController *photosBaseViewController = [[PhotosBaseViewController alloc] init];
    [self.navigationController pushViewController:photosBaseViewController animated:YES];
    [photosBaseViewController setTitle:@"Photos"];
    [photosBaseViewController release];
}

- (IBAction)bundles:(id)sender {
    BundlesBaseViewController *bundlesBaseViewController = [[BundlesBaseViewController alloc] init];
    [self.navigationController pushViewController:bundlesBaseViewController animated:YES];
    [bundlesBaseViewController setTitle:@"Bundles"];
    [bundlesBaseViewController release];
}

- (IBAction)sync:(id)sender {
    [[ChuteAPI shared] syncWithResponse:^(void) {
        DLog(@"synced");
    } andError:^(NSError *error) {
        DLog(@"%@", [error localizedDescription]);
    }];
}
@end
