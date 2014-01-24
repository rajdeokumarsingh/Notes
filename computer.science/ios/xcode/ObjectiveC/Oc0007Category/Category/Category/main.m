//
//  main.m
//  Category
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NSString+NumberConvenience.h"
#import "CategoryThing.h"

int main(int argc, const char * argv[])
{

    @autoreleasepool {
//        NSMutableDictionary * dict = [NSMutableDictionary dictionaryWithCapacity:20];
//        [dict setObject:[@"hello" lengthAsNumber] forKey:@"hello"];
//        [dict setObject:[@"world" lengthAsNumber] forKey:@"world"];
//        
//        NSLog(@"dict: [%@]", dict);
        
        CategoryThing * thing = [[CategoryThing alloc] init];
        [thing setThing1:28];
        [thing setThing2:45];
        [thing setThing3:99];
        NSLog(@"thing is : [%@]", thing);
    }
    return 0;
}

