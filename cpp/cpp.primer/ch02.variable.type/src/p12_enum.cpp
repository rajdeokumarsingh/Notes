#include <iostream>
using namespace std;

enum open_modes {input, output, append};
enum WeekDay {Monday = 1, Tuesday = 2, Wednsday, Thursday};
enum Point {point2d = 2, point2w, point3d = 3, point3w};

int main(int argc, char ** argv) {

    cout << input << ", " 
        << output << ", " 
        << append << endl;

    cout << Monday << ", " 
        << Tuesday << ", " 
        << Wednsday << endl;

    cout << point2d << ", " 
        << point2w << ", " 
        << point3d << ", "
        << point3w << endl;
    return 0;
}
