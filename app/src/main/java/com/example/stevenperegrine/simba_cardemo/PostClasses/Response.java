package com.example.stevenperegrine.simba_cardemo.PostClasses;

import java.io.File;

public class Response {

    String imageName;

    File image;

    String message;


    public String getImageName() {
        return imageName;
    }

    public Response setImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public File getImage() {
        return image;
    }

    public Response setImage(File image) {
        this.image = image;
        return this;
    }
}

