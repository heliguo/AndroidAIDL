package com.example.androidaidl.aidl.custombinder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:lgh on 2019-11-19 14:56
 */
public class CustomBook implements Parcelable {

    private int price;
    private String name;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.price);
        dest.writeString(this.name);
    }

    public CustomBook() {
    }

    protected CustomBook(Parcel in) {
        this.price = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<CustomBook> CREATOR = new Creator<CustomBook>() {
        @Override
        public CustomBook createFromParcel(Parcel source) {
            return new CustomBook(source);
        }

        @Override
        public CustomBook[] newArray(int size) {
            return new CustomBook[size];
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}

