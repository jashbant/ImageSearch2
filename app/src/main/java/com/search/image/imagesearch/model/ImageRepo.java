package com.search.image.imagesearch.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jashbantkumar.singh on 24/06/18.
 */

public class ImageRepo {
    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    List<Photo> photo = new ArrayList<>();
    String pages;

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    String page;
    String total;
}
