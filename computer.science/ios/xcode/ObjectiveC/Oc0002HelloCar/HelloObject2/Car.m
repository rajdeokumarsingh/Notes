//
//  Car.m
//  HelloObject2
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "Car.h"
#import "Engine.h"
#import "Tire.h"

@implementation Car

- (id) init {
    if(self = [super init]) {
        engine = [Engine new];

        for (int i=0; i<TIRE_MAX_NUM; i++) {
            tires[i] = [Tire new];
        }
    }
    return self;
}

- (void) print {
    NSLog(@"%@", engine);
    
    for (int i=0; i<TIRE_MAX_NUM; i++) {
        NSLog(@"%@", tires[i]);
    }
}

- (Engine *) getEngine {
    return engine;
}

- (void) setEngine: (Engine *) eng {
    self->engine = eng;
}

- (Tire *) getTile: (unsigned int) index {
    if (index >= TIRE_MAX_NUM) {
        NSLog(@"parameter index invalid");
        return NULL;
    }

    return tires[index];
}

- (void) setTire: (Tire *) tire
         atIndex: (unsigned int) index {
    if (index >= TIRE_MAX_NUM) {
        NSLog(@"parameter index invalid");
        return;
    }
    tires[index] = tire;
}

@end
