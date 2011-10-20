//
//  GCCommentComponent.h
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 9/8/11.
//  Copyright 2011 Chute. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GCUIBaseViewController.h"
#import "GetChute.h"

@interface GCCommentComponent : GCUIBaseViewController <UITableViewDataSource, UITableViewDelegate, UITextFieldDelegate>{
    IBOutlet UITableView *commentTable;
    
    NSArray *comments;
    GCAsset *asset;
    GCChute *chute;
    IBOutlet UITextField *commentTV;
    IBOutlet UIButton *addCommentButton;
}
@property (nonatomic, retain) NSArray *comments;
@property (nonatomic, retain) GCAsset *asset;
@property (nonatomic, retain) GCChute *chute;

-(IBAction)postComment;

@end
