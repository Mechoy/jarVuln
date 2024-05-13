package com.mechoy.poiOoxml;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POI_XSSFWorkbook {
    public static void main(String[] args) throws Exception {
        XSSFWorkbook xssfSheets = new XSSFWorkbook("temple.xlsx");
//        XSSFSheet sheet = xssfSheets.getSheetAt(0);
    }
}
