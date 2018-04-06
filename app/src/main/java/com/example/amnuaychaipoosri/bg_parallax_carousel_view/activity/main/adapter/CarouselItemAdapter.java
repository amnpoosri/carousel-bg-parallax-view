package com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.GlideApp;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.R;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main.viewholder.CarouselItemViewHolder;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main.viewholder.CarouselInvisbileItemViewHolder;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel.Item;

import java.util.List;

public class CarouselItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items;
    private Context context;
    private boolean isInvisibleStart;

    private final static int INVISIBLE_START_VIEW_TYPE = 100;
    public CarouselItemAdapter(Context context, boolean isInvisibleStart) {
        this.context = context;
        this.isInvisibleStart = isInvisibleStart;
    }

    public void setDao(List<Item> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == INVISIBLE_START_VIEW_TYPE)
            return new CarouselInvisbileItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_carousel_invisible_item, parent, false));
        else
            return new CarouselItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_carousel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CarouselItemViewHolder) {
            if (isInvisibleStart)
                position=position-1;
            final Item item = items.get(position);
            CarouselItemViewHolder vh = (CarouselItemViewHolder) holder;
            GlideApp.with(context)
                    .load(item.getCoverUrl())
                    .placeholder(R.drawable.place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vh.ivCover);
            vh.titleTv.setText(item.getTitle());
            vh.subTitleTv.setText(item.getSubTitle());


            vh.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        if (isInvisibleStart)
            return items.size()+1;
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isInvisibleStart && position==0)
            return INVISIBLE_START_VIEW_TYPE;
        return super.getItemViewType(position);
    }
}
