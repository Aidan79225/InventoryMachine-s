package com.aidan.secondinventoryworkplatform.SettingPage;

import android.widget.TextView;

import com.aidan.secondinventoryworkplatform.dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.dialog.SearchableItem;

import java.util.List;

/**
 * Created by Aidan on 2017/1/8.
 */

public interface SettingContract {
    interface view{
        void findView();
        void setViewClick();
        void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List<SearchableItem> dataList);
        void dismiss();
        void showProgress(String msg);
        void hideProgress();
        void updateProgress(int value);
        void dismissAllowingStateLoss();
    }
    interface presenter{
        void start();
        void locationTextViewClick(TextView locationTextView);
        void departmentTextViewClick(TextView departmentTextView);
        void agentTextViewClick(TextView agentTextView);
        void useGroupTextViewClick(TextView departmentTextView);
        void userTextViewClick(TextView agentTextView);

        void searchTextViewClick();

    }
}
