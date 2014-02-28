//
//  CategoryThing.h
//  Category
//
//  Created by Jiang Rui on 14-1-22.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CategoryThing : NSObject {
    int thing1;
    int thing2;
    int thing3;
}
@end

@interface CategoryThing (Thing1)
- (void) setThing1: (int) thing1;

@end

@interface CategoryThing (Thing2)
- (void) setThing2: (int) thing2;

@end

@interface CategoryThing (Thing3)
- (void) setThing3: (int) thing3;

@end