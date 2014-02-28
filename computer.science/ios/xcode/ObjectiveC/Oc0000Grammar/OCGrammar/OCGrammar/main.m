//
//  main.m
//  OCGrammar
//
//  Created by Jiang Rui on 14-1-23.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "G0000TestNull.h"
#import "G0002Collections.h"

int main(int argc, const char * argv[])
{

    @autoreleasepool {
        G0000TestNull *nullTest = [G0000TestNull new];
        [nullTest testNull];
        
        G0002Collections * testArray = [G0002Collections new];
        [testArray testArray];
        [testArray testDict];
        [testArray testNSNull];
    }
    return 0;
}
