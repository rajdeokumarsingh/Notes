//
//  MyTest.h
//  HelloProtocol
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "TestProtocol.h"
#import "RunProtocol.h"

@interface MyTest : NSObject<TestProtocol, RunProtocol>

@end
