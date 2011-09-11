//
//  GCThumbnailComponent.h
//  ChuteSDKDevProject
//
//  Created by Brandon Coston on 8/30/11.
//  Copyright 2011 Chute. All rights reserved.
//
//  Init and optionally set thumbsize and spacing size.  Thumbnails are square and spacing size is the distance between them.  If you don't specify these it defaults to 63 for thumbSize and 1 for spacing size.  You can also set thumbCountPerRow to specify the number of thumbnails per row, if you don't set this it will automatically figure out the max thumbnails per row.  Regardless of each of these settings the rows are automatically centered.

//  The default behavior is to show the tapped thumbnail full screen.  You can override this behavior in a subclass by replacing the objectTappedAtIndex method.

//  If you subclass the view you can either create the view programatically or use interface builder.  If you use interface builder remember to connect your table to objectTable.  The default viewDidLoad method will automatically set the background to clear, remove seperator lines, set the delegate and data source, and disallow selection of rows on the table.

#import <UIKit/UIKit.h>
#import "GetChute.h"

@interface GCThumbnailComponent : GCUIBaseViewController <UITableViewDelegate, UITableViewDataSource>{
    NSArray *objects;
    IBOutlet UITableView *objectTable;
    NSInteger thumbSize;
    NSInteger spacingSize;
    NSInteger thumbCountPerRow;
    NSInteger initialThumbOffset;
}
@property (nonatomic, retain) NSArray *objects;
@property (nonatomic) NSInteger thumbSize;
@property (nonatomic) NSInteger spacingSize;
@property (nonatomic) NSInteger thumbCountPerRow;

//override in subclass to change behavior
-(void)objectTappedAtIndex:(NSInteger)index;

@end