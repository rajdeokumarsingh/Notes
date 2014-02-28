//
//  G0000TestNull.m
//  OCGrammar
//
//  Created by Jiang Rui on 14-1-23.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "G0000TestNull.h"

@implementation G0000TestNull

- (void) testNull {
    NSDictionary *myDictionary = [NSDictionary dictionary];
    NSNumber *myNumber = [myDictionary valueForKey: @"MyNumber"];
    NSLog(@"myNumber = %@", myNumber); // output myNumber = (null)
    
    // true, and you can send message to nil
    if (myNumber == nil) {
        NSLog(@"test 1 myNumber == nil");
        NSLog(@"send message to nil, int value: %d", [myNumber intValue]);
    }
    
    // true
    if (myNumber == NULL) {
        NSLog(@"test 2 myNumber == NULL");
    }

    // true
    if (myNumber == Nil) {
        NSLog(@"test 3 myNumber == Nil");
    }

    // false, NSNull is an empty object.
    if ([myNumber isEqual:[NSNull null]]) {
        NSLog(@"test 4 myNumber == [NSNull null]");
    }
    
    // no crash, you can send message to NULL!!!
    NSNumber * nullNumber = NULL;
    NSLog(@"send message to NULL, int value: %d", [nullNumber intValue]);

    // send message to empty id
    id eid = NULL;
    NSLog(@"send message to empty id : %d", [eid intValue]);

    
}
@end
