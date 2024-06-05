package com.mechoy.jasperreports;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) throws Exception {
        Map<String, Object> hashMap = new HashMap<>();
//        hashMap.put("REPORT_DATA_SOURCE",new JREmptyDataSource());
//        ArrayList<String> list = new ArrayList<>();
//        list.add("username");
//        hashMap.put("SORT_FIELDS",list);
        Connection connection = getConnection();
        JasperReport report = JasperCompileManager.compileReport("src/main/resources/jasperreports/toPdf.jrxml");

        JasperRunManager.runReportToPdf(report,hashMap,connection);

        // 关闭数据库连接
        connection.close();

    }

    public static Connection getConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/test?useSSL=true";
        String user = "root";
        String password = "root";

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}
