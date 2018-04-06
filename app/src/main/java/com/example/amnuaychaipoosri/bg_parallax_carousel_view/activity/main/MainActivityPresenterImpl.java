package com.example.amnuaychaipoosri.bg_parallax_carousel_view.activity.main;

import com.example.amnuaychaipoosri.bg_parallax_carousel_view.constant.Constant;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.manager.HttpManager;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.BaseResponse;
import com.example.amnuaychaipoosri.bg_parallax_carousel_view.model.ItemsListModel.ItemsListModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenterImpl implements IMainActivity.Presenter {

    private IMainActivity.View view;
    private final static String TAG = "RedeemFragmentPresenterImpl";

    public MainActivityPresenterImpl(IMainActivity.View iView) {
        this.view = iView;
    }

    @Override
    public void viewOnCreate() {

    }

    @Override
    public void viewOnStart() {

    }

    @Override
    public void viewOnResume() {

    }

    @Override
    public void viewOnPause() {

    }

    @Override
    public void viewOnStop() {

    }

    @Override
    public void viewOnDestroy() {

    }

    @Override
    public void viewOnCreateView() {

    }

    @Override
    public void viewOnDestroyView() {

    }

    @Override
    public void loadMainViewData() {
        view.showProgressBar();
        Call<BaseResponse<ItemsListModel>> call = HttpManager.getInstance(Constant.Mode.MODE_DEVELOPMENT).getService().loadHomeData();
        call.enqueue(new Callback<BaseResponse<ItemsListModel>>() {
            @Override
            public void onResponse(Call<BaseResponse<ItemsListModel>> call, Response<BaseResponse<ItemsListModel>> response) {
                view.hideProgressBar();
                ItemsListModel itemsListModel = response.body().getResponseObject();
                if (response.isSuccessful()) { // 200
                    view.updateAdapterData(itemsListModel);
                } else {
                    //case error;

                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ItemsListModel>> call, Throwable t) {
                //case error;
                view.hideProgressBar();
            }
        });
    }
}
