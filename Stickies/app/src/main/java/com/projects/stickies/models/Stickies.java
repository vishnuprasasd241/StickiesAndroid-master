package com.projects.stickies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 21/07/14.
 */
public class Stickies implements Parcelable  {

    private String text;
    private long time;

    public Stickies(long timeInMillis) {
        setTime(timeInMillis);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(text);
        out.writeLong(time);
    }

    public static final Parcelable.Creator<Stickies> CREATOR
            = new Parcelable.Creator<Stickies>() {
        public Stickies createFromParcel(Parcel in) {
            return new Stickies(in);
        }

        public Stickies[] newArray(int size) {
            return new Stickies[size];
        }
    };

    private Stickies(Parcel in) {
        text = in.readString();
        time = in.readLong();
    }
}
