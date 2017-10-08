package com.example.android.wellnessjournal;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.wellnessjournal.Food.FoodActivity;
import com.example.android.wellnessjournal.Utils.FirebaseMethods;
import com.example.android.wellnessjournal.Utils.ImageManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.wellnessjournal.R.drawable.add_picture;

/**
 * Created by Maryline on 9/18/2017.
 */


public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ImageViewHolder>{

    private FirebaseMethods mFirebaseMethods;

    private static final String TAG="PicturesAdapter";

    public List<MyImage> getImages() {
        return images;
    }

    public void setImages(List<MyImage> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    private List<MyImage> images;



    final private ListItemClickHandler mOnClickHandler;
    private Cursor mCursor;

    public PicturesAdapter(List<MyImage> images, ListItemClickHandler listener){
        this.images = images;

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

        MyImage image = images.get(position);
       // image.getPictureUri();
       // Bitmap bm = BitmapFactory.decodeFile(image.getPath());


        //Bitmap bm = ImageManager.getBitmapFromURL("http://weknowyourdreams.com/single/monkey/monkey-12.jpg");
        Picasso.with(context).load(image.getUrl()).fit().centerCrop().into(viewHolder.poster);

        //viewHolder.poster.setImageBitmap(ThumbnailUtils
         //       .extractThumbnail(bm,
           //            300, 300));


    }

    @Override
    public int getItemCount() {
        return images.size();


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
