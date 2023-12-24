package com.moeabdel.myapplication;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String name;

    public String getName() {
        return name;
    }
    public String getTarget(){
        return target;
    }

    public String getbodyPart() {
        return bodyPart;
    }

    public String getEqiupment() {
        return equipment;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getInstructions() {
        return instructions;
    }
    public String getSecondaryMuscle(){
        return secondaryMuscle;
    }
    public String getGameId(){
        return id;
    }
    public String getThumbNailImage(){
        return thumbNailImage;
    }

    private String equipment;
    private String bodyPart;
    private String gifUrl;
    private String instructions;
    private String target;
    private String secondaryMuscle;
    private String id;
    private String thumbNailImage;

    public Exercise(String name, String id){
        this.name = name;
        this.id = id;
        //this.thumbNailImage = thumbNailImage;
    }

    /*public Exercise(String name, String equipment, String bodyPart, String gifUrl,String instructions,String target, String secondaryMuscle)
                      {
            this.name = name;
            this.equipment = equipment;
            this.bodyPart = bodyPart;
            this.gifUrl = gifUrl;
            this.instructions = instructions;
            this.target = target;
            this.secondaryMuscle = secondaryMuscle;
    }*/
}
