
// Author: Pierce Brooks

#ifndef FTHREE_MRUBY_USAGE_H
#define FTHREE_MRUBY_USAGE_H

#include <sys/types.h>
#include <mruby/mruby.h>

#define LOG_LEVEL (ANDROID_LOG_VERBOSE)
#define LOG_TAG ("MRUBY")

#ifdef __cplusplus
extern "C"
{
#endif

extern pthread_mutex_t* mutex;
extern mrb_state* state;

void showMessage(const char* message);
void showNumber(int number);
void show(const char* message, int number);
int begin();
int end();
char* run(const char* script, const char* entry);

#ifdef __cplusplus
}
#endif

#endif
