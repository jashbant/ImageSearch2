package com.search.image.imagesearch.view;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.search.image.imagesearch.com.search.utility.HttpClient;
import com.search.image.imagesearch.model.Image;

/**
 * Created by jashbantkumar.singh on 24/06/18.
 */


    public class ImageViewModel extends ViewModel {
        private MutableLiveData<Image> images;
        public LiveData<Image> getPhoto(String url,Activity context) {

                images = new MutableLiveData<Image>();
                loadPhoto(url,context);

            return images;
        }

        private void loadPhoto(String url,Activity context) {



            HttpClient.getInstance(context).loadWebData(images,url);

        }
    }

