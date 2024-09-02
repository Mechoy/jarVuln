package com.mechoy.poiOoxml;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.extractor.XSSFExportToXml;
import org.apache.poi.xssf.usermodel.XSSFMap;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class POITest {
    public static void main(String[] args) throws Exception {
        testXSSFWorkbook();
//        testWorkbookFactory();
//        testXSSFExportToXml();
    }

    /**
     * 在3.10-FINAL及之前版本中存在XXE
     * @throws Exception
     */
    public static void testXSSFWorkbook() throws Exception {
        // 在创建XSSFWorkbook对象的时候就会实现XXE
        XSSFWorkbook xssfSheets = new XSSFWorkbook("src/main/resources/poiOoxml/poi33.xlsx");
//        XSSFSheet sheet = xssfSheets.getSheetAt(0);
    }

    /**
     * 在3.10-FINAL及之前版本中存在XXE
     * 与上面写法不同，但原理都一样
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void testWorkbookFactory() throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(new FileInputStream("src/main/resources/poiOoxml/poi3.xlsx"));
        Sheet sheet = wb.getSheetAt(0);
    }

    /**
     * 在4.10.0及之前版本中存在XXE，漏洞点位于excel文件的/xl/xmlMaps.xml文件中
     * @throws Exception
     */
    public static void testXSSFExportToXml() throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("src/main/resources/poiOoxml/poi4.xlsx"));
        for (XSSFMap map : wb.getCustomXMLMappings()) {
            XSSFExportToXml exporter = new XSSFExportToXml(map); // 使用 XSSFExportToXml 将 xlsx 转成 xml
            //第一个参数是输出流无所谓，需要保证第二个参数要为 true
            exporter.exportToXML(System.out, true);
        }
    }
}
