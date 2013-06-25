#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, const char* argv[]) {
  char a[10];
  char b[20];
  memset(a, 0, sizeof(a));
  memset(b, 0, sizeof(b));

  printf("Hello, World!!");
  return 0;
}