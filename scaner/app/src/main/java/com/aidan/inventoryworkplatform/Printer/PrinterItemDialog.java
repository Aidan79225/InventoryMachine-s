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
import com.aidan.inventoryworkplatform.KeyConstants;
import com.aidan.inventoryworkplatform.Model.BarCodeCreator;
import com.aidan.inventoryworkplatform.R;
import com.brother.ptouch.sdk.LabelInfo;
import com.brother.ptouch.sdk.NetPrinter;
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
    private TextView AuthorityNameTextView, brandTextView, agentGroupTextView, itemYearTextView, itemDateTextView, itemNickNameTextView, itemNameTextView, idTextView, areaTextView;
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
        AuthorityNameTextView = (TextView) findViewById(R.id.AuthorityNameTextView);
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
        AuthorityNameTextView.setText(KeyConstants.AuthorityName);
        brandTextView.setText(item.getBrand() + "/" + item.getType());
        agentGroupTextView.setText(item.getCustodian().getName() + "/" + item.getCustodyGroup().getName());
        itemYearTextView.setText(item.getYears());
        itemDateTextView.setText(item.ADtoCal());
        itemNickNameTextView.setText(item.getNickName());
        itemNameTextView.setText(item.getName());
        idTextView.setText(item.getTagIdNumber());
        areaTextView.setText(item.getLoaclNumber());
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

    public int dpToPix(int dp) {
        return (int) Resources.getSystem().getDisplayMetrics().density * dp;
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

//                Bitmap bitmap = getBitmapFromView(itemInformationContainer);
                int width = getScreenWidth();
                int height = getScreenWidth() * 3 / 7;
                Bitmap bitmap = TagCreator.transStringToImage(item.getTagContentString(), width, height, height / 10 - dpToPix(2) * 2, dpToPix(2));
                try {
                    bitmap = TagCreator.mergeBitmap(bitmap, BarCodeCreator.encodeAsBitmap(item.getBarcodeNumber(), BarcodeFormat.CODE_128, width, height / 5), dpToPix(2));
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
                NetPrinter[] netPrinters = printer.getNetPrinters("PT-P900W");
                if (netPrinters == null || netPrinters.length == 0) {
                    showToast("列印失敗,找不到機器");
                    dismiss();
                } else {
                    PrinterInfo printInfo = new PrinterInfo();
                    printInfo.printerModel = PrinterInfo.Model.PT_P900W;
                    printInfo.port = PrinterInfo.Port.NET;
                    printInfo.ipAddress = netPrinters[0].ipAddress;
                    printInfo.macAddress = netPrinters[0].macAddress;
                    printInfo.labelNameIndex = LabelInfo.PT.W36.ordinal();
                    printInfo.orientation = PrinterInfo.Orientation.LANDSCAPE;
                    printInfo.align = PrinterInfo.Align.CENTER;
                    printInfo.isAutoCut = false;
                    printInfo.isCutAtEnd = true;
                    printInfo.isHalfCut = true;
                    printer.setPrinterInfo(printInfo);
                    printer.startCommunication();
                    PrinterStatus status = printer.printFile(file.getAbsolutePath());
                    printer.endCommunication();
                    if (status.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                        showToast("列印失敗,找不到機器");
                    } else {
                        showToast("列印成功");
                    }
                    dismiss();
                }
            }
        });
        trd.start();
    }

    private void showToast(final String msg) {
        barcodeImageView.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
