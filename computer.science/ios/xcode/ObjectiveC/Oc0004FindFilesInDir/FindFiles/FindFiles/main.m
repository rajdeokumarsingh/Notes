//
//  main.m
//  FindFiles
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

int main(int argc, const char * argv[])
{

    @autoreleasepool {
        
        NSFileManager * fileManager = [NSFileManager defaultManager];
        NSString * dir = [@"~/interview" stringByExpandingTildeInPath];
        
//        NSDirectoryEnumerator * direnum = [fileManager enumeratorAtPath:dir];
//        
//        NSString * filename;
//        while (filename = [direnum nextObject]) {
//            NSLog(@"file name is : %@", filename);
//        }
        
        for (NSString * filename in [fileManager enumeratorAtPath:dir]) {
            NSLog(@"file name is : %@", filename);
        }
    }
    return 0;
}