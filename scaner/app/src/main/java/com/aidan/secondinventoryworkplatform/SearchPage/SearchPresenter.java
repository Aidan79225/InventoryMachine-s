package com.aidan.secondinventoryworkplatform.SearchPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.aidan.secondinventoryworkplatform.DatePicker.TimePickerView;
import com.aidan.secondinventoryworkplatform.dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.dialog.SearchableItem;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Agent;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Department;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Location;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.SortCategory;
import com.aidan.secondinventoryworkplatform.Entity.TagContent;
import com.aidan.secondinventoryworkplatform.Model.BarCodeCreator;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.AgentSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.DepartmentSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.LocationSingleton;
import com.aidan.secondinventoryworkplatform.Printer.TagCreator;
import com.brother.ptouch.sdk.LabelInfo;
import com.brother.ptouch.sdk.NetPrinter;
import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.brother.ptouch.sdk.PrinterStatus;
import com.google.zxing.BarcodeFormat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Aidan on 2017/1/8.
 */

public class SearchPresenter implements SearchContract.presenter {
    SearchContract.view view;
    String[] locationStrings = {};
    String[] agentStrings = {};
    String[] departmentStrings = {};
    Location location;
    Agent agent;
    Department useGroup;
    TagContent selectTagContent = null;
    SortCategory sortCategory = null;
    Calendar minCalendar;
    Calendar maxCalendar;
    Date minDate = null;
    Date maxDate = null;

    SearchPresenter(SearchContract.view view) {
        this.view = view;
        minCalendar = Calendar.getInstance();
        minCalendar.set(Calendar.HOUR_OF_DAY, 0);
        minCalendar.set(Calendar.MINUTE, 0);
        maxCalendar = Calendar.getInstance();
        maxCalendar.set(Calendar.HOUR_OF_DAY, 23);
        maxCalendar.set(Calendar.MINUTE, 59);
    }

    @Override
    public void start() {
        init();
        view.findView();
        view.setViewClick();
    }

    private void init() {
        List<Location> locationList = LocationSingleton.getInstance().getDataList();
        List<String> locationStringList = new ArrayList<>();
        for (Location location : locationList) {
            locationStringList.add(location.name);
        }
        locationStrings = locationStringList.toArray(locationStrings);

        List<Agent> agentList = AgentSingleton.getInstance().getDataList();
        List<String> agentStringList = new ArrayList<>();
        for (Agent agent : agentList) {
            agentStringList.add(agent.name);
        }
        agentStrings = agentStringList.toArray(agentStrings);


        List<Department> departmentList = DepartmentSingleton.getInstance().getDataList();
        List<String> departmentStringList = new ArrayList<>();
        for (Department department : departmentList) {
            departmentStringList.add(department.name);
        }
        departmentStrings = departmentStringList.toArray(departmentStrings);
    }

    @Override
    public void locationTextViewClick(final TextView locationTextView) {
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll(LocationSingleton.getInstance().getDataList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                locationTextView.setText(item.getName());
                location = (Location) item;
            }
        }, "地點列表", temp);
    }

    @Override
    public void agentTextViewClick(final TextView agentTextView) {
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll(AgentSingleton.getInstance().getDataList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                agentTextView.setText(item.getName());
                agent = (Agent) item;
            }
        }, "保管人列表", temp);
    }

    @Override
    public void useGroupTextViewClick(final TextView useGroupTextVie) {
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll(DepartmentSingleton.getInstance().getDataList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                useGroupTextVie.setText(item.getName());
                useGroup = (Department) item;
            }
        }, "使用部門列表", temp);
    }

    @Override
    public void tagContentTextViewClick(final TextView tagContentTextView) {
        List<SearchableItem> temp = new ArrayList<>();
        for (TagContent tagContent : TagContent.values()) {
            temp.add(tagContent);
        }
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                selectTagContent = (TagContent) item;
                tagContentTextView.setText(item.getName());
            }
        }, "標籤內容", temp);
    }

    @Override
    public void sortTextViewClick(final TextView sortTextView) {
        List<SearchableItem> temp = new ArrayList<>();
        for (SortCategory sortCategory : SortCategory.values()) {
            temp.add(sortCategory);
        }
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                sortCategory = (SortCategory) item;
                sortTextView.setText(item.getName());
            }
        }, "排序條件", temp);
    }

    @Override
    public void minDateTextViewClick(Activity activity) {
        showDatePicker(minCalendar, new Runnable() {
            @Override
            public void run() {
                minDate = minCalendar.getTime();
                maxCalendar.set(minCalendar.get(Calendar.YEAR), minCalendar.get(Calendar.MONTH), minCalendar.get(Calendar.DAY_OF_MONTH));
                maxDate = maxCalendar.getTime();
                view.setMinDateTextView(minCalendar);
                view.setMaxDateTextView(maxCalendar);
            }
        }, activity);
    }

    @Override
    public void maxDateTextViewClick(Activity activity) {
        showDatePicker(maxCalendar, new Runnable() {
            @Override
            public void run() {
                maxDate = maxCalendar.getTime();
                view.setMaxDateTextView(maxCalendar);
            }
        }, activity);
    }

    //    public void showDatePicker(final Calendar c, final Runnable callback) {
