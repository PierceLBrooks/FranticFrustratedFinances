
// Author: Pierce Brooks

#ifndef FTHREE_MRUBY_USAGE_H
#define FTHREE_MRUBY_USAGE_H

#ifdef __cplusplus
extern "C"
{
#endif

void showMessage(const char* message);
void showNumber(int number);
void show(const char* message, int number);
int run(const char* script, const char* entry);

#ifdef __cplusplus
}
#endif

#endif
