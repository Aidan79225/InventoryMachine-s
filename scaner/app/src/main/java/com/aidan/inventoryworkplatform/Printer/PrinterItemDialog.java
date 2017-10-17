package com.aidan.inventoryworkplatform.Printer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
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

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Aidan on 2017/10/15.
 */

public class PrinterItemDialog extends Dialog {
    private Item item;
    private ImageView barcodeImageView;
    private TextView brandTextView, agentGroupTextView, itemYearTextView, itemDateTextView, itemNickNameTextView, itemNameTextView, idTextView, areaTextView;
    private View itemInformationContainer;

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
        itemInformationContainer = findViewById(R.id.itemInformationContainer);
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

    public Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


    public void print() {
        Thread trd = new Thread(new Runnable() {
            @Override
            public void run() {
                String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/欣華盤點系統/圖片暫存";
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                Bitmap bitmap = getBitmapFromView(itemInformationContainer);
                String fileName = "temp.png";
                File file = new File(dir, fileName);
                if (file.exists()) {
                    file.delete();
                    file = new File(dir, fileName);
                }

                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Printer printer = new Printer();
                PrinterInfo printInfo = new PrinterInfo();
                printInfo.printerModel = PrinterInfo.Model.PT_P900W;
                printInfo.port = PrinterInfo.Port.NET;
                printInfo.ipAddress = "172.0.0.1";
                printInfo.labelNameIndex = LabelInfo.PT.W36.ordinal();
                printer.setPrinterInfo(printInfo);


                printer.startCommunication();
                PrinterStatus status = printer.printFile(file.getAbsolutePath());
                printer.endCommunication();
                if (status.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                    Toast.makeText(getContext(), "列印失敗,找不到機器", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "列印成功", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
        trd.start();
    }
}
