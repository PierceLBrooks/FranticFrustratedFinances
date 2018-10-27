
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public abstract class BasicListFragment extends ListFragment implements Citizen, AdapterView.OnItemClickListener
{
    private static final String TAG = "PLB-BasicListFrag";

    private ListView list;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    protected abstract void itemClick(View view, int position);
    protected abstract @IdRes int getItem();
    protected abstract @LayoutRes int getInflationResource();
    protected abstract void createView(@NonNull View view);
    protected abstract void onBirth();
    protected abstract void onDeath();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(getInflationResource(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        list = getListView();
        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), getInflationResource(), getItem(), items);
        setListAdapter(adapter);
        list.setOnItemClickListener(this);
        createView(view);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        itemClick(view, position);
    }

    @Override
    public Family getFamily()
    {
        return Family.FRAGMENT;
    }

    @Override
    public void birth()
    {
        Mayor.getInstance().register(this);
        onBirth();
    }

    @Override
    public void death()
    {
        onDeath();
        Mayor.getInstance().unregister(this);
    }

    public View getView(int position)
    {
        int first = list.getFirstVisiblePosition();
        int last = first+(list.getChildCount()-1);
        if ((position < first) || (position > last))
        {
            return list.getAdapter().getView(position, null, list);
        }
        return list.getChildAt((position+list.getHeaderViewsCount())-first);
    }

    public int getItemCount()
    {
        return items.size();
    }

    public String getItem(int position)
    {
        return adapter.getItem(position);
    }

    public void addItem(String item)
    {
        items.add(item);
        adapter.notifyDataSetChanged();
    }

    public void removeItem(int position)
    {
        items.remove(position);
        adapter.notifyDataSetChanged();
    }
}
