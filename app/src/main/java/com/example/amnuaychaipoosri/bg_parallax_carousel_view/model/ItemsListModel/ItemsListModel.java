package com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemsListModel implements Parcelable {

    @SerializedName("itemsList")
    @Expose
    private List<ItemsList> itemsList = null;

    protected ItemsListModel(Parcel in) {
        itemsList = in.createTypedArrayList(ItemsList.CREATOR);
    }

    public static final Creator<ItemsListModel> CREATOR = new Creator<ItemsListModel>() {
        @Override
        public ItemsListModel createFromParcel(Parcel in) {
            return new ItemsListModel(in);
        }

        @Override
        public ItemsListModel[] newArray(int size) {
            return new ItemsListModel[size];
        }
    };

    public List<ItemsList> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<ItemsList> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(itemsList);
    }
}