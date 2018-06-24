package com.search.image.imagesearch.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.search.image.imagesearch.com.search.utility.HttpClient;
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


            HttpClient.getInstance(context).loadWebData(images,url);

        }
    }

