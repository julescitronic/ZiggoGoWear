package com.julescitronic.ziggogoforwearos;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class channelOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_overview);

        TextView title = findViewById(R.id.titleText);
        TextView time = findViewById(R.id.timeText);
        TextView channel = findViewById(R.id.channelText);
        TextView description = findViewById(R.id.descriptionText);

        ImageView titleImage = findViewById(R.id.titleImage);
        ImageView channelImage = findViewById(R.id.channelLogo);

        Button recordButton = findViewById(R.id.recordButton);
        ImageButton notifyButton = findViewById(R.id.notifyButton);

        JSONArray data = getData();

        try {
            title.setText(data.getJSONObject(0).getString("currentTitle"));
        } catch (JSONException e){

        }


    }


    private JSONArray getData(){
        JSONArray result = null;

        //Set strict mode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Make url
        URL url = null;
        StringBuffer response = new StringBuffer();
        String responseJSON;

        try {
            //Make URL
            url = new URL("http://ziggowear.duckdns.org/webScraper/php/getChannel.php");

        }catch (Exception e){
            Log.d("JULES DEBUG SYSTEM", "invalid url");
        }

        //Retrieve data from server
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (IOException e){
            Log.d("JULES DEBUG SYSTEM: ", e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            //json
            responseJSON = response.toString();
        }

        //Make a json object

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONObject(responseJSON).getJSONArray("");
        } catch (JSONException e ){
            Log.d("JULES DEBUG SYSTEM: ", e.toString());
        }

        result = jsonArray;

        return result;
    }
}
