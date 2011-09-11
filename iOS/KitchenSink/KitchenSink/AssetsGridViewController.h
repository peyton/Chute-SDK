//
//  ChutePhotosGridViewController.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 09/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "UIBaseViewController.h"
#import "AQGridView.h"

@interface AssetsGridViewController : UIBaseViewController <AQGridViewDataSource, AQGridViewDelegate> {
    IBOutlet AQGridView *_gridView;
}

@property (nonatomic, retain) NSArray *photos;
@property (nonatomic, assign) NSUInteger chuteId;
@property (nonatomic, retain) GCChute *chute;

- (void)showPhotoViewer:(id) sender;

@end
