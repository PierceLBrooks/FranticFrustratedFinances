
// Author: Piece Brooks

package com.piercelbrooks.f3;

import android.content.SharedPreferences;
import android.util.Log;

import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.common.Preferences;
import com.piercelbrooks.common.Utilities;
import com.piercelbrooks.roe.MailProperties;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Settings extends Preferences implements MailProperties
{
    public enum Setting
    {
        MAIL_PROPERTIES
    }

    private static final String TAG = "F3-Settings";
    private static final String MAIL_PROPERTIES_KEY = "MAIL_PROPERTIES";
    private static final String MAIL_PROPERTIES_DELIMITER = "\n";

    public Settings()
    {
        super(((MainApplication)(Governor.getInstance().getCitizen(Family.APPLICATION))).getPreferences());
    }

    public static Properties getDefaultMailProperties()
    {
        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        return properties;
    }

    @Override
    public Properties getMailProperties()
    {
        Properties properties;
        String[] property;
        String key;
        String value;
        HashSet<String> serialization;
        Iterator<String> iterator;
        SharedPreferences source = getSource();
        if (!source.contains(MAIL_PROPERTIES_KEY))
        {
            properties = getDefaultMailProperties();
            setMailProperties(properties);
            return properties;
        }
        properties = new Properties();
        serialization = new HashSet<>();
        source.getStringSet(MAIL_PROPERTIES_KEY, serialization);
        iterator = serialization.iterator();
        while (iterator.hasNext())
        {
            property = iterator.next().split(MAIL_PROPERTIES_DELIMITER);
            if (property.length == 2)
            {
                key = property[0];
                value = property[1];
                if (properties.containsKey(key))
                {
                    Log.w(TAG, "Duplicate properties with key \""+key+"\": \""+value+"\" & \""+properties.get(key)+"\"");
                }
                properties.put(key, value);
            }
        }
        return properties;
    }

    public boolean setMailProperties(Properties properties)
    {
        String key;
        String value;
        HashSet<String> serialization;
        Map.Entry<Object, Object> entry;
        Iterator<Map.Entry<Object, Object>> iterator;
        if (properties == null)
        {
            return false;
        }
        serialization = new HashSet<>();
        iterator = properties.entrySet().iterator();
        while (iterator.hasNext())
        {
            entry = iterator.next();
            key = entry.getKey().toString();
            value = entry.getValue().toString();
            if ((key.contains(MAIL_PROPERTIES_DELIMITER)) || (value.contains(MAIL_PROPERTIES_DELIMITER)))
            {
                Log.w(TAG, "Property key/value pair (\""+key+"\" & \""+value+"\") contains references to delimiter (\""+MAIL_PROPERTIES_DELIMITER+"\")");
            }
            serialization.add(key+MAIL_PROPERTIES_DELIMITER+value);
        }
        return getSource().edit().putStringSet(MAIL_PROPERTIES_KEY, serialization).commit();
    }

    public List<String> getSettingSerialization(Setting setting)
    {
        if (setting == null)
        {
            return null;
        }
        List<String> serialization = null;
        switch (setting)
        {
            case MAIL_PROPERTIES:
                serialization = Utilities.toString(getMailProperties().entrySet());
                break;
        }
        return serialization;
    }
}