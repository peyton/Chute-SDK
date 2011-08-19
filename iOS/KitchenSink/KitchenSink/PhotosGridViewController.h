//
//  PhotosGridViewController.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 13/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "UIBaseViewController.h"
#import "AQGridView.h"
#import "ImageGridViewCell.h"

@interface PhotosGridViewController : UIBaseViewController <AQGridViewDelegate, AQGridViewDataSource> {
    
    AQGridView *_gridView;
    
    NSMutableArray *assets;
    NSMutableArray *assetViews;
    
    IBOutlet UITableView *uploadsTableView;
}

@property (nonatomic, retain) IBOutlet AQGridView *_gridView;

- (void)next;

@end
