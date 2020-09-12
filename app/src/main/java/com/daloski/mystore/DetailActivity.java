package com.daloski.mystore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daloski.mystore.models.Item;
import com.daloski.mystore.vmodels.DetailVModel;

public class DetailActivity extends AppCompatActivity {

    private DetailVModel viewModel;
    private Item item;
    private TextView itemDesc;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewModel = new ViewModelProvider(this).get(DetailVModel.class);

        Bundle data = getIntent().getExtras();
        item = (Item) data.get("item");

        setupViews();

        viewModel.getDetails(item.getUrl()).observe(this, s -> {
            Log.d("DetailActivity", "onCreate: desc: " + s);
            itemDesc.setText(s);
            toggleBar(false);
        });
    }

    private void setupViews(){
        progressBar = findViewById(R.id.progress_bar);
        toggleBar(true);

        itemDesc = findViewById(R.id.item_desc);
        TextView itemName = findViewById(R.id.item_name);
        ImageView itemImage = findViewById(R.id.item_cover);

        Glide.with(this).load(item.getImage()).into(itemImage);
        itemName.setText(item.getName());

    }

    private void toggleBar(boolean visible){
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}