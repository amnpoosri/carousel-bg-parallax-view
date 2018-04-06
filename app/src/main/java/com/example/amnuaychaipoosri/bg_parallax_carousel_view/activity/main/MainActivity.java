package com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.amnuaychaipoosri.bg_parallax_carousel_view.R;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main.adapter.CarouselAdapter;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.base.BaseActivity;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel.ItemsListModel;


public class MainActivity extends BaseActivity implements IMainActivity.View {

    private IMainActivity.Presenter mPresenter;
    private final static String TAG = "MainActivity";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    private CarouselAdapter carouselAdapter = null;
    private ItemsListModel itemsListModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenterImpl(this);
        initInflate();
        initInstance();
        mPresenter.viewOnCreate();
    }

    private void initInflate() {
        // findViewById HERE.
        swipeRefreshLayout = findViewById(R.id.activity_main_swipeRefreshLayout);
        recyclerView = findViewById(R.id.activity_main_rv);
        progressBar = findViewById(R.id.activity_main_progressBar);
    }

    private void initInstance() {
        // initial something HERE.
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        carouselAdapter = new CarouselAdapter(this, itemsListModel);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(carouselAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadMainViewData();
            }
        });

        mPresenter.loadMainViewData();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.viewOnStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.viewOnResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.viewOnPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.viewOnStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.viewOnDestroy();
    }

    @Override
    public void updateAdapterData(ItemsListModel itemsListModel) {
        this.itemsListModel = itemsListModel;
        carouselAdapter.setDao(itemsListModel);
        carouselAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        if (carouselAdapter.getItemCount() <= 0) {
            progressBar.setVisibility(View.VISIBLE);
        }
        else
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
        outState.putParcelable("ItemsListModel", itemsListModel);
    }

    @SuppressWarnings("UnusedParameters")
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
        itemsListModel = savedInstanceState.getParcelable("ItemsListModel");
    }
}
