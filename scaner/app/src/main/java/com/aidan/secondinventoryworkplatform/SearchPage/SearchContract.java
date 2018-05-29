package com.aidan.secondinventoryworkplatform.SearchPage;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.aidan.secondinventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.Dialog.SearchableItem;
import com.aidan.secondinventoryworkplatform.Entity.Item;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Aidan on 2017/1/8.
 */

public interface SearchContract {
    interface view{
        void findView();
        void setViewClick();
        void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List<SearchableItem> dataList);
        void showFragmentWithResult(List<Item> items);
        void clearViews();
        void showToast(String msg);
        void showProgress(String msg);
        void hideProgress();
        void updateProgress(int value);
        void setMinDateTextView(Calendar c);
        void setMaxDateTextView(Calendar c);
        Context getContext();
    }
    interface presenter{
        void start();
        void locationTextViewClick(TextView locationTextView);
        void agentTextViewClick(TextView agentTextView);
        void useGroupTextViewClick(TextView departmentTextView);
        void searchTextViewClick(String name, String number,String serialMinNumber,String serialMaxNumber);
        void printTextViewClick(Context context, String name, String number, String serialMinNumber, String serialMaxNumber);
        void tagContentTextViewClick(TextView tagContentTextView);
        void sortTextViewClick(TextView sortTextView);
        void minDateTextViewClick(Activity activity);
        void maxDateTextViewClick(Activity activity);
        void clearAll();
    }
}
