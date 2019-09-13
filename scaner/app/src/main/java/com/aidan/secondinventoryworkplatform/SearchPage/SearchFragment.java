package com.aidan.secondinventoryworkplatform.SearchPage;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aidan.secondinventoryworkplatform.BaseFragmentManager;
import com.aidan.secondinventoryworkplatform.dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.dialog.SearchItemDialog;
import com.aidan.secondinventoryworkplatform.dialog.SearchableItem;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.secondinventoryworkplatform.KeyConstants;
import com.aidan.secondinventoryworkplatform.R;

import java.util.Calendar;
import java.util.List;


/**
 * Created by Aidan on 2017/1/8.
 */

public class SearchFragment extends DialogFragment implements SearchContract.view {
    SearchContract.presenter presenter;
    ViewGroup rootView;
    EditText serialMinNumberEditText, serialMaxNumberEditText;
    TextView locationTextView, agentTextView;
    TextView searchTextView, clearTextView, printTextView, printLittleTagTextView;
    TextView useGroupTextView;
    EditText c0EditText, c1EditText, c2EditText, c3EditText, c4EditText, c5EditText;
    BaseFragmentManager baseFragmentManager;
    TextView tagContentTextView, sortTextView, minDateTextView, maxDateTextView;
    EditText nameEditText;
    ProgressDialog mProgressDialog;

    public static SearchFragment newInstance(BaseFragmentManager baseFragmentManager) {
        SearchFragment fragment = new SearchFragment();
        fragment.baseFragmentManager = baseFragmentManager;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        if (presenter == null) presenter = new SearchPresenter(this);
        presenter.start();
        return rootView;
    }

    @Override
    public void findView() {
        c0EditText = rootView.findViewById(R.id.c0EditText);
        c1EditText = rootView.findViewById(R.id.c1EditText);
        c2EditText = rootView.findViewById(R.id.c2EditText);
        c3EditText = rootView.findViewById(R.id.c3EditText);
        c4EditText = rootView.findViewById(R.id.c4EditText);
        c5EditText = rootView.findViewById(R.id.c5EditText);
        serialMinNumberEditText = rootView.findViewById(R.id.serialMinNumberEditText);
        serialMaxNumberEditText = rootView.findViewById(R.id.serialMaxNumberEditText);
        locationTextView = rootView.findViewById(R.id.locationTextView);
        agentTextView = rootView.findViewById(R.id.agentTextView);
        searchTextView = rootView.findViewById(R.id.searchTextView);
        clearTextView = rootView.findViewById(R.id.clearTextView);
        printTextView = rootView.findViewById(R.id.printTextView);
        useGroupTextView = rootView.findViewById(R.id.useGroupTextView);
        tagContentTextView = rootView.findViewById(R.id.tagContentTextView);
        sortTextView = rootView.findViewById(R.id.sortTextView);
        minDateTextView = rootView.findViewById(R.id.minDateTextView);
        maxDateTextView = rootView.findViewById(R.id.maxDateTextView);
        nameEditText = rootView.findViewById(R.id.nameEditText);
        printLittleTagTextView = rootView.findViewById(R.id.printLittleTagTextView);
    }

