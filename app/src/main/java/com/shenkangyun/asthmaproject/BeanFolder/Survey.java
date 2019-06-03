package com.shenkangyun.asthmaproject.BeanFolder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rilintech on 16/4/21.
 */
public class Survey implements Parcelable {
    private String answer;
    private String date;
    private String location;
    private String score;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.answer);
        dest.writeString(this.date);
        dest.writeString(this.location);
        dest.writeString(this.score);
    }

    public Survey() {
    }

    protected Survey(Parcel in) {
        this.answer = in.readString();
        this.date = in.readString();
        this.location = in.readString();
        this.score = in.readString();
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel source) {
            return new Survey(source);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };
}
