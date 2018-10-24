
// Author: Pierce Brooks

#include "mruby_usage.h"
#include <mruby/compile.h>
#include <mruby/string.h>
#include <memory.h>
#include <android/log.h>
#include <malloc.h>
#include <pthread.h>

#ifdef __cplusplus
extern "C"
{
#endif

// http://mruby.org/docs/articles/executing-ruby-code-with-mruby.html
// http://mruby.org/docs/api/headers/mruby.h.html#mrb_funcall_argv-function
// https://stackoverflow.com/questions/30746108/reading-the-return-value-of-mruby-program-via-c

pthread_mutex_t* mutex = NULL;
mrb_state* state = NULL;

void showMessage(const char* message)
{
    __android_log_print(LOG_LEVEL, LOG_TAG, "%s", message);
}

void showNumber(int number)
{
    __android_log_print(LOG_LEVEL, LOG_TAG, "%d", number);
}

void show(const char* message, int number)
{
    showMessage(message);
    showNumber(number);
}

int begin()
{
    int error = 0;
    if (mutex == NULL)
    {
        mutex = (pthread_mutex_t*)calloc(1, sizeof(pthread_mutex_t));
        pthread_mutex_init(mutex, NULL);
    }
    pthread_mutex_lock(mutex);
    if (state == NULL)
    {
        state = mrb_open();
        if (state == NULL)
        {
            error = -1;
        }
    }
    pthread_mutex_unlock(mutex);
    return error;
}

int end()
{
    pthread_mutex_lock(mutex);
    mrb_close(state);
    state = NULL;
    pthread_mutex_unlock(mutex);
    pthread_mutex_destroy(mutex);
    free(mutex);
    mutex = NULL;
    return 0;
}

char* run(const char* script, const char* entry)
{
    if (mutex == NULL)
    {
        return NULL;
    }
    pthread_mutex_lock(mutex);
    if (state == NULL)
    {
        return NULL;
    }

    char* result = NULL;
    int error = 0;
    int ai = mrb_gc_arena_save(state);
    mrb_value value = mrb_load_string(state, script);
    if (state->exc)
    {
        if (!mrb_undef_p(value))
        {
            mrb_print_error(state);
            error = -1;
        }
    }

    if (error == 0)
    {
        value = mrb_funcall_argv(state, mrb_top_self(state), mrb_intern_cstr(state, entry), 0, NULL);
        if (state->exc)
        {
            if (!mrb_undef_p(value))
            {
                mrb_print_error(state);
                error = -2;
            }
        }

        if (error == 0)
        {
            if (mrb_string_p(value))
            {
                char* output = mrb_str_to_cstr(state, value);
                size_t length = strlen(output);
                result = calloc(length+1, sizeof(char));
                for (size_t i = 0; i != length; ++i)
                {
                    result[i] = output[i];
                }
                result[length] = '\0';
            }
            else
            {
                error = -3;
            }
        }
    }

    if (error != 0)
    {
        show("Error:", error);
    }

    mrb_gc_arena_restore(state, ai);
    pthread_mutex_unlock(mutex);
    return result;
}

#ifdef __cplusplus
}
#endif
