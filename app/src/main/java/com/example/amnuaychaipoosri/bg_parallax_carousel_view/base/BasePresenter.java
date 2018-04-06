package com.example.amnuaychaipoosri.bg_parallax_carousel_view.base;

public interface BasePresenter {

    void viewOnCreate();

    void viewOnStart();

    void viewOnResume();

    void viewOnPause();

    void viewOnStop();

    void viewOnDestroy();

    void viewOnCreateView();

    void viewOnDestroyView();
}
