package com.example.sharul.knowyourgovernment10;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{


    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Official> OfficialList = new ArrayList<>();
    private Locator locator;
    private static final String TAG = "MainActivity";
    private ArrayList<String> rData = new ArrayList<>();
    private ArrayList<String> oData = new ArrayList<>();
    private ArrayList<String> nData = new ArrayList<>();

    private String[] officeArray = new String[100];
    private String[] oiArray = new String[100];
    private String[] nameArray = new String[100];
    private String[] addArray = new String[100];
    private String[] partyArray = new String[100];
    private String[] phArray = new String[100];
    private String[] urlArray = new String[100];
    private String[] emailArray = new String[100];
    private String[] phurlArray = new String[100];
    private String[] channelArray = new String[100];
    private String TLocation = "";
    NetworkInfo netInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rc);

        myAdapter = new MyAdapter(OfficialList, this);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();


        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            locator = new Locator(this);

            }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet Conection");
            final TextView et = new TextView(this);

            et.setText("Data Cannot Be Loaded Without A Network Connection");
            et.setGravity(Gravity.CENTER_HORIZONTAL);
            builder.setView(et);

            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

    public void setOfficialList(Object[] results)
    {
        Arrays.fill(officeArray, null);
        Arrays.fill(oiArray, null);
        Arrays.fill(nameArray, null);
        Arrays.fill(phArray, null);
        Arrays.fill(emailArray, null);
        Arrays.fill(partyArray, null);
        Arrays.fill(phurlArray, null);
        Arrays.fill(channelArray, null);
        Arrays.fill(addArray, null);
        Arrays.fill(urlArray, null);
        oData.clear();
        rData.clear();
        nData.clear();

        int len = 0;
        String ad = results[0].toString();
        TextView l = (TextView) findViewById(R.id.pLocation);
        try
        {
            JSONArray loc = new JSONArray(ad);
            nData.add(loc.getString(0));
            JSONObject j = new JSONObject(nData.get(0));
            String city = j.getString("city");
            String st = j.getString("state");
            String z = j.getString("zip");
            TLocation = city+","+st+" "+z;
             l.setText(city+","+st+" " + z);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String p = results[1].toString();
            JSONArray r = new JSONArray(p);
            len = r.length();
            for (int i = 0; i < r.length(); i++) {
                oData.add(r.getString(i));
                JSONObject n = new JSONObject(oData.get(i));
                nameArray[i] = n.getString("name");
                if(n.has("address"))
                {
                    addArray[i] = n.getString("address");
                }
                else
                {
                    addArray[i] = "No data provided";
                }
                if(n.has("party"))
                {
                    partyArray[i] = n.getString("party");
                }
                else
                {
                    partyArray[i] = "No data provided";
                }
                if(n.has("phones"))
                {
                    phArray[i] = n.getString("phones");
                }
                else
                {
                    phArray[i] = "No data provided";
                }
                if(n.has("urls"))
                {
                    urlArray[i] = n.getString("urls");
                }
                else
                {
                    urlArray[i] = "No data provided";
                }
                if(n.has("emails"))
                {
                    emailArray[i] = n.getString("emails");
                }
                else
                {
                    emailArray[i] = "No data provided";
                }
                if(n.has("photoUrl"))
                {
                    phurlArray[i] = n.getString("photoUrl");
                }
                else
                {
                    phurlArray[i] = "No data provided";
                }
                if(n.has("channels"))
                {
                    channelArray[i] = n.getString("channels");
                }
                else
                {
                    channelArray[i] = "No data provided";
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String s = results[2].toString();
            JSONArray r = new JSONArray(s);
            int k = 0;
            for (int i = 0; i < len; i++) {
                rData.add(r.getString(i));
                JSONObject n = new JSONObject(rData.get(i));
                officeArray[k] = n.getString("name");
                String la = n.getString("officialIndices");
                la =la.replace("[","");
                la =la.replace("]","");
                oiArray[k] = la;
                if(oiArray[k].contains(","))
                {
                     String parts[] = oiArray[k].split(",");
                    int pl = parts.length;
                    for(int m=0;m<pl;m++)
                    {
                        oiArray[k] = parts[m];
                        officeArray[k+1] = officeArray[k];
                        k++;
                    }
                }
                else
                {
                    k++;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OfficialList.clear();

        for(int i=0;i<len;i++)
        {


            Log.d(TAG,"Of: "+officeArray[i]);
            Official o = new Official(officeArray[i],nameArray[i],partyArray[i],addArray[i],phArray[i],emailArray[i],urlArray[i],phurlArray[i],channelArray[i]);
            OfficialList.add(o);


        }
        myAdapter = new MyAdapter(OfficialList, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.about:
                Intent intent_about = new Intent(this, About.class);
                startActivity(intent_about);
                return true;

            case R.id.location:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final EditText et = new EditText(this);
                et.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setGravity(Gravity.CENTER_HORIZONTAL);

                builder.setView(et);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String regexStr = "^[0-9]*$";
                        final String e = et.getText().toString();
                        if(e.toString().trim().matches(regexStr))
                        {
                            new MyAsync(MainActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,et.getText().toString());
                        }
                        else{
                            String output = e.replaceAll("\\s+","");
                            new GeocoderAsync(MainActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,output);
                        }



                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


                builder.setTitle("Enter a City, State or Zip Code:");

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setData(double lat, double lon) {

        Log.d(TAG, "setData: Lat: " + lat + ", Lon: " + lon);
        String address = doAddress(lat, lon);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(TAG, "onRequestPermissionsResult: CALL: " + permissions.length);
        Log.d(TAG, "onRequestPermissionsResult: PERM RESULT RECEIVED");

        if (requestCode == 5) {
            Log.d(TAG, "onRequestPermissionsResult: permissions.length: " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: HAS PERM");
                        locator.setUpLocationManager();
                        locator.determineLocation();
                    } else {
                        Toast.makeText(this, "Location permission was denied - cannot determine address", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onRequestPermissionsResult: NO PERM");
                    }
                }
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }


    private String doAddress(double latitude, double longitude)
    {
        Log.d(TAG, "doAddress: Lat: " + latitude + ", Lon: " + longitude);
        String zip = "";
        List<Address> addresses = null;
        for (int times = 0; times < 3; times++) {
            Geocoder geocoder = new Geocoder(this, Locale.US.getDefault());

            try {
                Log.d(TAG, "doAddress: Getting address now");
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder sb = new StringBuilder();

                for (Address ad : addresses) {
                    Log.d(TAG, "doLocation: " + ad);
                    sb.append("\nAddress\n\n");
                    for (int i = 0; i < ad.getMaxAddressLineIndex(); i++)
                        sb.append("\t" + ad.getAddressLine(i) + "\n");
                    sb.append("\t" + ad.getCountryName() + " (" + ad.getCountryCode() + ")\n" + ad.getPostalCode());
                     zip = ad.getPostalCode().toString();
                    new MyAsync(MainActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,zip);
                    Log.d(TAG,"ZIP: " + zip);
                }

                return sb.toString();
            } catch (IOException e) {
                Log.d(TAG, "doAddress: " + e.getMessage());

            }
            Toast.makeText(this, "GeoCoder service is slow - please wait", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "GeoCoder service timed out - please try again", Toast.LENGTH_LONG).show();
        return null;
    }

    public void noLocationAvailable() {
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        locator.shutdown();
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Official c = OfficialList.get(pos);
        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
        intent.putExtra("", pos);
        intent.putExtra("location",TLocation);
        intent.putExtra(Official.class.getName(), c);
        startActivityForResult(intent,1);



    }
}
