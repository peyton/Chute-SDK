//
//  GCUploader.h
//
//  Created by Achal Aggarwal on 09/09/11.
//  Copyright 2011 NA. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GetChute.h"
#import "GCParcel.h"

@interface GCUploader : NSObject

@property (nonatomic, retain) NSMutableArray *queue;

+ (GCUploader *)sharedUploader;

- (void) addParcel:(GCParcel *) _parcel;
- (void) removeParcel:(GCParcel *) _parcel;

@end
