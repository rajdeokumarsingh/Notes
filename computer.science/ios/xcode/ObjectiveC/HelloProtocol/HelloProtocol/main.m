//
//  main.m
//  HelloProtocol
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MyTest.h"

int main(int argc, const char * argv[])
{

    @autoreleasepool {
        MyTest * test = [MyTest new];
        [test setup];
        [test tearDown];
        [test run];
    }
    
    return 0;
}

