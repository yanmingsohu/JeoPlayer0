// CatfoOD 2009 6 10

#include "jeo_decoder_WmaPlayer.h"
#include "playnet.h"

/*
 * Class:     jeo_decoder_WmaPlayer
 * Method:    setVolume
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_jeo_decoder_WmaPlayer_setVolume
  (JNIEnv * env, jobject, jint vol)
{
	vol = vol>jeo_decoder_WmaPlayer_MAXVOLUME? jeo_decoder_WmaPlayer_MAXVOLUME: vol;
	vol = vol<0? 0: vol;

	setvolume( vol );
}

/*
 * Class:     jeo_decoder_WmaPlayer
 * Method:    play
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT jboolean JNICALL Java_jeo_decoder_WmaPlayer_play
  (JNIEnv * evn, jobject, jstring url)
{
	static WCHAR surl[512];
	ZeroMemory( surl, sizeof(surl) );

	jsize len = evn->GetStringLength( url );
	evn->GetStringRegion( url, 0, len, (jchar*)surl );

	if ( SUCCEEDED( play( surl ) ) ) {
		return JNI_TRUE;
	} else {
		return JNI_FALSE;
	}
}

/*
 * Class:     jeo_decoder_WmaPlayer
 * Method:    pause
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jeo_decoder_WmaPlayer_pause
  (JNIEnv *env, jobject)
{
	pause();
}

/*
 * Class:     jeo_decoder_WmaPlayer
 * Method:    resume
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jeo_decoder_WmaPlayer_resume
  (JNIEnv *env, jobject)
{
	resume();
}

/*
 * Class:     jeo_decoder_WmaPlayer
 * Method:    stop
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jeo_decoder_WmaPlayer_stop
  (JNIEnv *env, jobject)
{
	stop();
}
