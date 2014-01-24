//
//  TodoItem.h
//  ToDoList
//
//  Created by Jiang Rui on 14-1-24.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TodoItem : NSObject

@property NSString *itemName;

@property BOOL completed;

@property NSDate *creationDate;

@property NSDate * completionDate;

- (void)markAsCompleted:(BOOL)isComplete;

@end
