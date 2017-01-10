#include <jni.h>
#include <string>

JNIEXPORT jstring JNICALL
Java_com_sven_develop_MainActivity_exceptionFromJNI(JNIEnv *env, jobject instance) {
    std::string hello = NULL;
    return env->NewStringUTF(hello.c_str());
}

extern "C"
jstring
Java_com_sven_develop_MainActivity_stringFromJNI(JNIEnv* env,jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


