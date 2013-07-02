#include <iostream>

#include <encode.h>
#include <decode.h>
#include <db.h>
#include <ui.h>

using namespace std;

int main(int argc, char ** argv) {
    cout << "enter main ..." << endl;
    encode();
    decode();
    query();
    draw();

    cout << "quit main ..." << endl;
    return 0;
}
