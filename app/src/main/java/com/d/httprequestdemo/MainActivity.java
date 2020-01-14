package com.d.httprequestdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    // API
    String url = "https://jsonplaceholder.typicode.com/todos/1";

    String urlPost = "https://jsonplaceholder.typicode.com/posts";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvText);

//        MyTask myTask = new MyTask();
//        myTask.execute(url);
//
//

        PostTask postTask = new PostTask();
        postTask.execute(urlPost);


    }

    public class PostTask extends AsyncTask<String,Long,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                //Content-Type: application/x-www-form-urlencoded
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");


                // khoi tao param
                StringBuilder params = new StringBuilder();

                params.append("title");
                params.append("=");
                params.append("Hello");

                params.append("&");
                params.append("body");
                params.append("=");
                params.append("HuyNguyen");

                params.append("&");
                params.append("userId");
                params.append("=");
                params.append("123456789");






                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter
                        (new OutputStreamWriter(os, "UTF-8"));

                // dua param vao body cua request
                writer.append(params);

                // giai phong bo nho
                writer.flush();
                // ket thuc truyen du lieu vao output
                writer.close();
                os.close();


                // lay du lieu tra ve
                StringBuilder response = new StringBuilder();

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
                else if(responseCode == 201){
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
                else {
                    return  httpURLConnection.getResponseCode()+"";
                }


                return response.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Message",e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }

    public class MyTask extends AsyncTask<String, Long, String> {


        @Override
        protected String doInBackground(String... strings) {

            String link = strings[0];

            try {
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                String data = "";

                Scanner scanner
                        = new Scanner(inputStream);
                while (scanner.hasNext()) {
                    data = data + scanner.nextLine();
                }
                scanner.close();

                return data;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String ketQua) {
            super.onPostExecute(ketQua);

            textView.setText(ketQua);

        }
    }

}
