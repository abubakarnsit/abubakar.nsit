package com.example.groceryapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.groceryapp.Data.DatabaseHandler;
import com.example.groceryapp.Model.Grocery;
import com.example.groceryapp.UI.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.groceryapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;
    private DatabaseHandler db;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText quantity;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db=new DatabaseHandler(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                createPopupDialog();
            }
        });


     recyclerView=(RecyclerView) findViewById(R.id.recyclerViewID);
     recyclerView.setHasFixedSize(true);
     recyclerView.setLayoutManager(new LinearLayoutManager(this));

     groceryList=new ArrayList<>();
     listItems=new ArrayList<>();

     groceryList=db.getAllGroceries();
     for(Grocery c : groceryList){
         Grocery grocery=new Grocery();
         grocery.setName(c.getName());
         grocery.setQuantity("Qty: "+c.getQuantity());
         grocery.setId(c.getId());
         grocery.setDateItemAdded("Added on: " + c.getDateItemAdded());

         listItems.add(grocery);

     }
     recyclerViewAdapter =new RecyclerViewAdapter(this,listItems);
     recyclerView.setAdapter(recyclerViewAdapter);
     recyclerViewAdapter.notifyDataSetChanged();


    }

    private void createPopupDialog() {
        dialogBuilder=new AlertDialog.Builder(this);
        View view =getLayoutInflater().inflate(R.layout.popup,null);
        groceryItem=(EditText) view.findViewById(R.id.groceryItem);
        quantity=(EditText) view.findViewById(R.id.groceryQty);
        saveButton=(Button) view.findViewById(R.id.saveButton);
        dialogBuilder.setView(view);
        dialog=dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!groceryItem.getText().toString().isEmpty()&&!quantity.getText().toString().isEmpty())
                    saveGroceryToDB(v);
            }
        });
    }

    private void saveGroceryToDB(View v) {
        Grocery grocery=new Grocery();

        String newGrocery=groceryItem.getText().toString();
        String newgroceryQuantity=quantity.getText().toString();

        grocery.setName(newGrocery);
        grocery.setQuantity(newgroceryQuantity);


        db.addGrocery(grocery);
        Snackbar.make(v,"Item saved",Snackbar.LENGTH_LONG).show();
        //Log.d("item added id:",String.valueOf(db.getGroceriesCount()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }
        },0);
    }

}
