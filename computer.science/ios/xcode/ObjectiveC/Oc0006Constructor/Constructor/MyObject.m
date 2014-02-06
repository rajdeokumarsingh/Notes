//
//  MyObject.m
//  Constructor
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "MyObject.h"

@implementation MyObject

- (id) init {
    if (self = [super init]) {
        mName = @"Tomcat";
        mAge = [NSNumber numberWithInt:20];
    }
    
    NSLog(@"MyObject init: %@", self);
    return self;
}

- (id) init : (NSString *) name
  withNumber: (NSNumber *) number {
    if (self = [super init]) {
        mName = name;
        mAge = number;
    }
    return self;
}

- (void) dealloc {
    NSLog(@"dealocc");
}

- (NSString *) getName {
    return mName;
}

- (NSNumber *) getAge {
    return mAge;
}

- (NSString * ) description {
    return [NSString stringWithFormat:@"name is: %@, age is %d",
            mName, [mAge intValue]];
}


@end
