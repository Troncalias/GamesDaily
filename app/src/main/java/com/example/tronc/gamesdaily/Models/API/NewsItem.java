package com.example.tronc.gamesdaily.Models.API;


import android.os.Parcel;
import android.os.Parcelable;

public class NewsItem implements Parcelable {

    private String gid;
    private String title;
    private String url;
    private boolean is_external_url;
    private String author;
    private String contents;
    private String feedlabel;
    private long date;
    private String feedname;
    private int feed_type;
    private int appid;

    public NewsItem() {
    }

    public NewsItem(String gid, String title, String url, boolean is_external_url, String author, String contents, String feedlabel, long date, String feedname, int feed_type, int appid) {
        this.gid = gid;
        this.title = title;
        this.url = url;
        this.is_external_url = is_external_url;
        this.author = author;
        this.contents = contents;
        this.feedlabel = feedlabel;
        this.date = date;
        this.feedname = feedname;
        this.feed_type = feed_type;
        this.appid = appid;
    }

    protected NewsItem(Parcel in) {
        gid = in.readString();
        title = in.readString();
        url = in.readString();
        is_external_url = in.readByte() != 0;
        author = in.readString();
        contents = in.readString();
        feedlabel = in.readString();
        date = in.readLong();
        feedname = in.readString();
        feed_type = in.readInt();
        appid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gid);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeByte((byte) (is_external_url ? 1 : 0));
        dest.writeString(author);
        dest.writeString(contents);
        dest.writeString(feedlabel);
        dest.writeLong(date);
        dest.writeString(feedname);
        dest.writeInt(feed_type);
        dest.writeInt(appid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };

    public String getGid() {
        return gid;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public boolean isIs_external_url() {
        return is_external_url;
    }

    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    public String getFeedlabel() {
        return feedlabel;
    }

    public long getDate() {
        return date;
    }

    public String getFeedname() {
        return feedname;
    }

    public int getFeed_type() {
        return feed_type;
    }

    public int getAppid() {
        return appid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIs_external_url(boolean is_external_url) {
        this.is_external_url = is_external_url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setFeedlabel(String feedlabel) {
        this.feedlabel = feedlabel;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setFeedname(String feedname) {
        this.feedname = feedname;
    }

    public void setFeed_type(int feed_type) {
        this.feed_type = feed_type;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    @Override
    public String toString() {
        return
                "title='" + title + '\'';
    }
}
