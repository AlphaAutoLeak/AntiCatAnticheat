#include "pch.h"
#include "jni.h"
#include <malloc.h>


char* jstringToChar(JNIEnv* env, jstring jstr) {

	char* rtn = NULL;

	jclass clsstring = env->FindClass("java/lang/String");

	jstring strencode = env->NewStringUTF("GB2312");

	jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");

	jbyteArray barr = (jbyteArray)env->CallObjectMethod(jstr, mid, strencode);

	jsize alen = env->GetArrayLength(barr);

	jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);

	if (alen > 0) {

		rtn = (char*)malloc(alen + 1);

		memcpy(rtn, ba, alen);

		rtn[alen] = 0;

	}

	env->ReleaseByteArrayElements(barr, ba, 0);

	return rtn;

}


extern "C" JNIEXPORT void JNICALL init1
(JNIEnv * env, jclass)
{
}

extern "C" JNIEXPORT jboolean JNICALL init2
(JNIEnv * env, jclass)
{
	return true;
}

extern "C" JNIEXPORT jint JNICALL codeVerify
(JNIEnv * env, jclass)
{
	jclass reg = env->FindClass("com/alphaautoleak/dynamic/DynamicRegisterer");

	jmethodID id = env->GetStaticMethodID(reg,"getVerifyCode","()I");

	return env->CallStaticIntMethod(reg, id);
}


extern "C" JNIEXPORT jint JNICALL network
(JNIEnv * env, jclass, jint wtf)
{
	return wtf ^ 1074135009;
}

extern "C" JNIEXPORT jobject JNICALL message
(JNIEnv * env, jclass, jobject obj)
{
	return NULL;
}

extern "C" void regSingleNative(JNIEnv* env ,jclass clazz , char* name , char* desc , void* ptr) {

	JNINativeMethod jnm[1];

	jnm[0].name = name;
	jnm[0].signature = desc;
	jnm[0].fnPtr = ptr;

	env->RegisterNatives(clazz, jnm, sizeof(jnm) / sizeof(jnm[0]));

}

extern "C" JNIEXPORT void JNICALL regNative
(JNIEnv * env, jclass ,jstring name , jstring desc)
{

	jclass nativeloader = env->FindClass("moe/catserver/mc/cac/NativeLoader");

	char* n = jstringToChar(env, name);
	char* d = jstringToChar(env, desc);

	if (strcmp(d, "()Z") == 0)
	{
		regSingleNative(env, nativeloader, n, d, (void*)init2);
	}
	if (strcmp(d,"()V") == 0)
	{
		regSingleNative(env, nativeloader, n, d, (void*)init1);
	}
	if (strcmp(d, "()I") == 0)
	{
		regSingleNative(env, nativeloader, n, d, (void*)codeVerify);
	}
	if (strcmp(d, "(I)I") == 0)
	{
		regSingleNative(env, nativeloader, n, d, (void*)network);
	}
	if (strcmp(d, "(Lmoe/catserver/mc/cac/NativeServerDynamicSandboxMessage;)Lmoe/catserver/mc/cac/NativeClientReportMessage;") == 0)
	{
		regSingleNative(env , nativeloader , n , d ,(void*)message);
	}

}



extern "C" JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{

	JNIEnv* env;

	if (vm->GetEnv((void**)&env, JNI_VERSION_1_8) != JNI_OK) {
		return -1;
	}

	jclass reg = env->FindClass("com/alphaautoleak/dynamic/DynamicRegisterer");

	JNINativeMethod jnm[1];

	jnm[0].name = (CHAR*)"registerFakeNative";
	jnm[0].signature = (CHAR*)"(Ljava/lang/String;Ljava/lang/String;)V";
	jnm[0].fnPtr = (void*)regNative;

	env->RegisterNatives(reg, jnm, sizeof(jnm) / sizeof(jnm[0]));

	jmethodID id = env->GetMethodID(reg,"<init>","()V");

	env->NewObject(reg,id); //call this shit

	return JNI_VERSION_1_8;
}
