#include <iostream>
using namespace std;

int main(int argc, char ** argv) {
    cout << "please input two number:" << endl;

    int v1 = 0;
    int v2 = 0;
    cin >> v1 >> v2;
    // equals:
    // (cin >> v1)>> v2;
    // (cin >> v1) returns cin

    cout << "The numbers are: " << v1 << ", " << v2 << endl;
    return 0;
}
