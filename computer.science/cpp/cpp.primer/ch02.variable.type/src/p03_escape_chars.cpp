#include <iostream>
using namespace std;

int main(int argc, char ** argv) {
    cout <<"\\n" << "\n end" << endl;
    cout <<"\\t" << "\t end"<< endl;
    cout <<"\\v" << "\v end" << endl;
    cout <<"\\b" << "\b end" << endl;
    cout <<"\\r" << "\r end" << endl;
    cout <<"\\f" << "\f end"<< endl;

    // \ooo: ooo is an octal for a char
    cout <<"\\115:" << "\115" << endl;
    cout <<"\\062:" << "\062" << endl;

    // \xddd: xddd is a hexadecimal for a char
    cout <<"\\10:" << "\x62" << endl;

    cout <<"wchar_t 'a': " << L'a' << endl;
    cout <<"wchar_t str: " << L"Hello world" << endl;

    cout << "string connection: " "part 1, " "part 2, " "end" << endl;
    cout << "string connection 1: "
        "part 1, "
        "part 2, "
        "end" << endl;
    cout << "string connection2 : part 1,\
        part 2, end" << endl;  // seems not good

    // compile error: escape newline
    // cout << "split keyword" << en\dl;

    return 0;
}
