package com.search.image.imagesearch.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.search.image.imagesearch.R;
import com.search.image.imagesearch.com.search.utility.ImageLoader;
import com.search.image.imagesearch.model.Photo;

import java.util.List;




public class ImageViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   // private QuickViewItemClickListener itemClickListener;
    private Context mContext;
    private List<Photo> imageData;



    public ImageViewAdapter(Context context,List<Photo> data) {
        mContext = context;
        imageData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imageview_list_item_row, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).setData(imageData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (imageData == null) {
            return 0;
        } else {
            return imageData.size();
        }
    }


    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final View itemView;

        public final ImageView imageView;



        public ImageViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.list_image_view);


        }

        private void setData(final Photo photo) {



            ImageLoader imgLoader = new ImageLoader(mContext);
            int loader = R.drawable.ic_launcher_background;
            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView
         // String url=http://farm%7Bfarm%7D.static.flickr.com/%7Bserver%7D/%7Bid%7D_%7Bsecret%7D.jpg

            String url =  String.format(mContext.getResources().getString(R.string.url),photo.getFarm(),photo.getServer(),photo.getId(),photo.getSecret());
            imgLoader.DisplayImage(url, loader, imageView);


        }


        @Override
        public void onClick(View view) {

        }
    }


}
