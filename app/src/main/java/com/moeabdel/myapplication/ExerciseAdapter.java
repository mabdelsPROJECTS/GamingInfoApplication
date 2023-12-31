package com.moeabdel.myapplication;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {

    //private ArrayList<Exercise> exerciseArrayList;
    private ArrayList<GameReview> gameReviewArrayList;
    private MainActivity mainActivity;
   // private ConstraintLayout layout;

    private Picasso picasso;



    public ExerciseAdapter(ArrayList<GameReview> gameReviewArrayList, MainActivity mainActivity){
        //this.exerciseArrayList = exerciseArrayList;
        this.gameReviewArrayList = gameReviewArrayList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_recyclerview, parent,false);
        itemview.setOnClickListener(mainActivity);
        //itemview.setOnLongClickListener(mainActivity);
        picasso = Picasso.get();
        return new ExerciseViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        GameReview gameReview = gameReviewArrayList.get(position);
        String allPlatforms = gameReview.getAllPlatforms();
        String name = gameReview.getGameName();
        String genres = gameReview.getGenresComplete();
        String fullImageUrl = gameReview.getFullImageUrl();
        if (allPlatforms.contains("Xbox") && !allPlatforms.contains("PlayStation") && !allPlatforms.contains("PC")) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.xboxGreen));
            //backgroundImage.setImageResource(R.drawable.newxboximage);

        } else if (allPlatforms.contains("PlayStation") && !allPlatforms.contains("Xbox") && !allPlatforms.contains("PC")) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.playstationBlue));

        } else if(allPlatforms.contains("PlayStation") && allPlatforms.contains("Xbox") && allPlatforms.contains("PC")) {
            holder.layout.setBackgroundColor(Color.BLACK);

        }
        else if(allPlatforms.contains("PC") && !allPlatforms.contains("PlayStation") && !allPlatforms.contains("Xbox")){
            holder.layout.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.steamColor));
        }
        else{
            holder.layout.setBackgroundColor(Color.BLACK);
        }


        holder.recyclerViewTitleText.setText(name);
        holder.recyclerViewPlatforms.setText(genres);
        picasso.load(fullImageUrl).error(R.drawable.noimagetodisplay).into(holder.recyclerViewImage);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your code that you want to execute after the delay
            }
        }, position * 300); // Adjust the delay time based on your preference
    }





    @Override
    public int getItemCount() {
        return gameReviewArrayList.size();
    }
}

