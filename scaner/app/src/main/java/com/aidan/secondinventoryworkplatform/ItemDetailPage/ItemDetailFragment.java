package com.aidan.secondinventoryworkplatform.ItemDetailPage;

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
import android.widget.TextView;

import com.aidan.secondinventoryworkplatform.ChangeDetailPage.ChangeDetailFragment;
import com.aidan.secondinventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.Dialog.SearchItemDialog;
import com.aidan.secondinventoryworkplatform.Dialog.SearchableItem;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.secondinventoryworkplatform.Printer.PrinterItemDialog;
import com.aidan.secondinventoryworkplatform.R;

import java.util.List;


/**
 * Created by s352431 on 2016/11/22.
 */
public class ItemDetailFragment extends DialogFragment implements ItemDetailContract.view {
    ItemDetailContract.presenter presenter;
    ViewGroup rootView;
    TextView yearsTextView, buyDateTextView, brandTextView,
            typeTextView, locationTextView,
            nameTextView, itemIdTextView,
            custodianTextView,
            useGroupTextView,
            tagContentTextView;
    Button confirmButton,printButton , cancelButton, moveButton, deleteButton;
    ItemListFragment.RefreshItems refreshItems;

    public static ItemDetailFragment newInstance(Item item, ItemListFragment.RefreshItems refreshItems) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.presenter = new ItemDetailPresenter(fragment, item);
        fragment.refreshItems = refreshItems;
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
        confirmButton = (Button) rootView.findViewById(R.id.confirmButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        printButton= (Button) rootView.findViewById(R.id.printButton);
        yearsTextView = (TextView) rootView.findViewById(R.id.yearsTextView);
        buyDateTextView = (TextView) rootView.findViewById(R.id.buyDateTextView);
        brandTextView = (TextView) rootView.findViewById(R.id.brandTextView);
        typeTextView = (TextView) rootView.findViewById(R.id.typeTextView);

        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        nameTextView = (TextView) rootView.findViewById(R.id.nameTextView);
        itemIdTextView = (TextView) rootView.findViewById(R.id.itemIdTextView);

        custodianTextView = (TextView) rootView.findViewById(R.id.custodianTextView);
        useGroupTextView = (TextView) rootView.findViewById(R.id.useGroupTextView);
        deleteButton = (Button) rootView.findViewById(R.id.deleteButton);
        moveButton = (Button) rootView.findViewById(R.id.moveButton);
        tagContentTextView= (TextView) rootView.findViewById(R.id.tagContentTextView);
    }

    @Override
    public void setViewValue(Item item) {
        yearsTextView.setText(item.getYears());
        buyDateTextView.setText(item.ADtoCal());
        brandTextView.setText(item.getBrand());
        typeTextView.setText(item.getType());
        custodianTextView.setText(item.getCustodian().name);
        useGroupTextView.setText(item.getUseGroup().name);
        locationTextView.setText(item.getLocation().name);
        nameTextView.setText(item.getName());
        itemIdTextView.setText(item.getIdNumber());
        if(item.getTagContent() != null){
            tagContentTextView.setText(item.getTagContent().getName());
        }
    }

    @Override
    public void setViewClick() {

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveItemToChecked(true);
                refreshItems.refresh();
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshItems.refresh();
                dismiss();
            }
        });
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.locationTextViewClick();
            }
        });
        custodianTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.agentTextViewClick();
            }
        });
        useGroupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.useGroupTextViewClick();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteButton();
            }
        });
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.moveButtonClick();
            }
        });
        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.printButtonClick();
            }
        });

        tagContentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.tagContentTextViewClick();
            }
        });

    }
    @Override
    public void showSetDialog(DialogInterface.OnClickListener clickListener, String title, final String[] temp){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(title);
        dialog.setItems(temp, clickListener);
        dialog.create().show();
    }
    @Override
    public void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List<SearchableItem> dataList){
        SearchItemDialog dialog = new SearchItemDialog(getActivity(),dataList);
        dialog.setTitle(title);
        dialog.setOnClickListener(clickListener);
        dialog.show();
    }

    @Override
    public void showPrintDialog(Item item) {
        PrinterItemDialog dialog = new PrinterItemDialog(getActivity());
        dialog.setItem(item);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void gotoChangeDetail(Item item) {
        ChangeDetailFragment fragment = ChangeDetailFragment.newInstance(item,refreshItems);
        fragment.show(getFragmentManager(),ChangeDetailFragment.class.getName());
        dismissAllowingStateLoss();
    }
}
