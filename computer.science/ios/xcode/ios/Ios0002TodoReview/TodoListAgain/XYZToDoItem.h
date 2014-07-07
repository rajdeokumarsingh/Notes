//
//  XYZToDoItem.h
//  TodoListAgain
//
//  Created by Jiang Rui on 14-1-26.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface XYZToDoItem : NSObject

@property NSString *itemName;

@property BOOL completed;

@property (readonly) NSDate *creationDate;


@end
