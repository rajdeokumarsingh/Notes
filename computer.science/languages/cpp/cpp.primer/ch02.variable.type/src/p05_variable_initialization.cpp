#include <iostream>
using namespace std;

int gInteger;   // 0 for default
string gString;  // default constructor

int main(int argc, char ** argv) {

    double d1(32.3); // direct initialization, recommend
    double d2 = 50.0;  // copy initialization

    string str1("string init");
    string str2 = "string init2";
    string str3;    // default initialization, default constructor

    int localInt; // not initialized

    cout << "global integer: " << gInteger << endl;
    cout << "local integer: " << localInt << endl;
    return 0;
}
