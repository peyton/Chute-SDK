//
//  ImageGridViewCell.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 13/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "ImageGridViewCell.h"

@implementation ImageGridViewCell

- (id) initWithFrame:(CGRect)frame reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithFrame:frame reuseIdentifier:reuseIdentifier];
    if (self == nil) {
        return nil;
    }
    
    imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 62.0f, 62.0f)];
    imageView.center = self.contentView.center;
    
    checkImage = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"checkmark.png"]];
    checkImage.hidden = YES;
    checkImage.center = CGPointMake(52.0f, 52.0f);
    
    [self.contentView addSubview:imageView];
    [self.contentView addSubview:checkImage];
    
    return self;
}

- (void)setImage:(UIImage *)image {
    [imageView setImage:image];
}

- (void)setChecked:(BOOL)checked {
    checkImage.hidden = !checked;
}

@end