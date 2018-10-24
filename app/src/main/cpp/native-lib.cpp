
// Author: Pierce Brooks

#include <jni.h>
#include <string>
#include "mruby_usage.h"

#ifdef __cplusplus
extern "C"
{
#endif

JNIEXPORT void JNICALL Java_com_piercelbrooks_roe_Ruby_begin(JNIEnv* env, jobject thiz)
{
    if (begin() != 0)
    {
        assert("Ruby failed to begin!");
    }
}

JNIEXPORT void JNICALL Java_com_piercelbrooks_roe_Ruby_end(JNIEnv* env, jobject thiz)
{
    if (end() != 0)
    {
        assert("Ruby failed to end!");
    }
}

JNIEXPORT jstring JNICALL Java_com_piercelbrooks_roe_Ruby_run(JNIEnv* env, jobject thiz, jstring script, jstring entry)
{
    std::string output;
    const char* scriptNative = env->GetStringUTFChars(script, NULL);
    const char* entryNative = env->GetStringUTFChars(entry, NULL);
    char* result = run(scriptNative, entryNative);
    env->ReleaseStringUTFChars(script, scriptNative);
    env->ReleaseStringUTFChars(entry, entryNative);
    if (result == NULL)
    {
        return NULL;
    }
    output = std::string(result, strlen(result));
    free(result);
    return env->NewStringUTF(output.c_str());
}

#ifdef __cplusplus
}
#endif
