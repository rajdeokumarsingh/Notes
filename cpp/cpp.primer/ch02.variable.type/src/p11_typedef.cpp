#include <iostream>
using namespace std;

typedef double wage;
typedef int number;
typedef const char * str;

int main(int argc, char ** argv) {
    wage w = 6.123;
    number num = 20;
    str s("test");

    cout << w << ", " << num << ", " << s << endl;
    return 0;
}
