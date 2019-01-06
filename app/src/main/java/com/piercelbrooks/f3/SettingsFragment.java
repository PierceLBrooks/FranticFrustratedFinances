
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends SerialFragment<SettingsFragment> implements Serial<Settings.Setting>
{
    private static final String TAG = "F3-SettingsFrag";
    private static Settings settings = null;

    public SettingsFragment()
    {
        super();
    }

    public static Settings getSettings()
    {
        if (settings == null)
        {
            settings = new Settings();
        }
        return settings;
    }

    @Override
    protected SettingsFragment getSerial()
    {
        return this;
    }

    @Override
    protected String getTitle()
    {
        return "MAIL";
    }

    @Override
    protected void onExit()
    {
        ((MainActivity)getMunicipality()).showLaunch();
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.SETTINGS;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return SettingsFragment.class;
    }

    @Override
    public Class<?> getSerialClass() {
        return null;
    }

    @Override
    public Serial<Settings.Setting> getDeserialization(List<String> source)
    {
        Utilities.throwUnimplementedException("SettingsFragment::getDeserialization");
        return null;
    }

    @Override
    public String getIdentifier() {
        return "SettingsFragment";
    }

    @Override
    public List<String> getSerialization() {
        ArrayList<String> serialization = new ArrayList<>();
        Settings.Setting[] members = Settings.Setting.values();
        Settings.Setting member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(Settings.Setting member) {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(Settings.Setting member) {
        return getSettings().getSettingSerialization(member);
    }
}
