//
//  main.m
//  MemoryTracker
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RetainTracker.h"

int main(int argc, const char * argv[])
{

    @autoreleasepool {
        
        NSLog(@"auto release scope begin!");
        
        RetainTracker * tracker = [RetainTracker new];

        NSLog(@"audo release scope end!");
    }
    
    NSLog(@"before return!");
    return 0;
}

