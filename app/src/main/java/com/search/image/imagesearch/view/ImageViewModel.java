package com.search.image.imagesearch.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.search.image.imagesearch.com.search.utility.ImageLoader;
import com.search.image.imagesearch.model.Image;

/**
 * Created by jashbantkumar.singh on 24/06/18.
 */


    public class ImageViewModel extends ViewModel {
        private MutableLiveData<Image> images;
        public LiveData<Image> getPhoto(String url,Context context) {

                images = new MutableLiveData<Image>();
                loadPhoto(url,context);

            return images;
        }

        private void loadPhoto(String url,Context context) {
            // Do an asynchronous operation to fetch users.
            ImageLoader imgLoader = new ImageLoader(context);
           // int loader = R.drawable.ic_launcher_background;
            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView
            imgLoader.loadWebData(images,url);
        }
    }

