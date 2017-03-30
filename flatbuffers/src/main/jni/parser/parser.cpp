//
// Created by Miroslaw Stanek on 26.10.2015.
//

#ifndef __MAIN_H__
#define __MAIN_H__

#include <flatbuffers/idl.h>
#include <android/log.h>
#include <stdbool.h>
#include "parser.h"

#define  LOG_TAG    "FlatBufferParserJNI"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

JNIEXPORT jbyteArray JNICALL
Java_org_kazminov_flatbuffers_FlatBufferParser_parseJsonNative(JNIEnv *env,
                                                                      jobject instance,
                                                                      jstring json_,
                                                                      jstring schema_) {
    const char *json = env->GetStringUTFChars(json_, 0);
    const char *schema = env->GetStringUTFChars(schema_, 0);

    flatbuffers::Parser parser;
    bool schemaOk = parser.Parse(schema);
    bool jsonOk = parser.Parse(json);
    bool ok = schemaOk && jsonOk;

    LOGD("parseJsonNative: schemaOk=%d, jsonOk=%d", schemaOk, jsonOk);

    env->ReleaseStringUTFChars(json_, json);
    env->ReleaseStringUTFChars(schema_, schema);

    if (ok) {
        flatbuffers::uoffset_t length = parser.builder_.GetSize();
        jbyteArray result = env->NewByteArray(length);
        uint8_t *bufferPointer = parser.builder_.GetBufferPointer();
        env->SetByteArrayRegion(result, 0, length, reinterpret_cast<jbyte *>(bufferPointer));
        return result;
    }

    return NULL;
}

#endif // __MAIN_H__