package com.aidan.inventoryworkplatform.FilePage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Constants;
import com.aidan.inventoryworkplatform.R;
import com.aidan.inventoryworkplatform.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import droidninja.filepicker.FilePickerActivity;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Aidan on 2016/11/20.
 */

public class FileFragment extends DialogFragment implements FileContract.view {
    ViewGroup rootView;
    FileContract.presenter presenter;
    TextView inputTextView, outputTextView;
    ArrayList<String> filePaths = new ArrayList<>();
    ArrayList<String> docPaths = new ArrayList<>();
    Runnable fileRunnable;
    int type = 0;
    private static final int readTxtType = 19;
    private static final int REQUEST_EXTERNAL_STORAGE = 0x1;
    @Override
    public void checkPermission() {
        int permission = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        //未取得權限，向使用者要求允許權限
        else {
            //已有權限，可進行檔案存取
            if(fileRunnable != null){
                fileRunnable.run();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //取得權限，進行檔案存取
                    if(fileRunnable != null){
                        fileRunnable.run();
                    }
                }
                return;
        }
    }

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
                fileRunnable = new Runnable(){
                    @Override
                    public void run() {
                        startPickerActivity();
                    }
                };
                checkPermission();
            }
        });
        outputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileRunnable = new Runnable(){
                    @Override
                    public void run() {
                        showFileNameDialog();
                    }
                };
                checkPermission();


            }
        });
    }

    @Override
    public void showProgressUpdate(int value) {

    }

    public void showFileNameDialog() {
        final AlertDialog.Builder editDialog = new AlertDialog.Builder(getActivity());
        editDialog.setTitle("請輸入檔名");

        final EditText editText = new EditText(getActivity());
        SimpleDateFormat parse_DateFormatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String id = "";
        try {
            String MS = Singleton.preferences.getString(Constants.MS, "");
            JSONObject jsonObject = new JSONObject(MS);
            id = jsonObject.getString(Constants.MS01);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editText.setText("PD" + id + parse_DateFormatter.format(date));
        editDialog.setView(editText);

        editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
                presenter.saveFile(editText.getText().toString());
                arg0.dismiss();
            }
        });
        editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
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
        intent.putStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS, filePaths);
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

                if (docPaths.size() > 0) presenter.readTxtButtonClick(docPaths.get(0));
                type = 0;
                break;
        }
    }

}
