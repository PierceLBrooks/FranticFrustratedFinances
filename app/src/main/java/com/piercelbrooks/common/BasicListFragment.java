
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import java.util.List;

public abstract class BasicListFragment <T extends Enum<T>> extends ListFragment implements Mayor<T>, AdapterView.OnItemClickListener
{
    public class Item
    {
        private BasicListFragment owner;
        private String label;
        private View view;
        private int position;

        public Item(@NonNull BasicListFragment owner, @NonNull View view, @NonNull String label, int position)
        {
            configure(owner, view, label, position);
        }

        public void configure(@NonNull BasicListFragment owner, @NonNull View view, @NonNull String label, int position)
        {
            this.owner = owner;
            this.label = label;
            this.view = view;
            this.position = position;
        }

        public @NonNull BasicListFragment getOwner()
        {
            return owner;
        }

        public @NonNull View getView()
        {
            return view;
        }

        public @NonNull String getLabel()
        {
            return label;
        }

        public void setLabel(String label)
        {
            owner.setItemLabel(position, label);
        }

        public void setPosition(int position)
        {
            this.position = position;
        }

        public int getPosition()
        {
            return position;
        }
    }

    private static final String TAG = "PLB-BaseListFrag";
    private static final String ITEM_LABEL_PREFIX = "\" ";
    private static final String ITEM_LABEL_SUFFIX = " \"";

    private ListView list;
    private ArrayList<Item> items;
    private ArrayList<String> itemLabels;
    private ArrayList<String> itemLabelOriginals;
    private ArrayAdapter<String> adapter;
    private View selection;
    private Drawable selectionBackground;
    private int selectionIndex;

    protected abstract boolean itemLabelAffix();
    protected abstract void itemClick(View view, int position);
    protected abstract @IdRes int getItemID();
    protected abstract @LayoutRes int getItemLayout();

    public BasicListFragment() {
        super();
        list = null;
        items = null;
        itemLabels = null;
        itemLabelOriginals = null;
        adapter = null;
        selection = null;
        selectionBackground = null;
        selectionIndex = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        list = getListView();
        items = new ArrayList<>();
        itemLabels = new ArrayList<>();
        itemLabelOriginals = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), getItemLayout(), getItemID(), itemLabels);
        setListAdapter(adapter);
        list.setOnItemClickListener(this);
        createView(view);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        selection = view;
        selectionIndex = position;
        if (selection != null)
        {
            selectionBackground = selection.getBackground();
        }
        itemClick(view, position);
    }

    @Override
    public Family getFamily()
    {
        return Family.MAYOR;
    }

    @Override
    public Municipality<T> getMunicipality()
    {
        Activity activity = getActivity();
        if (activity instanceof  Municipality)
        {
            return (Municipality<T>)getActivity();
        }
        return null;
    }

    @Override
    public void birth()
    {
        Governor.getInstance().register(this);
        onBirth();
    }

    @Override
    public void death()
    {
        onDeath();
        Governor.getInstance().unregister(this);
    }

    public int getItemCount()
    {
        return itemLabels.size();
    }

    public View getItemView(int position)
    {
        if ((position < 0) || (position >= itemLabels.size()))
        {
            return null;
        }
        if (position < items.size())
        {
            Item item = items.get(position);
            if (item != null)
            {
                if (item.getPosition() == position)
                {
                    return item.getView();
                }
            }
        }
        int first = list.getFirstVisiblePosition();
        int last = first+(list.getChildCount()-1);
        if ((position < first) || (position > last))
        {
            return adapter.getView(position, null, list);
        }
        return list.getChildAt((position+list.getHeaderViewsCount())-first);
    }

    public String getItemLabel(int position)
    {
        if ((position < 0) || (position >= itemLabels.size()))
        {
            return null;
        }
        return itemLabelOriginals.get(position);
    }

    public boolean setItemLabel(int position, String label)
    {
        if (label == null)
        {
            return false;
        }
        Item item = getItem(position);
        if (item == null)
        {
            return false;
        }
        item.configure(this, item.getView(), label, position);
        itemLabelOriginals.set(position, label);
        if (itemLabelAffix())
        {
            itemLabels.set(position, ITEM_LABEL_PREFIX+label+ITEM_LABEL_SUFFIX);
        }
        else
        {
            itemLabels.set(position, label);
        }
        adapter.notifyDataSetChanged();
        return true;
    }

    public Item getItem(int position)
    {
        if ((position < 0) || (position >= itemLabels.size()))
        {
            return null;
        }
        while (position >= items.size())
        {
            items.add(null);
        }
        Item item = new Item(this, getItemView(position), getItemLabel(position), position);
        items.set(position, item);
        return item;
    }

    public Item addItem(String label)
    {
        if (label == null)
        {
            return null;
        }
        itemLabelOriginals.add(label);
        if (itemLabelAffix())
        {
            itemLabels.add(ITEM_LABEL_PREFIX+label+ITEM_LABEL_SUFFIX);
        }
        else
        {
            itemLabels.add(label);
        }
        adapter.notifyDataSetChanged();
        return getItem(getItemCount()-1);
    }

    public void addItems(List<String> labels)
    {
        if (labels == null)
        {
            return;
        }
        for (int i = 0; i != labels.size(); ++i)
        {
            addItem(labels.get(i));
        }
    }

    public boolean removeItem(int position)
    {
        if ((position < 0) || (position >= itemLabels.size()))
        {
            return false;
        }
        if (selectionIndex == position)
        {
            if (selection != null)
            {
                selection.setBackground(selectionBackground);
                selection = null;
            }
            selectionIndex = -1;
        }
        items.remove(position);
        itemLabels.remove(position);
        itemLabelOriginals.remove(position);
        adapter.notifyDataSetChanged();
        for (int i = position; i != itemLabels.size(); ++i)
        {
            Item item = items.get(i);
            item.setPosition(item.getPosition()-1);
        }
        return true;
    }

    public void removeItems()
    {
        while (getItemCount() != 0)
        {
            removeItem(0);
        }
    }
}
