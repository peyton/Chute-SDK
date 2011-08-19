//
//  PhotoViewer.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 14/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "UIBaseViewController.h"

@class ImageScroller;

@interface PhotoViewer : UIBaseViewController <UIScrollViewDelegate> {
    IBOutlet UIScrollView *pagingScrollView;
    
    NSMutableSet *recycledPages;
    NSMutableSet *visiblePages;
    
    // these values are stored off before we start rotation so we adjust our content offset appropriately during rotation
    int           firstVisiblePageIndexBeforeRotation;
    CGFloat       percentScrolledIntoFirstVisiblePage;
    
    int pageIndex;
}


@property (nonatomic, retain) NSArray *photos;

- (void)selectedPage:(NSUInteger)index;

- (void)configurePage:(ImageScroller *)page forIndex:(NSUInteger)index;
- (BOOL)isDisplayingPageForIndex:(NSUInteger)index;

- (CGRect)frameForPagingScrollView;
- (CGRect)frameForPageAtIndex:(NSUInteger)index;
- (CGSize)contentSizeForPagingScrollView;

- (void)tilePages;
- (ImageScroller *)dequeueRecycledPage;

- (NSUInteger)imageCount;
- (NSString *)imageURLAtIndex:(NSUInteger)index;
- (UIImage *)imageAtIndex:(NSUInteger)index;
@end
