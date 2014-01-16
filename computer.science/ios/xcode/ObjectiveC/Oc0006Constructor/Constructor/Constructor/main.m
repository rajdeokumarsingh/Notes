//
//  main.m
//  Constructor
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MyObject.h"

int main(int argc, const char * argv[])
{

    @autoreleasepool {
//        MyObject * obj = [MyObject alloc: @"jack" withNumber: [NSNumber numberWithInt:25]];
//        MyObject * obj = [MyObject new];
        MyObject * obj = [[MyObject alloc] init];
        
        NSLog(@"obj is %@", obj);
    }
    return 0;
}

