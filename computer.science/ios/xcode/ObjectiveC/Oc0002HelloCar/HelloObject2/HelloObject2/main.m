//
//  main.m
//  HelloObject2
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Tire.h"
#import "Engine.h"
#import "Car.h"
#import "Slant6.h"
#import "AllWeatherRadial.h"

int main(int argc, const char * argv[])
{

    @autoreleasepool {
        id car = [Car new];
//        [car print];
        
        [car setEngine:[Slant6 new]];
        [car setTire:[AllWeatherRadial new] atIndex: 0];
        [car print];
        
        // dfdk fdjkfkdf  dkf
    }
    return 0;
}

