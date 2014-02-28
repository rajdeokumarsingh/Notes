//
//  Tire.m
//  Constructor
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "Tire.h"

@implementation Tire

- (id) init {
    if (self = [super init]) {
        pressure = 34.0;
        treadDepth = 28.5;
    }
    return self;
}

- (id) initPressure : (float) p
         withTreadDepth: (float) d {
    if (self = [super init]) {
        pressure = p;
        treadDepth = d;
    }
    return self;
}

- (void) setPressure: (float) p {
    pressure = p;
}

- (void) setTreadDepth: (float) depth {
    treadDepth = depth;
}

- (float) getPressure {
    return pressure;
}

- (float) getTreadDepth {
    return treadDepth;
}

- (NSString *) description {
    return [NSString stringWithFormat:@"presure: %f, tread depth: %f",
            pressure, treadDepth];
}

- (void) dealloc {
    NSLog(@"Tire dealloc!");
}
@end
