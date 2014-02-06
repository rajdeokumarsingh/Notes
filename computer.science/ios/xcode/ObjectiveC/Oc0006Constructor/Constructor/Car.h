//
//  Car.h
//  Constructor
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
@class Tire;

@interface Car : NSObject {
    NSString * mName;
    
    Tire * mTires[2];
    NSMutableArray * mMutableTires;
}

- (void) addTire : (id) car;
@end
