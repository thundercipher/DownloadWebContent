package com.tanay.thunderbird.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> // <for url, method for showing progress of task (here no method, so void, type of var returned by doInBackground>
    {
        @Override
        protected String doInBackground(String... strings) {                          // String... is like a string array (kind of)
            Log.i("URL:", strings[0]);

            // code to actually download the content
            String result = "";
            URL url;                                                                  // not a string variable, has to have the data in the right format to be valid
            HttpURLConnection urlConnection = null;

            try
            {
                url = new URL(strings[0]);                                            // can give exception if the URL stored in strings[0] isn't proper
                urlConnection =  (HttpURLConnection) url.openConnection();            // to open the browser
                InputStream in = urlConnection.getInputStream();                      // to hold the input of the data
                InputStreamReader reader = new InputStreamReader(in);                 // to read the input data

                int data = reader.read();                                  // data is read character by character, and to keep track of the location that we're currently on through the html code

                while(data != -1)                                          // at the end of the reading, data has the value = -1
                {
                    char current = (char) data;
                    result += current;                                     // to append the read characters to the result string
                    data = reader.read();
                }

                return result;                                             // returned back to the original task and printed in the logs (in the onCreate method
            }

            catch (Exception e)
            {
                e.printStackTrace();
                return "FAILED!";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("https://www.facebook.com/").get();
        }

        catch (ExecutionException e) {
            e.printStackTrace();                // method to print all the info about the error that might occur
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }

            Log.i("Contents of the URL:", result);
    }
}
