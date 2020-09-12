package com.daloski.mystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.TimedText;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.daloski.mystore.adapters.ItemListAdapter;
import com.daloski.mystore.api.Api;
import com.daloski.mystore.models.Item;
import com.daloski.mystore.vmodels.DetailVModel;
import com.daloski.mystore.vmodels.MainVModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView itemList;
    private ItemListAdapter adapter;
    private MainVModel viewModel;
    private SearchView searchView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        viewModel = new ViewModelProvider(this).get(MainVModel.class);

        viewModel.getItems("%20").observe(this, items -> {
            toggleBar(false);
            updateList(items);
        });
    }

    private void setupViews(){
        // Progress Bar
        progressBar = findViewById(R.id.progress_bar);
        toggleBar(true);

        // SearchView
        searchView = findViewById(R.id.search_view);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateList(null);

                toggleBar(true);
                viewModel.getItems(query).observe(MainActivity.this, items -> {
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                    toggleBar(false);
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Recycler View
        adapter = new ItemListAdapter(new ArrayList<>(), this);
        itemList = findViewById(R.id.item_list);
        itemList.setHasFixedSize(true);
        itemList.setLayoutManager(new LinearLayoutManager(this));
        itemList.setAdapter(adapter);
    }

    private void updateList(List<Item> items){
        adapter.setItems(items != null ? items : new ArrayList<>());
        adapter.notifyDataSetChanged();
    }

    private void toggleBar(boolean visible){
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        int position = itemList.getChildAdapterPosition(view);
        Item item = adapter.getItems().get(position);
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("item", item);
        startActivity(i);
    }
}