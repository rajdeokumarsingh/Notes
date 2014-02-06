//
//  AllWeatherTire.h
//  Constructor
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "Tire.h"

@interface AllWeatherTire : Tire {
    NSString * mName;
    float mRainHandling;
}

@property float rainHandling;
@property NSString * name;

- (id) initWithPressure : (float) p
              treadDepth: (float) d;

@end
