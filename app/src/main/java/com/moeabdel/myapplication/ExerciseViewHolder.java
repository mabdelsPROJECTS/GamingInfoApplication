package com.moeabdel.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {

    TextView recyclerViewTitleText;
    ImageView recyclerViewImage;
    ImageView backGroundImage;
    ConstraintLayout layout;
    TextView recyclerViewPlatforms;

    public ExerciseViewHolder(@NonNull View itemview){
        super(itemview);
        recyclerViewTitleText = itemview.findViewById(R.id.recyclerViewText);
        recyclerViewImage = itemview.findViewById(R.id.recyclerViewArtworkImage);
        //backGroundImage = itemview.findViewById(R.id.backgroundImage);
        //layout = itemview.findViewById(R.id.adapterlayout);
        recyclerViewPlatforms = itemview.findViewById(R.id.platformsTextView);
        layout = itemview.findViewById(R.id.layout);
    }
}
