package com.example.android.wellnessjournal;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.wellnessjournal.Food.FoodActivity;
import com.example.android.wellnessjournal.Utils.FirebaseMethods;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    public void onBindViewHolder(final ImageViewHolder viewHolder, int position) {

        Context context = viewHolder.poster.getContext();
        Log.d(TAG, "onBindViewHolder: binding ImageViewHolder");

        MyImage image = images.get(position);

        Picasso.with(context).load(image.getUrl()).fit().centerCrop().into(viewHolder.poster);


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

            //Toast.makeText(poster.getContext(), "BOUUUH !", Toast.LENGTH_SHORT).show();
            final Dialog dialog = new Dialog(poster.getContext());
            dialog.setContentView(R.layout.custom_dialog_box);
            dialog.setTitle("Alert Dialog View");
            Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.btnDeletePicture)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            mFirebaseMethods = new FirebaseMethods(poster.getContext());
                            MyImage image = images.get(getAdapterPosition());
                            mFirebaseMethods.removeFromDatabase(image.getPhotoKey());

                            notifyDataSetChanged();
                            dialog.dismiss();

                        }
                    });


            // show dialog on screen
            dialog.show();
        }


    }
}
