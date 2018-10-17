
// Author: Pierce Brooks

#include "mruby_usage.h"
#include <memory.h>
#include <android/log.h>

// http://mruby.org/docs/articles/executing-ruby-code-with-mruby.html

void show(const char* message)
{
    __android_log_print(ANDROID_LOG_VERBOSE, "MRUBY", "%s", message);
}

int test()
{
    mrb_state* state = mrb_open();
    if (state == NULL)
    {
        return -1;
    }
    //mrb_init_mrbgems(state);
    // mrb_load_nstring() for strings without null terminator or with known length
    int code = 0;
    int ai = mrb_gc_arena_save(state);
    mrb_value value = mrb_load_string(state, "puts 'Hello, world!'");
    switch (value.tt)
    {
        case MRB_TT_TRUE:
            show("Yes, ma'am!");
            break;
        case MRB_TT_FALSE:
            show("No, sir!");
            break;
        default:
            code = -2;
    }
    mrb_gc_arena_restore(state, ai);
    //mrb_final_mrbgems(state);
    mrb_close(state);
    return code;
}
