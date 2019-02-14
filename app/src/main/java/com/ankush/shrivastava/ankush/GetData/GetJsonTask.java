package com.ankush.shrivastava.ankush.GetData;

import android.os.AsyncTask;
import android.util.Log;

import com.ankush.shrivastava.ankush.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

    public class GetJsonTask extends AsyncTask<String,Void,String>  {

        private String ApiUrl="http://test.terasol.in/moviedata.json";
       dataRecieved dataRecieved;

       public GetJsonTask(dataRecieved dataRecieved){
           this.dataRecieved=dataRecieved;
       }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            URL url;
            InputStream stream;

            try {
                url = new URL(ApiUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                stream = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                result = getData(reader);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dataRecieved.onDataRecieved(new JSONArray(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("hhhhhhhhhhhhhhhhhhhhd","hhhhhhhhhhhhhhhhhhhhd");
            Log.i("dhhh_length_hhhd",String.valueOf(result.length()));
            return "";
        }


        public String getData(BufferedReader reader) throws IOException {
            StringBuffer result = new StringBuffer();
            String temp = "";
            while ((temp = reader.readLine()) != null) {
                result.append(temp + "\n");
            }
            return result.toString();
        }

    }
