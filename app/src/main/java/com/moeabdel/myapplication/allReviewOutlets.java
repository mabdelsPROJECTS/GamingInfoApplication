package com.moeabdel.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class allReviewOutlets extends AppCompatActivity {

    private TextView outletName;
    private TextView outletScore;
    private TextView outletSnippet;
    private TextView outLetUrl;
    String name;
    int score;
    String snippet;
    private LinearLayout linearLayout;
    private Picasso picasso;
    private ImageView outletPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_review_outlets);
        picasso = Picasso.get();
        outletName = findViewById(R.id.OutLetName);
        outletScore = findViewById(R.id.outletScore);
        outletSnippet = findViewById(R.id.outletSnippet);
        outLetUrl = findViewById(R.id.outletUrl);
        linearLayout = findViewById(R.id.linearLayout);
        outletPic = findViewById(R.id.outletImage);
        outletSnippet.setVisibility(View.GONE);
        outletName.setVisibility(View.GONE);
        outletScore.setVisibility(View.GONE);
        outletPic.setVisibility(View.GONE);
        outLetUrl.setVisibility(View.GONE);


        ArrayList<Bundle> outletDataList = getIntent().getParcelableArrayListExtra("outletDataList");
        if (outletDataList != null) {
             //linearLayout = findViewById(R.id.linearLayout);

            for (Bundle outletData : outletDataList) {
                String outletNameString = outletData.getString("outletName");
                String og = outletData.getString("og");
                String externalUrl = outletData.getString("externalUrl");
                String snippet = outletData.getString("snippet");
                int score = outletData.getInt("score");
                String color = outletData.getString("color");


                ImageView imageView = new ImageView(this);
                int imageWidth = 50;
                int imageHeight = 40;

                int imageWidthInPixels = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        imageWidth,
                        getResources().getDisplayMetrics()
                );

                int imageHeightInPixels = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        imageHeight,
                        getResources().getDisplayMetrics()
                );

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageWidthInPixels, imageHeightInPixels);
                layoutParams.gravity = Gravity.CENTER; // Center the image
                layoutParams.setMargins(0,24,0,0);
                imageView.setLayoutParams(layoutParams);


               // imageView.setLayoutParams(layoutParams);


                picasso.load(og).into(imageView);



                linearLayout.addView(imageView);

                //NEW TEXTVIEW ELEMENT

                TextView outletNameTextView = new TextView(this);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.cinzel);
                LinearLayout.LayoutParams outletNameParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

                outletNameParams.setMargins(0, 8, 0, 8);
                outletNameTextView.setGravity(Gravity.CENTER);
                outletNameTextView.setTextColor(Color.BLACK);
                Typeface boldOutLetName = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
                outletNameTextView.setTypeface(boldOutLetName);
                outletNameTextView.setLayoutParams(outletNameParams);
                //outletNameTextView.setTypeface(typeface);
                outletNameTextView.setText(outletNameString);
                linearLayout.addView(outletNameTextView);

            // NEW TEXTVIEW ELEMENT

                TextView snippetTextView = new TextView(this);
                Typeface typeface2 = ResourcesCompat.getFont(this, R.font.cinzel);
                LinearLayout.LayoutParams snippetParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                snippetParams.setMargins(16,8, 16, 8);

                snippetTextView.setTypeface(typeface2);
                snippetTextView.setLayoutParams(snippetParams);
                snippetTextView.setText( snippet);
                snippetTextView.setTextColor(Color.BLACK);
                //Typeface boldSnippet = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
               // snippetTextView.setTypeface(boldSnippet);
                linearLayout.addView(snippetTextView);

                //NEW TEXTVIEW ELEMENT
                FrameLayout frameLayout = new FrameLayout(this);


                ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
                progressBar.setMax(100);
                progressBar.setProgress(score);
                if(color.equals("Blue")){
                    progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.playstationBlue)));
                }
                else if(color.equals("Green")){
                    progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.xboxGreen)));
                }
                else if(color.equals("Black")){
                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLACK));
                }
                else if(color.equals("steamBlack")){
                    progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.steamColor)));
                }

                TextView textView = new TextView(this);
                String scoreString = String.valueOf(score);
                textView.setText(scoreString);
                textView.setTextColor(Color.WHITE); // Set text color as needed
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTypeface(null, Typeface.BOLD);

                FrameLayout.LayoutParams progressBarParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT // Adjust height as needed
                );
                progressBarParams.setMargins(16, 8, 16, 8);
                progressBar.setLayoutParams(progressBarParams);
                progressBar.setScaleY(4);


                FrameLayout.LayoutParams textViewParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                );
                //textViewParams.gravity = Gravity.CENTER; // Center the TextView in the FrameLayout
                textView.setLayoutParams(textViewParams);


                frameLayout.addView(progressBar);
                frameLayout.addView(textView);


                FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT // Adjust height as needed
                );
                frameLayout.setLayoutParams(frameLayoutParams);
                linearLayout.addView(frameLayout);
                //TextView scoreTextView = new TextView(this);
                //Typeface typeface3 = ResourcesCompat.getFont(this, R.font.cinzel);

               // scoreTextView.setTypeface(typeface3);
               // scoreTextView.setText("Score: " + score);
                //linearLayout.addView(scoreTextView);

                // NEW TEXTVIEW ELEMENT
                TextView externalUrlTextView = new TextView(this);
                LinearLayout.LayoutParams UrlParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                UrlParams.setMargins(8, 8, 8, 60);
                externalUrlTextView.setLayoutParams(UrlParams);

                //Typeface typeface1 = ResourcesCompat.getFont(this, R.font.cinzel);

                //externalUrlTextView.setTypeface(typeface1);
                //externalUrlTextView.setTypeface(customTypeface);
                externalUrlTextView.setText( externalUrl);
                Linkify.addLinks(externalUrlTextView, Linkify.ALL);
                if(color.equals("Blue")) {
                    externalUrlTextView.setLinkTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.playstationBlue)));
                }
                else if(color.equals("Green")){
                    externalUrlTextView.setLinkTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.xboxGreen)));
                }
                else{
                    externalUrlTextView.setLinkTextColor(Color.BLACK);
                }
                linearLayout.addView(externalUrlTextView);
            }
        }
    }

    public void onClick(View v){

    }

    }
