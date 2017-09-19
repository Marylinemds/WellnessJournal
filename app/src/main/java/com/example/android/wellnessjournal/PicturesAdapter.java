package com.example.android.wellnessjournal;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.wellnessjournal.Food.FoodActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.wellnessjournal.R.drawable.add_picture;

/**
 * Created by Maryline on 9/18/2017.
 */


public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ImageViewHolder>{

    private static final String TAG="PicturesAdapter";


    final private ListItemClickHandler mOnClickHandler;
    private Cursor mCursor;

    public PicturesAdapter(ListItemClickHandler listener){

        mOnClickHandler = listener;
        //this.imgURLs = imgURLs;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: creating ImageViewHolder");
        Context context = parent.getContext();
        int layoutIdForPoster = R.layout.picture_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForPoster, parent, shouldAttachToParentImmediately);
        ImageViewHolder viewHolder = new ImageViewHolder(view);


        return viewHolder;
    }

    public interface ListItemClickHandler{
        void onClick(int clickedItemIndex);
    }


    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, int position) {

        Context context = viewHolder.poster.getContext();
        Log.d(TAG, "onBindViewHolder: binding ImageViewHolder");


        Picasso.with(context).load("https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/S" +
                "aimiri_sciureus-1_Luc_Viatour.jpg/640px-Saimiri_sciureus-1_Luc_Viatour.jpg").into(viewHolder.poster);



    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView poster;

        public ImageViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.picture_poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(poster.getContext(), "BOUUUH !", Toast.LENGTH_SHORT).show();

        }
    }
}
