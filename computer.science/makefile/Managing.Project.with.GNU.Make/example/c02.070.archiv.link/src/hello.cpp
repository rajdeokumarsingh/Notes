#include <iostream>
#include "lucky.h"
#include "funny.h"

using namespace std;

int main(int argc, char ** argv) {
    cout << "Get lucky: " << generateLockNumber() << endl;
    cout << "Get funny: " << getFun() << endl;

    return 0;
}

