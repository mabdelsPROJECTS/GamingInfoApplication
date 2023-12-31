package com.moeabdel.myapplication;

public class SecondRecyclerViewClass {

    private String name;
    private String imageUrl;
    private int gameId;

    public String getSecondRecyclerViewName() {
        return name;
    }

    public String getSecondRecyclerViewImageUrl() {
        return imageUrl;
    }

    public String getSecondRecyclerViewTier() {
        return tier;
    }
    public int getSecondRecyclerViewGameId(){
        return gameId;
    }
    public String getReleaseDate(){
        return releaseDate;
    }

    private String tier;
    private String releaseDate;


    public SecondRecyclerViewClass(String name, String imageUrl, String tier, int gameId, String releaseDate){
        this.name = name;
        this.imageUrl = imageUrl;
        this.tier = tier;
        this.gameId = gameId;
        this.releaseDate = releaseDate;


    }


}
