//
//  GCCommentComponent.m
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 9/8/11.
//  Copyright 2011 Chute Corporation. All rights reserved.
//

#import "GCCommentComponent.h"

#define kOFFSET_FOR_KEYBOARD 85.0

@interface GCCommentComponent (Private)
-(UIView*)viewForComment:(GCComment*)comment;
@end

@implementation GCCommentComponent
@synthesize comments, asset, chute;

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}

-(UIView*)viewForComment:(GCComment *)comment{
    UIView *v = [[UIView alloc] initWithFrame:CGRectMake(2, 2, commentTable.frame.size.width-4, [self tableView:commentTable heightForRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]]-4)];
    [v setClipsToBounds:YES];
    [v setBackgroundColor:[UIColor whiteColor]];
    UIImageView *iv = [[UIImageView alloc] initWithFrame:CGRectMake(2, 2, v.frame.size.height-4, v.frame.size.height-4)];
    GCUser *u = [comment user];
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(iv.frame.size.width+7, 0, v.frame.size.width-iv.frame.size.width-11, v.frame.size.height)];
    [label setText:[NSString stringWithFormat:@"%@",[comment objectForKey:@"comment"]]];
    [label setNumberOfLines:3];
    [label setFont:[UIFont systemFontOfSize:15]];
    [v addSubview:label];
    [label release];
    NSString *avatarURLString = [u avatarURL];
    DO_IN_BACKGROUND([NSData dataWithContentsOfURL:[NSURL URLWithString:avatarURLString]], ^(NSData* response){
        [iv setImage:[UIImage imageWithData:response]];
        [v addSubview:iv];
        [iv release];
    });
    return [v autorelease];
}

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

-(void)prepareComments{
    if(![self asset])
        return;
    if(![self chute])
        return;
    
    [asset setParentID:[chute objectID]];
    
    [asset commentsInBackgroundWithCompletion:^(GCResponse *response) {
        if ([response isSuccessful]) {
            [self setComments:[response object]];
            [commentTable reloadData];
        }
        else {
            NSLog(@"Error getting inbox: %@",[[response error] localizedDescription]); 
        }
    }];
}

-(IBAction)postComment{
    [commentTV resignFirstResponder];
    if([@"" isEqualToString:commentTV.text])
        return;
    if(![self asset])
        return;
    if(![self chute])
        return;
    [self showHUDWithTitle:@"Posting Comment" andOpacity:1];
    
    [asset setParentID:[chute objectID]];
    
    [asset addComment:commentTV.text inBackgroundWithCompletion:^(GCResponse *response) {
        [self hideHUD];
        if ([response isSuccessful]) {
            [self prepareComments];
        }
        else {
            NSLog(@"Error posting comment:%@",[[response error] localizedDescription]);
        }
    }];
}
-(IBAction)hideKeyboard:(id)sender{
    [commentTV resignFirstResponder];
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    [commentTable setAllowsSelection:NO];
    [commentTable setDelegate:self];
    [commentTable setDataSource:self];
    [commentTable setBackgroundColor:[UIColor clearColor]];
    [commentTable setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    if(!commentTV){
        commentTV = [[UITextField alloc] init];
    }
    [commentTV setDelegate:self];
    [commentTV setClearButtonMode:UITextFieldViewModeAlways];
    [commentTV setReturnKeyType:UIReturnKeyDone];
    if(!addCommentButton){
        addCommentButton = [[UIButton alloc] init];
    }
    [addCommentButton addTarget:self action:@selector(postComment) forControlEvents:UIControlEventTouchUpInside];
    [self prepareComments];
    // Do any additional setup after loading the view from its nib.
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self prepareComments];
}


-(void)viewWillDisappear:(BOOL)animated{
    [super viewWillDisappear:animated];
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

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    // Return the number of sections.
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    // Return the number of rows in the section.
    if(![self comments])
        return 0;
    return [[self comments] count];
}


// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
		
        cell = [[[UITableViewCell alloc] init] autorelease];
    }
    else{
        for(UIView *content in [cell.contentView subviews])
            [content removeFromSuperview];
    }
    [cell.contentView addSubview:[self viewForComment:[[self comments] objectAtIndex:indexPath.row]]];
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
	
	return 65;
}

- (void) dealloc {
    [comments release];
    [asset release];
    [chute release];
    [super dealloc];
}

@end