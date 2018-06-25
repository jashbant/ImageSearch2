package com.search.image.imagesearch.com.search.utility;

/**
 * Created by jashbantkumar.singh on 24/06/18.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.search.image.imagesearch.R;
import com.search.image.imagesearch.model.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClient {

    MemoryCache memoryCache=new MemoryCache();
    FileCache fileCache;
    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;
    Activity mContext;
   private static HttpClient mInstance=null;
    public static  HttpClient getInstance(Activity context){
        if(mInstance==null){
            mInstance = new HttpClient(context);
        }
        return mInstance;
    }

    private HttpClient(Activity context){
        mContext =context;
        fileCache=new FileCache(context);
        executorService=Executors.newFixedThreadPool(5);
    }

    int stub_id = R.drawable.ic_launcher_foreground;
    public void DisplayImage(String url, int loader, ImageView imageView)
    {
        stub_id = loader;
        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);
        if(bitmap!=null)
            imageView.setImageBitmap(bitmap);
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(loader);
        }
    }
public void loadWebData(MutableLiveData<Image> image, String url){
    if(!isNetworkConnected()){
        Toast.makeText(mContext,"Please check your internet connection...",Toast.LENGTH_LONG).show();
        return;
    }
  try {
      mProgress = new ProgressDialog(mContext);
      mProgress.setMessage("Please wait...");
      mProgress.show();
  }catch (Exception e){

  }
        executorService.submit(new JsonLoader(image,url));
}
    ProgressDialog mProgress;

    public void loadData(final MutableLiveData<Image> image,String url)
    {
        try {

            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
          /*  OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);*/
String response = getStringFromInputStream(is);
//System.out.println("Jashbant"+response);
            Gson gson = new Gson();
final Image data=gson.fromJson(response,Image.class);
           // System.out.println("Jashbant"+image);
            Handler mainHandler = new Handler(Looper.getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {

                    mProgress.dismiss();
                    image.setValue(data);
                } // This is your code
            };
            mainHandler.post(myRunnable);
        } catch (Exception ex){
            mProgress.dismiss();
            Toast.makeText(mContext,"Please try again...",Toast.LENGTH_LONG).show();
            ex.printStackTrace();

        }
    }
////////

  /*  class AsyncImageLoader extends AsyncImageLoader<String, String, Integer> {
        private Context mContext;

        ProgressDialog mProgress;

        public AsyncImageLoader(Context context, Context mContext) {
            super(context);
            this.mContext = mContext;
        }



        @Override
        public void onPreExecute() {
            mProgress = new ProgressDialog(mContext);
            mProgress.setMessage("Downloading nPlease wait...");
            mProgress.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mProgress.setMessage(values[0]);
        }

        @Override
        protected Integer doInBackground(String... values) {


        }

        @Override
        protected void onPostExecute(Integer results) {
            mProgress.dismiss();
            //This is where you return data back to caller

        }
    }*/



    /////////////

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
    private void queuePhoto(String url, ImageView imageView)
    {
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url)
    {    String path = Environment.getExternalStorageDirectory().toString();


        StringTokenizer tokenizer = new StringTokenizer(url,"/");
        String token="";
        while(tokenizer.hasMoreTokens()){
             token=tokenizer.nextToken();
        }

        File file = null;
        try {
            file = new File(mContext.getFilesDir().getCanonicalPath()+token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // path = file.getAbsolutePath();
      //File  file = new File(path, token);
        Bitmap b = decodeFile(file);
        if(b!=null)
            return b;

        //from web
        if(!isNetworkConnected()){
            Toast.makeText(mContext,"Please check your internet connection...",Toast.LENGTH_LONG).show();
            return null;
        }
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(file);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(file);
           // bitmap=BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception ex){
            Toast.makeText(mContext,"Please try again...",Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u;
            imageView=i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad=photoToLoad;
        }

        @Override
        public void run() {
            if(imageViewReused(photoToLoad))
                return;
            Bitmap bmp=getBitmap(photoToLoad.url);
            memoryCache.put(photoToLoad.url, bmp);
            if(imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
            Activity a=(Activity)photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }

    class JsonLoader implements Runnable {
//String url ="https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=kittens";
        String url="";
        MutableLiveData<Image> image;
        public JsonLoader(MutableLiveData<Image> image,String url) {
            this.url = url;
            this.image=image;
        }

        @Override
        public void run() {
            loadData(image,url);
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad){
        String tag=imageViews.get(photoToLoad.imageView);
        if(tag==null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