    @Override
    public void setViewClick() {
        locationTextView.setOnClickListener(v -> presenter.locationTextViewClick(locationTextView));
        agentTextView.setOnClickListener(v -> presenter.agentTextViewClick(agentTextView));

        useGroupTextView.setOnClickListener(v -> presenter.useGroupTextViewClick(useGroupTextView));
        tagContentTextView.setOnClickListener(v -> presenter.tagContentTextViewClick(tagContentTextView));
        sortTextView.setOnClickListener(v -> presenter.sortTextViewClick(sortTextView));
        minDateTextView.setOnClickListener(v -> presenter.minDateTextViewClick(getActivity()));
        maxDateTextView.setOnClickListener(v -> presenter.maxDateTextViewClick(getActivity()));
        clearTextView.setOnClickListener(v -> presenter.clearAll());
        searchTextView.setOnClickListener(v -> {
            String c0 = c0EditText.getText().toString();
            String c1 = c1EditText.getText().toString();
            String c2 = c2EditText.getText().toString();
            String c3 = c3EditText.getText().toString();
            String c4 = c4EditText.getText().toString();
            String c5 = c5EditText.getText().toString();
            String name = nameEditText.getText().toString();
            presenter.searchTextViewClick(name, c0, c1, c2, c3, c4, c5, serialMinNumberEditText.getText().toString(), serialMaxNumberEditText.getText().toString());
        });

        printTextView.setOnClickListener(v -> {
            String c0 = c0EditText.getText().toString();
            String c1 = c1EditText.getText().toString();
            String c2 = c2EditText.getText().toString();
            String c3 = c3EditText.getText().toString();
            String c4 = c4EditText.getText().toString();
            String c5 = c5EditText.getText().toString();
            String name = nameEditText.getText().toString();
            presenter.printTextViewClick(rootView.getContext(), name, c0, c1, c2, c3, c4, c5, serialMinNumberEditText.getText().toString(), serialMaxNumberEditText.getText().toString());
        });
        printLittleTagTextView.setOnClickListener(v -> {
            String id = "";
            id += c1EditText.getText().toString();
            id += c2EditText.getText().toString();
            id += c3EditText.getText().toString();
            id += c4EditText.getText().toString();
            id += c5EditText.getText().toString();
            String name = nameEditText.getText().toString();
            presenter.printLittleTextViewClick(rootView.getContext(), name, id, serialMinNumberEditText.getText().toString(), serialMaxNumberEditText.getText().toString());
        });
        c1EditText.addTextChangedListener(getNextTextWatcher(1, c2EditText));
        c2EditText.addTextChangedListener(getNextTextWatcher(2, c3EditText));
        c3EditText.addTextChangedListener(getNextTextWatcher(2, c4EditText));
        c4EditText.addTextChangedListener(getNextTextWatcher(2, c5EditText));
        c5EditText.addTextChangedListener(getNextTextWatcher(4, serialMinNumberEditText));
        serialMinNumberEditText.addTextChangedListener(getNextTextWatcher(7, serialMaxNumberEditText));
        printTextView.setVisibility(KeyConstants.showPrint ? View.VISIBLE : View.GONE);
    }

    private TextWatcher getNextTextWatcher(final int length, final EditText next) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == length) {
                    next.requestFocus();
                }
            }
        };
    }

    @Override
    public void clearViews() {
        c1EditText.setText("");
        c2EditText.setText("");
        c3EditText.setText("");
        c4EditText.setText("");
        c5EditText.setText("");
        serialMinNumberEditText.setText("");
        serialMaxNumberEditText.setText("");
        locationTextView.setText("請點選存置地點");
        agentTextView.setText("請點選保管人");
        tagContentTextView.setText("請點選標籤內容");
        sortTextView.setText("請點選排序條件");
        minDateTextView.setText("請點選起始日期");
        maxDateTextView.setText("請點選最後日期");
        nameEditText.setText("");
    }

    @Override
    public void showToast(final String msg) {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(rootView.getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List<SearchableItem> dataList) {
        SearchItemDialog dialog = new SearchItemDialog(getActivity(), dataList);
        dialog.setTitle(title);
        dialog.setOnClickListener(clickListener);
        dialog.show();
    }

    @Override
    public void showFragmentWithResult(List<Item> items) {
        Fragment fragment = ItemListFragment.newInstance(items, baseFragmentManager, true);
        baseFragmentManager.loadFragment(fragment);
    }

    @Override
    public void showProgress(final String title) {
        rootView.post(() -> {
            mProgressDialog = new ProgressDialog(rootView.getContext());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle(title);
            mProgressDialog.setMessage("正在處理請稍後...");
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        });
    }

    @Override
    public void hideProgress() {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void updateProgress(final int value) {
        rootView.post(() -> mProgressDialog.setProgress(value));
    }

    @Override
    public void setMinDateTextView(Calendar c) {
        minDateTextView.setText((c.get(Calendar.YEAR) - 1911) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void setMaxDateTextView(Calendar c) {
        maxDateTextView.setText((c.get(Calendar.YEAR) - 1911) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH));
    }


}
