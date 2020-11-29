#include "TestJNI.h"

JNIEXPORT void JNICALL Java_TestJNI_say (JNIEnv *env, jobject obj) {
	printf("hello world");
}
