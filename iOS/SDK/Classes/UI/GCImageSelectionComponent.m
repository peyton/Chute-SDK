//
//  GCImageSelectionComponent.m
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 9/7/11.
//  Copyright 2011 Chute Corporation. All rights reserved.
//

#import "GCImageSelectionComponent.h"
#import "GCAsset.h"

@interface GCImageSelectionComponent (Private)
-(UIView*)viewForIndexPath:(NSIndexPath*)indexPath;
@end

@implementation GCImageSelectionComponent
@synthesize images, selectedIndicator;

-(NSArray*)selectedImages{
    return [selected allObjects];
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

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    [imageTable setAllowsSelection:NO];
    [imageTable setDelegate:self];
    [imageTable setDataSource:self];
    [imageTable setBackgroundColor:[UIColor clearColor]];
    [imageTable setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    selected = [[NSMutableSet alloc] init];
    [self.view setBackgroundColor:[UIColor blackColor]];
    if(!selectedIndicator){
        UIImageView *temp = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 20, 20)];
        UIGraphicsBeginImageContext(CGSizeMake(20, 20));
        [[UIColor greenColor] setFill];
        [[UIColor blackColor] setStroke];
        [@"\u2713" drawInRect:temp.frame withFont:[UIFont systemFontOfSize:20]];
        UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        [temp setImage:image];
        [temp setBackgroundColor:[UIColor whiteColor]];
        [self setSelectedIndicator:temp];
        [temp release];
    }
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

-(void)dealloc{
    [selected release];
    [images release];
    [selectedIndicator release];
    [super dealloc];
}

-(void)objectTappedWithGesture:(UIGestureRecognizer*)gesture{
    UIImageView *view = (UIImageView*)[gesture view];
    GCAsset *asset = [[self images] objectAtIndex:[view tag]];
    if(![selected containsObject:asset]){
        UIImageView *v = [[UIImageView alloc] initWithImage:self.selectedIndicator.image];
        [v setBackgroundColor:[UIColor whiteColor]];
        [v setFrame:CGRectMake(57, 57, 20, 20)];
        [view addSubview:v];
        [v release];
        [selected addObject:asset];
    }
    else{
        for(UIImageView *v in view.subviews){
            [v removeFromSuperview];
        }
        [selected removeObject:asset];
    }
}

#pragma mark UITableViewDataSource Delegate Methods

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    // Return the number of sections.
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    // Return the number of rows in the section.
	if(!images)
		return 0;
    return ceil([images count]/4.0);
}


// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
		
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    for(UIView *v in cell.contentView.subviews){
        [v removeFromSuperview];
    }
	[cell.contentView addSubview:[self viewForIndexPath:indexPath]];
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
	
	return 82;
}

-(UIView*)viewForIndexPath:(NSIndexPath*)indexPath{
    UIView *view = [[[UIView alloc] initWithFrame:CGRectMake(0, 0, imageTable.frame.size.width, [self tableView:imageTable heightForRowAtIndexPath:indexPath])] autorelease];
    int index = indexPath.row * 4;
	int maxIndex = index + 3;
    CGRect rect = CGRectMake(3, 1, 77, 77);
    int x = 4;
    if (maxIndex >= [[self images] count]) {
        x = x - (maxIndex - [[self images] count]) - 1;
    }
    
    for (int i=0; i<x; i++) {
        GCAsset *asset = [[self images] objectAtIndex:index+i];
        UIImageView *image = [[[UIImageView alloc] initWithFrame:rect] autorelease];
        [image setTag:index+i];
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(objectTappedWithGesture:)];
        [image addGestureRecognizer:tap];
        [tap release];
        [image setUserInteractionEnabled:YES];
        [image setImage:[asset thumbnail]];
        if([selected containsObject:asset]){
            UIImageView *v = [[UIImageView alloc] initWithImage:self.selectedIndicator.image];
            [v setBackgroundColor:[UIColor whiteColor]];
            [v setFrame:CGRectMake(57, 57, 20, 20)];
            [image addSubview:v];
            [v release];
        }
        [view addSubview:image];
        rect = CGRectMake((rect.origin.x+79), rect.origin.y, rect.size.width, rect.size.height);
    }
    return view;
}

@end
