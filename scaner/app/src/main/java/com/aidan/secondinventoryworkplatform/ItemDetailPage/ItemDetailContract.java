package com.aidan.secondinventoryworkplatform.ItemDetailPage;

import android.content.DialogInterface;

import com.aidan.secondinventoryworkplatform.dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.dialog.SearchableItem;
import com.aidan.secondinventoryworkplatform.Entity.Item;

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
        void showPrintDialog(Item item);
        void showLittlePrintDialog(Item item);
        void gotoMoveChangeDetail(Item item);
        void gotoDeleteChangeDetail(Item item);
    }
    interface presenter{
        void start();
        void saveItemToChecked(boolean flag);
        void locationTextViewClick();
        void agentTextViewClick();
        void useGroupTextViewClick();
        void deleteButton();
        void moveButtonClick();
        void printButtonClick();
        void printLittleButtonClick();
        void tagContentTextViewClick();
    }
}