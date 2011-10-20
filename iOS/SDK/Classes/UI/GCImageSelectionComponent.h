//
//  GCImageSelectionComponent.h
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 9/7/11.
//  Copyright 2011 Chute. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GCUIBaseViewController.h"

@interface GCImageSelectionComponent : GCUIBaseViewController <UITableViewDelegate, UITableViewDataSource>{
    NSArray *images;
    NSMutableSet *selected;
    IBOutlet UIImageView *selectedIndicator;
    IBOutlet UITableView *imageTable;
}
@property (nonatomic, retain) NSArray *images;
@property (nonatomic, retain) IBOutlet UIImageView *selectedIndicator;

-(NSArray*)selectedImages;

@end
