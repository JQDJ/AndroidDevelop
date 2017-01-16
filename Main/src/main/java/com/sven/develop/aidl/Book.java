package com.sven.develop.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sven.Zhan on 2017/1/16.
 */

public class Book implements Parcelable {

    public String name;
    public float price;

    public Book(){}

    public Book(String name, float price) {
        this.name = name;
        this.price = price;
    }

    protected Book(Parcel in) {
        name = in.readString();
        price = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {

        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
