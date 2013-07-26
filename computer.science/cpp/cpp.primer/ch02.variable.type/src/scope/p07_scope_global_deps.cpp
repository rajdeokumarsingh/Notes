#include <iostream>

using namespace std;

// default is extern
string gString("string in global scope");

// FIXME: const global only constrain in this file
// const string cgString("const string in global scope");
extern const string cgString("const string in global scope");


