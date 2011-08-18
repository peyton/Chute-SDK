//
//  ImageScroller.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 14/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface ImageScroller : UIScrollView <UIScrollViewDelegate> {
    UIImageView   *imageView;
    NSUInteger     index;
}

@property (assign) NSUInteger index;

- (void)displayImage:(UIImage *)image;
- (void)displayImageWithURL:(NSString *)imageURL;

- (void)setMaxMinZoomScalesForCurrentBounds;

- (CGPoint)pointToCenterAfterRotation;
- (CGFloat)scaleToRestoreAfterRotation;
- (void)restoreCenterPoint:(CGPoint)oldCenter scale:(CGFloat)oldScale;

@end
