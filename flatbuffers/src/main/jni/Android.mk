LOCAL_PATH:= $(call my-dir)


include $(CLEAR_VARS)

LOCAL_MODULE := flatbuffers

LOCAL_CPP_EXTENSION := .cpp
LOCAL_SDK_VERSION := 9
LOCAL_SRC_FILES := parser/parser.cpp \
    flatbufferslib/idl_gen_text.cpp \
    flatbufferslib/idl_parser.cpp

LOCAL_C_INCLUDES := $(LOCAL_PATH)/flatbufferslib

LOCAL_CFLAGS += -ffast-math -O3 -funroll-loops -Wall -fexceptions
LOCAL_LDLIBS += -landroid -lz -llog

LOCAL_ARM_MODE := arm

include $(BUILD_SHARED_LIBRARY)
