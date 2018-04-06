package com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemsList implements Parcelable{

    @SerializedName("itemListId")
    @Expose
    private Integer itemListId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("itemBgUrl")
    @Expose
    private String itemBgUrl;
    @SerializedName("itemFgColor")
    @Expose
    private String itemFgColor;
    @SerializedName("itemTitle")
    @Expose
    private String itemTitle;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    protected ItemsList(Parcel in) {
        if (in.readByte() == 0) {
            itemListId = null;
        } else {
            itemListId = in.readInt();
        }
        type = in.readString();
        itemBgUrl = in.readString();
        itemFgColor = in.readString();
        itemTitle = in.readString();
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<ItemsList> CREATOR = new Creator<ItemsList>() {
        @Override
        public ItemsList createFromParcel(Parcel in) {
            return new ItemsList(in);
        }

        @Override
        public ItemsList[] newArray(int size) {
            return new ItemsList[size];
        }
    };

    public Integer getItemListId() {
        return itemListId;
    }

    public void setItemListId(Integer itemListId) {
        this.itemListId = itemListId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemBgUrl() {
        return itemBgUrl;
    }

    public void setItemBgUrl(String itemBgUrl) {
        this.itemBgUrl = itemBgUrl;
    }

    public String getItemFgColor() {
        return itemFgColor;
    }

    public void setItemFgColor(String itemFgColor) {
        this.itemFgColor = itemFgColor;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (itemListId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemListId);
        }
        dest.writeString(type);
        dest.writeString(itemBgUrl);
        dest.writeString(itemFgColor);
        dest.writeString(itemTitle);
        dest.writeTypedList(items);
    }
}