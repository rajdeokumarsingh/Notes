#include <iostream>
using namespace std;

/* declare and define

declare:
    not allocate memory
    One variable could be declared more than once

define:
    allocate memory
    One variable could be defined only once
*/


int gInteger;       // declare and define
string gString;     // declare and define

extern char * gOutsizeVar;  // only declare

extern double pi = 3.1415926; // define with extern, strange, warning

int main(int argc, char ** argv) {
    // FIXME: error
    // extern double pii = 3.1415926; 

    return 0;
}
