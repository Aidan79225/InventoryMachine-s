package com.aidan.inventoryworkplatform.SearchPage;

import android.content.DialogInterface;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
import com.aidan.inventoryworkplatform.Entity.Item;

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
    }
    interface presenter{
        void start();
        void locationTextViewClick(TextView locationTextView);
        void departmentTextViewClick(TextView departmentTextView);
        void agentTextViewClick(TextView agentTextView);
        void useGroupTextViewClick(TextView departmentTextView);
        void userTextViewClick(TextView agentTextView);
        void searchTextViewClick(String number,String serialMinNumber,String serialMaxNumber);
        void tagContentTextViewClick(TextView tagContentTextView);
        void sortTextViewClick(TextView sortTextView);
        void minDateTextViewClick(TextView minDateTextView);
        void maxDateTextViewClick(TextView maxDateTextView);
        void clearAll();
    }
}
