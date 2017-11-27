package io.meta5.guesscelebrity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cyber1443 on 11/27/2017.
 */

public class GetData extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        String html = null;
        HttpURLConnection connection;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            int data = reader.read();
            while (data != -1){
                char current =(char)data;
                html += current;
                data = reader.read();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] splitedHTML = html.split("<div class=\"sidebarContainer\">");



        return splitedHTML[0];
    }
}
