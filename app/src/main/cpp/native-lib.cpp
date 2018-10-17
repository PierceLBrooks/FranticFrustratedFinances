
// Author: Pierce Brooks

#include <jni.h>
#include <string>
#include "mruby_usage.h"

extern "C"
{
JNIEXPORT jstring JNICALL Java_com_piercelbrooks_f3_MainActivity_runScript(JNIEnv* env, jobject thiz, jstring script, jstring entry)
{
    std::string hello = "Hello from C++";
    showMessage("before test");
    const char* scriptNative = env->GetStringUTFChars(script, NULL);
    const char* entryNative = env->GetStringUTFChars(entry, NULL);
    int result = run(scriptNative, entryNative);
    env->ReleaseStringUTFChars(script, scriptNative);
    env->ReleaseStringUTFChars(entry, entryNative);
    showMessage("after test");
    hello += " ("+std::to_string(result)+")";
    return env->NewStringUTF(hello.c_str());
}
};
