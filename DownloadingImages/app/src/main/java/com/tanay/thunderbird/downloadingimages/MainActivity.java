package com.tanay.thunderbird.downloadingimages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedImg;

    public void downloadImage(View view)
    {
        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;

        try
        {
            myImage = task.execute(" // https://www.theurbangent.com/wp-content/uploads/2011/07/neal-caffrey-style-tug-white-collar-2.jpg").get();
            downloadedImg.setImageBitmap(myImage);
        }

        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        catch (Exception e)                                                                  // the image might not be properly downloaded, so for that we add this general catch manually
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadedImg = (ImageView) findViewById(R.id.imageView);
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>                      // Bitmap for the image
    {

        @Override
        protected Bitmap doInBackground(String... strings) {

            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();                                                         // to connect to our connection, duh
                InputStream inputStream = connection.getInputStream();                        // downloads the image in one single go, contrary to the downloading of characters
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);                    // converts the downloaded data into an image

                return myBitmap;
            }

            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }
}
