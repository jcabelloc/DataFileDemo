package edu.tamu.jcabelloc.datafiledemo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String filename = "testFile";
    String fileContents = "Test Message!";
    FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("File Directory", getApplicationContext().getFilesDir().toString());
        Log.d("Cache Directory", getApplicationContext().getCacheDir().toString());
    }

    public void saveTestFile(View view){
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void createCacheFile(View view) {
        String url = "https://developer.android.com/topic/libraries/architecture/room";
        File file;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, ".html", getApplicationContext().getCacheDir());
        } catch (IOException e) {
            // Error while creating file
            e.printStackTrace();
        }
        //return file;
    }

    public void createFile(View view) {
        String fileName = "textFile.txt";
        Log.d("Create File: ", "Inside");
        File directory = getApplicationContext().getFilesDir();
        File file = new File(directory, fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
