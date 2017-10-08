package com.example.android.wellnessjournal;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Maryline on 9/14/2017.
 */

public class MyImage {
    private String title, description, path, photo_id, user_id, url, photoKey;
    private Calendar datetime;
    private long datetimeLong;
    protected SimpleDateFormat df = new SimpleDateFormat("MMMM d, yy  h:mm");

    public String getUrl() {
        return url;
    }

    public String getPhotoKey() {
        return photoKey;
    }

    public void setPhotoKey(String photoKey) {
        this.photoKey = photoKey;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    /*


        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }
    */
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }





    public MyImage() {

    }

    public MyImage( String description, String path, String user_id, long datetime, String url, String photoKey) {
        //this.title = title;
        this.description = description;
        this.photoKey = photoKey;
        this.path = path;
        //this.photo_id = photo_id;
        this.user_id = user_id;
        this.datetimeLong = datetime;
        this.url = url;
        //this.pictureUri = pictureUri;
    }


    /**
     * Gets datetime.
     *
     * @return Value of datetime.
     */
    //public Calendar getDatetime() { return datetime; }

    /**
     * Sets new datetimeLong.
     *
     * @param datetimeLong New value of datetimeLong.
     */
    public void setDatetime(long datetimeLong) {
        this.datetimeLong = datetimeLong;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetimeLong);
        this.datetime = cal;
    }

    /**
     * Sets new datetime.
     *
     * @param datetime New value of datetime.
     */
    //public void setDatetime(Calendar datetime) { this.datetime = datetime; }

    /**
     * Gets description.
     *
     * @return Value of description.
     */
    public String getDescription() { return description; }



    /**
     * Gets datetimeLong.
     *
     * @return Value of datetimeLong.
     */
    public long getDatetimeLong() { return datetimeLong; }

    /**
     * Sets new description.
     *
     * @param description New value of description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets new path.
     *
     * @param path New value of path.
     */
    public void setPath(String path) { this.path = path; }

    /**
     * Gets path.
     *
     * @return Value of path.
     */
    public String getPath() { return path; }

    @Override
    public String toString() {
        return "MyImage{" +
                ", description='" + description + '\'' +
                ", path='" + path + '\'' +
                ", user_id='" + user_id + '\'' +
                ", datetimeLong=" + datetimeLong +
                ", url=" + url +
                '}';
    }
}

