//
//  NSString+NumberConvenience.m
//  Category
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "NSString+NumberConvenience.h"

@implementation NSString (NumberConvenience)

- (NSNumber *) lengthAsNumber {
    unsigned long length = [self length];
    return [NSNumber numberWithUnsignedLong:length];
}

@end
