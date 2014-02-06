//
//  Mother.m
//  Delegate
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "Mother.h"

@implementation Mother

- (id) initWithDelegate: (id<HomeJob>) dl {
    if (self = [super init]) {
        delegate = dl;
    }
    return self;
}

- (void) start {
    [delegate takeEat];
    // [delegate takeSleep];
}

@end
