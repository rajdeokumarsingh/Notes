//
//  MyTest.m
//  HelloProtocol
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "MyTest.h"

@implementation MyTest

- (void) setup {
    NSLog(@"test setup");
}

- (void) tearDown {
    NSLog(@"test tear down");
}

- (void) run {
    NSLog(@"run");    
}
@end
