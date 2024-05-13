package com.mechoy.jasperreports;

import net.sf.jasperreports.engine.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class JasperReportsTest {
    public static void main(String[] args) throws Exception {
        Map<String, Object> hashMap = new HashMap<>();
        File file = new File("1.jrxml");
        JasperReport parentReport = JasperCompileManager.compileReport(new FileInputStream(file));
        JasperPrint jasperPrint = JasperFillManager.fillReport(parentReport, hashMap, new JREmptyDataSource());//解析

//        JasperCompileManager.compileReportToFile("1.jrxml","1.jasper");
//        JasperFillManager.fillReport("1.jasper",hashMap,new JRResultSetDataSource(getResultSet()));
    }


    public static ResultSet getResultSet() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/test?useSSL=true";
        String user = "root";
        String password = "root";

        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        String sql = "Select * from users";
        // 执行给定的SQL语句，该语句返回单个 ResultSet对象
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }
}
