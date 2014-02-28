//
//  Tire.h
//  Constructor
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Tire : NSObject {
    float pressure;
    float treadDepth;
}

- (id) initPressure : (float) p
         withTreadDepth: (float) d;

- (void) setPressure: (float) p;
- (void) setTreadDepth: (float) depth;

- (float) getPressure;
- (float) getTreadDepth;

@end
