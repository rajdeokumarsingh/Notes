#include <iostream>
using namespace std;

int main(int argc, char ** argv) {
    const double pi = 3.1415926;
    // pi = 0.0 // error

    const double pii(3.1415926);

    // FIXME: got error,  must initialize a const variable
    // const int intNotInit;
    
    const string str1("const string");
    const string emptyStr; // default ""

    // const reference
    const int MAX_INT = 500;
    const int &max = MAX_INT;
    // error:
    // int &max2 = MAX_INT;

    const int &ref = 42; // ok
    // int &ref2 = 42; // error

    double dnum = 3.3434;
    // int &inum = dnum; // error
    const int & cinum = dnum;

    return 0;
}
