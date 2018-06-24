package com.search.image.imagesearch.model;

/**
 * Created by jashbantkumar.singh on 24/06/18.
 */

public class Image {

    public ImageRepo getPhotos() {
        return photos;
    }

    public void setPhotos(ImageRepo photos) {
        this.photos = photos;
    }

    ImageRepo photos;
    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    String stat;

}

