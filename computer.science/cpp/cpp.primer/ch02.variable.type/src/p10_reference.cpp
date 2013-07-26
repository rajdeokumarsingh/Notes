#include <iostream>
using namespace std;

int main(int argc, char ** argv) {
    double pi = 3.1415926;

    // FIXME: A reference is an alias to other variable
    double &ref = pi;

    ref = 2.387;
    cout << "reference: " << pi << endl;

    // FIXME: error
    // int &rel_to_no_var;
    // int &rel_to_value = 0;

    return 0;
}
