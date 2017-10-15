package com.aidan.inventoryworkplatform.Printer;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Toast;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Model.BarCodeCreator;
import com.aidan.inventoryworkplatform.R;
import com.google.zxing.BarcodeFormat;

/**
 * Created by Aidan on 2017/10/15.
 */

public class PrinterItemDialog extends Dialog {
    private Item item;
    private ImageView barcodeImageView;
    public PrinterItemDialog(@NonNull Context context) {
        super(context);
    }
    public void setItem(Item item){
        this.item = item;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_print_item);
        findView();
        setBarCodeImage();
    }
    private void findView(){
        barcodeImageView = (ImageView)findViewById(R.id.barcodeImageView);
    }

    private void setBarCodeImage(){
        try {
            barcodeImageView.setImageBitmap(BarCodeCreator.encodeAsBitmap(item.getBarcodeNumber(), BarcodeFormat.CODE_128,getScreenWidth(),getScreenWidth()/12 ));
        }catch (Exception e){
            Toast.makeText(getContext(),"創造條碼失敗",Toast.LENGTH_SHORT).show();
        }

    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
