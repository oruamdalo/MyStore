package com.daloski.mystore.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daloski.mystore.R;
import com.daloski.mystore.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ListViewHolder>{
    private List<Item> items;
    private View.OnClickListener listener;

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView name, author;
        public ImageView image;

        public ListViewHolder(View v, View.OnClickListener _listener) {
            super(v);

            image = v.findViewById(R.id.item_cover);
            name = v.findViewById(R.id.item_name);
            author = v.findViewById(R.id.item_author);

            v.setOnClickListener(_listener);
        }
    }

    public void setItems(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    public List<Item> getItems() {
        return items;
    }

    public ItemListAdapter(List<Item> items, View.OnClickListener listener) {
        this.items = new ArrayList<>(items);
        this.listener = listener;
    }

    @Override
    public ItemListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);

        return new ListViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Item item = items.get(position);


        holder.name.setText(item.getName());

        holder.author.setText(item.getUrl());

        Log.d("adapter", "onBindViewHolder: image: " + item.getImage());
        Glide.with(holder.itemView.getContext()).load(item.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
