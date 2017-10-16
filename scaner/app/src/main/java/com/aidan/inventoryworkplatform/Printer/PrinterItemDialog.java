package com.aidan.inventoryworkplatform.Printer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Model.BarCodeCreator;
import com.aidan.inventoryworkplatform.R;
import com.brother.ptouch.sdk.LabelInfo;
import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.brother.ptouch.sdk.PrinterStatus;
import com.google.zxing.BarcodeFormat;

/**
 * Created by Aidan on 2017/10/15.
 */

public class PrinterItemDialog extends Dialog {
    private Item item;
    private ImageView barcodeImageView;
    private TextView brandTextView, agentGroupTextView, itemYearTextView, itemDateTextView, itemNickNameTextView, itemNameTextView, idTextView, areaTextView;

    public PrinterItemDialog(@NonNull Context context) {
        super(context);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_print_item);
        findView();
        setContent();
        setBarCodeImage();
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                print();
            }
        });
    }

    private void findView() {
        barcodeImageView = (ImageView) findViewById(R.id.barcodeImageView);
        brandTextView = (TextView) findViewById(R.id.brandTextView);
        agentGroupTextView = (TextView) findViewById(R.id.agentGroupTextView);
        itemYearTextView = (TextView) findViewById(R.id.itemYearTextView);
        itemDateTextView = (TextView) findViewById(R.id.itemDateTextView);
        itemNickNameTextView = (TextView) findViewById(R.id.itemNickNameTextView);
        itemNameTextView = (TextView) findViewById(R.id.itemNameTextView);
        idTextView = (TextView) findViewById(R.id.idTextView);
        areaTextView = (TextView) findViewById(R.id.areaTextView);
    }

    private void setContent() {
        brandTextView.setText(item.getBrand() + "/" + item.getType());
        agentGroupTextView.setText(item.getCustodian().getName() + "/" + item.getCustodyGroup().getName());
        itemYearTextView.setText(item.getYears());
        itemDateTextView.setText(ADtoCal(item));
        itemNickNameTextView.setText(item.getNickName());
        idTextView.setText(item.getIdNumber());
        areaTextView.setText(item.getLoaclNumber());
    }

    private String ADtoCal(Item item) {
        String temp = String.valueOf((Integer.parseInt(item.getDate()) - 19110000));
        return temp.substring(0, temp.length() - 4) + "/" + temp.substring(temp.length() - 4, temp.length() - 2) + "/" + temp.substring(temp.length() - 2);
    }

    private void setBarCodeImage() {
        try {
            barcodeImageView.setImageBitmap(BarCodeCreator.encodeAsBitmap(item.getBarcodeNumber(), BarcodeFormat.CODE_128, getScreenWidth(), getScreenWidth() / 12));
        } catch (Exception e) {
            Toast.makeText(getContext(), "創造條碼失敗", Toast.LENGTH_SHORT).show();
        }

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }



    public void print() {
        Thread trd = new Thread(new Runnable() {
            @Override
            public void run() {
                String externalStorageDir = Environment.getExternalStorageDirectory().toString();    //print setting
                Printer printer = new Printer();
                PrinterInfo printInfo = new PrinterInfo();
                printInfo.printerModel = PrinterInfo.Model.PT_P900W;
                printInfo.port = PrinterInfo.Port.NET;
                printInfo.ipAddress = "172.0.0.1";
                printInfo.labelNameIndex = LabelInfo.PT.W36.ordinal();
                printer.setPrinterInfo(printInfo);

                //Image print
                String imageFile = externalStorageDir + "/Test1Page.pdf";
                printer.startCommunication();
                PrinterStatus status = printer.printFile(imageFile);
                printer.endCommunication();
                if (status.errorCode != PrinterInfo.ErrorCode.ERROR_NONE ) {
                    Toast.makeText(getContext(),"列印失敗,找不到機器",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"列印成功",Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
        trd.start();
    }
}
