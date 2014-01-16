//
//  Shape.m
//  
//
//  Created by Jiang Rui on 14-1-15.
//
//

#import "Shape.h"

@implementation Shape

- (void) setFillColor: (ShapeColor) color {
    self->fillColor = color;
}

- (void) draw {
    NSLog(@"draw a shape, rect: [%d, %d, %d, %d], color: %d",
          bounds.x, bounds.y, bounds.width, bounds.heigth, fillColor);
}

- (void) setBounds: (ShapeRect) b {
    self->bounds = b;
}

@end
