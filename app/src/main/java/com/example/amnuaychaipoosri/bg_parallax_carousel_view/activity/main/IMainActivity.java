package com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main;


import com.example.amnuaychaipoosri.bg_parallax_carousel_view.base.BasePresenter;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.base.BaseView;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel.ItemsListModel;

public interface IMainActivity {

    interface View extends BaseView<Presenter> {
        void updateAdapterData(ItemsListModel itemsListModel);
        void showProgressBar();
        void hideProgressBar();
    }

    interface Presenter extends BasePresenter {
        void loadMainViewData();
    }

}
