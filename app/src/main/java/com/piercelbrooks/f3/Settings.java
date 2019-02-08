
// Author: Piece Brooks

package com.piercelbrooks.f3;

import android.content.SharedPreferences;
import android.util.Log;

import com.piercelbrooks.common.Constants;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.common.Preferences;
import com.piercelbrooks.common.Utilities;
import com.piercelbrooks.roe.MailProperties;
import com.piercelbrooks.roe.Mailbox;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Settings extends Preferences implements MailProperties
{
    public enum Setting
    {
        INCOMING_MAIL_PROPERTIES,
        OUTGOING_MAIL_PROPERTIES
    }

    private static final String TAG = "F3-Settings";
    private static final String INCOMING_MAIL_PROPERTIES_KEY = "INCOMING_MAIL_PROPERTIES";
    private static final String OUTGOING_MAIL_PROPERTIES_KEY = "OUTGOING_MAIL_PROPERTIES";
    private static final String MAIL_PROPERTIES_DELIMITER = "\n";

    public Settings()
    {
        super(((MainApplication)(Governor.getInstance().getCitizen(Family.APPLICATION))).getPreferences());
    }

    public static Properties getDefaultIncomingMailProperties()
    {
        Properties properties = new Properties();
        properties.setProperty(Mailbox.MAIL_PROTOCOL_PROPERTY, "imaps");
        properties.setProperty(Mailbox.MAIL_HOST_PROPERTY, "imap.gmail.com");
        properties.setProperty(Mailbox.MAIL_DEBUG_PROPERTY, String.valueOf(Constants.DEBUG));
        return properties;
    }

    public static Properties getDefaultOutgoingMailProperties()
    {
        Properties properties = new Properties();
        properties.setProperty(Mailbox.MAIL_TRANSPORT_PROPERTY, "smtp");
        properties.setProperty(Mailbox.MAIL_HOST_PROPERTY, "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        //properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", String.valueOf(Constants.SMTP_SSL_PORT));
        properties.put("mail.smtp.socketFactory.port", String.valueOf(Constants.SMTP_SSL_PORT));
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        //properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty(Mailbox.MAIL_DEBUG_PROPERTY, String.valueOf(Constants.DEBUG));
        return properties;
    }

    @Override
    public Properties getIncomingMailProperties()
    {
        Properties properties;
        String[] property;
        String key;
        String value;
        Set<String> serialization;
        Iterator<String> iterator;
        SharedPreferences source = getSource();
        if (!source.contains(INCOMING_MAIL_PROPERTIES_KEY))
        {
            properties = getDefaultIncomingMailProperties();
            if (!setIncomingMailProperties(properties))
            {
                Log.w(TAG, "Unable to set default incoming mail properties!");
            }
            return properties;
        }
        serialization = source.getStringSet(INCOMING_MAIL_PROPERTIES_KEY, new HashSet<String>());
        if (serialization == null)
        {
            return null;
        }
        properties = new Properties();
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
                properties.setProperty(key, value);
            }
        }
        return properties;
    }

    @Override
    public Properties getOutgoingMailProperties()
    {
        Properties properties;
        String[] property;
        String key;
        String value;
        Set<String> serialization;
        Iterator<String> iterator;
        SharedPreferences source = getSource();
        if (!source.contains(OUTGOING_MAIL_PROPERTIES_KEY))
        {
            properties = getDefaultOutgoingMailProperties();
            if (!setOutgoingMailProperties(properties))
            {
                Log.w(TAG, "Unable to set default outgoing mail properties!");
            }
            return properties;
        }
        serialization = source.getStringSet(OUTGOING_MAIL_PROPERTIES_KEY, new HashSet<String>());
        if (serialization == null)
        {
            return null;
        }
        properties = new Properties();
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
                properties.setProperty(key, value);
            }
        }
        return properties;
    }

    public boolean setIncomingMailProperties(Properties properties)
    {
        String key;
        String value;
        HashSet<String> serialization;
        Map.Entry<Object, Object> entry;
        Iterator<Map.Entry<Object, Object>> iterator;
        if (properties == null)
        {
            return getSource().edit().remove(INCOMING_MAIL_PROPERTIES_KEY).commit();
        }
        serialization = new HashSet<>();
        iterator = Utilities.getEntries(properties).iterator();
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
        return getSource().edit().putStringSet(INCOMING_MAIL_PROPERTIES_KEY, serialization).commit();
    }

    public boolean setOutgoingMailProperties(Properties properties)
    {
        String key;
        String value;
        HashSet<String> serialization;
        Map.Entry<Object, Object> entry;
        Iterator<Map.Entry<Object, Object>> iterator;
        if (properties == null)
        {
            return getSource().edit().remove(OUTGOING_MAIL_PROPERTIES_KEY).commit();
        }
        serialization = new HashSet<>();
        iterator = Utilities.getEntries(properties).iterator();
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
        return getSource().edit().putStringSet(OUTGOING_MAIL_PROPERTIES_KEY, serialization).commit();
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
            case INCOMING_MAIL_PROPERTIES:
                serialization = Utilities.toString(Utilities.getEntries(getIncomingMailProperties()));
                break;
            case OUTGOING_MAIL_PROPERTIES:
                serialization = Utilities.toString(Utilities.getEntries(getOutgoingMailProperties()));
                break;
        }
        if (serialization != null)
        {
            serialization.add(0, "{");
            serialization.add(0, setting.name());
            serialization.add(" ");
            serialization.add("}");
        }
        return serialization;
    }
}
