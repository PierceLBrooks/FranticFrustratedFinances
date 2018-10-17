
#include <jni.h>
#include <string>
#include "mruby_usage.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_piercelbrooks_f3_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    int result = test();
    hello += " ("+std::to_string(result)+")";
    return env->NewStringUTF(hello.c_str());
}
