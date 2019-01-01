package com.example.tronc.gamesdaily.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class AppNewsContainer implements Parcelable {

    private AppNews appnews;

    public AppNewsContainer(AppNews appnews) {
        this.appnews = appnews;
    }

    public AppNewsContainer() {
    }

    protected AppNewsContainer(Parcel in) {
        appnews = in.readParcelable(AppNews.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(appnews, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppNewsContainer> CREATOR = new Creator<AppNewsContainer>() {
        @Override
        public AppNewsContainer createFromParcel(Parcel in) {
            return new AppNewsContainer(in);
        }

        @Override
        public AppNewsContainer[] newArray(int size) {
            return new AppNewsContainer[size];
        }
    };

    public AppNews getAppnews() {
        return appnews;
    }

    public void setAppnews(AppNews appnews) {
        this.appnews = appnews;
    }
}