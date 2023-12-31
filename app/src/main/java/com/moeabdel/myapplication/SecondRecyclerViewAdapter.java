package com.moeabdel.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SecondRecyclerViewAdapter extends RecyclerView.Adapter<SecondRecyclerViewHolder> {

    //private ArrayList<Exercise> exerciseArrayList;
    private ArrayList<SecondRecyclerViewClass> gameReviewArrayList;
    private MainActivity mainActivity;
    // private ConstraintLayout layout;

    private Picasso picasso;



    public SecondRecyclerViewAdapter(ArrayList<SecondRecyclerViewClass> gameReviewArrayList, MainActivity mainActivity){
        //this.exerciseArrayList = exerciseArrayList;
        this.gameReviewArrayList = gameReviewArrayList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public SecondRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_recycler_view, parent,false);
        itemview.setOnClickListener(mainActivity);
        //itemview.setOnLongClickListener(mainActivity);
        picasso = Picasso.get();
        return new SecondRecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondRecyclerViewHolder holder, int position) {
        /*SuperHero superhero = superHeroesArrayList.get(position);
        Exercise exercise = exerciseArrayList.get(position);
        GameReview gameReview = gameReviewArrayList.get(position);
        String allPlatforms = gameReview.getAllPlatforms();
        String name = gameReview.getGameName();
        String genres = gameReview.getGenresComplete();
        String fullImageUrl = gameReview.getFullImageUrl();
         String bodypart = exercise.getbodyPart();
        String gifUrl = exercise.getGifUrl();
        if (allPlatforms.contains("Xbox") && !allPlatforms.contains("PlayStation") && !allPlatforms.contains("PC")) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.xboxGreen));
            //backgroundImage.setImageResource(R.drawable.newxboximage);

        } else if (allPlatforms.contains("PlayStation") && !allPlatforms.contains("Xbox") && !allPlatforms.contains("PC")) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.playstationBlue));

        } else if(allPlatforms.contains("PlayStation") && allPlatforms.contains("Xbox") && allPlatforms.contains("PC)")) {
            holder.layout.setBackgroundColor(Color.BLACK);

        }
        else if(allPlatforms.contains("PC") && !allPlatforms.contains("PlayStation") && !allPlatforms.contains("Xbox")){
            holder.layout.setBackgroundColor(ContextCompat.getColor(mainActivity, R.color.steamColor));
        }
        else{
            holder.layout.setBackgroundColor(Color.BLACK);
        }


        holder.recyclerViewTitleText.setText(name);
        //holder.recyclerViewTitleText.setText(exercise.getName());
        //holder.recyclerViewAliases.setText(bodypart);
        holder.recyclerViewPlatforms.setText(genres);
        //Glide.with(mainActivity).load(fullImageUrl).into(holder.recyclerViewImage);
        picasso.load(fullImageUrl).error(R.drawable.noimagetodisplay).into(holder.recyclerViewImage);
*/
        SecondRecyclerViewClass secondRecyclerViewClass = gameReviewArrayList.get(position);
        String name = secondRecyclerViewClass.getSecondRecyclerViewName();
        String imageUrl = secondRecyclerViewClass.getSecondRecyclerViewImageUrl();
        String tier = secondRecyclerViewClass.getSecondRecyclerViewTier();
        String releaseDate = secondRecyclerViewClass.getReleaseDate();


        // Parse the input date string
        Instant instant = Instant.parse(releaseDate);

        // Convert to LocalDateTime in the default time zone (UTC)
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        // Format as a normal date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String normalDateString = dateTime.format(formatter);
        holder.recyclerViewTitleText.setText(name);
        holder.recyclerViewPlatforms.setText(normalDateString);
        picasso.load(imageUrl).error(R.drawable.noimagetodisplay).into(holder.recyclerViewImage);

    }

    @Override
    public int getItemCount() {
        return gameReviewArrayList.size();
    }
}


