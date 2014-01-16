//
//  Shape-Objects.m
//  HelloObject
//
//  Created by Jiang Rui on 14-1-15.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import "Shape-Objects.h"

@implementation Circle

- (void) setFillColor: (ShapeColor) color {
    if (color == kBlueColor) {
        color = kRedColor;
    }
    
    [super setFillColor:color];
}

- (void) draw {
    NSLog(@"draw a circle, rect: [%d, %d, %d, %d], color: %d",
          bounds.x, bounds.y, bounds.width, bounds.heigth, fillColor);
    
}

@end

@implementation Rectangle

- (void) draw {
    NSLog(@"draw a rectangle, rect: [%d, %d, %d, %d], color: %d",
          bounds.x, bounds.y, bounds.width, bounds.heigth, fillColor);
}

@end
