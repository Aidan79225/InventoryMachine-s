package com.aidan.secondinventoryworkplatform.ChangeDetailPage;

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

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.Dialog.SearchItemDialog;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ChangeTarget;
import com.aidan.secondinventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.secondinventoryworkplatform.R;
import com.aidan.secondinventoryworkplatform.Utils.LocalCacheHelper;

import java.util.Calendar;
import java.util.Date;
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
    private EditText changeNumberEditText,changeIdEditText;
    private TextView changeTargetEditText,changeOrderIdTextView;
    private Button confirmButton,cancelButton;

    private View moveContainer;
    private TextView moveDepartmentTextView,newAgentTextView,moveLocationTextView;

    private View scrappedContainer;
    private TextView PA3VWWTextView,PA3VNTextView,PA3DRTextView,PA8PDTextView,PA8ATextView;
    private EditText PA3VWWEditText,PA3VNEditText,PA3DREditText,PA8PDEditText,PA8AEditText;
    ItemListFragment.RefreshItems refreshItems;

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

        changeTargetEditText = (TextView) rootView.findViewById(R.id.changeTargetEditText);
        changeNumberEditText = (EditText) rootView.findViewById(R.id.changeNumberEditText);
        changeOrderIdTextView = (TextView ) rootView.findViewById(R.id.changeOrderIdTextView);
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
        PA3VWWEditText = (EditText)rootView.findViewById(R.id.PA3VWWEditText);
        PA3VNEditText = (EditText)rootView.findViewById(R.id.PA3VNEditText);
        PA3DREditText = (EditText)rootView.findViewById(R.id.PA3DREditText);
        PA8PDEditText = (EditText)rootView.findViewById(R.id.PA8PDEditText);
        PA8AEditText = (EditText)rootView.findViewById(R.id.PA8AEditText);

    }



    @Override
    public void reloadInfo(Item item) {
        itemIdTextView.setText(item.getPA3MOC8());
        buyDateTextView.setText(item.ADtoCal());
        quantityTextView.setText(item.getPA3QY());
        unitPriceTextView.setText(item.getPA3UNP());
        nameTextView.setText(item.getPA3MK()+"/"+item.getPA3PS());
        yearsTextView.setText(item.getPA3PY());
        scrappedTextView.setText(item.getScrappedADtoCal());
        changeIdEditText.setText(item.getPA3MOC8());
        changeNumberEditText.setText(item.getPA3MOB());
        setChangeOrderIdTextView();
        setDateTextView();
    }

    private void setChangeOrderIdTextView(){
        changeOrderIdTextView.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1911).substring(1)+ String.format("%04d", LocalCacheHelper.getInt(getActivity(), getThisYesrSerialNumberKey())+1));
    }
    private void setDateTextView(){
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        dateTextView.setText((y-1911)+"/"+(m+1)+"/"+d);
        dateTextView.setTag(c);
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
                LocalCacheHelper.setInt(getActivity(), getThisYesrSerialNumberKey(),LocalCacheHelper.getInt(getActivity(),getThisYesrSerialNumberKey())+1);
                dismissAllowingStateLoss();
                if(refreshItems != null){
                    refreshItems.refresh();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });
        moveDepartmentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.moveDepartmentClick();
            }
        });
        newAgentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.newAgentClick();
            }
        });
        moveLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.moveLocationClick();
            }
        });

        PA3VWWTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.PA3VWWClick();
            }
        });
        PA3VNTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.PA3VNClick();
            }
        });
        PA3DRTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.PA3DRClick();
            }
        });
        PA8PDTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.PA8PDClick();
            }
        });
        PA8ATextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.PA8AClick();
            }
        });


    }

    public static ChangeDetailFragment newInstance(Item item, ItemListFragment.RefreshItems refreshItems, ChangeTarget changeTarget) {
        ChangeDetailFragment fragment = new ChangeDetailFragment();
        fragment.presenter = new ChangeDetailPresenter(fragment, item, changeTarget);
        fragment.refreshItems = refreshItems;
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
    public void showScrappedViews(ChangeTarget data, Item item) {
        scrappedContainer.setVisibility(View.VISIBLE);
        moveContainer.setVisibility(View.GONE);
        changeTargetEditText.setText(data.getName());
        PA3VWWEditText.setText(item.getPA3VWW());
        PA3VNEditText.setText(item.getPA3VN());
        PA3DREditText.setText(item.getPA3DR());
        PA8PDEditText.setText(item.getPA8PD());
        PA8AEditText.setText(item.getPA8A());
    }

    @Override
    public void showMoveViews(ChangeTarget data, Item item) {
        scrappedContainer.setVisibility(View.GONE);
        moveContainer.setVisibility(View.VISIBLE);
        changeTargetEditText.setText(data.getName());
        moveDepartmentTextView.setText(item.getPA3OUTN());
        newAgentTextView.setText(item.getPA3OUN());
        moveLocationTextView.setText(item.getPA3LOCN());
    }

    @Override
    public void setMoveDepartment(String name) {
        moveDepartmentTextView.setText(name);
    }

    @Override
    public void setNewAgent(String name) {
        newAgentTextView.setText(name);
    }

    @Override
    public void setMoveLocation(String name) {
        moveLocationTextView.setText(name);
    }

    @Override
    public String getMoveDepartment() {
        return moveDepartmentTextView.getText().toString();
    }

    @Override
    public String getNewAgent() {
        return newAgentTextView.getText().toString();
    }

    @Override
    public String getMoveLocation() {
        return moveLocationTextView.getText().toString();
    }

    @Override
    public String getChangeTargetName() {
        return changeTargetEditText.getText().toString();
    }

    @Override
    public void setPA3VWW(String name) {
        PA3VWWEditText.setText(name);
    }

    @Override
    public void setPA3VN(String name) {
        PA3VNEditText.setText(name);
    }

    @Override
    public void setPA3DR(String name) {
        PA3DREditText.setText(name);
    }

    @Override
    public void setPA8PD(String name) {
        PA8PDEditText.setText(name);
    }

    @Override
    public void setPA8A(String name) {
        PA8AEditText.setText(name);
    }

    @Override
    public String getPA3VWW() {
        return PA3VWWEditText.getText().toString();
    }

    @Override
    public String getPA3VN() {
        return PA3VNEditText.getText().toString();
    }

    @Override
    public String getPA3DR() {
        return PA3DREditText.getText().toString();
    }

    @Override
    public String getPA8PD() {
        return PA8PDEditText.getText().toString();
    }

    @Override
    public String getPA8A() {
        return PA8AEditText.getText().toString();
    }

    @Override
    public Calendar getDate() {
        return ((Calendar)dateTextView.getTag());
    }

    @Override
    public String getChangeOrderId() {
        return changeOrderIdTextView.getText().toString();
    }

    @Override
    public String getChangeId() {
        return changeIdEditText.getText().toString();
    }

    @Override
    public String getNumber() {
        return changeNumberEditText.getText().toString();
    }

    public String getThisYesrSerialNumberKey(){
        return Constants.PREFERENCE_SERIAL_NUMBER + Calendar.getInstance().get(Calendar.YEAR);
    }
}
