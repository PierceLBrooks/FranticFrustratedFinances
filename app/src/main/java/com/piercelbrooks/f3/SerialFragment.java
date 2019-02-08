
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.piercelbrooks.common.BasicListFragment;
import com.piercelbrooks.common.Utilities;

import java.util.List;

public abstract class SerialFragment <T extends Serial> extends BasicListFragment<MayoralFamily>
{
    private static final String TAG = "F3-SerialFrag";
    private static final String TAB = " * ";

    private Button serialExit;
    private TextView serialTitle;

    protected abstract T getSerial();
    protected abstract String getTitle();
    protected abstract void onExit();

    public SerialFragment()
    {
        super();
    }

    @Override
    protected boolean itemLabelAffix()
    {
        return false;
    }

    @Override
    protected void itemClick(View view, int position)
    {

    }

    @Override
    protected int getItemID()
    {
        return R.id.serial_item_label;
    }

    @Override
    protected int getItemLayout()
    {
        return R.layout.serial_item;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.serial_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        serialExit = view.findViewById(R.id.serial_exit);
        serialExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onExit();
            }
        });

        serialTitle = view.findViewById(R.id.serial_title);
        serialTitle.setText(getTitle());

        load();
    }

    @Override
    public void onBirth()
    {

    }

    @Override
    public void onDeath()
    {

    }

    public void unload()
    {
        removeItems();
    }

    public void load()
    {
        load(getSerial());
    }

    public void load(T serial)
    {
        unload();
        if (serial != null)
        {
            List<String> serialization = serial.getSerialization();
            if (serialization != null)
            {
                int levelPrevious = -1;
                int level = -1;
                boolean value = false;
                String member = "";
                String temp = "";
                for (int i = 0; i != serialization.size(); ++i)
                {
                    temp += serialization.get(i)+"\n";
                }
                Log.d(TAG, temp);
                for (int i = 0; i != serialization.size(); ++i)
                {
                    temp = serialization.get(i);
                    if (temp == null)
                    {
                        continue;
                    }
                    levelPrevious = level;
                    level = Utilities.count(temp, '\t');
                    if (!value)
                    {
                        member += temp;
                        if (levelPrevious == level)
                        {
                            value = !value;
                        }
                        else
                        {
                            member = TAB+member.replaceAll("\t", TAB);
                            addItem(member);
                            member = "";
                        }
                    }
                    else
                    {
                        value = !value;
                        if (member.isEmpty())
                        {
                            continue;
                        }
                        temp = temp.trim();
                        if ((!member.trim().isEmpty()) && (!temp.isEmpty()))
                        {
                            member += " = ";
                        }
                        member += temp;
                        member = TAB+member.replaceAll("\t", TAB);
                        addItem(member);
                        member = "";
                    }
                }
            }
        }
    }
}
