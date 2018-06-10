package edu.tamu.jcabelloc.datafiledemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    public void saveImage(View view){
        String imgFile = "ImgFile.png";
        try {
            OutputStream outputStream;
            outputStream = openFileOutput(imgFile, Context.MODE_PRIVATE);
            Bitmap myImage = getImage();
            myImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getImage() {
        ImageDownloader task = new ImageDownloader();

        Bitmap myImage = null;
        try {
            myImage = task.execute("https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png").get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return myImage;

    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
