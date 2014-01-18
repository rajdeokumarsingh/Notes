//
//  shape_types.h
//  HelloObject
//
//  Created by Jiang Rui on 14-1-15.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#ifndef HelloObject_shape_types_h
#define HelloObject_shape_types_h

typedef enum {
    kRedColor,
    kBlueColor,
    kGreenColor
} ShapeColor;

typedef struct {
    int x;
    int y;
    int width;
    int heigth;
} ShapeRect;



#endif
