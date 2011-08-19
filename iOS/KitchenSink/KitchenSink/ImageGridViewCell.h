//
//  ImageGridViewCell.h
//  KitchenSink
//
//  Created by Achal Aggarwal on 13/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "AQGridViewCell.h"
#import "UIImageView+WebCache.h"

@interface ImageGridViewCell : AQGridViewCell {
    UIImageView *imageView;
    UIImageView *checkImage;
}

- (void)setImage:(UIImage *)image;
- (void)setChecked:(BOOL)checked;

@end
