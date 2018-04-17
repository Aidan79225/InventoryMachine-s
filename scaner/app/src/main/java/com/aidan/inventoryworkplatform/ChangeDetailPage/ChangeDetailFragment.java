package com.aidan.inventoryworkplatform.ChangeDetailPage;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.R;

/**
 * Created by Aidan on 2018/4/17.
 */

public class ChangeDetailFragment  extends DialogFragment implements ChangeDetailContract.view {
    private ViewGroup rootView;
    private ChangeDetailContract.presenter presenter;

    public static ChangeDetailFragment newInstance(Item item) {
        ChangeDetailFragment fragment = new ChangeDetailFragment();
        fragment.presenter = new ChangeDetailPresenter(fragment, item);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_detail, container, false);
        presenter.start();
        return rootView;
    }

    @Override
    public void findView() {

    }
}
