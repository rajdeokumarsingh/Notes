//
//  HomeJob.h
//  Delegate
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol HomeJob <NSObject>

@required
- (void) takeEat;

@optional
- (void) takeSleep;
- (void) takeBath;

@end
