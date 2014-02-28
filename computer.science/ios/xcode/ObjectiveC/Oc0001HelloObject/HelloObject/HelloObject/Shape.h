//
//  Shape.h
//  
//
//  Created by Jiang Rui on 14-1-15.
//
//

#import <Foundation/Foundation.h>
#include "shape_types.h"

@interface Shape : NSObject {
    ShapeColor fillColor;
    ShapeRect bounds;
}

- (void) setFillColor: (ShapeColor) fillColor;

- (void) setBounds: (ShapeRect) bounds;

- (void) draw;

@end
