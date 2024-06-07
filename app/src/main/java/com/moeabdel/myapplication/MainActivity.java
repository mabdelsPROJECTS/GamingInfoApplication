package com.moeabdel.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.moeabdel.myapplication.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private int gameReviewOrSecondRecycler;
    private ArrayList<Exercise> arrayList = new ArrayList<>();
    private ArrayList<GameReview> gameReviewArrayList = new ArrayList<>();
    private ArrayList<SecondRecyclerViewClass> secondRecyclerViewList = new ArrayList<>();
    private RequestQueue requestQueue;

    private EditText data;
    private static ExerciseAdapter exerciseAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progBar;

    private ArrayList<String> allThumbNailImages = new ArrayList<>();


    private static final String TAG = "MainActivity";
    private static SecondRecyclerViewAdapter secondRecyclerViewAdapter;

    private ImageView superHeroesImage;

    String apiKeyDePaulEmail = "8b3a8c5c83mshd1a9e2a7d367e51p154bebjsn6873d7e24c61";
    String backupApiKeyMoeDo55 = "2140e55926msha53e94c1852a64cp143b85jsndb7efd86aa7d";
    String helloUSYahooAPIkey = "22b96556d2msha3f10db4c4ad126p199115jsn721527e3c0c3";

    String apiURL = "https://opencritic-api.p.rapidapi.com/game/search";
    String secondActivityURL = "https://opencritic-api.p.rapidapi.com/game/";
    String thirdApiKeyBackupGmail = "ca3802b7d6msh6c7759ce753d4cap166fc1jsn84b65c5a98c4";
    String reviewAPIUrl = "https://opencritic-api.p.rapidapi.com/reviews/game/";
    String anotherAPIUrl = "https://opencritic-api.p.rapidapi.com/";
    String thumbNailActualImage = "";
    String gameId = "";
    String imageBaseUrl = "https://img.opencritic.com/";
    String testGameId = "13283";
    int position = 0;
    private Button mainSearchButton;
    private Boolean isPressed = false;
    private String outletImageUrl = "https://img.opencritic.com/";


    String timestamp = Long.toString(System.currentTimeMillis());
    // String hash = generateMD5Hash(timestamp + privateKey + publicKey);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        data = binding.searchBar;
        requestQueue = Volley.newRequestQueue(this);
        exerciseAdapter = new ExerciseAdapter(gameReviewArrayList, this);
        secondRecyclerViewAdapter = new SecondRecyclerViewAdapter(secondRecyclerViewList, this);
        recyclerView = binding.recyclerView;
        //recyclerView.setAdapter(exerciseAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progBar = binding.progressBar;
        superHeroesImage = binding.superheroesImage;
        mainSearchButton = binding.mainSearchButton;
        progBar.setVisibility(View.INVISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Glide.with(this).load(R.drawable.u55q).into(superHeroesImage);


    }


    public void getData(View v) {
        progBar.setVisibility(View.VISIBLE);
        gameReviewOrSecondRecycler = 0;
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (!isPressed) {
            // Disable further clicks until a delay
            isPressed = true;
            mainSearchButton.setEnabled(false);

            position = 0;

            progBar.setVisibility(View.VISIBLE);
            if (gameReviewArrayList.size() != 0) {
                int size = gameReviewArrayList.size();

                for (int i = size - 1; i >= 0; i--) {
                    gameReviewArrayList.remove(i);
                    exerciseAdapter.notifyItemRemoved(i);
                }
            }
            if (arrayList.size() != 0) {
                arrayList.clear();
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            doDownload(data.getText().toString());


        }
    }


    private void doReviewDownload(String input, int position) {
        // RequestQueue queue = Volley.newRequestQueue(this);
        Uri.Builder urlBuilder = Uri.parse(secondActivityURL).buildUpon();
        urlBuilder.appendPath(input);

        String url = urlBuilder.build().toString();

        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        parseReviewJSON(response, position);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Code to execute after the delay
                                goToDoReviewDownload();
                            }
                        }, 1000); // 3000 milli



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                        if (error != null) {
                            //Log.e(TAG, "Error Response Code: " + error.networkResponse.statusCode);
                            if (error.networkResponse.statusCode == 429) {
                                Toast.makeText(MainActivity.this, "No More API Calls For 24 Hours", Toast.LENGTH_SHORT).show();
                                // Handle the error if needed
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", helloUSYahooAPIkey);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");
                params.put("accept", "application/json");

                Log.d(TAG, "getHeaders: " + params);

                return params;
            }
        };

        requestQueue.add(getRequest);
    }


    private void doDownload(String input) {
        int position = 0;
        //RequestQueue queue = Volley.newRequestQueue(this);
        Uri.Builder urlBuilder = Uri.parse(apiURL).buildUpon();


        urlBuilder.appendQueryParameter("criteria", input);
        String url = urlBuilder.build().toString();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                        goToDoReviewDownload();
                        // if (!arrayList.isEmpty()) {
                        // for (int i = 0; i < arrayList.size(); i++) {
                        //   Exercise exercise = arrayList.get(i);
                        //   String gameId = exercise.getGameId();
                        // doReviewDownload(gameId, i, queue);
                        // }
                        // }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null && error.networkResponse != null) {
                            Log.d(TAG, "onErrorResponse: " + error.toString());
                            Log.e(TAG, "Error Response Code: " + error.networkResponse.statusCode);
                            if (error.networkResponse.statusCode == 429) {
                                Toast.makeText(MainActivity.this, "No More API Calls For 24 Hours", Toast.LENGTH_SHORT).show();
                                // Handle the error if needed
                            }

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", helloUSYahooAPIkey);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");

                return params;
            }
        };
        requestQueue.add(getRequest);

    }

    private void goToDoReviewDownload() {
        int size = arrayList.size();
        if (position < size) {
            Exercise exercise = arrayList.get(position);
            String gameId = exercise.getGameId();
            doReviewDownload(gameId, position);
           // try {
               // Thread.sleep(300);
          //  } catch (InterruptedException e) {
             //   throw new RuntimeException(e);
           // }
            position++;
        }
        enableSearchButton();
    }

    public void enableSearchButton(){
        if(position == arrayList.size()){
            mainSearchButton.setEnabled(true);
        }
    }


    private String parseJSON(String s) {
        String gameId = "";
        try {
            JSONArray jsonArray = new JSONArray(s);
            int size = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.optString("name");
                gameId = jsonObject.optString("id");

                Exercise exercise = new Exercise(name, gameId);
                arrayList.add(exercise);

                // if (arrayList.size() > 0) {
                // while (position < arrayList.size()) {
                //doReviewDownload(gameId, position);
                // position++;
            }
            // }
            // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progBar.setVisibility(View.INVISIBLE);

        return gameId;
    }


    private void parseReviewJSON(String gameId, int position) {
        int id = 0;
        try {
            String allYoutubeVideos = "";
            String allPlatforms = "";
            String genresComplete = "";
            String title = "";
            String tier = "";
            String secondActImage = "";
            String screenShotsUrl = "";
            JSONObject jsonObject = new JSONObject(gameId);
            JSONObject images = jsonObject.getJSONObject("images");
            if (images.has("box")) {
                JSONObject boxImages = images.getJSONObject("box");
                secondActImage = boxImages.optString("og");
            } else if (images.has("banner")) {
                JSONObject bannerObj = images.getJSONObject("banner");
                secondActImage = bannerObj.optString("og");
            }
            if (images.has("screenshots")) {
                JSONArray screenshots = images.getJSONArray("screenshots");
                screenShotsUrl = "https://img.opencritic.com/" + secondActImage + ",";
                for (int i = 0; i < screenshots.length(); i++) {
                    JSONObject firstScreenShot = screenshots.getJSONObject(i);
                    if (i + 1 != screenshots.length()) {
                        screenShotsUrl = screenShotsUrl + "https://img.opencritic.com/" + firstScreenShot.optString("og") + ",";
                    } else {
                        screenShotsUrl = screenShotsUrl + "https://img.opencritic.com/" + firstScreenShot.optString("og");
                    }
                }
            }
            id = jsonObject.optInt("id");
            String fullImageUrl = "https://img.opencritic.com/" + secondActImage;

            if (jsonObject.has("mainChannel")) {
                JSONObject mainChannel = jsonObject.getJSONObject("mainChannel");
                title = mainChannel.optString("title");
            }
            if (jsonObject.has("Genres")) {
                JSONArray genresArray = jsonObject.getJSONArray("Genres");
                for (int i = 0; i < genresArray.length(); i++) {
                    JSONObject jsonObject1 = genresArray.getJSONObject(i);
                    String name = jsonObject1.optString("name");
                    genresComplete = genresComplete + " " + name;
                }
            }
            int percentRecommended = jsonObject.getInt("percentRecommended");
            int topCriticScore = jsonObject.getInt("topCriticScore");
            if (jsonObject.has("tier")) {
                tier = jsonObject.optString("tier");
            }
            String name = jsonObject.optString("name");
            String releaseDate = jsonObject.getString("firstReleaseDate");

            String hasLootBoxes = jsonObject.optString("hasLootBoxes");
            String description = jsonObject.optString("description");
            if (jsonObject.has("Platforms")) {
                JSONArray platformsArray = jsonObject.getJSONArray("Platforms");
                for (int i = 0; i < platformsArray.length(); i++) {
                    JSONObject jsonOb = platformsArray.getJSONObject(i);
                    String platformName = jsonOb.optString("name");
                    if (i + 1 != platformsArray.length()) {
                        allPlatforms = allPlatforms + platformName + ", ";
                    } else {
                        allPlatforms = allPlatforms + platformName;
                    }
                }
            }
            if (jsonObject.has("trailers")) {
                JSONArray trailers = jsonObject.getJSONArray("trailers");
                //String partOneYoutubeUrl = "https://www.youtube.com/watch?v=";
                for (int i = 0; i < trailers.length(); i++) {
                    JSONObject objectTrailers = trailers.getJSONObject(i);
                    String videoId = objectTrailers.optString("videoId");
                    if (i + 1 != trailers.length()) {
                        allYoutubeVideos = allYoutubeVideos + videoId + ",";
                    } else {
                        allYoutubeVideos = allYoutubeVideos + videoId;
                    }

                }
            }
            screenShotsUrl = screenShotsUrl + "," + allYoutubeVideos;
            int medianScore = jsonObject.getInt("medianScore");
            GameReview gameReview = new GameReview(title, tier, description, topCriticScore,
                    percentRecommended, name, allPlatforms, fullImageUrl, genresComplete, medianScore,
                    id, screenShotsUrl, allYoutubeVideos, releaseDate);
            if (!gameReviewArrayList.contains(gameReview)) {
                gameReviewArrayList.add(gameReview);
                exerciseAdapter.notifyItemInserted(position);
            }
            //gameReviewArrayList.add(gameReview);
            //exerciseAdapter.notifyItemInserted(position);


            // progBar.setVisibility(View.INVISIBLE);

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        progBar.setVisibility(View.INVISIBLE);
        isPressed = false;

    }



    private void parseNewlyReleasedGamesJson(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            int size = jsonArray.length();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.optString("name");
                String tier = jsonObject.optString("tier");
                int gameId = jsonObject.optInt("id");
                String releaseDate = jsonObject.optString("firstReleaseDate");
                if (jsonObject.has("images")) {
                    JSONObject images = jsonObject.getJSONObject("images");
                    if (images.has("box")) {
                        String smallImage = " ";
                        JSONObject box = images.getJSONObject("box");
                        if(box.has("sm")) {
                             smallImage = box.getString("sm");
                        }
                        smallImage = outletImageUrl + smallImage;
                        SecondRecyclerViewClass secondRecyclerViewClass = new SecondRecyclerViewClass(name, smallImage, tier, gameId, releaseDate);
                        if (!secondRecyclerViewList.contains(secondRecyclerViewClass)) {
                            secondRecyclerViewList.add(secondRecyclerViewClass);
                            secondRecyclerViewAdapter.notifyItemInserted(i);
                        }
                       // secondRecyclerViewList.add(secondRecyclerViewClass);
                        //secondRecyclerViewAdapter.notifyItemInserted(i);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progBar.setVisibility(View.INVISIBLE);
    }


    public void doNewGamesReleasedDownload() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String newlyReleased = "https://opencritic-api.p.rapidapi.com/game/recently-released";
        Uri.Builder urlBuilder = Uri.parse(newlyReleased).buildUpon();
        //Uri.Builder urlBuilder = Uri.parse(secondActivityURL).buildUpon();


        //urlBuilder.appendPath(input);
        String url = urlBuilder.build().toString();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseNewlyReleasedGamesJson(response);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Code to execute after the delay

                            }
                        }, 2000); // 3000 milli


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "onErrorResponse: " + error.toString());
                        Log.e(TAG, "Error Response Code: " + error.networkResponse.statusCode);
                        if (error.networkResponse.statusCode == 429) {
                            Toast.makeText(MainActivity.this, "No More API Calls For 24 Hours", Toast.LENGTH_SHORT).show();
                            // Handle the error if needed
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", helloUSYahooAPIkey);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");

                return params;
            }
        };
        queue.add(getRequest);
        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }


    public void goToNewlyReleasedGame(View v) {
        progBar.setVisibility(View.VISIBLE);
        gameReviewOrSecondRecycler = 1;
        recyclerView.setAdapter(secondRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        position = 0;
        if (secondRecyclerViewList.size() != 0) {
            int size = secondRecyclerViewList.size();

            for (int i = size - 1; i >= 0; i--) {
                secondRecyclerViewList.remove(i);
                secondRecyclerViewAdapter.notifyItemRemoved(i);
            }
        }
        if (arrayList.size() != 0) {
            arrayList.clear();
        }

        doNewGamesReleasedDownload();

    }


    @Override
    public void onClick(View v) {
        progBar.setVisibility(View.VISIBLE);

        if (gameReviewOrSecondRecycler == 0) {
            int pos = recyclerView.getChildLayoutPosition(v);
            //Exercise exercise = arrayList.get(pos);
            GameReview gameReview = gameReviewArrayList.get(pos);

            String fullImageUrl = gameReview.getFullImageUrl();
            String title = gameReview.getTitle();
            String tier = gameReview.getTier();
            String name = gameReview.getGameName();
            String description = gameReview.getDescription();
            int topCriticScore = gameReview.getTopCriticScore();
            int percentRecommended = gameReview.getPercentRecommend();
            int medianScore = gameReview.getMedianScore();
            String allPlatforms = gameReview.getAllPlatforms();
            int gameIdNum = gameReview.getGameIdNum();
            String screenShotsUrl = gameReview.getScreenShotsUrl();
            String allYoutubeVideos = gameReview.getAllYoutubeVideos();
            String releaseDate = gameReview.getReleaseDate();

            Bundle bundle = new Bundle();
            bundle.putInt("gameId", gameIdNum);
            bundle.putString("image", fullImageUrl);
            bundle.putString("title", title);
            bundle.putString("tier", tier);
            bundle.putString("name", name);
            bundle.putString("screenShots", screenShotsUrl);
            bundle.putString("description", description);
            bundle.putInt("topCriticScore", topCriticScore);
            bundle.putInt("percentRecommended", percentRecommended);
            bundle.putInt("medianScore", medianScore);
            bundle.putString("allPlatforms", allPlatforms);
            bundle.putString("allYoutubeVideos", allYoutubeVideos);
            bundle.putString("releaseDate", releaseDate);
            //Intent intent = new Intent(this, ExerciseActivity.class);
            Intent intent = new Intent(this, GameReviewExperimental.class);
            intent.putExtras(bundle);
            progBar.setVisibility(View.INVISIBLE);
            startActivity(intent);

            //String name = exercise.getName();
            //String gameId = exercise.getGameId();
            //String secondActivityUrl = secondActivityURL + gameId;

            //doReviewDownload(gameId);
            // doDownload(data.getText().toString());

            //Exercise exercise1 = new Exercise(name, gameId);
            // Exercise newExercise = new Exercise(name, equipment, bodyPart,
            //  gifUrl, instructions, target, secondaryMuscles);
            // Intent intent = new Intent(this, ExerciseActivity.class);
            // intent.putExtra("firstDetails",exercise1);
            //intent.putExtra("gamesArrayList", gameReviewArrayList);
            // startActivity(intent);


        } else {
            int pos = recyclerView.getChildLayoutPosition(v);
            SecondRecyclerViewClass secondRecyclerViewClass = secondRecyclerViewList.get(pos);
            int gameId = secondRecyclerViewClass.getSecondRecyclerViewGameId();
            String gameIdString = String.valueOf(gameId);
            doSecondReviewDownload(gameIdString);
        }
    }
    private void doSecondReviewDownload(String s){
        Uri.Builder urlBuilder = Uri.parse(secondActivityURL).buildUpon();
        urlBuilder.appendPath(s);

        String url = urlBuilder.build().toString();

        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        parseSingleGameId(response);

                        //goToDoReviewDownload();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                        if (error != null) {
                            //Log.e(TAG, "Error Response Code: " + error.networkResponse.statusCode);
                            if (error.networkResponse.statusCode == 429) {
                                Toast.makeText(MainActivity.this, "No More API Calls For 24 Hours", Toast.LENGTH_SHORT).show();
                                // Handle the error if needed
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("X-RapidAPI-Key", apiKeyDePaulEmail);
                params.put("X-RapidAPI-Host", "opencritic-api.p.rapidapi.com");
                params.put("accept", "application/json");

                Log.d(TAG, "getHeaders: " + params);

                return params;
            }
        };

        requestQueue.add(getRequest);
    }

    private void parseSingleGameId(String response){
        try {
            String allYoutubeVideos = "";
            String allPlatforms = "";
            String genresComplete = "";
            String title = "";
            String tier = "";
            String secondActImage = "";
            String screenShotsUrl = "";
            JSONObject jsonObject = new JSONObject(response);
            JSONObject images = jsonObject.getJSONObject("images");
            if (images.has("box")) {
                JSONObject boxImages = images.getJSONObject("box");
                secondActImage = boxImages.optString("og");
            } else if (images.has("banner")) {
                JSONObject bannerObj = images.getJSONObject("banner");
                secondActImage = bannerObj.optString("og");
            }
            if (images.has("screenshots")) {
                JSONArray screenshots = images.getJSONArray("screenshots");
                screenShotsUrl = "https://img.opencritic.com/" + secondActImage + ",";
                for (int i = 0; i < screenshots.length(); i++) {
                    JSONObject firstScreenShot = screenshots.getJSONObject(i);
                    if (i + 1 != screenshots.length()) {
                        screenShotsUrl = screenShotsUrl + "https://img.opencritic.com/" + firstScreenShot.optString("og") + ",";
                    } else {
                        screenShotsUrl = screenShotsUrl + "https://img.opencritic.com/" + firstScreenShot.optString("og");
                    }
                }
            }

            String fullImageUrl = "https://img.opencritic.com/" + secondActImage;

            if (jsonObject.has("mainChannel")) {
                JSONObject mainChannel = jsonObject.getJSONObject("mainChannel");
                title = mainChannel.optString("title");
            }
            if (jsonObject.has("Genres")) {
                JSONArray genresArray = jsonObject.getJSONArray("Genres");
                for (int i = 0; i < genresArray.length(); i++) {
                    JSONObject jsonObject1 = genresArray.getJSONObject(i);
                    String name = jsonObject1.optString("name");
                    genresComplete = genresComplete + " " + name;
                }
            }
            int percentRecommended = jsonObject.getInt("percentRecommended");
            int topCriticScore = jsonObject.getInt("topCriticScore");
            if (jsonObject.has("tier")) {
                tier = jsonObject.optString("tier");
            }
            String name = jsonObject.optString("name");
            String releaseDate = jsonObject.getString("firstReleaseDate");

            String hasLootBoxes = jsonObject.optString("hasLootBoxes");
            String description = jsonObject.optString("description");
            if (jsonObject.has("Platforms")) {
                JSONArray platformsArray = jsonObject.getJSONArray("Platforms");
                for (int i = 0; i < platformsArray.length(); i++) {
                    JSONObject jsonOb = platformsArray.getJSONObject(i);
                    String platformName = jsonOb.optString("name");
                    if (i + 1 != platformsArray.length()) {
                        allPlatforms = allPlatforms + platformName + ", ";
                    } else {
                        allPlatforms = allPlatforms + platformName;
                    }
                }
            }
            if (jsonObject.has("trailers")) {
                JSONArray trailers = jsonObject.getJSONArray("trailers");
                //String partOneYoutubeUrl = "https://www.youtube.com/watch?v=";
                for (int i = 0; i < trailers.length(); i++) {
                    JSONObject objectTrailers = trailers.getJSONObject(i);
                    String videoId = objectTrailers.optString("videoId");
                    if (i + 1 != trailers.length()) {
                        allYoutubeVideos = allYoutubeVideos + videoId + ",";
                    } else {
                        allYoutubeVideos = allYoutubeVideos + videoId;
                    }

                }
            }
            screenShotsUrl = screenShotsUrl + "," + allYoutubeVideos;
            int medianScore = jsonObject.getInt("medianScore");

            Bundle bundle = new Bundle();
            //bundle.putInt("gameId", gameIdNum);
            bundle.putString("releaseDate", releaseDate);
            bundle.putString("image", fullImageUrl);
            bundle.putString("title", title);
            bundle.putString("tier", tier);
            bundle.putString("name", name);
            bundle.putString("screenShots", screenShotsUrl);
            bundle.putString("description", description);
            bundle.putInt("topCriticScore", topCriticScore);
            bundle.putInt("percentRecommended", percentRecommended);
            bundle.putInt("medianScore", medianScore);
            bundle.putString("allPlatforms", allPlatforms);
            bundle.putString("allYoutubeVideos", allYoutubeVideos);
            //Intent intent = new Intent(this, ExerciseActivity.class);
            Intent intent = new Intent(this, GameReviewExperimental.class);
            intent.putExtras(bundle);
            progBar.setVisibility(View.INVISIBLE);
            startActivity(intent);


        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        progBar.setVisibility(View.INVISIBLE);
    }

    }

