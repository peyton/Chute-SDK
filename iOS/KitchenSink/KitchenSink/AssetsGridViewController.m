//
//  ChutePhotosGridViewController.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 09/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "AssetsGridViewController.h"
#import "PhotoViewer.h"

@implementation AssetsGridViewController
@synthesize photos;
@synthesize chuteId;
@synthesize chute;

- (void) gridView:(AQGridView *)gridView didDeselectItemAtIndex:(NSUInteger)index {
    [self gridView:gridView didSelectItemAtIndex:index];
}

- (void) gridView: (AQGridView *) gridView didSelectItemAtIndex: (NSUInteger) index {
    PhotoViewer *photoViewer = [[PhotoViewer alloc] init];
    self.navigationItem.backBarButtonItem =[[[UIBarButtonItem alloc] initWithTitle:@"Back"
                                                                             style: UIBarButtonItemStyleBordered
                                                                            target:nil
                                                                            action:nil] autorelease];
    [photoViewer setTitle:[self title]];
    [photoViewer setPhotos:photos];
    [photoViewer selectedPage:index];
    [self.navigationController pushViewController:photoViewer animated:YES];
    [photoViewer release];
}

- (NSUInteger) numberOfItemsInGridView: (AQGridView *) gridView {
    return [photos count];
}

- (AQGridViewCell *) gridView: (AQGridView *) gridView cellForItemAtIndex: (NSUInteger) index {
    static NSString *identifier = @"gridCell";
    AQGridViewCell *cell = [gridView dequeueReusableCellWithIdentifier:identifier];
    
    if (nil==cell) {
        cell = [[AQGridViewCell alloc] initWithFrame:CGRectMake(0, 0, 64, 64) reuseIdentifier:identifier];
    }
    
    [cell setSelectionGlowShadowRadius:0.0];
    [cell setSelectionGlowColor:[UIColor colorWithRed:0.0f green:255.0f blue:0.0f alpha:0.3f]];
    
    UIImageView *_imageView = nil;
    _imageView = (UIImageView *)[cell.contentView viewWithTag:100];
    if (IS_NULL(_imageView)) {
        _imageView = [[[UIImageView alloc] initWithFrame:CGRectMake(1, 1, 62.0, 62.0)] autorelease];
        _imageView.tag = 100;
        [cell.contentView addSubview:_imageView];
        _imageView.center = cell.contentView.center;
    }
    
 	GCAsset *photo = [photos objectAtIndex:index];
    NSString *_photoURL = [photo urlStringForImageWithWidth:75 andHeight:75];
    
    //960x540
    
    [_imageView setImageWithURL:[NSURL URLWithString:_photoURL] placeholderImage:[UIImage imageNamed:@"placeholder.png"]];
    
    return cell;
}

- (CGSize) portraitGridCellSizeForGridView: (AQGridView *) gridView {
    return CGSizeMake(64.0f, 64.0f);
}

- (void)dealloc
{
    [chute release];
    [photos release];
    [super dealloc];
}

- (void)showPhotoViewer:(id) sender {
    
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    __block typeof(self) bself = self;
    
    [chute assetsInBackgroundWithCompletion:^(GCResponse *response) {
        if ([response isSuccessful]) {
            if (bself.photos) {
                [bself.photos release], bself.photos = nil;
            }
            [bself setPhotos:[response object]];
            [_gridView reloadData];
        }
        else {
            [self quickAlertWithTitle:@"Error!" message:[[response error] localizedDescription] button:@"Okay"];
        }
    }];
}

@end
