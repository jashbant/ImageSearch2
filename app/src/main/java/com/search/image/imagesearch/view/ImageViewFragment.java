package com.search.image.imagesearch.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.search.image.imagesearch.R;
import com.search.image.imagesearch.model.Image;
import com.search.image.imagesearch.model.Photo;

import java.util.List;

/**
 * Created by jashbantkumar.singh on 24/06/18.
 */

public class ImageViewFragment extends  android.support.v4.app.Fragment implements
        View.OnClickListener{
    private RecyclerView quickViewList;
    private EditText editText;
    private Button searchButton;
    private ImageViewAdapter quickViewAdaptor;
    private LinearLayoutManager layoutManager;
   Context mContext;
    public ImageViewFragment() {
    }

    public static ImageViewFragment newInstance(Context context) {

        return new ImageViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);


        return inflater.inflate(R.layout.fragment_imageview_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);




    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);



    }


    private void initViews(View view){
        quickViewList = (RecyclerView) view.findViewById(R.id.quickview_cart_list);
        editText =(EditText)view.findViewById(R.id.edittext_search);
        searchButton=(Button) view.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuickView(editText.getText().toString());
            }
        });
        layoutManager= new GridLayoutManager(getActivity(),3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//or HORIZONTAL
        quickViewList.setLayoutManager(layoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //toolbar = getActivity().findViewById(R.id.toolbar);

    }




    private void setQuickViewData(List<Photo> data){
        if(data==null)
            return;


        quickViewAdaptor = new ImageViewAdapter(getActivity(),data);
        quickViewList.setAdapter(quickViewAdaptor);


    }




    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onClick(View view) {

    }


    private void loadQuickView(String str) {

        ImageViewModel model = ViewModelProviders.of(this).get(ImageViewModel.class);
        String url=String.format(getActivity().getResources().getString(R.string.url_content),str);
        model.getPhoto(url,getActivity().getApplicationContext()).observe(this,  new Observer<Image>() {
            @Override
            public void onChanged(@Nullable Image image) {

                setQuickViewData(image.getPhotos().getPhoto());

            }});

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}


