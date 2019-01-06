
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.piercelbrooks.common.BasicFragment;

public class LaunchFragment extends BasicFragment<MayoralFamily>
{
    private static final String TAG = "F3-LaunchFrag";

    private Button launchLedgers;
    private Button launchSettings;
    private Button launchSettingsClear;
    private Button launchExit;

    public LaunchFragment()
    {
        super();
        launchLedgers = null;
        launchSettings = null;
        launchSettingsClear = null;
        launchExit = null;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.launch_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        launchLedgers = view.findViewById(R.id.launch_ledgers);
        launchSettings = view.findViewById(R.id.launch_settings);
        launchSettingsClear = view.findViewById(R.id.launch_settings_clear);
        launchExit = view.findViewById(R.id.launch_exit);

        launchLedgers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showLedgers();
            }
        });

        launchSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showSettings();
            }
        });

        launchSettingsClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SettingsFragment.getSettings().reset();
                getMunicipality().getOwner().makeToast("Settings reset!");
            }
        });

        launchExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).finish();
            }
        });
    }

    @Override
    public void onBirth()
    {

    }

    @Override
    public void onDeath()
    {

    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.LAUNCH;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return LaunchFragment.class;
    }
}
