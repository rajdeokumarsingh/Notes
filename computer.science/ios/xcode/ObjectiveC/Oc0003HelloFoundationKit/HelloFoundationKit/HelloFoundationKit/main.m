//
//  main.m
//  HelloFoundationKit
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

void typeDemo(void)
{
    NSRange range1 = {17, 4};
    NSRange range2 = NSMakeRange(28, 8);
    NSPoint point = NSMakePoint(23.3, 43.5);
    NSSize size = NSMakeSize(34.4, 45.3);
    NSRect rect = NSMakeRect(0, 1, 200, 500);
}

void stringDemo(void)
{
    NSString * str = @"test string";
    uint cnt = 20;
    NSString * str2 = [NSString stringWithFormat:@"you got %u apples", cnt];
    NSLog(str2);
    NSLog(@"string len : %lu", (unsigned long)[str2 length]);
    
    NSLog(@"string is equal :%d", [str isEqualToString:str2]);
    NSLog(@"string is equal :%d", [str isEqualToString:@"test string"]);
    
    NSComparisonResult result = [str compare:str2];
    
    NSLog(@"has prefix: %d", [str hasPrefix:@"test"]);
    NSLog(@"has suffix: %d", [str hasSuffix:@"string"]);
    
    NSRange range = [str rangeOfString:@"str"];
    NSLog(@"range is : %lu, %lu",
          (unsigned long)range.location, (unsigned long)range.length);
}

void mutableStringDemo(void)
{
    NSMutableString * mstring = [NSMutableString stringWithCapacity:50];
    [mstring appendString:@"maths physics chemistry politics music chinese"];
    [mstring appendFormat:@"%d", 200];
    NSLog(mstring);
    
    NSRange range = [mstring rangeOfString:@"politics"];
    range.length++;
    [mstring deleteCharactersInRange:range];
    
    NSLog(mstring);
}

void arrayDemo(void)
{
    NSArray * array = [NSArray arrayWithObjects:
                       @"maths", @"chinese", @"physics", nil];
    for (int i = 0; i < [array count]; i++) {
        NSLog(@"array content: %d, %@", i, [array objectAtIndex:i]);
    }
    
    NSString * str = @"maths:chinese:biology";
    array = [str componentsSeparatedByString:@":"];
    for (int i = 0; i < [array count]; i++) {
        NSLog(@"array content: %d, %@", i, [array objectAtIndex:i]);
    }
}

void mutableArrayDemo(void)
{
    // capacity could be expanded
    NSMutableArray * marray = [NSMutableArray arrayWithCapacity:2];
    [marray addObject:@"chinese"];
    [marray addObject:@"maths"];
    [marray addObject:@"physics"];
    [marray addObject:@"no good"];
    
    [marray removeObjectAtIndex:1];
    
    for (int i = 0; i < [marray count]; i++) {
        NSLog(@"array object: %@", [marray objectAtIndex:i]);
    }
    
    for (NSString * s in marray) {
        NSLog(@"array object: %@", s);
    }
}

void dictDemo(void)
{
    NSDictionary * dict = [NSDictionary dictionaryWithObjectsAndKeys:
                           @"object1", @"key1", @"object2", @"key2", nil];
    NSArray * keys = [dict allKeys];
    for (NSString * s in keys) {
        NSLog(@"key is: %@, value is: %@", s, [dict valueForKey:s]);
    }
    
    NSMutableDictionary * mdict = [NSMutableDictionary dictionaryWithCapacity:20];
    [mdict setObject:@"object 1" forKey:@"key 1"];
    [mdict setObject:@"object 2" forKey:@"key 2"];
    [mdict setObject:@"object 3" forKey:@"key 3"];
    [mdict removeObjectForKey:@"key 2"];
    for (NSString * s in [mdict allKeys]) {
        NSLog(@"key is: %@, value is: %@", s, [mdict valueForKey:s]);
    }
}

void numberDemo(void)
{
    NSNumber * numDouble = [NSNumber numberWithDouble:23.5];
    NSNumber * numInt = [NSNumber numberWithInt:34];
    
    NSMutableArray * marray = [NSMutableArray arrayWithCapacity:10];
    [marray addObject:numDouble];
    [marray addObject:numInt];
    
    for (NSNumber * num in marray) {
        NSLog(@"array number is: %@", num);
        NSLog(@"int value is: %d", [num intValue]);
        NSLog(@"double value is: %f", [num doubleValue]);
    }
    
    NSMutableDictionary * mdict = [NSMutableDictionary dictionaryWithCapacity:10];
    [mdict setObject:numDouble forKey:@"key 1"];
    [mdict setObject:numInt forKey:@"key 2"];
}

void valueDemo(void)
{
    
    NSRect rect = NSMakeRect(0, 0, 100, 200);
    //        NSValue * value = [NSValue valueWithBytes:&rect objCType:@encode(NSRect)];
    NSValue * value = [NSValue valueWithRect:rect];
    
    NSRect rect_new = [value rectValue];
    //        [value getValue: &rect_new];
    
    NSLog(@"rect is: %f, %f, %f, %f",
          rect_new.origin.x, rect_new.origin.y, rect_new.size.width, rect_new.size.height);
}

void nullObjectDemo(void)
{
    
    NSMutableDictionary * mdict = [NSMutableDictionary dictionaryWithCapacity:5];
    [mdict setObject:[NSNull null] forKey:@"home address"];
    NSLog(@"home address is: %@", [mdict objectForKey:@"home address"]);
}

int main(int argc, const char * argv[])
{

    @autoreleasepool {

//        typeDemo();
//        stringDemo();
//        mutableStringDemo();
//        arrayDemo();
//        mutableArrayDemo();
//        dictDemo();
//        numberDemo();
//        valueDemo();
        
        nullObjectDemo();
        
    }
    return 0;
}

