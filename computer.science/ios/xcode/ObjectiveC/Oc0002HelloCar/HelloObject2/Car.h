//
//  Car.h
//  HelloObject2
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Engine;
@class Tire;

#define TIRE_MAX_NUM 4

@interface Car : NSObject {
    Engine * engine;
    Tire * tires[TIRE_MAX_NUM];
}

- (Engine *) getEngine;

- (void) setEngine: (Engine *) engine;

- (Tire *) getTile: (unsigned int) index;

- (void) setTire: (Tire *) tire
         atIndex: (unsigned int) index;

- (void) print;

@end
