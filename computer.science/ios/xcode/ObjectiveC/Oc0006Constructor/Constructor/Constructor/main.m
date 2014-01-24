//
//  main.m
//  Constructor
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MyObject.h"
#import "Tire.h"
#import "Car.h"
#import "AllWeatherTire.h"

int main(int argc, const char * argv[])
{

    @autoreleasepool {
//        MyObject * obj = [[MyObject alloc] init];
//        NSLog(@"obj is %@", obj);
        
// read file from a file
//        NSString * str = [[NSString alloc] initWithContentsOfFile:@"/tmp/test"];
//        NSLog(@"file /tmp/test is: %@", str);
        
        // default constructor
//        Tire * tire = [[Tire alloc]init];
//        NSLog(@"The tire 1 is : %@", tire);
//        
//        // customized constructor
//        Tire * tire2 = [[Tire alloc]initPressure:45.0 withTreadDepth:23.5];
//        NSLog(@"The tire 2 is : %@", tire2);
 
        Car * car = [[Car alloc]init];
        NSLog(@"car is %@", car);
        
        AllWeatherTire * allWhether = [[AllWeatherTire alloc]initWithPressure:99 treadDepth:123];
        [allWhether setMRainHandling:765];
        [allWhether setName:@"test my tire"];
        NSLog(@"all whether tire: %@", allWhether);
    }
    return 0;
}