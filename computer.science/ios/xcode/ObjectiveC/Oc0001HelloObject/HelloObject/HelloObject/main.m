//
//  main.m
//  HelloObject
//
//  Created by Jiang Rui on 14-1-15.
//  Copyright (c) 2014年 Jiang Rui. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "shape_types.h"
#import "Shape-Objects.h"


int main(int argc, const char * argv[])
{
    @autoreleasepool {
        
        // insert code here...
        NSLog(@"Hello, World!");

        ShapeRect rect = {0, 0, 30, 40};
        id circle = [Circle new];
        [circle setBounds:(ShapeRect) rect];
        [circle setFillColor: (ShapeColor) kBlueColor];
        [circle draw];
        
        rect.heigth = 200;
        id rectangle = [Rectangle new];
        [rectangle setBounds:(ShapeRect) rect];
        [rectangle setFillColor: (ShapeColor) kBlueColor];
        [rectangle draw];
        
    }
    
    return 0;
}

