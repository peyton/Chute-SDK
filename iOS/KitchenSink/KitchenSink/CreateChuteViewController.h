//
//  CreateChuteViewController.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 08/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "UIBaseViewController.h"

@interface CreateChuteViewController : UIBaseViewController {
    UITextField *chuteName;
    UISegmentedControl *permission;
    UIButton *membersButton;
    UIButton *photosButton;
}

@property (nonatomic, retain) IBOutlet UITextField *chuteName;
@property (nonatomic, retain) IBOutlet UISegmentedControl *permission;
@property (nonatomic, retain) IBOutlet UIButton *membersButton;
@property (nonatomic, retain) IBOutlet UIButton *photosButton;

- (IBAction)toggle:(id)sender;
- (IBAction)save:(id)sender;
@end
