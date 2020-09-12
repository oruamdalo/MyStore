package com.daloski.mystore.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String name, author, url, image;

    public Item(String name, String author, String url, String image) {
        this.name = name;
        this.author = author;
        this.url = url;
        this.image = image;
    }

    protected Item(Parcel in) {
        name = in.readString();
        author = in.readString();
        url = in.readString();
        image = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(author);
        parcel.writeString(url);
        parcel.writeString(image);
    }
}
