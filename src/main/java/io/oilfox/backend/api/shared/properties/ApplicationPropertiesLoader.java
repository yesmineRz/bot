package io.oilfox.backend.api.shared.properties;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Singleton
public class ApplicationPropertiesLoader {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationPropertiesLoader.class);

    private HashMap<String, String> properties = new HashMap<>();

    public ApplicationPropertiesLoader() throws IOException {
        Load();
    }

    public void Load() throws IOException {
        Load("application.properties");
        Load("data.json");
    }

    public void Load(String fn) throws IOException {
        List<String> propertySources = new ArrayList<String>() {{
            add(fn);
            add("db.properties");
            add("email.properties");
            add("common.properties");
        }};

        for (String fileName : propertySources) {
            InputStream inputStream = null;

            try {
                CustomProperties prop = new CustomProperties();

                inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

                if (inputStream != null) {
                    prop.load(inputStream);
                } else {

                    continue;
                    //throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
                }

                for (String name : prop.stringPropertyNames()) {

                    properties.put(name, prop.getProperty(name));
                }

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
    }

    /**
     * Externally sets a property (useful for unit tests)
     *
     * @param name
     * @param value
     */
    public void setProperty(String name, String value) {

        properties.put(name, value);
    }

    public String getProperty(String name, String defaultValue) {

        if (!properties.containsKey(name)) {

            logger.warn(String.format("Cannot find configuration property '%s', returning default value of '%s'", name, defaultValue));
            return defaultValue;
        }

        return properties.get(name);
    }

    public HashMap<String, String> getHashMap(String prefix) {

        HashMap<String, String> result = new HashMap<>();

        for (String name : properties.keySet()) {

            if (name.startsWith(prefix)) {
                result.put(name, properties.getOrDefault(name, null));
            }
        }

        return result;
    }

    public Properties getProperties(String prefix) {

        HashMap<String, String> map = getHashMap(prefix);

        Properties result = new Properties();

        for (String name : properties.keySet()) {

            if (name.startsWith(prefix)) {
                result.setProperty(name, properties.get(name));
            }
        }

        return result;
    }
}