package com.mechoy.hivemind;

import org.apache.hivemind.Registry;
import org.apache.hivemind.impl.DefaultClassResolver;
import org.apache.hivemind.impl.RegistryBuilder;
import org.apache.hivemind.impl.XmlModuleDescriptorProvider;
import org.apache.hivemind.util.FileResource;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class HivemindTest {
    public static void main(String[] args) throws Exception {
        RegistryBuilder registryBuilder = new RegistryBuilder();
        DefaultClassResolver defaultclassresolver = new DefaultClassResolver();
        registryBuilder.addModuleDescriptorProvider(new XmlModuleDescriptorProvider(defaultclassresolver));

        File[] afile = (new File("src/main/resources/hivemind")).listFiles();
        ArrayList arraylist = new ArrayList();
        for(int j = 0; j < afile.length; ++j) {
            if (afile[j].getName().endsWith(".xml")) {
                arraylist.add(new FileResource(afile[j].getCanonicalPath()));
                System.out.println(afile[j].getName());
            }
        }

        XmlModuleDescriptorProvider xmlmoduledescriptorprovider = new XmlModuleDescriptorProvider(defaultclassresolver, arraylist);
        registryBuilder.addModuleDescriptorProvider(xmlmoduledescriptorprovider);
        Registry registry = registryBuilder.constructRegistry(Locale.getDefault());
//        Object service = registry.getService(DataSource.class);
    }
}
