package com.mechoy.poiOoxml;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;

public class POI_WorkbookFactory {
    public static void main(String[] args) throws Exception {
        Workbook wb1 = WorkbookFactory.create(new FileInputStream("temple.xlsx"));
        Sheet sheet = wb1.getSheetAt(0);
        System.out.println(sheet.getLastRowNum());
    }
}
