package com.search.image.imagesearch.view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.search.image.imagesearch.R;
import com.search.image.imagesearch.com.search.utility.HttpClient;
import com.search.image.imagesearch.model.Photo;

import java.util.List;




public class ImageViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Activity mContext;
    private List<Photo> imageData;



    public ImageViewAdapter(Activity context,List<Photo> data) {
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



         ;
            int loader = R.drawable.ic_launcher_background;

            String url =  String.format(mContext.getResources().getString(R.string.url_image),photo.getFarm(),photo.getServer(),photo.getId(),photo.getSecret());
            HttpClient.getInstance(mContext).DisplayImage(url, loader, imageView);


        }


        @Override
        public void onClick(View view) {

        }
    }


}
