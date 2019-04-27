// nativevs.cpp : 定义 DLL 应用程序的入口点。
//

#include "jeo_core_VSTFactory.h"
#include "windows.h"
#include "vst.h"

/*
BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
					 )
{
    return TRUE;
}
*/

JNIEXPORT jbyteArray JNICALL Java_jeo_core_VSTFactory_process 
  (JNIEnv *env, jclass c, jbyteArray arr, jint size) 
{
	jboolean copyd;
	jbyte* data = env->GetByteArrayElements( arr, &copyd );

	if (copyd) {
		processing( (LPBYTE)data, size );
		//env->SetByteArrayRegion(arr, 0, size, (const jbyte*)data);
	}
	env->ReleaseByteArrayElements(arr, data, 0);

	return arr;
}

/*
 * Class:     jeo_core_VSTFactory
 * Method:    closeAllVst
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jeo_core_VSTFactory_closeAllVst
  (JNIEnv *, jobject)
{
	closeAllVst();
}

/*
 * Class:     jeo_core_VSTFactory
 * Method:    openAllVst
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jeo_core_VSTFactory_openAllVst
  (JNIEnv *, jobject)
{
	openAllVst();
}

/*
 * Class:     jeo_core_VSTFactory
 * Method:    openEdit
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jeo_core_VSTFactory_openEdit
  (JNIEnv *, jobject)
{
	openEdit();
}

/*
 * Class:     jeo_core_VSTFactory
 * Method:    setBufferSize
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_jeo_core_VSTFactory_setBufferSize
  (JNIEnv *, jobject, jint bsize)
{
	setBufferSize( (int)bsize );
}

/*
 * Class:     jeo_core_VSTFactory
 * Method:    setSampleRate
 * Signature: (F)V
 */
JNIEXPORT void JNICALL Java_jeo_core_VSTFactory_setSampleRate
  (JNIEnv *, jobject, jfloat rate)
{
	setSampleRate( rate );
}

/*
 * Class:     jeo_core_VSTFactory
 * Method:    vstIsOpen
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_jeo_core_VSTFactory_vstIsOpen
  (JNIEnv *, jobject)
{
	return vstIsOpen();
}
