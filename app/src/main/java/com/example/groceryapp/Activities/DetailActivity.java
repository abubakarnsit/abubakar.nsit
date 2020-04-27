package com.example.groceryapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.groceryapp.R;

public class DetailActivity extends AppCompatActivity {
    private TextView itemname;
    private TextView quantity;
    private TextView dateAdded;
    private int groceryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        itemname=(TextView) findViewById(R.id.itemNamedet);
        quantity=(TextView) findViewById(R.id.quantitydet);
        dateAdded=(TextView) findViewById(R.id.dateaddeddet);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            itemname.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            dateAdded.setText(bundle.getString("date"));
            groceryId=bundle.getInt("id");
        }

    }
}
