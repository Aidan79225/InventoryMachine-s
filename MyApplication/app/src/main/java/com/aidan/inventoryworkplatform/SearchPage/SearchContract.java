package com.aidan.inventoryworkplatform.SearchPage;

import android.content.DialogInterface;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Entity.Item;

import java.util.List;

/**
 * Created by Aidan on 2017/1/8.
 */

public interface SearchContract {
    interface view{
        void findView();
        void setViewClick();
        void showSetDialog(DialogInterface.OnClickListener clickListener, String title, final String[] temp);
        void showFragmentWithResult(List<Item> items);
        void clearViews();
    }
    interface presenter{
        void start();
        void locationTextViewClick(TextView locationTextView);
        void departmentTextViewClick(TextView departmentTextView);
        void agentTextViewClick(TextView agentTextView);
        void searchTextViewClick();
        void clearAll();
    }
}
