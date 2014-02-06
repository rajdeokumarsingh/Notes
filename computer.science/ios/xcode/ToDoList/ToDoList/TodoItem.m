//
//  TodoItem.m
//  ToDoList
//
//  Created by Jiang Rui on 14-1-24.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "TodoItem.h"

@implementation TodoItem

- (void)markAsCompleted:(BOOL)isComplete {
    self.completed = isComplete;
    [self setCompletionDate];
}

- (void)setCompletionDate {
    if (self.completed) {
        self.completionDate = [NSDate date];
    } else {
        self.completionDate = nil;
    }
}
@end
