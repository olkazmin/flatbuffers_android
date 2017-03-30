package org.kazminov.myflattbufsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.kazminov.fbsdemo.model.Users;
import org.kazminov.flatbuffers.FlatBufferParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);

        final String strUsersJson = readAsset("users.json");
        final String strUsersSchema = readAsset("Users.fbs");

        final FlatBufferParser flatBufferParser = new FlatBufferParser();
        final long starTime = System.currentTimeMillis();
        final byte[] bytes = flatBufferParser.parseJsonNative(strUsersJson, strUsersSchema);
        final long endTime = System.currentTimeMillis();

        final String parsingResultString = String.format("Parsing result: ok=%b, time=%d ms", bytes != null, endTime - starTime);

        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        final Users users = Users.getRootAsUsers(byteBuffer);
        users.usersLength();

        final String result = parsingResultString + String.format(", users count=%d", users.usersLength());
        tv.setText(result);
    }

    public String readAsset(String path) {
        try {
            final InputStream stream = getAssets().open(path);
            final byte[] bytes = readAsByteArray(stream);
            return new String(bytes);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static byte[] readAsByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[READ_BUFFER_SIZE];
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    private static final int READ_BUFFER_SIZE = 1024;
}
