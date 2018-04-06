package com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable{

    @SerializedName("itemId")
    @Expose
    private Integer itemId;
    @SerializedName("coverUrl")
    @Expose
    private String coverUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;

    protected Item(Parcel in) {
        if (in.readByte() == 0) {
            itemId = null;
        } else {
            itemId = in.readInt();
        }
        coverUrl = in.readString();
        title = in.readString();
        subTitle = in.readString();
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (itemId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemId);
        }
        dest.writeString(coverUrl);
        dest.writeString(title);
        dest.writeString(subTitle);
    }
}