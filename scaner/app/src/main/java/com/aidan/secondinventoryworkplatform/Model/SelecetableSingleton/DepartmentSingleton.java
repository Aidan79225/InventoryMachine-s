package com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Department;

/**
 * Created by Aidan on 2016/11/20.
 */

public class DepartmentSingleton extends SelectableItemSingleton<Department>{
    private static DepartmentSingleton departmentSingleton;
    public static DepartmentSingleton getInstance(){
        if(departmentSingleton == null){
            synchronized (AgentSingleton.class){
                if(departmentSingleton == null){
                    departmentSingleton = new DepartmentSingleton();
                }
            }
        }
        return departmentSingleton;
    }

    @Override
    public String getTableName() {
        return Constants.PREFERENCE_DEPARTMENT;
    }

    @Override
    public Class<? extends Department> getDataConstructor() {
        return Department.class;
    }
}
