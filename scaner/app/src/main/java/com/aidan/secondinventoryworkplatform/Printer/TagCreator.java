package com.aidan.secondinventoryworkplatform.Printer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by Aidan on 2017/10/22.
 */

public class TagCreator {
    public static final int width = 900;
    public static final int height = 462;
    public static Bitmap transStringToImage(String res, int textSizePix, int lineMargin) {
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        TextPaint paint = new TextPaint();
        Typeface typeface = Typeface.DEFAULT;
        paint.setTypeface(typeface);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSizePix);
        canvas.drawRect(0, 0, width, height, paint);
        paint.setColor(Color.BLACK);
        String[] strings = res.split("\n");
        int baseLine = textSizePix + lineMargin;
        for (String temp : strings) {
            String[] lines = temp.split("\t");
            int count = 0;
            int lineWidth = width / lines.length;
            for(String line : lines){
                canvas.drawText(line, lineMargin + lineWidth * count, baseLine , paint);
                count++;
            }
            baseLine += lineMargin * 2 + textSizePix;
        }
        return bmp;
    }

    public static Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap, int margin) {
        Canvas canvas = new Canvas(backBitmap);
        Rect frontRect = new Rect(0, backBitmap.getHeight() - frontBitmap.getHeight() + margin, frontBitmap.getWidth(), backBitmap.getHeight() - margin);
        canvas.drawBitmap(frontBitmap, null, frontRect, null);
        return backBitmap;
    }

    public static Bitmap mergeQRBitmap(Bitmap backBitmap, Bitmap frontBitmap, int margin) {
        Canvas canvas = new Canvas(backBitmap);
        Rect frontRect = new Rect(backBitmap.getWidth()-frontBitmap.getWidth(), 0, backBitmap.getWidth(), frontBitmap.getHeight());
        canvas.drawBitmap(frontBitmap, null, frontRect, null);
        return backBitmap;
    }



}
