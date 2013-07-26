#include <iostream>

using namespace std;

extern string gString;
extern string cgString;

int main(int argc, char ** argv) {
    cout << "gString is defined in p07_scope_global_deps.cpp:"
        << gString << endl;
    cout << "cgString is defined in p07_scope_global_deps.cpp:"
        << cgString << endl;
    return 0;
}
