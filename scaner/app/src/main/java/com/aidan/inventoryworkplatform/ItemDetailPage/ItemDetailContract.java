package com.aidan.inventoryworkplatform.ItemDetailPage;

import android.content.DialogInterface;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
import com.aidan.inventoryworkplatform.Entity.Item;

import java.util.List;

/**
 * Created by s352431 on 2016/11/22.
 */
public interface ItemDetailContract {
    interface view{
        void findView();
        void setViewValue(Item item);
        void setViewClick();
        void showSetDialog(DialogInterface.OnClickListener clickListener, String title, final String[] temp);
        void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List<SearchableItem> dataList);
    }
    interface presenter{
        void start();
        void saveItemToChecked(boolean flag);
        void locationTextViewClick(TextView locationTextView);
        void departmentTextViewClick(TextView departmentTextView);
        void agentTextViewClick(TextView agentTextView);
        void useGroupTextViewClick(TextView departmentTextView);
        void userTextViewClick(TextView agentTextView);
        void deleteTextViewClick(TextView deleteTextView);
        void printTextViewClick(TextView printTextView);
    }
}