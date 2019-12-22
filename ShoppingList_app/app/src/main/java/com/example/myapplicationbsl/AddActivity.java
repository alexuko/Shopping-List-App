package com.example.myapplicationbsl;
/**
 * @Author: Roberto Alejandro Rivera Mejia
 * @version 1, 24 Oct 2019
 * */
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
/**
 * AddActivity class extends AppCompatActivity class, Base class for activities that use action bar features.
 * thus we can use all of its methods, on this activity we are adding new created items to our DB
 * <p>
 * we declare all of our variables to private, so we can use the only within the class
 * */
public class AddActivity extends AppCompatActivity {
    // private fields of the class
    private DataBaseHelper dbh;
    private SQLiteDatabase sql_db;
    private Button btn_cancel;
    private Button btn_clear;
    private Button btn_save;
    private NumberPicker numPicker;
    private EditText et_new_strings;
    private String db_name = "test3.db";
    private String tbl_name = "basicSL";

    /**
     * method onCreate fires when the system first creates the activity. On activity creation, the activity enters the Created state
     * set the user interface layout for this activity
     * the layout file is defined in the project res/layout/add_list_layout.xml file
     * pull out the views from the XML file that we are going to need to display information
     * set the min and max values of the NumberPicker view
     * initialize a DataBaseHelper for the current activity and the name of the table we will be using
     * <p>
     * clear btn listener
     * additional feature that resets the fields EditText and NumberPicker to their original values
     * <p>
     * cancel btn listener
     * function when click on cancel button, fires an explicit intent and take you to listActivity
     * <p>
     * save btn listener
     * listener set to the xml view btn_save then when the button is pressed, it calls the method
     * saveItemInDb() passing the text taken from the EditText view field
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_list_layout);

        // pull the list view and the edit text from the xml add_list_layout
        btn_save = findViewById(R.id.add_ly_button_save);
        btn_clear = findViewById(R.id.add_ly_clear);
        btn_cancel = findViewById(R.id.add_ly_button_cancel);
        et_new_strings = findViewById(R.id.add_ly_textField);
        numPicker = findViewById(R.id.add_ly_numberPicker);
        numPicker.setMinValue(1);
        numPicker.setMaxValue(100);

        dbh = new DataBaseHelper(this, db_name, null, 1);
        sql_db = dbh.getWritableDatabase();


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_new_strings.getText().clear();
                numPicker.setValue(1);

            }
        });

        // button cancel listener
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            // overridden method to handle a button click
            public void onClick(View v) {
                Toast.makeText(AddActivity.this, " Action canceled",Toast.LENGTH_SHORT).show();
                // we create an explicit intent that takes us from this activity to the ListActivity (ListActivity)
                Intent intent = new Intent(AddActivity.this, ListActivity.class);
                // method fires up the intent
                startActivity(intent);
            }
        });

        //button save item listener
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItemInDb(et_new_strings.getText().toString());

            }
        });

    }

    /**
     * method saves the item into the DB table
     * <p>
     * as a validation we want to make sure that the user types a valid name and that the
     * field is not left empty
     * initialize a ContentValues to hold the names of the rows we want to edit along with the
     * name in the edit textfield and the number picker, a default value to IS_ITEM_CROSSED is set to
     * false  as is a new item in the list
     * <p>
     * insert takes the values fro the content values and inserts them into a new row in the DB table
     * a toast message is display to let the user know that the item was inserted successfully
     * <p>
     * an new intent is initialized and set to go back to the List activity once the item has been saved
     * fired by the parent method startActivity()
     * @param item is the name of the item that the user types in the EditText field
     * @return true if the item was saved successfully
     *         false if the input name is invalid or empty
     */
    public boolean saveItemInDb(String item){

        if(!item.equals("") && !item.isEmpty() && item.length() > 2){

            ContentValues cv = new ContentValues();
            cv.put("ITEM_NAME", et_new_strings.getText().toString());
            cv.put("ITEM_QTY", numPicker.getValue());
            cv.put("IS_ITEM_CROSSED", false);
            sql_db.insert(tbl_name, null, cv);

            Toast.makeText(this, et_new_strings.getText().toString()+ " added to the Shopping List", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddActivity.this,ListActivity.class);
            startActivity(intent);

            return true;
        }else {
            Toast.makeText(this, "Not valid input", Toast.LENGTH_SHORT).show();
            return false;

        }
    }

}
