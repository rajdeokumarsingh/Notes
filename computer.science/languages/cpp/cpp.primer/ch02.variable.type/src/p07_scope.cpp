#include <iostream>
using namespace std;

string str1("string in global scope");

int main(int argc, char ** argv) {
    string str2("string in local scope");

    cout << "str1: " << str1 << endl;
    cout << "str2: " << str2 << endl;

    // FIXME: MUST avoid such situation
    int str1 = 50;
    cout << "str1 is override by an integer." << endl;
    cout << "str1: " << str1 << endl;

    /* TODO: scope:
        1. class scope
        2. namespace scope
     */

    return 0;
}
