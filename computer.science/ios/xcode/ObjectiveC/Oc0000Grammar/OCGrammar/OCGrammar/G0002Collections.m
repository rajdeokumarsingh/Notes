//
//  G0002Array.m
//  OCGrammar
//
//  Created by Jiang Rui on 14-1-24.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "G0002Collections.h"

@implementation G0002Collections

- (void) testArray {
    // NSArray is always ended with a nil
    NSArray * array1 = [NSArray arrayWithObjects:@"test", @123, @45.7, @'V', nil];
    NSLog(@"count of array1 is: %lu", (unsigned long)[array1 count]);
    
    // simple way
    NSArray * array2 = @[@"test", @1, @879L];
    
    if ([array2 containsObject:@"test"]) {
        NSLog(@"find string in the array");
    }
    
    NSArray * array3 = @[@1, @78, @10, @20, @0, @5];
    NSArray * sortArray = [array3 sortedArrayUsingSelector:@selector(compare:)];
    NSMutableString * sortArrayStr = [NSMutableString stringWithCapacity:100];
    for (NSNumber * number in sortArray) {
        [sortArrayStr appendFormat:@"%@, ", number];
    }
    
    // remove last comma and space
    NSRange range = {[sortArrayStr length]-2,2};
    [sortArrayStr deleteCharactersInRange:range];
    
    NSLog(@"sort array is : %@", sortArrayStr);
    
    
    NSMutableArray *mutableArray = [NSMutableArray array];
    [mutableArray addObject:@"gamma"];
    [mutableArray addObject:@"alpha"];
    [mutableArray addObject:@"beta"];
    [mutableArray replaceObjectAtIndex:0 withObject:@"epsilon"];
    
    [mutableArray sortedArrayUsingSelector:@selector(caseInsensitiveCompare:)];

}

- (void) testDict {
    
    
    NSDictionary *dictionary = [NSDictionary dictionaryWithObjectsAndKeys:
                                [NSObject new], @"anObject",
                                @"Hello, World!", @"helloString",
                                @42, @"magicNumber",
                                [NSNumber numberWithDouble:34.34], @"aValue",
                                nil];
    
    NSDictionary *dictionary1 = @{
                                 @"anObject" : [NSObject new],
                                 @"helloString" : @"Hello, World!",
                                 @"magicNumber" : @42,
                                 @"aValue" : [NSNumber numberWithFloat:83.4]
                                 };
    
    NSNumber * num = [dictionary valueForKey:@"magicNumber"];
    NSLog(@"The magic number is: %@", num);
    NSLog(@"The magic number is: %@", dictionary[@"magicNumber"]);
}

- (void) testNSNull {
    NSArray *array = @[ @"string", @42, [NSNull null] ];

    for (id object in array) {
        if (object == [NSNull null]) {
            NSLog(@"Found a null object");
        }
    }       
}
@end
