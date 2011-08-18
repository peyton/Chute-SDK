//
//  ChuteBaseViewController.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 08/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "ChuteBaseViewController.h"
#import "CreateChuteViewController.h"
#import "ListChutesViewController.h"

@implementation ChuteBaseViewController

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

- (IBAction)createChute:(id)sender {
    CreateChuteViewController *createChuteViewController = [[CreateChuteViewController alloc] init];
    [self.navigationController pushViewController:createChuteViewController animated:YES];
    [createChuteViewController setTitle:@"Create Chute"];
    [createChuteViewController release];
}

- (IBAction)browseChutes:(id)sender {
    ListChutesViewController *listChutesViewController = [[ListChutesViewController alloc] init];
    [self.navigationController pushViewController:listChutesViewController animated:YES];
    [listChutesViewController setTitle:@"Chutes"];
    [listChutesViewController release];
}
@end
