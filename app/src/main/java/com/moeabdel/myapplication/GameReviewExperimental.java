package com.moeabdel.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.moeabdel.myapplication.databinding.ActivityExerciseBinding;
import com.moeabdel.myapplication.databinding.ActivityGameReviewExperimentalBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameReviewExperimental extends AppCompatActivity {

    private TextView gameName;
    private ImageView nintendoIconImage;
    private TextView releaseDate;

    private ImageView backgroundImage;
    private ImageView stampImage;
    private TextView expandOrHide;
    private TextView topCriticScoreInside;
    private TextView percentRecommendInside;
    private TextView medianScoreInside;
    private TextView description;
    private String gifUrl;
    private ProgressBar topCriticProg;
    private ProgressBar medianScoreProg;
    private ProgressBar percentRecommendProg;
    //private ImageView gameImage;
    private ConstraintLayout layout;
    private PhotoView superHeroImage;
    private String originalDescriptionText;

    private ActivityGameReviewExperimentalBinding binding;
    private RequestQueue requestQueue;
    private String revertBackDescriptionString;
    private int wordCount;
    private int gameIdNum;
    Picasso picasso;
    String copyOfDescription;
    TextView avaliableOn;
    private String apiKey = "8b3a8c5c83mshd1a9e2a7d367e51p154bebjsn6873d7e24c61";

    private String outletReviewsUrl = "https://opencritic-api.p.rapidapi.com/reviews/game/";
    private String outletImageUrl = "https://img.opencritic.com/";
    private String colorLayout;

    private String steamPsnXboxUrl = "https://customsearch.googleapis.com/customsearch/v1?cx=e03efa4fc7dd246f0&dateRestrict=y%5B1%5D&num=10";

    private String GoogleSearchAPIKey = "AIzaSyCDmyF270Zjlstl8lKDuv-M7NgL-MvMKoE";
    private ImageView steamIconImage;
    private ImageView playstationIconImage;
    private ImageView xboxIconImage;

    private String thirdApiKeyBackup = "ca3802b7d6msh6c7759ce753d4cap166fc1jsn84b65c5a98c4";
    private ProgressBar progressBar;
    private String[] allYoutubeVideosSplit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameReviewExperimentalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        requestQueue = Volley.newRequestQueue(this);
        gameName = binding.gameName;
        topCriticScoreInside = binding.topCriticInsideScore;
        percentRecommendInside = binding.percentRecomendInsideScore;
        medianScoreInside = binding.medianScoreInsideScore;
        //gameImage = binding.gameImage;
        description = binding.gameDescription;
        topCriticProg = binding.topCriticProgBar;
        medianScoreProg = binding.medianScoreProgBar;
        percentRecommendProg = binding.percentRecomendProgBar;
        expandOrHide = binding.expandOrHideTextView;
        avaliableOn = binding.superheroWeight;
        //stampImage = binding.stampImage;
        steamIconImage = binding.steamIconImage;
        playstationIconImage = binding.playstationIconImage;
        xboxIconImage = binding.xboxIconImage;
        nintendoIconImage = binding.nintendoIconImage;
        releaseDate = binding.releaseDateTextView;
        picasso = Picasso.get();
        percentRecommendProg.setMax(100);
        medianScoreProg.setMax(100);
        topCriticProg.setMax(100);

        topCriticProg.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        medianScoreProg.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        percentRecommendProg.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        layout = binding.layout;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameIdNum = extras.getInt("gameId");
            String screenShotsUrl = extras.getString("screenShots");
            String[] splitScreenShots = screenShotsUrl.split(",");
            String allYoutubeVideos = extras.getString("allYoutubeVideos");
            String[] allYoutubeVideosSplit = allYoutubeVideos.split(",");
            String tier = extras.getString("tier");
            ViewPager viewPager = findViewById(R.id.viewPager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(this,splitScreenShots, tier);
            viewPager.setAdapter(adapter);



            String imageUrl = extras.getString("image");
            String allPlatforms = extras.getString("allPlatforms");
            String title = extras.getString("title");
            //String tier = extras.getString("tier");
            String name = extras.getString("name");
            String releaseDateString = extras.getString("releaseDate");

            //if(releaseDateString != null) {
                Instant instant = Instant.parse(releaseDateString);
                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyy");
                String normalDateString = dateTime.format(formatter);
                releaseDate.setText("Release Date: " + normalDateString);
                releaseDate.setVisibility(View.VISIBLE);
           // }
            // String hasLootBoxes = extras.getString("hasLootBoxes");
            String descriptionText = extras.getString("description");
            //gameIdNum = extras.getString("gameId");
            copyOfDescription = descriptionText;
            int medScore = extras.getInt("medianScore");
            if (medScore == -1) {
                medScore = 0;
            }
            String medScoreString = String.valueOf(medScore);
            int topCritic = extras.getInt("topCriticScore");
            if (topCritic == -1) {
                topCritic = 0;
            }
           // if (tier.equals("Mighty")) {
             //   picasso.load(R.drawable.masterpiece_12_8_2023).into(stampImage);
          ///  } else if (tier.equals("Strong")) {
           //     picasso.load(R.drawable.good_12_8_2023).into(stampImage);
           // } else if (tier.equals("Fair")) {
           //     picasso.load(R.drawable.average_12_8_2023).into(stampImage);
          //  } else if (tier.equals("Weak")) {
           //     picasso.load(R.drawable.below_average_12_8_2023).into(stampImage);
           // } else {
           //     stampImage.setVisibility(View.INVISIBLE);
           // }
            String topCriticString = String.valueOf(topCritic);
            int percentRecommend = extras.getInt("percentRecommended");
            if (percentRecommend == -1) {
                percentRecommend = 0;
            }
            if (allPlatforms.contains("Xbox") && !allPlatforms.contains("PlayStation") && allPlatforms.contains("PC")) {
                layout.setBackgroundColor(Color.BLACK);
                //backgroundImage.setImageResource(R.drawable.newxboximage);
                playstationIconImage.setVisibility(View.GONE);
                nintendoIconImage.setVisibility(View.GONE);


                colorLayout = "Black";
            } else if (allPlatforms.contains("PlayStation") && !allPlatforms.contains("Xbox") && allPlatforms.contains("PC")) {
                layout.setBackgroundColor(Color.BLACK);
                //backgroundImage.setImageResource(R.drawable._ndportraitps);
                xboxIconImage.setVisibility(View.GONE);
                nintendoIconImage.setVisibility(View.GONE);
                colorLayout = "Black";
            } else if (allPlatforms.contains("PlayStation") && !allPlatforms.contains("Xbox") && !allPlatforms.contains("PC)")) {
                layout.setBackgroundColor(ContextCompat.getColor(this, R.color.playstationBlue));
                xboxIconImage.setVisibility(View.GONE);
                steamIconImage.setVisibility(View.GONE);
                nintendoIconImage.setVisibility(View.GONE);
                colorLayout = "Blue";
            } else if (allPlatforms.contains("PC") && allPlatforms.contains("PlayStation") && allPlatforms.contains("Xbox")) {
                layout.setBackgroundColor(Color.BLACK);
                nintendoIconImage.setVisibility(View.GONE);
                colorLayout = "Black";
            } else if (allPlatforms.contains("Xbox") && !allPlatforms.contains("PlayStation") && !allPlatforms.contains("PC")) {
                layout.setBackgroundColor(ContextCompat.getColor(this, R.color.xboxGreen));
                //backgroundImage.setImageResource(R.drawable.newxboximage);
                playstationIconImage.setVisibility(View.GONE);
                nintendoIconImage.setVisibility(View.GONE);
                colorLayout = "Green";
            } else if (allPlatforms.contains("PC") && !allPlatforms.contains("PlayStation") && !allPlatforms.contains("Xbox")) {
                layout.setBackgroundColor(ContextCompat.getColor(this, R.color.steamColor));
                playstationIconImage.setVisibility(View.GONE);
                xboxIconImage.setVisibility(View.GONE);
                nintendoIconImage.setVisibility(View.GONE);
                colorLayout = "steamBlack";
            }
            else if(allPlatforms.contains("Nintendo Switch") || allPlatforms.contains("Wii U")){
                layout.setBackgroundColor(ContextCompat.getColor(this, R.color.nintendoRed));
                playstationIconImage.setVisibility(View.GONE);
                xboxIconImage.setVisibility(View.GONE);
                steamIconImage.setVisibility(View.GONE);
                colorLayout = "nintendoRed";
                topCriticProg.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
                percentRecommendProg.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
                medianScoreProg.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
                topCriticScoreInside.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.nintendoRed)));
                medianScoreInside.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.nintendoRed)));
                percentRecommendInside.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.nintendoRed)));
            }
            String percentRecommendString = String.valueOf(percentRecommend);
            avaliableOn.setText("Available on: " + allPlatforms);
            gameName.setText(name);
            originalDescriptionText = descriptionText;

            String[] words = descriptionText.split("\\s+");
            wordCount = words.length;

            if (wordCount > 40) {
                expandOrHide.setVisibility(View.VISIBLE);
                StringBuilder truncatedDescription = new StringBuilder();
                for (int i = 0; i < 40; i++) {
                    truncatedDescription.append(words[i]).append(" ");
                }
                descriptionText = truncatedDescription.toString().trim() + " ...";
                revertBackDescriptionString = truncatedDescription.toString().trim() + " ...";

            } else {
                expandOrHide.setVisibility(View.GONE);
            }

            description.setText(descriptionText);
            description.setText(descriptionText);
            topCriticProg.setProgress(topCritic);
            medianScoreProg.setProgress(medScore);
            percentRecommendProg.setProgress(percentRecommend);
            String topCriticInsideString = String.valueOf(topCritic);
            String percentRecommendInsideString = String.valueOf(percentRecommend);
            String medianScoreInsideString = String.valueOf(medScore);
            topCriticScoreInside.setText(topCriticInsideString);
            percentRecommendInside.setText(percentRecommendInsideString);
            medianScoreInside.setText(medianScoreInsideString);
        }
    }




    public void setToOriginalDescriptionText(View v) {
        if (expandOrHide.getText().toString().equals("Tap To Expand")) {
            description.setText(originalDescriptionText);
            int newHeight = calculateTextHeight(description, copyOfDescription);
            newHeight = newHeight + 100;
            setTextViewHeight(description, newHeight);

            expandOrHide.setText("Tap To Hide");
        } else if (expandOrHide.getText().toString().equals("Tap To Hide")) {
            description.setText(revertBackDescriptionString);
            ViewGroup.LayoutParams params = description.getLayoutParams();
            params.height = 420;
            description.setLayoutParams(params);
            expandOrHide.setText("Tap To Expand");
        }
    }


    private int calculateTextHeight(TextView textView, String text) {
        textView.setText(text);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(textView.getWidth(), View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);

        return textView.getMeasuredHeight();

    }

    private void setTextViewHeight(TextView textView, int height) {
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        params.height = height;
        textView.setLayoutParams(params);
    }

    public void goToOutletReviews(View v) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri.Builder urlBuilder = Uri.parse(outletReviewsUrl).buildUpon();
        //Uri.Builder urlBuilder = Uri.parse(secondActivityURL).buildUpon();

        //urlBuilder.appendQueryParameter("criteria", input);
        String gameIdString = String.valueOf(gameIdNum);
        urlBuilder.appendPath(gameIdString);
        urlBuilder.appendQueryParameter("skip", "20");
        String url = urlBuilder.build().toString();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);

                        //Log.d(TAG, "onResponse:" + response);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GameReviewExperimental.this, "OutLet Review Url not working", Toast.LENGTH_SHORT).show();
                        Log.d("ExerciseActivity", "error => " + error.toString());
                        //Log.d(TAG, "doDownload");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", thirdApiKeyBackup);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");

                return params;
            }
        };
        queue.add(getRequest);
    }

    private void parseJSON(String s) {
        try {
            List<Bundle> outletDataList = new ArrayList<>();
            if(!s.equals("[]")) {
                String outletName = "";
                String og = "";
                String externalUrl = "";
                String snippet = "";
                int score = 0;
                JSONArray jsonArray = new JSONArray(s);
                //List<Bundle> outletDataList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject outlets = jsonObject.getJSONObject("Outlet");
                    outletName = outlets.optString("name");
                    JSONObject imageScr = outlets.getJSONObject("imageSrc");
                    og = imageScr.getString("og");
                    og = outletImageUrl + og;
                    externalUrl = jsonObject.optString("externalUrl");
                    if (!jsonObject.isNull("score")) {
                        score = jsonObject.getInt("score");
                    }
                    snippet = jsonObject.optString("snippet");

                    Bundle bundle = new Bundle();
                    bundle.putString("outletName", outletName);
                    bundle.putString("og", og);
                    bundle.putString("externalUrl", externalUrl);
                    bundle.putString("snippet", snippet);
                    bundle.putInt("score", score);
                    bundle.putString("color", colorLayout);
                    outletDataList.add(bundle);
                    //intent.putExtras(bundle);
                    //startActivity(intent);


                }
                Intent intent = new Intent(this, allReviewOutlets.class);
                intent.putParcelableArrayListExtra("outletDataList", (ArrayList<? extends Parcelable>) outletDataList);
                startActivity(intent);
            }
            //Intent intent = new Intent(this, allReviewOutlets.class);
            //intent.putParcelableArrayListExtra("outletDataList", (ArrayList<? extends Parcelable>) outletDataList);
            //startActivity(intent);
            else{
                Toast.makeText(this, "There Are No Outlet Reviews At This Time", Toast.LENGTH_SHORT).show();
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }
    public void goToPlaystation(View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri.Builder urlBuilder = Uri.parse(steamPsnXboxUrl).buildUpon();

        urlBuilder.appendQueryParameter("q", gameName.getText().toString() + " price on Playstation");
        urlBuilder.appendQueryParameter("key", GoogleSearchAPIKey);
        String url = urlBuilder.build().toString();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parsePlaystation(response);



                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MainActivity", "error => " + error.toString());
                        Toast.makeText(GameReviewExperimental.this, "Error with Search URL", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", thirdApiKeyBackup);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");

                return params;
            }
        };
        queue.add(getRequest);
    }

    public void goToSteam(View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri.Builder urlBuilder = Uri.parse(steamPsnXboxUrl).buildUpon();

        urlBuilder.appendQueryParameter("q", gameName.getText().toString() + " price on Steam");
        urlBuilder.appendQueryParameter("key", GoogleSearchAPIKey);
        String url = urlBuilder.build().toString();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseSteam(response);

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MainActivity", "error => " + error.toString());
                        Toast.makeText(GameReviewExperimental.this, "Error with Search URL", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", thirdApiKeyBackup);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");

                return params;
            }
        };
        queue.add(getRequest);
    }


    public void parseSteam(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject firstObj = jsonArray.getJSONObject(0);
            String link = firstObj.optString("link");
            openInChrome(link);
            //String displaylink = firstObj.optString("displayLink");
            // Intent intent = new Intent(Intent.ACTION_VIEW);
            //  intent.setData(Uri.parse(link));
            // if(intent.resolveActivity(getPackageManager()) != null){
            //     startActivity(intent);
            //  }
        } catch (
                JSONException e) {
            e.printStackTrace();
            //  }
        }
    }

    public void parsePlaystation(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject firstObj = jsonArray.getJSONObject(0);
            String link = firstObj.optString("link");
           openInChrome(link);
            //String displaylink = firstObj.optString("displayLink");
            // Intent intent = new Intent(Intent.ACTION_VIEW);
            //  intent.setData(Uri.parse(link));
            // if(intent.resolveActivity(getPackageManager()) != null){
            //     startActivity(intent);
            //  }
        } catch (
                JSONException e) {
            e.printStackTrace();
            //  }
        }
    }

    public void goToXbox(View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri.Builder urlBuilder = Uri.parse(steamPsnXboxUrl).buildUpon();

        urlBuilder.appendQueryParameter("q", gameName.getText().toString() + " price on Xbox");
        urlBuilder.appendQueryParameter("key", GoogleSearchAPIKey);
        String url = urlBuilder.build().toString();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseSteam(response);

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MainActivity", "error => " + error.toString());
                        Toast.makeText(GameReviewExperimental.this, "Error with Search URL", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", thirdApiKeyBackup);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");

                return params;
            }
        };
        queue.add(getRequest);
    }

    public void goToNintendo(View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri.Builder urlBuilder = Uri.parse(steamPsnXboxUrl).buildUpon();

        urlBuilder.appendQueryParameter("q", gameName.getText().toString() + " price on Nintendo");
        urlBuilder.appendQueryParameter("key", GoogleSearchAPIKey);
        String url = urlBuilder.build().toString();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseSteam(response);

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("MainActivity", "error => " + error.toString());
                        Toast.makeText(GameReviewExperimental.this, "Error with Search URL", Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", thirdApiKeyBackup);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");

                return params;
            }
        };
        queue.add(getRequest);
    }


    public void openInChrome(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome"); // Use the package name for Chrome

        try {
            startActivity(intent);
        } catch (Exception e) {
            // If Chrome is not installed, open the URL in the default browser
            intent.setPackage(null);
            startActivity(intent);
        }
    }

}


