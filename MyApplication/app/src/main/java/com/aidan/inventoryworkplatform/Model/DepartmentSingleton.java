package com.aidan.inventoryworkplatform.Model;

import com.aidan.inventoryworkplatform.Entity.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class DepartmentSingleton {
    static DepartmentSingleton departmentSingleton;
    private List<Department> departmentList = new ArrayList<>();
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

    public List<Department> getDepartmentList() {
        return departmentList;
    }
}
