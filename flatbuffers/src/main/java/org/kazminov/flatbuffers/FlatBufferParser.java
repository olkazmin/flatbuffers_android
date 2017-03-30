package org.kazminov.flatbuffers;

/**
 * Created by olkazmin on 30.03.17.
 */

public class FlatBufferParser {


    static {
        System.loadLibrary("flatbuffers");
    }



    public native byte[] parseJsonNative(String json, String schema);
}
