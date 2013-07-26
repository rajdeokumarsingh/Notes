#include <iostream>
using namespace std;

int main(int argc, char ** argv) {
    wchar_t wc = L'a';

    int decimal = 20;
    int octal = 024;
    int hexadecimal = 0x56;

    long l = 0L;
    unsigned long ul = 1000UL;

    float f = 3.14F;
    double d = 3.34; // default
    long double ld = 3.34L; // L for extend
    double e = 5.13e10;

    return 0;
}
