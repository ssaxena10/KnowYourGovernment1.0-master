package com.example.sharul.knowyourgovernment10;

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
        import java.net.MalformedURLException;
        import java.net.ProtocolException;
        import java.net.URL;
        import java.net.URLConnection;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

public class MyAsync extends AsyncTask<String, Void, String> {
    private static final String TAG = "AsyncLoaderTask";
    public int len = 0;
    private MainActivity mainActivity;
    private final String googleURL = "https://www.googleapis.com/civicinfo/v2/representatives?";
    private final String yourAPIKey = "AIzaSyD6vswI4je-5sep10AfcsLRiX5x46LltMs";

    public MyAsync(MainActivity ma) {
        mainActivity = ma;
    }

    private ArrayList<String> OList = new ArrayList<>();
    private ArrayList<String> rList = new ArrayList<>();
    private ArrayList<String> nList = new ArrayList<>();
    private ArrayList<String> resultList = new ArrayList<>();
    public static HashMap<String, Double> wData = new HashMap<>();
    private Object[] res = new Object[100];
    private String geo = "";
    private String flag = "true";

    @Override
    protected void onPostExecute(String s) {

            mainActivity.setOfficialList(res);
    }

    @Override
    protected String doInBackground(String... params) {

            Log.d(TAG, "Async: " + params[0]);
            Uri.Builder buildURL = Uri.parse(googleURL).buildUpon();
            buildURL.appendQueryParameter("key", yourAPIKey);
            buildURL.appendQueryParameter("address", params[0]);

            String urlToUse = buildURL.build().toString();
            Log.d(TAG, "doInBackground: " + urlToUse);

            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(urlToUse);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }

                // Log.d(TAG, "doInBackground: " + sb.toString());

            } catch (Exception e) {
                Log.e(TAG, "doInBackground: ", e);
                return null;
            }
            parseJSON(sb.toString());

        return params[0];
    }

  private void parseJSON(String s) {
        try {
            JSONObject stock = new JSONObject(s);
            JSONArray oStock = (JSONArray) stock.get("officials");
            nList.add(stock.get("normalizedInput").toString());
            JSONArray rStock = (JSONArray) stock.get("offices");
            int olen = oStock.length();
            int rlen = rStock.length();
            for(int i = 0;i<olen;i++) {
                OList.add(oStock.getString(i));
            }
            for(int i = 0;i<rlen;i++) {
                rList.add(rStock.getString(i));
            }
       }
       catch (Exception e) {
            e.printStackTrace();
        }
        res[0] = nList;
        res[1] = OList;
        res[2] = rList;
        Log.d(TAG,"JSON Olist: " + OList.get(5));
        Log.d(TAG,"JSON nlist: " + nList.get(0));
        Log.d(TAG,"JSON rlist: " + rList.get(5));
    }
}