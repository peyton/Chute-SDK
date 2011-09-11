//
//  GCAssetSliderComponent.h
//  realtyChute
//
//  Created by Brandon Coston on 9/03/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GCUIBaseViewController.h"

@interface GCAssetSliderComponent : GCUIBaseViewController <UIScrollViewDelegate> {
    NSMutableArray *sliderObjects;
    NSArray *objects;
    IBOutlet UIScrollView *objectSlider;
    NSInteger currentPage;
}
@property (nonatomic, readonly) NSInteger currentPage;
@property (nonatomic, retain) NSArray *objects;
@property (nonatomic, retain) NSMutableArray *sliderObjects;

//can override to add custom behavior when finished switching pages.  Reccomended to call [super loadObjectsForCurrentPosition] in your implementation though to insure views are loaded properly in slider.  This method is not required to be overridden.
- (void)loadObjectsForCurrentPosition;

//Implement in child class.  Page is zero indexed.  Any UIView or subclass should work, please retain any objects that you need to use with the view.  This method must be overridden.
- (UIView*)viewForPage:(NSInteger)page;

- (void)resizeScrollView;

- (void)nextObject;
- (void)previousObject;

- (void)switchToObjectAtIndex:(NSUInteger)index animated:(BOOL)_animated;
//same as switchToObjectAtIndex: animated: with NO passed in to animated field
- (void)switchToObjectAtIndex:(NSNumber*)index;

//returns the size and position of a view for a given page.  Note that page is not zero indexed.  Page indexing starts at 1.
- (CGRect)rectForPage:(NSInteger)page;

- (void)queueObjectRetrievalForPage:(NSInteger)page;

@end
