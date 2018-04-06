package com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amnuaychaipoosri.bg_parallax_carousel_view.R;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.widget.SquareImageView;


public class CarouselItemViewHolder extends RecyclerView.ViewHolder{
    public SquareImageView ivCover;
    public TextView titleTv;
    public TextView subTitleTv;
    public LinearLayout ll;
    public CarouselItemViewHolder(View itemView) {
        super(itemView);
        ll = (LinearLayout) itemView.findViewById(R.id.viewholder_carousel_item_ll);
        ivCover = (SquareImageView) itemView.findViewById(R.id.viewholder_carousel_item_cover_iv);
        titleTv = (TextView) itemView.findViewById(R.id.viewholder_carousel_item_title_tv);
        subTitleTv = (TextView) itemView.findViewById(R.id.viewholder_carousel_item_subTitle_tv);

    }
}
