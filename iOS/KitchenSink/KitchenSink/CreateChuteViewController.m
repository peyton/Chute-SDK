//
//  CreateChuteViewController.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 08/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "CreateChuteViewController.h"

@implementation CreateChuteViewController
@synthesize chuteName;
@synthesize permission;
@synthesize membersButton;
@synthesize photosButton;

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
    [self setChuteName:nil];
    [self setPermission:nil];
    [self setMembersButton:nil];
    [self setPhotosButton:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)dealloc {
    [chuteName release];
    [permission release];
    [membersButton release];
    [photosButton release];
    [super dealloc];
}
- (IBAction)toggle:(id)sender {
    [sender setSelected:![sender isSelected]];
}

- (IBAction)save:(id)sender {
    [chuteName resignFirstResponder];
    __block typeof(self) bself = self;
    
    
    GCChute *_newChute = [GCChute new];
    [_newChute setName:chuteName.text];
    
    //[_newChute setPermissionView:GCPermissionTypePublic];
    [_newChute setPermissionView:permission.selectedSegmentIndex];
    [_newChute setPermissionAddMembers:membersButton.selected?1:0];
    [_newChute setPermissionAddPhotos:photosButton.selected?1:0];
    
    [_newChute saveInBackgroundWithCompletion:^(BOOL value, NSError *error) {
        if (value) {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Chute Create" message:@"New chute has been created!" delegate:nil cancelButtonTitle:@"Okay" otherButtonTitles:nil];
            [alert show];
            [alert release];
            [bself.navigationController popViewControllerAnimated:YES];

        }
        else {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Error!" message:[error localizedDescription] delegate:nil cancelButtonTitle:@"Okay" otherButtonTitles:nil];
            [alert show];
            [alert release];

        }
    }];
}
@end
