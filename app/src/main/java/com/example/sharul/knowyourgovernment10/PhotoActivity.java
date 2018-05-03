package com.example.sharul.knowyourgovernment10;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    private List<Official> List = new ArrayList<>();
    private ImageView imageView;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        TextView tloc = (TextView) findViewById(R.id.loc);
        TextView o = (TextView) findViewById(R.id.pOffice);
        TextView n = (TextView) findViewById(R.id.pName);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent Lintent = getIntent();
        if (Lintent.hasExtra("location")) {
            String ofL = Lintent.getSerializableExtra("location").toString();
            tloc.setText(ofL);
        }
        //Official ni;
        Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            String na = intent.getSerializableExtra("name").toString();
            n.setText(na);
        }

        Intent intent1 = getIntent();
        if (intent1.hasExtra("office")) {
            String of = intent.getSerializableExtra("office").toString();
            o.setText(of);
        }

        Intent intent2 = getIntent();
        if(intent2.hasExtra("party")) {
            String p = intent.getSerializableExtra("party").toString();
            if (p.equals("Republican")) {
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            } else if (p.equals("Democratic")) {
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            } else {
                getWindow().getDecorView().setBackgroundColor(Color.BLACK);
            }
        }

        Intent p = getIntent();
        if (p.hasExtra("url")) {
            url = intent.getSerializableExtra("url").toString();

            if (url != null) {
                Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        final String changedUrl = url.replace("http:", "https:");
                        picasso.load(changedUrl)
                                .error(R.drawable.brokenimage)
                                .placeholder(R.drawable.placeholder)
                                .into(imageView);
                    }
                }).build();
                if(!url.equals("No data provided")) {
                    picasso.load(url)
                            .error(R.drawable.brokenimage)
                            .placeholder(R.drawable.placeholder)
                            .into(imageView);
                }
            } else {
                Picasso.with(this).load(url)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missingimage)
                        .into(imageView);
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
            Intent intent = new Intent(PhotoActivity.this, MainActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    }

