//
//  main.m
//  OcHelloWorld
//
//  Created by Jiang Rui on 14-1-15.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

void readFile();

int main(int argc, const char * argv[])
{

    @autoreleasepool {
        // @ means the string is a NSString
        NSLog(@"print a number: %d", 500);
        NSLog(@"Hello, Objective-C!");
        
        NSString * str = @"a ns string";
        NSLog(@"output a ns string: %@", str);
        
        // printf is OK but depreciated
        // printf("test, len: %lu\n", strlen("test"));
    }
//    readFile();
    return 0;
}

void readFile() {
    FILE * tmp = fopen("/tmp/test", "r");
    char chars[100];
    // memset(chars, 0, sizeof(chars));
    
    while (fgets(chars, sizeof(chars), tmp)) {
        chars[strlen(chars)-1] = '\0';
        NSLog(@"get line: %s, size: %lu", chars, strlen(chars));
        // memset(chars, 0, sizeof(chars));
    }
    fclose(tmp);
    tmp = NULL;
}

