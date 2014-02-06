//
//  Car.m
//  Constructor
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "Car.h"
#import "Tire.h"

@implementation Car

- (id) init {
    if (self = [super init]) {
        mTires[0] = [[Tire alloc] init];
        mTires[1] = [[Tire alloc] initPressure:99.0 withTreadDepth:88.5];
        
        mMutableTires = [NSMutableArray new];
        [mMutableTires addObject:[Tire new]];
        [mMutableTires addObject:mTires[0]];
    }
    return self;
}

// the tires in mTires and mMutableTires will be deleted automatically
// if car is in a auto release pool
- (void) dealloc {
    NSLog(@"Car dealloc");
}

- (void) addTire : (id) tire {
    [mMutableTires addObject:tire];
}

- (NSString *) description {
    NSMutableString * str = [NSMutableString stringWithCapacity:64];
    
    for (Tire * tire in mMutableTires) {
        [str appendString:[NSString stringWithFormat:@"tire %@ \n", tire]];
    }
    return str;
}
@end
