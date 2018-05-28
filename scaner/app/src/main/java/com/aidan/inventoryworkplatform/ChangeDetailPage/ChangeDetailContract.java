package com.aidan.inventoryworkplatform.ChangeDetailPage;

import android.content.DialogInterface;

import com.aidan.inventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
import com.aidan.inventoryworkplatform.Entity.Item;

import java.util.List;

/**
 * Created by Aidan on 2018/4/18.
 */

public interface ChangeDetailContract {
    interface view{
        void findView();
        void reloadInfo(Item item);
        void setViewClick();
        void showSetDialog(DialogInterface.OnClickListener clickListener, String title, final String[] temp);
        void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List dataList);
        void showScrappedViews();
        void showMoveViews();
    }
    interface presenter{
        void start();
        void changeTargetClick();
        void confirmButtonClick();
    }

}
