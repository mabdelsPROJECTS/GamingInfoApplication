package com.moeabdel.myapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class GameReview implements Serializable {

    private String outlet;
    private String title;

    public String getOutlet() {
        return outlet;
    }

    public String getGameName() {
        return gameName;
    }

    public String getAllPlatforms() {
        return allPlatforms;
    }

    public String getFullImageUrl() {
        return fullImageUrl;
    }
    public String getDescription(){
        return description;
    }

    public int getScore() {
        return score;
    }

    public String getSnippet() {
        return snippet;
    }
    public String getGenresComplete(){
        return genresComplete;
    }
    public int getGameIdNum(){
        return gameIdNum;
    }
    private String fullImageUrl;

    private String gameName;
    private String platform;
    private String externalUrl;
    private int score;
    private String snippet;
    private String genresComplete;

    private String tier;
    private String description;
    private int topCriticScore;
    private  int percentRecommend;
    private String allPlatforms;
    private int medianScore;
    private int gameIdNum;
    private String screenShotsUrl;
    private String allYoutubeVideos;
    private String releaseDate;
    public int getPercentRecommend(){
        return percentRecommend;
    }
    public String getTitle(){
        return title;
    }
    public String getTier(){
        return tier;
    }
    public int getTopCriticScore(){
        return topCriticScore;
    }
    public int getMedianScore(){
        return medianScore;
    }
    public String getScreenShotsUrl(){
        return screenShotsUrl;
    }
    public String getAllYoutubeVideos(){
        return allYoutubeVideos;
    }
    public String getReleaseDate(){
        return releaseDate;
    }



    public GameReview( String title, String tier, String description, int topCriticScore,
                       int percentRecommend, String gameName, String allPlatforms, String fullImageUrl,
                       String genresComplete, int medianScore, int gameIdNum, String screenShotsUrl,
                       String allYoutubeVideos, String releaseDate){

    //this.outlet = outlet;
    this.gameName = gameName;
    this.allPlatforms = allPlatforms;
    this.fullImageUrl = fullImageUrl;
    this.score = score;
    this.snippet = snippet;
    this.genresComplete = genresComplete;
    this.percentRecommend = percentRecommend;
    this.title = title;
    this.tier = tier;
    this.description = description;
    this.topCriticScore = topCriticScore;
    this.medianScore = medianScore;
    this.gameIdNum = gameIdNum;
    this.screenShotsUrl = screenShotsUrl;
    this.allYoutubeVideos = allYoutubeVideos;
    this.releaseDate = releaseDate;
    }
}
