package com.tanay.thunderbird.weathernow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView resultTextView;

    public class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try
            {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1)
                {
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }

                return result;
            }

            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "Could not find the weather!", Toast.LENGTH_LONG).show();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try
            {
                String message = "";

                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content: ", weatherInfo);

                JSONArray array = new JSONArray(weatherInfo);
                int n = array.length();

                for(int i=0; i<n; i++)
                {
                    JSONObject jsonPart = array.getJSONObject(i);

                    String main = "";
                    String description = "";
                    main =  jsonPart.getString("main");
                    description =  jsonPart.getString("description");

                    if((main != "")&&(description != ""))
                    {
                        message += main + ": " + description + "\r\n";                                     // \r\n for a line break in java
                    }
                }

                if(message != "")
                {
                    resultTextView.setText(message);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Could not find the weather!", Toast.LENGTH_LONG).show();
                }

            }

            catch(JSONException e)
            {
                Toast.makeText(getApplicationContext(), "Could not find the weather!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void findWeather(View view)
    {
        Log.i("cityName: ", cityName.getText().toString());

        InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);                    // to remove keyboard from display
        manager.hideSoftInputFromWindow(cityName.getWindowToken(),0);

        try
        {
            String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");
            DownloadTask task = new DownloadTask();
            task.execute("http://api.openweathermap.org/data/2.5/weather/?q=" + encodedCityName);
        }

        catch(UnsupportedEncodingException e)
        {
            Toast.makeText(getApplicationContext(), "Could not find the weather!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        cityName = (EditText)findViewById(R.id.cityName);
    }
}
