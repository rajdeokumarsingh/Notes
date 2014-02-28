//
//  main.m
//  Delegate
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Nanny.h"
#import "Mother.h"

int main(int argc, const char * argv[])
{
    @autoreleasepool {
        Nanny * nanny = [Nanny new];
        Mother * mother = [[Mother alloc] initWithDelegate:nanny];
        
        [mother start];
    }
 
    return 0;
}

