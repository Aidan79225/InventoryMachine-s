package com.aidan.secondinventoryworkplatform.ChangeDetailPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.dialog.SearchItemDialog;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ChangeTarget;
import com.aidan.secondinventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.secondinventoryworkplatform.R;
import com.aidan.secondinventoryworkplatform.Utils.LocalCacheHelper;

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
    private EditText changeNumberEditText,changeIdEditText, changeOrderIdEditText;
    private TextView changeTargetEditText;
    private Button confirmButton,cancelButton;

    private View moveContainer;
    private TextView moveDepartmentTextView,newAgentTextView,moveLocationTextView;

    private View scrappedContainer;
    private TextView PA3VWWTextView,PA3VNTextView,PA3DRTextView,PA8PDTextView,PA8ATextView;
    private EditText PA3VWWEditText,PA3VNEditText,PA3DREditText,PA8PDEditText,PA8AEditText;
    ItemListFragment.RefreshItems refreshItems;

    @Override
    public void findView() {
        itemIdTextView = rootView.findViewById(R.id.itemIdTextView);
        buyDateTextView = rootView.findViewById(R.id.buyDateTextView);
        quantityTextView = rootView.findViewById(R.id.quantityTextView);
        unitPriceTextView = rootView.findViewById(R.id.unitPriceTextView);
        nameTextView = rootView.findViewById(R.id.nameTextView);
        yearsTextView = rootView.findViewById(R.id.yearsTextView);
        scrappedTextView = rootView.findViewById(R.id.scrappedTextView);
        changeTargetTextView = rootView.findViewById(R.id.changeTargetTextView);
        dateTextView = rootView.findViewById(R.id.dateTextView);

        changeTargetEditText = rootView.findViewById(R.id.changeTargetEditText);
        changeNumberEditText = rootView.findViewById(R.id.changeNumberEditText);
        changeOrderIdEditText = rootView.findViewById(R.id.changeOrderIdEditText);
        changeIdEditText = rootView.findViewById(R.id.changeIdEditText);

        confirmButton = rootView.findViewById(R.id.confirmButton);
        cancelButton = rootView.findViewById(R.id.cancelButton);

        moveContainer =  rootView.findViewById(R.id.moveContainer);
        moveDepartmentTextView = rootView.findViewById(R.id.moveDepartmentTextView);
        newAgentTextView = rootView.findViewById(R.id.newAgentTextView);
        moveLocationTextView = rootView.findViewById(R.id.moveLocationTextView);

        scrappedContainer =  rootView.findViewById(R.id.scrappedContainer);
        PA3VWWTextView = rootView.findViewById(R.id.PA3VWWTextView);
        PA3VNTextView = rootView.findViewById(R.id.PA3VNTextView);
        PA3DRTextView = rootView.findViewById(R.id.PA3DRTextView);
        PA8PDTextView = rootView.findViewById(R.id.PA8PDTextView);
        PA8ATextView = rootView.findViewById(R.id.PA8ATextView);
        PA3VWWEditText = rootView.findViewById(R.id.PA3VWWEditText);
        PA3VNEditText = rootView.findViewById(R.id.PA3VNEditText);
        PA3DREditText = rootView.findViewById(R.id.PA3DREditText);
        PA8PDEditText = rootView.findViewById(R.id.PA8PDEditText);
        PA8AEditText = rootView.findViewById(R.id.PA8AEditText);

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
        changeNumberEditText.setText(item.getPA3MOB());
        changeIdEditText.setText(item.getPA3MOC8());
        setChangeOrderIdEditText();
        setDateTextView();
    }

    private void setChangeOrderIdEditText(){
        changeOrderIdEditText.setText("");
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
        return changeOrderIdEditText.getText().toString();
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
