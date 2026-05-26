package com.mechoy;

import org.apache.poi.ss.formula.functions.T;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

        ArrayList<String[]> strings = new ArrayList<>();
        strings.add(new String[]{"win","C:\\Users\\Administor\\AppData\\Roaming\\Microsoft\\Windows\\PowerShell\\PSReadLine\\ConsoleHost_history.txt"});
//        strings.add(new String[]{"linux","/etc/passwd"});

        System.out.println(mapper.writeValueAsString(strings));


    }
}