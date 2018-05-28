package com.aidan.inventoryworkplatform.ChangeDetailPage;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Constants;
import com.aidan.inventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.inventoryworkplatform.Dialog.SearchItemDialog;
import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.R;
import com.aidan.inventoryworkplatform.Utils.LocalCacheHelper;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Aidan on 2018/4/17.
 */

public class ChangeDetailFragment  extends DialogFragment implements ChangeDetailContract.view {
    private ViewGroup rootView;
    private ChangeDetailContract.presenter presenter;
    private TextView    itemIdTextView,buyDateTextView,quantityTextView,unitPriceTextView,
                        nameTextView,yearsTextView,scrappedTextView,changeTargetTextView,
                        dateTextView;
    private EditText changeTargetEditText,changeNumberEditText,changeIdEditText;
    private TextView changeOrderIdTextView;
    private Button confirmButton,cancelButton;

    private View moveContainer;
    private TextView moveDepartmentTextView,newAgentTextView,moveLocationTextView;

    private View scrappedContainer;
    private TextView PA3VWWTextView,PA3VNTextView,PA3DRTextView,PA8PDTextView,PA8ATextView;

    @Override
    public void findView() {
        itemIdTextView = (TextView) rootView.findViewById(R.id.itemIdTextView);
        buyDateTextView = (TextView) rootView.findViewById(R.id.buyDateTextView);
        quantityTextView = (TextView) rootView.findViewById(R.id.quantityTextView);
        unitPriceTextView = (TextView) rootView.findViewById(R.id.unitPriceTextView);
        nameTextView = (TextView) rootView.findViewById(R.id.nameTextView);
        yearsTextView = (TextView) rootView.findViewById(R.id.yearsTextView);
        scrappedTextView = (TextView) rootView.findViewById(R.id.scrappedTextView);
        changeTargetTextView = (TextView) rootView.findViewById(R.id.changeTargetTextView);
        dateTextView = (TextView) rootView.findViewById(R.id.dateTextView);

        changeTargetEditText = (EditText) rootView.findViewById(R.id.changeTargetEditText);
        changeNumberEditText = (EditText) rootView.findViewById(R.id.changeNumberEditText);
        changeOrderIdTextView = (EditText) rootView.findViewById(R.id.changeOrderIdTextView);
        changeIdEditText = (EditText) rootView.findViewById(R.id.changeIdEditText);

        confirmButton = (Button) rootView.findViewById(R.id.confirmButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);

        moveContainer =  rootView.findViewById(R.id.moveContainer);
        moveDepartmentTextView = (TextView) rootView.findViewById(R.id.moveDepartmentTextView);
        newAgentTextView = (TextView) rootView.findViewById(R.id.newAgentTextView);
        moveLocationTextView = (TextView) rootView.findViewById(R.id.moveLocationTextView);

        scrappedContainer =  rootView.findViewById(R.id.scrappedContainer);
        PA3VWWTextView = (TextView) rootView.findViewById(R.id.PA3VWWTextView);
        PA3VNTextView = (TextView) rootView.findViewById(R.id.PA3VNTextView);
        PA3DRTextView = (TextView) rootView.findViewById(R.id.PA3DRTextView);
        PA8PDTextView = (TextView) rootView.findViewById(R.id.PA8PDTextView);
        PA8ATextView = (TextView) rootView.findViewById(R.id.PA8ATextView);

    }



    @Override
    public void reloadInfo(Item item) {
        itemIdTextView.setText(item.getPA3MOC8());
        buyDateTextView.setText(item.getPA3BD());
        quantityTextView.setText(item.getPA3QY());
        unitPriceTextView.setText(item.getPA3UNP());
        nameTextView.setText(item.getPA3MK()+"/"+item.getPA3PS());
        yearsTextView.setText(item.getPA3PY());
        scrappedTextView.setText(item.getScrappedADtoCal());
        changeIdEditText.setText(item.getPA3MOC8());
        changeNumberEditText.setText(item.getPA3MOB());
        setChangeOrderIdTextView();
    }

    private void setChangeOrderIdTextView(){
        changeOrderIdTextView.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1911).substring(1)+ String.format("%04d", LocalCacheHelper.getInt(getActivity(), Constants.PREFERENCE_SERIAL_NUMBER)));
    }

    @Override
    public void setViewClick() {
        changeTargetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.changeTargetClick();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.confirmButtonClick();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

    }

    public static ChangeDetailFragment newInstance(Item item) {
        ChangeDetailFragment fragment = new ChangeDetailFragment();
        fragment.presenter = new ChangeDetailPresenter(fragment, item);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_change_detail, container, false);
        presenter.start();
        return rootView;
    }

    @Override
    public void showSetDialog(DialogInterface.OnClickListener clickListener, String title, final String[] temp){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(title);
        dialog.setItems(temp, clickListener);
        dialog.create().show();
    }
    @Override
    public void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List dataList){
        SearchItemDialog dialog = new SearchItemDialog(getActivity(),dataList);
        dialog.setTitle(title);
        dialog.setOnClickListener(clickListener);
        dialog.show();
    }

    @Override
    public void showScrappedViews() {
        scrappedContainer.setVisibility(View.VISIBLE);
        moveContainer.setVisibility(View.GONE);
    }

    @Override
    public void showMoveViews() {
        scrappedContainer.setVisibility(View.GONE);
        moveContainer.setVisibility(View.VISIBLE);
    }
}
