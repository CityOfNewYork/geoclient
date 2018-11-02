#include <jni.h>
//#include <stdlib.h>
//#include <string.h>
//
//#include <assert.h>
//#include <stddef.h>

 #include "geoclient.h"

/**
 *
 * JNI C API implementation of the Geoclient Java API interface:
 *
 *     gov.nyc.doitt.gis.geoclient.jni.Geoclient
 *          #callGeo(ByteBuffer workAreaOne, ByteBuffer workAreaTwo)
 *
 * The actual Java implementation class is:
 *
 *     gov.nyc.doitt.gis.geoclient.jni.GeoclientJni
 *
 * and the actual call is made from GeoclientJni's private method:
 *
 *     private native void callgeo1( Object work_area1,
 *         int work_area1_byte_offset,
 *         boolean work_area1_is_direct,
 *         Object work_area2,
 *         int work_area2_byte_offset,
 *         boolean work_area2_is_direct );
 *
 * This file does not call the Geosupport C API directly, but instead calls
 * C function:
 *
 *     void callgeo(char *  work_area1, char *  work_area2)
 *
 * located in file geoclient.c and imported above using geosupport.h.
 *
 * Geoclient separates C sources into those that deal with the JNI API
 * (i.e., this file) and those that actually call the Geosupport C API. This
 * is to make it easier to use Gradle to build the various platform, architecture,
 * and operating system combinations that Geosupport runs on.
 *
 * NOTE:
 *
 *      This file was originally generated using Gluegen version 2.3.2.
 *
 */
JNIEXPORT void JNICALL
Java_gov_nyc_doitt_gis_geoclient_jni_GeoclientJni_callgeo1(
        JNIEnv *env, jobject _unused,
        jobject work_area1,
        jint work_area1_byte_offset,
        jboolean work_area1_is_nio,
        jobject work_area2,
        jint work_area2_byte_offset,
        jboolean work_area2_is_nio) {

    char * _work_area1_ptr = NULL;
    char * _work_area2_ptr = NULL;

    if ( NULL != work_area1 ) {
        _work_area1_ptr =
            (char *) ( JNI_TRUE == work_area1_is_nio ?  (*env)->GetDirectBufferAddress(env, work_area1) :  (*env)->GetPrimitiveArrayCritical(env, work_area1, NULL) );
    }

    if ( NULL != work_area2 ) {
        _work_area2_ptr =
            (char *) ( JNI_TRUE == work_area2_is_nio ?  (*env)->GetDirectBufferAddress(env, work_area2) :  (*env)->GetPrimitiveArrayCritical(env, work_area2, NULL) );
    }

    callgeo((char * ) (((char *) _work_area1_ptr) + work_area1_byte_offset), (char * ) (((char *) _work_area2_ptr) + work_area2_byte_offset));

    if ( JNI_FALSE == work_area1_is_nio && NULL != work_area1 ) {
        (*env)->ReleasePrimitiveArrayCritical(env, work_area1, _work_area1_ptr, 0);
    }

    if ( JNI_FALSE == work_area2_is_nio && NULL != work_area2 ) {
        (*env)->ReleasePrimitiveArrayCritical(env, work_area2, _work_area2_ptr, 0);
    }
}
