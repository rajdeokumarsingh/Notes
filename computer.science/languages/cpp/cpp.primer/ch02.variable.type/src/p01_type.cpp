#include <iostream>
using namespace std;

int main(int argc, char ** argv) {
    bool b = 0;
    char c = 0;
    wchar_t wc = 0;
    short s = 0;
    int i = 0;
    long l = 0;
    float f = 0;
    double d = 0;
    long double ld = 0;
    // default is signed type

    cout << "sizeof bool:" << sizeof(b) << endl;
    cout << "sizeof char:" << sizeof(c) << endl;
    cout << "sizeof wchar_t:" << sizeof(wc) << endl;
    cout << "sizeof short:" << sizeof(s) << endl;
    cout << "sizeof int:" << sizeof(i) << endl;
    cout << "sizeof long:" << sizeof(l) << endl;
    cout << "sizeof float:" << sizeof(f) << endl;
    cout << "sizeof double:" << sizeof(d) << endl;
    cout << "sizeof long double:" << sizeof(ld) << endl;

    /*
        sizeof bool:1
        sizeof char:1
        sizeof wchar_t:4 , in 64-bit linux, 2 in 32-bit ??
        sizeof short:2
        sizeof int:4
        sizeof long:8
        sizeof float:4
        sizeof double:8
        sizeof long double:16
    */

    return 0;
}
