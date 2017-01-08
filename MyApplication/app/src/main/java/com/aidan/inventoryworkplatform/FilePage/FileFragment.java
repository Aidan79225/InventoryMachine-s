package com.aidan.inventoryworkplatform.FilePage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import droidninja.filepicker.FilePickerActivity;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

/**
 * Created by Aidan on 2016/11/20.
 */

public class FileFragment extends DialogFragment implements FileContract.view {
    ViewGroup rootView;
    FileContract.presenter presenter;
    TextView inputTextView, outputTextView;
    ArrayList<String> filePaths = new ArrayList<>();
    ArrayList<String> docPaths = new ArrayList<>();
    int type = 0;
    private static final int readTxtType = 19;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_input_file, container, false);
        presenter = new FilePresenter(this);
        presenter.start();
        return rootView;
    }

    @Override
    public void findView() {
        inputTextView = (TextView) rootView.findViewById(R.id.inputTextView);
        outputTextView = (TextView) rootView.findViewById(R.id.outputTextView);
    }

    @Override
    public void setViewClick() {
        inputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPickerActivity();
            }
        });
        outputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileNameDialog();
            }
        });
    }

    public void showFileNameDialog() {
        final AlertDialog.Builder editDialog = new AlertDialog.Builder(getActivity());
        editDialog.setTitle("請輸入檔名");

        final EditText editText = new EditText(getActivity());
        SimpleDateFormat parse_DateFormatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        editText.setText("PDA"+parse_DateFormatter.format(date) );
        editDialog.setView(editText);

        editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
                presenter.saveFile(editText.getText().toString());
            }
        });
        editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {


            }
        });
        editDialog.show();
    }



    public void startPickerActivity() {
        FilePickerBuilder.getInstance().setMaxCount(1)
                .setSelectedFiles(filePaths)
                .setActivityTheme(R.style.AppTheme);
        type = readTxtType;
        Intent intent = new Intent(getActivity(), FilePickerActivity.class);
        Bundle bundle = new Bundle();
        intent.putStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS,filePaths);
        bundle.putInt(FilePickerConst.EXTRA_PICKER_TYPE, FilePickerConst.DOC_PICKER);
        intent.putExtras(bundle);
        startActivityForResult(intent, FilePickerConst.REQUEST_CODE_DOC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null)
                    handleChooseDoc(data);
                break;
        }
    }

    private void handleChooseDoc(Intent data) {
        docPaths.clear();
        docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
        switch (type) {
            case readTxtType:

                if(docPaths.size()>0)presenter.readTxtButtonClick(docPaths.get(0));
                type = 0;
                break;
        }
    }

}
