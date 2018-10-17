
// Author: Pierce Brooks

#include "mruby_usage.h"
#include "mruby/compile.h"
#include "mruby/mruby.h"
#include "mruby/string.h"
#include <memory.h>
#include <android/log.h>

// http://mruby.org/docs/articles/executing-ruby-code-with-mruby.html
// http://mruby.org/docs/api/headers/mruby.h.html#mrb_funcall_argv-function
// https://stackoverflow.com/questions/30746108/reading-the-return-value-of-mruby-program-via-c

void showMessage(const char* message)
{
    __android_log_print(ANDROID_LOG_VERBOSE, "MRUBY", "%s", message);
}

void showNumber(int number)
{
    __android_log_print(ANDROID_LOG_VERBOSE, "MRUBY", "%d", number);
}

void show(const char* message, int number)
{
    __android_log_print(ANDROID_LOG_VERBOSE, "MRUBY", "%s %d", message, number);
}

int run(const char* script, const char* entry)
{
    mrb_state* state = mrb_open();
    if (state == NULL)
    {
        return -1;
    }
    //mrb_init_mrblib(state);
    //mrb_init_mrbgems(state);

    // mrb_load_nstring() for strings without null terminator or with known length
    int code = 0;
    int ai = mrb_gc_arena_save(state);

    mrb_value value = mrb_load_string(state, script);
    if (state->exc)
    {
        if (!mrb_undef_p(value))
        {
            code = -2;
        }
    }

    if (code == 0)
    {
        value = mrb_funcall_argv(state, mrb_top_self(state), mrb_intern_cstr(state, entry), 0, NULL);
        if (state->exc)
        {
            if (!mrb_undef_p(value))
            {
                code = -3;
            }
        }

        if (code == 0)
        {
            showNumber((int)value.tt);
            //showNumber((int)value.value.i);
            switch (value.tt)
            {
                case MRB_TT_TRUE:
                    showMessage("Yes, ma'am!");
                    break;
                case MRB_TT_FALSE:
                    showMessage("No, sir!");
                    break;
                case MRB_TT_STRING:
                    showMessage(mrb_str_to_cstr(state, value));
                    break;
                default:
                    code = -4;
                    break;
            }
        }
    }

    if (code != 0)
    {
        show("Error:", code);
    }

    mrb_gc_arena_restore(state, ai);
    //mrb_final_mrbgems(state);
    mrb_close(state);
    return code;
}
