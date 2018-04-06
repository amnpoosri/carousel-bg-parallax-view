package com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.GlideApp;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.R;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main.viewholder.CarouselBgParallaxViewHolder;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main.viewholder.CarouselViewHolder;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.constant.Constant;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel.ItemsList;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel.ItemsListModel;

public class CarouselAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ItemsListModel itemsListModel = null;
    private Context context;

    private final static int HORIZONTAL_VIEW_TYPE = 100;
    private final static int HORIZONTAL_INVISIBLE_START_VIEW_TYPE = 101;

    public CarouselAdapter(Context context, ItemsListModel itemsListModel) {
        this.context = context;
        this.itemsListModel = itemsListModel;
    }

    public void setDao(ItemsListModel itemsListModel) {
        this.itemsListModel = itemsListModel;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HORIZONTAL_INVISIBLE_START_VIEW_TYPE)
            return new CarouselBgParallaxViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_carousel_bg_parallax, parent, false));
        else
            return new CarouselViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_carousel, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemsList itemsList = itemsListModel.getItemsList().get(position);

        if (holder instanceof CarouselViewHolder) {
            CarouselViewHolder vh = (CarouselViewHolder) holder;
            vh.titleTv.setText(itemsList.getItemTitle());
            if (vh.gridAdapter == null) {
                vh.setHorizontalAdapter(context, itemsList.getItems(),false);
            } else {
                vh.updateGridAdapter(itemsList.getItems());
            }
            vh.li.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        else if(holder instanceof CarouselBgParallaxViewHolder)
        {
            final CarouselBgParallaxViewHolder vh = (CarouselBgParallaxViewHolder) holder;
            vh.titleTv.setText(itemsList.getItemTitle());

            if (vh.gridAdapter == null) {
                GlideApp.with(context)
                        .load(itemsList.getItemBgUrl())
                        .placeholder(R.drawable.place_holder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(vh.bgIv);
                vh.alphaView.setBackgroundColor(Color.parseColor(itemsList.getItemFgColor()));
                vh.horizontalRv.setOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        // Get first visible item
                        if (vh.linearLayoutManager.findFirstVisibleItemPosition()==0)
                        {
                            View firstVisibleItem = vh.linearLayoutManager.findViewByPosition(vh.linearLayoutManager.findFirstVisibleItemPosition());
                            int leftScrollXCalculated = firstVisibleItem.getLeft();

                            float x = leftScrollXCalculated * 0.2f;
                            vh.bgIv.setTranslationX(leftScrollXCalculated * 0.2f);

                            // Calculate color view transparency
                            float distanceFromLeft = firstVisibleItem.getLeft();
                            if (distanceFromLeft<=0) {
                                float alpha = (float) ((Math.abs(distanceFromLeft) / (float) firstVisibleItem.getWidth()) * 0.80);
                                vh.bgIv.setAlpha(1 - alpha);
                                vh.alphaView.setAlpha(alpha);
                                Log.d("PPPPP1", "Alpha: " + alpha);
                                Log.d("PPPPP2", "FirstVisibleItem: " + firstVisibleItem.getLeft());
                            }
                        }
                    }
                });

                vh.setHorizontalAdapter(context, itemsList.getItems(), true);

            } else {
                vh.updateGridAdapter(itemsList.getItems());
            }
            vh.li.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }


    @Override
    public int getItemCount() {
        if (itemsListModel == null)
            return 0;
        else if (itemsListModel.getItemsList() == null)
            return 0;
        return itemsListModel.getItemsList().size();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (itemsListModel.getItemsList().get(position).getType().equals(Constant.CarouselType.NORMAL))
                return HORIZONTAL_VIEW_TYPE;
            else if (itemsListModel.getItemsList().get(position).getType().equals(Constant.CarouselType.BG_PARALLAX))
                return HORIZONTAL_INVISIBLE_START_VIEW_TYPE;
            else
                return HORIZONTAL_VIEW_TYPE;
        }
        catch (Exception e)
        {
            return HORIZONTAL_VIEW_TYPE;
        }
    }

    private Bitmap createBlurBitmap(Bitmap src, float r) {
        if (r <= 0) {
            r = 0.1f;
        } else if (r > 25) {
            r = 25.0f;
        }

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, src);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(r);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;
    }
    //
//    public interface OnApplicationItemClickListener {
//        void onApplicationClicked(ApplicationDao application);
//    }
//
//    public interface OnTraceListener {
//        void onItemShown(int position);
//    }

}