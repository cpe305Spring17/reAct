package edu.calpoly.react;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.calpoly.react.model.Action;
import edu.calpoly.react.model.Category;
import edu.calpoly.react.model.database.DBConnection;

/**
 * Created by Nishanth on 5/17/17.
 */

public class CreateNewActivity extends AppCompatActivity {

    ArrayList<String> categories;
    ArrayAdapter<String> adapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        spinner = (Spinner) findViewById(R.id.category_spinner);
        generateCategories();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = parent.getItemAtPosition(position).toString();
                if ("Create New...".equals(selectedText)) {
                    openCategoryDialog(view);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // not implemented
            }
        });
    }

    private void generateCategories() {
        categories = new ArrayList<>();
        categories.add("Select Category");
        List<Category> dbCategories  = DBConnection.getInstance().getAllCategories();
        Collections.sort(dbCategories);
        for (Category category: dbCategories) {
            categories.add(category.getName());
        }
        categories.add(getString(R.string.create_new));
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_create_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cancel_new_activity) {
            Intent intent = new Intent(this, ActivitiesHome.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void openCategoryDialog(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Create Category");
        // Set up the input
        final EditText input = new EditText(this);
        input.setHint("Category Name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String categoryName = input.getText().toString();
                if (categoryName != null) {
                    Category newCategory = new Category(categoryName);
                    if (newCategory.isValid()) {
                        DBConnection.getInstance().addCategory(newCategory);
                        generateCategories();
                        spinner.setSelection(adapter.getPosition(categoryName));
                    }
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Spinner theSpinner = (Spinner) findViewById(R.id.category_spinner);
                theSpinner.setSelection(0);
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void addNewActivity(View view) {
        EditText activityName = (EditText) findViewById(R.id.activity_name);
        Category category = null;
        String categoryName = spinner.getSelectedItem().toString();
        if (!"Select Category".equals(categoryName) && !"Create New...".equals(categoryName)) {
            category = new Category(categoryName);
        }
        //Add functionality for the category implementation
        Action newAct = new Action(activityName.getText().toString(), category);
        DBConnection.getInstance().addActivity(newAct);

        //Observer Pattern: calling new intent will notify the view class
        Intent intent = new Intent(this, ActivitiesHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
