package io.oilfox.backend.api.shared.properties;

import java.util.Properties;

public class CustomProperties extends Properties {

    public CustomProperties() {
    }

    public CustomProperties(Properties defaults) {
        super(defaults);
    }

    public synchronized Object put(Object key, Object value) {

        String strValue = value.toString();

        if (strValue.matches("^\\$\\{.*\\}$")) {

            // ${DATABASE} --> DATABASE
            String envName = strValue.substring(2, strValue.length()-1);

            strValue = System.getenv(envName);
        }

        if (strValue != null && !strValue.equals("")) {

            return super.put(key, strValue);
        }

        return value;
    }
}
