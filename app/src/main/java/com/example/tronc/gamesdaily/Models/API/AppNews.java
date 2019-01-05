package com.example.tronc.gamesdaily.Models.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Class que funciona como intermediaria para executar a API da steam, guardando a lista destas news
 */
public class AppNews implements Parcelable {

    private int appid;
    private List<NewsItem> newsitems;
    private int count;

    public AppNews() {
    }

    public AppNews(int appid, List<NewsItem> newsitems, int count) {
        this.appid = appid;
        this.newsitems = newsitems;
        this.count = count;
    }

    protected AppNews(Parcel in) {
        appid = in.readInt();
        newsitems = in.createTypedArrayList(NewsItem.CREATOR);
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(appid);
        dest.writeTypedList(newsitems);
        dest.writeInt(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppNews> CREATOR = new Creator<AppNews>() {
        @Override
        public AppNews createFromParcel(Parcel in) {
            return new AppNews(in);
        }

        @Override
        public AppNews[] newArray(int size) {
            return new AppNews[size];
        }
    };

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public List<NewsItem> getNewsitems(CharSequence i) {
        return newsitems;
    }

    public void setNewsitems(List<NewsItem> newsitems) {
        this.newsitems = newsitems;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
