//
//  MyObject.h
//  Constructor
//
//  Created by Jiang Rui on 14-1-16.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MyObject : NSObject {
    NSString * mName;
    NSNumber * mAge;
}

- (NSString *) getName;

- (NSNumber *) getAge;

@end
