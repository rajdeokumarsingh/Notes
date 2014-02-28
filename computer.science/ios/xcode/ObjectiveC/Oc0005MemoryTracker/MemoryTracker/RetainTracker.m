//
//  RetainTracker.m
//  MemoryTracker
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "RetainTracker.h"

@implementation RetainTracker

- (id) init {
    if(self = [super init]) {
        NSLog(@"RetainTracker init");
    }
    return self;
}

- (void) dealloc {
    NSLog(@"RetainTracker dealloc");
//    [super dealloc];
}


@end
