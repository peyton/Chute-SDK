//
//  SelectChuteViewController.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 13/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "UIBaseViewController.h"

@interface SelectChuteViewController : UIBaseViewController <UITableViewDataSource, UITableViewDelegate> {
    UITableView *chuteList;
    NSArray *data;
}

@property (nonatomic, retain) IBOutlet UITableView *chuteList;
@property (nonatomic, retain) NSMutableArray *selectedChutes;

@property (nonatomic, retain) NSArray *selectedAssets;

- (void) next;

@end
