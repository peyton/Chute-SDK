//
//  PhotosGridViewController.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 13/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "PhotosGridViewController.h"
#import "SelectChuteViewController.h"

@implementation PhotosGridViewController
@synthesize _gridView;

#pragma mark GridView

- (void) gridView:(AQGridView *) gridView toggleCellForIndex: (NSUInteger) index {
    [[assets objectAtIndex:index] toggle];
    ImageGridViewCell *cell = (ImageGridViewCell *)[gridView cellForItemAtIndex:index];
    [cell setChecked:[[assets objectAtIndex:index] selected]];
}

- (void) gridView: (AQGridView *) gridView didSelectItemAtIndex: (NSUInteger) index {
    [self gridView:gridView toggleCellForIndex:index];
}

- (void) gridView: (AQGridView *) gridView didDeselectItemAtIndex: (NSUInteger) index {
    [self gridView:gridView toggleCellForIndex:index];
}

- (NSUInteger) numberOfItemsInGridView: (AQGridView *) gridView {
    return [assets count];
}

- (AQGridViewCell *) gridView: (AQGridView *) gridView cellForItemAtIndex: (NSUInteger) index {
    static NSString *identifier = @"gridCell";
    
    ImageGridViewCell *cell = (ImageGridViewCell *)[gridView dequeueReusableCellWithIdentifier:identifier];
    
    if (cell == nil) {
        cell = [[ImageGridViewCell alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 64.0f, 64.0f) reuseIdentifier:identifier];
    }
    
    [cell setSelectionGlowShadowRadius:0.0f];
    [cell setSelectionGlowColor:[UIColor clearColor]];
    
    [cell setImage:[assetViews objectAtIndex:index]];
    [cell setChecked:[[assets objectAtIndex:index] selected]];
    
    return cell;
}

- (CGSize) portraitGridCellSizeForGridView: (AQGridView *) gridView {
    return CGSizeMake(64.0f, 64.0f);
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

- (void)next {
    
    NSMutableArray *_selectedAssets = [[NSMutableArray alloc] init];
    for (ChuteAsset *asset in assets) {
        if ([asset selected]) {
            [_selectedAssets addObject:asset];
        }
    }
    
    if ([_selectedAssets count] == 0) {
        [self quickAlertWithTitle:@"" message:@"Please select the photos to sync" button:@"okay"];
        [_selectedAssets release];
        return;
    }
    
    
    SelectChuteViewController *selectChute = [[SelectChuteViewController alloc] init];
    [selectChute setTitle:@"Select Chute(s)"];
    [selectChute setSelectedAssets:_selectedAssets];
    self.navigationItem.backBarButtonItem =[[[UIBarButtonItem alloc] initWithTitle:@"Back"
                                                                             style: UIBarButtonItemStyleBordered
                                                                            target:nil
                                                                            action:nil] autorelease];
    [self.navigationController pushViewController:selectChute animated:YES];
    [selectChute release];
    [_selectedAssets release];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [[[ChuteAssetManager shared] uploadingAssets] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    /*static NSString *identifier = @"uploadCell";
    
    UploadTableViewCell *cell = (UploadTableViewCell *)[tableView dequeueReusableCellWithIdentifier:identifier];
    
    if (cell == nil) {
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"UploadTableViewCell" owner:self options:nil];
        cell = (UploadTableViewCell *)[nib objectAtIndex:0];
    }
    
    ChuteAsset *_asset = [[[ChuteAssetManager shared] uploadingAssets] objectAtIndex:indexPath.row];
    cell.imageView.image = [_asset thumbnail];
    [cell.progress setProgress:[_asset progress]];
    
    [cell.contentView setTransform:CGAffineTransformMakeRotation(M_PI_2)];
    
    [_asset addObserverForKeyPath:@"progress" block:^(NSDictionary *change) {
        [cell.progress setProgress:[_asset progress]];
    }];
    
    return cell;
     */
    return nil;
}

- (void) showUploadsTableView {
    CGRect f = _gridView.frame;
    f.size.height = 297.0f;
    [UIView animateWithDuration:0.3f animations:^(void) {
        [_gridView setFrame:f];
        uploadsTableView.hidden = NO;
    }];
}

- (void) hideUploadsTableView {
    CGRect f = _gridView.frame;
    f.size.height = 367.0f;
    [UIView animateWithDuration:0.3f animations:^(void) {
        [_gridView setFrame:f];
        uploadsTableView.hidden = YES;
    }];
}

#pragma mark - View lifecycle

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    for (ChuteAsset *_asset in assets) {
        _asset.selected = NO;
    }
    [_gridView reloadData];
    
    [uploadsTableView setTransform:CGAffineTransformMakeRotation(-M_PI_2)];
    [uploadsTableView scrollsToTop];
    
    [[NSNotificationCenter defaultCenter] addObserverForName:@"uploadsUpdated" object:nil queue:nil usingBlock:^(NSNotification *notification) {
        
        if ([[[ChuteAssetManager shared] uploadingAssets] count] > 0) {
            [self showUploadsTableView];
        }
        else {
            [self hideUploadsTableView];
        }
        
        [uploadsTableView reloadData];
    }];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Next" style:UIBarButtonItemStyleBordered target:self action:@selector(next)];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [[ChuteAssetManager shared] loadAssetsCompletionBlock:^(void) {
        if (assets) {
            [assets release], assets = nil;
        }
        
        assets = [[NSMutableArray alloc] initWithArray:[[ChuteAssetManager shared] assetsArray]];
        
        assetViews = [[NSMutableArray alloc] init];
        
        for (id obj in assets) {
            [assetViews addObject:[[obj thumbnail] retain]];
        }
        [_gridView reloadData]; 
    }];
}

- (void)viewDidUnload
{
    [self set_gridView:nil];
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
    [_gridView release];
    [super dealloc];
}
@end
