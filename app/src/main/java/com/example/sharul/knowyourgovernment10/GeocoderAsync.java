package com.example.sharul.knowyourgovernment10;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sharul on 13-04-2017.
 */

class GeocoderAsync extends AsyncTask<String, Void, String> {
    private static final String TAG = "AsyncLoaderTask";
    public int len = 0;
    private MainActivity mainActivity;
    public GeocoderAsync(MainActivity ma) {
        mainActivity = ma;
    }
    public static HashMap<String, Double> wData = new HashMap<>();
    String zip = "";
    List<Address> addresses = null;
    Double lat, lon;

    @Override
    protected void onPostExecute(String s) {
        new MyAsync(mainActivity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,zip);
//
    }

    @Override
    protected String doInBackground(String... params) {
        String regexStr = "^[0-9]*$";
        String location = params[0];
        String sensor = "true";
        location = location.replaceAll(" ", "%20");
        String myUrl = "http://maps.googleapis.com/maps/api/geocode/json?";

        Uri.Builder buildURL = Uri.parse(myUrl).buildUpon();
        buildURL.appendQueryParameter("address", location);
        buildURL.appendQueryParameter("sensor", sensor);

        //URLConnection urlConnection = url.openConnection();
        String urlToUse = buildURL.build().toString();
        StringBuilder sb = new StringBuilder();
        try {
            URL u = new URL(urlToUse);
            Log.d(TAG,urlToUse);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            return null;
        }
        parseCITY(sb.toString());

        return params[0];
    }
    private void parseCITY(String s)
    {
        try
        {
            JSONObject loc = new JSONObject(s);
            JSONArray larray = (JSONArray) loc.get("results");
            JSONObject geo = (JSONObject) larray.get(0);
            JSONObject latlon = (JSONObject) geo.get("geometry") ;
            JSONObject location = (JSONObject) latlon.get("location");
            //wData.put("Geo",larray.getString("geometry"));
            wData.put("long",location.getDouble("lng"));
            wData.put("lat",location.getDouble("lat"));
            //JSONArray larray = (JSONArray) loc.get("results");

        }catch (Exception e) {
            e.printStackTrace();
        }
        lat = wData.get("lat");
        lon = wData.get("long");
        Geocoder geocoder = new Geocoder(mainActivity, Locale.US.getDefault());

        try {
            Log.d(TAG, "doAddress: Getting address now");

            addresses = geocoder.getFromLocation(lat, lon, 1);
            StringBuilder sb = new StringBuilder();

            for (Address ad : addresses) {
                Log.d(TAG, "doLocation: " + ad);
                sb.append("\nAddress\n\n");
                for (int i = 0; i < ad.getMaxAddressLineIndex(); i++)
                    sb.append("\t" + ad.getAddressLine(i) + "\n");
                sb.append("\t" + ad.getCountryName() + " (" + ad.getCountryCode() + ")\n" + ad.getPostalCode());
                zip = ad.getPostalCode().toString();
                Log.d(TAG,"ZIP: " + zip);
            }

            //return sb.toString();
        } catch (Exception e) {
            Log.d(TAG, "doAddress: " + e.getMessage());

        }

    }
}
