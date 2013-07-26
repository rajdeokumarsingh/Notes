#include <iostream>
using namespace std;

int main(int argc, char ** argv) {
    cout << "please input numbers:" << endl;

    int value = 0;
    int sum = 0;
    // EOS(end-of-line) is:  
    // ctrl-d (linux)
    // ctrl-z (windows)
    while(cin >> value) {
        sum += value;
    }

    cout << "The sum is: " << sum << endl;
    return 0;
}
