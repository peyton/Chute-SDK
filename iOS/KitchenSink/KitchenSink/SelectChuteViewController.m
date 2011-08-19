//
//  SelectChuteViewController.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 13/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "SelectChuteViewController.h"

@implementation SelectChuteViewController
@synthesize chuteList;
@synthesize selectedChutesIndex;
@synthesize selectedAssets;

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    
    id obj = [data objectAtIndex:indexPath.row];
    
    if ([selectedChutesIndex indexOfObject:[obj objectForKey:@"id"]] != NSNotFound) {
        cell.accessoryType  = UITableViewCellAccessoryNone;
        [selectedChutesIndex removeObject:[obj objectForKey:@"id"]];
    }
    else {
        cell.accessoryType  = UITableViewCellAccessoryCheckmark;
        [selectedChutesIndex addObject:[obj objectForKey:@"id"]];
    }
    
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

#pragma mark TableView Methods
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [data count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *identifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (nil == cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
    }
    
    id obj = [data objectAtIndex:indexPath.row];
    
    if ([selectedChutesIndex indexOfObject:[obj objectForKey:@"id"]] != NSNotFound) {
        cell.accessoryType  = UITableViewCellAccessoryCheckmark;
    }
    else {
        cell.accessoryType  = UITableViewCellAccessoryNone;
    }
    
    cell.textLabel.text = [obj objectForKey:@"name"];
    return cell;
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

- (void) next {
    [[ChuteAPI shared] startUploadingAssets:selectedAssets forChutes:selectedChutesIndex];
    [self quickAlertWithTitle:@"Uploading" message:@"The file(s) are being uploaded to our servers." button:@"Okay"];
    [self.navigationController popToRootViewControllerAnimated:YES];
}

#pragma mark - View lifecycle

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Next" style:UIBarButtonItemStyleBordered target:self action:@selector(next)];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.selectedChutesIndex = [[NSMutableArray alloc] init];
    
    // Do any additional setup after loading the view from its nib.
    [[ChuteAPI shared] getMyChutesWithResponse:^(NSArray *arr) {
        if (data) {
            [data release], data = nil;
        }
        data = [arr retain];
        [chuteList reloadData];
    } andError:^(NSError *error) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Error!" message:[error localizedDescription] delegate:nil cancelButtonTitle:@"Okay" otherButtonTitles:nil];
        [alert show];
        [alert release];
    }];
}

- (void)viewDidUnload
{
    [self setChuteList:nil];
    [self.selectedChutesIndex release];
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
    [selectedAssets release];
    [chuteList release];
    [super dealloc];
}

@end
