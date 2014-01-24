//
//  AllWeatherTire.m
//  Constructor
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "AllWeatherTire.h"

@implementation AllWeatherTire

- (id) init {
    if (self = [super init]) {
        mRainHandling = 20;
    }
    return self;
}

- (id) initWithPressure : (float) p
              treadDepth: (float) d {
    if (self = [super initPressure:p withTreadDepth:d]) {
        mRainHandling = 20;
    }
    return self;
}

//- (void) setRainHandling:(float)rh {
//    rainHandling = rh;
//}
//
//- (float) rainHandling {
//    return rainHandling;
//}
@synthesize rainHandling = mRainHandling;
@synthesize name = mName;

- (NSString *) description {
    return  [NSString stringWithFormat: @"super [%@], name: %@, rainHandling: %f",
             [super description], mName, mRainHandling];
}

@end
