package com.example.sharul.knowyourgovernment10;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.sharul.knowyourgovernment10.R.layout.official;


public class OfficialActivity extends AppCompatActivity
{

    private ImageView photo;
    private String name = "";
    private String office = "";
    private String purl = "";
    private String party = "";
    private String location = "";
    private ArrayList<String> aData = new ArrayList<>();
    private String channel = "";
    private ArrayList<String> chData = new ArrayList<>();
    private ArrayList<String> chtype = new ArrayList<>();
    private ArrayList<String> chid = new ArrayList<>();
    private static final String TAG = "OfficialActivity";
    int len = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);
        TextView tloc = (TextView) findViewById(R.id.pLocation);
        TextView o = (TextView) findViewById(R.id.pOffice);
        TextView n = (TextView) findViewById(R.id.pName);
        TextView a = (TextView) findViewById(R.id.Address);
        TextView p = (TextView) findViewById(R.id.Phone);
        TextView pa = (TextView) findViewById(R.id.Party);
        TextView e = (TextView) findViewById(R.id.Email);
        TextView w = (TextView) findViewById(R.id.Web);
        photo = (ImageView) findViewById(R.id.photo_off);
        a.setMovementMethod(new ScrollingMovementMethod());
        Intent Lintent = getIntent();
        if(Lintent.hasExtra("location"))
        {
            String offloc = Lintent.getSerializableExtra("location").toString();
            location = offloc;
            tloc.setText(offloc);
        }
        Intent intent = getIntent();
        if (intent.hasExtra(Official.class.getName())) {
           final Official ni = (Official) intent.getSerializableExtra(Official.class.getName());
            if (ni.getEmail().equals(""))  // change to null for JSON
            {
                e.setText("No Data Provided");
            } else {
                String fWeb = "";
                fWeb = ni.getEmail();
                fWeb = fWeb.replace("[","");
                fWeb = fWeb.replace("]","");
                fWeb = fWeb.replaceAll("\"","");
                e.setText(fWeb);

                Linkify.addLinks(e, Linkify.EMAIL_ADDRESSES);
            }
            if (ni.getOfficeAdd().equals("")) {
                a.setText("No Data Provided");
            } else {
                String FinalAdd = "";
                try
                {
                    JSONArray f = new JSONArray(ni.getOfficeAdd());
                    JSONObject add1 = (JSONObject) f.get(0);
                    aData.add(add1.getString("line1"));
                    FinalAdd = aData.get(0).concat("\n");
                    if (add1.has("line2"))
                    {
                        aData.add(add1.getString("line2"));
                        FinalAdd = FinalAdd.concat(aData.get(1)).concat("\n");
                    }
                    else
                    {
                        aData.add("");
                    }
                    aData.add(add1.getString("city"));
                    FinalAdd = FinalAdd.concat(aData.get(2)).concat(", ");
                    aData.add(add1.getString("state"));
                    FinalAdd = FinalAdd.concat(aData.get(3)).concat(" ");
                    aData.add(add1.getString("zip"));
                    FinalAdd = FinalAdd.concat(aData.get(4));

                    a.setText(FinalAdd);
                    Linkify.addLinks(((TextView) findViewById(R.id.Address)), Linkify.MAP_ADDRESSES);


                }catch (Exception err)
                {
                    err.printStackTrace();
                }
            }

            if (ni.getPhone().equals("")) {
                p.setText("No Data Provided");
            } else {String fPh = "";
                fPh = ni.getPhone().replaceAll("\"","");
                fPh = fPh.replace("[","");
                fPh = fPh.replace("]","");
                p.setText(fPh);
                Linkify.addLinks(p, Linkify.PHONE_NUMBERS);

            }
            if (ni.getWebsite().equals("")) {
                w.setText("No Data Provided");
            } else {
                String fWeb = "";
                fWeb = ni.getWebsite();
                fWeb = fWeb.replace("[","");
                fWeb = fWeb.replaceAll("/","");
                fWeb = fWeb.replace("]","");
                fWeb = fWeb.replaceAll("\"","");
                w.setText(fWeb);
                Linkify.addLinks(w, Linkify.WEB_URLS);

            }
            o.setText(ni.getOffice());
            n.setText(ni.getName());
            pa.setText("(" + ni.getParty() + ")");
            if (ni.getParty().equals("Republican")) {
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            } else if (ni.getParty().equals("Democratic")) {
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            } else {
                pa.setVisibility(View.INVISIBLE);
                getWindow().getDecorView().setBackgroundColor(Color.BLACK);
            }

            if (ni.getPhurl() != null) {
                Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {// Here we try https if the http image attempt failed
                        final String changedUrl = ni.getPhurl().replace("http:", "https:");
                        picasso.load(changedUrl)
                                .error(R.drawable.brokenimage)
                                .placeholder(R.drawable.placeholder)
                                .into(photo);
                    }
                }).build();
                if(!ni.getPhurl().equals("No data provided")) {
                    picasso.load(ni.getPhurl())
                            .error(R.drawable.brokenimage)
                            .placeholder(R.drawable.placeholder)
                            .into(photo);
                }
            } else {
                Picasso.with(this).load(ni.getPhurl())
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missingimage)
                        .into(photo);
            }
            name = ni.getName();
            office = ni.getOffice();
            purl = ni.getPhurl();
            party = ni.getParty();
            channel = ni.getChannel();
            JSONObject c = null;
            try {
                JSONArray r = new JSONArray(channel);
                len = r.length();
                for (int i = 0; i < r.length(); i++) {
                    chData.add(r.getString(i));
                    JSONObject na = new JSONObject(chData.get(i));
                    chtype.add(na.getString("type"));
                    chid.add(na.getString("id"));

                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }


            if(chtype.contains("Facebook"))
            {
                ImageView btn = (ImageView) findViewById(R.id.facebook);
                btn.setVisibility(View.VISIBLE);
            }
            if(chtype.contains("Twitter"))
            {
                ImageView btn = (ImageView) findViewById(R.id.twitter);
                btn.setVisibility(View.VISIBLE);
            }
            if(chtype.contains("YouTube"))
            {
                ImageView btn = (ImageView) findViewById(R.id.youtube);
                btn.setVisibility(View.VISIBLE);
            }
            if(chtype.contains("GooglePlus"))
            {
                ImageView btn = (ImageView) findViewById(R.id.google);
                btn.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.home)
        {
            Intent intent = new Intent(OfficialActivity.this, MainActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void im_click(View v)
    {

        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("location",location);
        intent.putExtra("name",name);
        intent.putExtra("office",office);
        intent.putExtra("party",party);
        intent.putExtra("url",purl);
        startActivityForResult(intent,1);
    }

    public void y_click(View v)
    {
        int pos =0;
        for(int i=0;i<len;i++)
        {
            if(chtype.get(i).equals("YouTube"))
                pos = i;
        }
        String name = chid.get(pos);
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW); intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/" + name)));
        }

    }

    public void g_click(View v)
    {
        int pos =0;
        for(int i=0;i<len;i++)
        {
            if(chtype.get(i).equals("GooglePlus"))
                pos = i;
        }

        String name = chid.get(pos);
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW); intent.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity"); intent.putExtra("customAppUri", name);
            startActivity(intent);
        }
        catch (ActivityNotFoundException e) { startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://plus.google.com/" + name)));
        }
    }

    public void t_click(View v)
    {
        int pos =0;
        for(int i=0;i<len;i++)
        {
            if(chtype.get(i).equals("Twitter"))
                pos = i;
        }
        Intent intent = null;
        String name = chid.get(pos); try {
        // get the Twitter app if possible
        getPackageManager().getPackageInfo("com.twitter.android", 0);
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name)); intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    } catch (Exception e) {
// no Twitter app, revert to browser
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name)); }
        startActivity(intent);

    }
    public void f_click(View v)
    {
        int pos =0;
        for(int i=0;i<len;i++)
        {
            if(chtype.get(i).equals("Facebook"))
                pos = i;
        }
        String FACEBOOK_URL = "https://www.facebook.com/" + chid.get(pos);
        String urlToUse;
        PackageManager packageManager = getPackageManager(); try {
        int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode; if (versionCode >= 3002850) { //newer versions of fb app
            urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL; } else { //older versions of fb app
            urlToUse = "fb://page/" + chid.get(pos); }
    } catch (PackageManager.NameNotFoundException e) { urlToUse = FACEBOOK_URL; //normal web url
    }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW); facebookIntent.setData(Uri.parse(urlToUse)); startActivity(facebookIntent);
    }

}
