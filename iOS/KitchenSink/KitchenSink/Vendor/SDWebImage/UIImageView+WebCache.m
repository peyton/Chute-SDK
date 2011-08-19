/*
 * This file is part of the SDWebImage package.
 * (c) Olivier Poitrey <rs@dailymotion.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

#import "UIImageView+WebCache.h"
#import "SDWebImageManager.h"
#include <objc/runtime.h>
//#import "ImageScroller.h"

@implementation UIImageView (WebCache)

static char bool_key;
static char block_key;

- (void)setUpdateFrame:(BOOL)value {
    objc_setAssociatedObject(self,&bool_key,[NSNumber numberWithBool:value] ,OBJC_ASSOCIATION_RETAIN);
}

- (BOOL)updateFrame {
    NSNumber *temp = objc_getAssociatedObject(self,&bool_key);
    return [temp boolValue];
}

- (void)setBlockObject:(void (^)(void))block {
    objc_setAssociatedObject(self, &block_key, block, OBJC_ASSOCIATION_RETAIN);
}

- (void (^)(void))blockObject {
    return objc_getAssociatedObject(self, &block_key);
}

- (void)setImageWithURL:(NSURL *)url
{
    [self setImageWithURL:url placeholderImage:nil updateFrame:NO];
}

- (void)setImageWithURL:(NSURL *)url placeholderImage:(UIImage *)placeholder {
    [self setImageWithURL:url placeholderImage:placeholder updateFrame:NO];
}

- (void)setImageWithURL:(NSURL *)url placeholderImage:(UIImage *)placeholder updateFrame:(BOOL)update
{
    [self setImageWithURL:url placeholderImage:placeholder updateFrame:update andBlock:^(void) {}];
}

- (void)setImageWithURL:(NSURL *)url placeholderImage:(UIImage *)placeholder updateFrame:(BOOL)update andBlock:(void (^)(void)) block {
    
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    
    // Remove in progress downloader from queue
    [manager cancelForDelegate:self];
    
    self.image = placeholder;
    
    [self setUpdateFrame:update];
    [self setBlockObject:block];
    
    if (url)
    {
        [manager downloadWithURL:url delegate:self];
    }
}

- (void)cancelCurrentImageLoad
{
    [[SDWebImageManager sharedManager] cancelForDelegate:self];
}

- (void)webImageManager:(SDWebImageManager *)imageManager didFinishWithImage:(UIImage *)image
{
    self.image = image;
    if ([self updateFrame]) {
        CGRect f = self.frame;
        f.size.width = image.size.width;
        f.size.height = image.size.height;
        [self setFrame:f];
        [self setNeedsDisplay];
    }
    /*
    if ([self.superview class] == [ImageScroller class]) {
        ImageScroller *superView = (ImageScroller *) self.superview;
        superView.contentSize = [self.image size];
        [superView setMaxMinZoomScalesForCurrentBounds];
        superView.zoomScale = superView.minimumZoomScale;
        [superView setNeedsDisplay];
    }*/
    
    void (^block)(void) = [self blockObject];
    block();
}

@end
