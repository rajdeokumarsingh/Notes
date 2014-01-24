//
//  Mother.h
//  Delegate
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "HomeJob.h"

@interface Mother : NSObject {
    id<HomeJob> delegate;
}

- (id) initWithDelegate: (id<HomeJob>) delegate;

- (void) start;

@end