//        DatePickerDialog d = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                c.set(year, month, day);
//                callback.run();
//            }
//        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
//        d.show();
//    }
    public void showDatePicker(final Calendar c, final Runnable callback, Context context) {
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Calendar temp = Calendar.getInstance();
                temp.setTime(date);
                c.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH));
                callback.run();
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();
        pvTime.setDate(c);
        pvTime.show();
    }

    @Override
    public void searchTextViewClick(String name, String c0, String c1, String c2, String c3, String c4, String c5,String serialMinNumber,String serialMaxNumber){
        int minSerialNumber = serialMinNumber.length() > 0 ? Integer.valueOf(serialMinNumber) : 0;
        int maxSerialNumber = serialMaxNumber.length() > 0 ? Integer.valueOf(serialMaxNumber) : Integer.MAX_VALUE;
        List<Item> itemList = getItemListWithCondition(name,  c0, c1, c2, c3, c4, c5, minSerialNumber, maxSerialNumber);
        view.showFragmentWithResult(itemList);
    }

    @Override
    public void printTextViewClick(Context context, String name, String c0, String c1, String c2, String c3, String c4, String c5, String serialMinNumber, String serialMaxNumber) {
        int minSerialNumber = serialMinNumber.length() > 0 ? Integer.valueOf(serialMinNumber) : 0;
        int maxSerialNumber = serialMaxNumber.length() > 0 ? Integer.valueOf(serialMaxNumber) : Integer.MAX_VALUE;
        final List<Item> itemList = getItemListWithCondition(name, c0, c1, c2, c3, c4, c5, minSerialNumber, maxSerialNumber);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("列印").
                setMessage("將會列印 " + itemList.size() + " 個項目，您確定要列印嗎？").
                setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        print(itemList);
                        view.showToast("列印中請稍後");
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public List<Item> getItemListWithCondition(String name, String c0, String c1, String c2, String c3, String c4, String c5, int minSerialNumber, int maxSerialNumber) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : ItemSingleton.getInstance().getItemList()) {
            if (!name.isEmpty() && !item.getName().contains(name)) {
                continue;
            }
            if (location != null && !item.getLocation().name.equals(location.name)) {
                continue;
            }
            if (agent != null && !item.getCustodian().name.equals(agent.name)) {
                continue;
            }
            if (useGroup != null && !item.getUseGroup().name.equals(useGroup.name)) {
                continue;
            }
            if(!c0.isEmpty() && !c0.equals(item.getPA3C0())){
                continue;
            }
            if(!c1.isEmpty() && !c1.equals(item.getPA3C1())){
                continue;
            }
            if(!c2.isEmpty() && !c2.equals(item.getPA3C2())){
                continue;
            }
            if(!c3.isEmpty() && !c3.equals(item.getPA3C3())){
                continue;
            }
            if(!c4.isEmpty() && !c4.equals(item.getPA3C4())){
                continue;
            }
            if(!c5.isEmpty() && !c5.equals(item.getPA3C5())){
                continue;
            }

            int serialNumber = Integer.valueOf(item.getSerialNumber().substring(2));
            if (serialNumber < minSerialNumber || serialNumber > maxSerialNumber) {
                continue;
            }
            if (minDate != null && minDate.getTime() > item.getDate().getTime()) {
                continue;
            }
            if (maxDate != null && maxDate.getTime() < item.getDate().getTime()) {
                continue;
            }
            item.setTagContent(selectTagContent);
            itemList.add(item);

        }
        if (sortCategory != null) {
            switch (sortCategory) {
                case Agent:
                    Collections.sort(itemList, new Comparator<Item>() {
                        @Override
                        public int compare(Item o1, Item o2) {
                            return o1.getCustodian().getName().compareTo(o2.getCustodian().getName());
                        }
                    });
                    break;
                case Group:
                    Collections.sort(itemList, new Comparator<Item>() {
                        @Override
                        public int compare(Item o1, Item o2) {
                            return o1.getCustodyGroup().getName().compareTo(o2.getCustodyGroup().getName());
                        }
                    });
                    break;
                case Location:
                    Collections.sort(itemList, new Comparator<Item>() {
                        @Override
                        public int compare(Item o1, Item o2) {
                            return o1.getLocation().getName().compareTo(o2.getLocation().getName());
                        }
                    });
                    break;
            }
        }
        return itemList;
    }

    @Override
    public void clearAll() {
        location = null;
        agent = null;
        useGroup = null;
        selectTagContent = null;
        sortCategory = null;
        minDate = null;
        maxDate = null;
        minCalendar = Calendar.getInstance();
        minCalendar.set(Calendar.HOUR_OF_DAY, 0);
        minCalendar.set(Calendar.MINUTE, 0);
        maxCalendar = Calendar.getInstance();
        maxCalendar.set(Calendar.HOUR_OF_DAY, 23);
        maxCalendar.set(Calendar.MINUTE, 59);
        view.clearViews();
    }

    public int dpToPix(int dp) {
        return (int) Resources.getSystem().getDisplayMetrics().density * dp;
    }

    public void print(final List<Item> itemList) {
        Thread trd = new Thread(new Runnable() {
            @Override
            public void run() {
                view.showProgress("製造標籤中");
                String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/欣華盤點系統/圖片暫存";
                List<File> fileList = new ArrayList<>();
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                for (int i = 0; i < itemList.size(); i++) {
                    Item item = itemList.get(i);
                    Bitmap bitmap = TagCreator.transStringToImage(item.getTagContentString(), TagCreator.height / 10, 0);
                    try {
                        bitmap = TagCreator.mergeBitmap(bitmap, BarCodeCreator.encodeAsBitmap(item.getBarcodeNumber(), BarcodeFormat.CODE_128, TagCreator.width, TagCreator.height / 4), dpToPix(2));
                        bitmap = TagCreator.mergeQRBitmap(bitmap, BarCodeCreator.encodeAsBitmap(item.getBarcodeNumber(), BarcodeFormat.QR_CODE, TagCreator.height / 3, TagCreator.height / 3), dpToPix(2));

                        String fileName = item.getNumber() + item.getSerialNumber() + ".png";
                        File file = new File(dir, fileName);
                        if (file.exists()) {
                            file.delete();
                            file = new File(dir, fileName);
                        }
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();
                        fileList.add(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    view.updateProgress((i + 1) * 100 / itemList.size());
                }
                view.hideProgress();

                view.showProgress("列印中");
                Printer printer = new Printer();
                NetPrinter[] netPrinters = printer.getNetPrinters("PT-P900W");
                if (netPrinters == null || netPrinters.length == 0) {
                    view.hideProgress();
                    view.showToast("列印失敗,找不到機器");
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
                    printInfo.isCutAtEnd = false;
                    printInfo.isHalfCut = true;
                    printer.setPrinterInfo(printInfo);
                    printer.startCommunication();
                    for (int i = 0; i < fileList.size(); i++) {
                        File file = fileList.get(i);
                        PrinterStatus status = printer.printFile(file.getAbsolutePath());
                        if (status.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                            view.hideProgress();
                            view.showToast("列印失敗,找不到機器");
                            printer.endCommunication();
                            return;
                        }
                        view.updateProgress((i + 1) * 100 / fileList.size());

                    }
                    printer.endCommunication();
                    view.hideProgress();
                    view.showToast("列印成功");
                }
            }
        });
        trd.start();
    }


}
