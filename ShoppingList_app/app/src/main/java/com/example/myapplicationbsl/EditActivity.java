package com.example.myapplicationbsl;
/**
 * @Author: Roberto Alejandro Rivera Mejia
 * @version 1, 24 Oct 2019
 *
 * add imports required for this activity
 * */
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 *EditActivity class extends AppCompatActivity class, Base class for activities that use action bar features.
 * thus we can use all of its methods
 * this activity is taking care of the edition  of an specific item
 * <p>
 * we declare all of our variables to private, so we can use the only within the class
 * */
public class EditActivity extends AppCompatActivity {
    // private fields of the class
    private int itemID;
    private Button btn_cancel;
    private Button btn_save;
    private Button btn_clear;
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private String db_name = "test3.db";
    private String table = "basicSL";
    private EditText et_field;
    private NumberPicker numberPicker;

    /**
     * fires when the system first creates the activity. On activity creation, the activity enters the Created state
     * set the user interface layout for this activity
     * the layout file is defined in the project res/layout/edit_item_layout.xml file
     * <p>
     * get the intent data that we pass as extra data, in this case itemID, thus we have key value pair
     * and query the database table
     * pull out the views from the XML file that we are going to need to display information
     * initialize the Database helper so we cam use our DB class methods in the current activity
     * we save the query to the database in a string myQuery  requesting an id from the DB table
     * and with a  cursor execute the query, validate that there is a fiels with the ID > 0
     * and set the views EditText and NumberPicker with the values previously saved
     * <p>
     * cancel btn listener
     * function when click on cancel button, fires an explicit intent and takes the ListActivity
     * <p>
     * button save takes the new edited values for the views EditText and numberPicker
     * validates that the field name is valid or not empty
     * we call the dbHelper method updateItem() setting up the new values
     * display a toast message to the user informing that the item has been updated  and
     * with an Intent we return to the ListActivity.class
     * <p>
     * clear btn listener
     * additional feature that resets the fields EditText and NumberPicker to their original values
     ** @param savedInstanceState  reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item_layout);
        Intent intent = getIntent();
        itemID = intent.getIntExtra("itemID",0);

        // grab the textview from the layout and update the text to show the new count value
        System.out.println(itemID);
        // pull the id's from the views we are going to need from the xml Edit layout
        et_field = (EditText) findViewById(R.id.edit_ly_textField);
        btn_clear = (Button) findViewById(R.id.edit_ly_clear);
        numberPicker = (NumberPicker) findViewById(R.id.edit_ly_numberPicker);
        btn_save = (Button) findViewById(R.id.edit_ly_button_save);
        btn_cancel = (Button) findViewById(R.id.edit_ly_button_cancel);

        // set a minimum and a maximum value on the number picker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);


        dbHelper = new DataBaseHelper(this,db_name,null,1);
        db = dbHelper.getReadableDatabase();

        String myQuery = "select * from " + table + " where ITEM_ID = "+ itemID;
        Cursor crs = db.rawQuery(myQuery, null);
        crs.moveToFirst();
            if(crs.getCount() > 0){
                et_field.setText(crs.getString(1));
                numberPicker.setValue(crs.getInt(2));
                Toast.makeText(this,"edit " + crs.getString(1),Toast.LENGTH_SHORT).show();
            }


        // will dismiss this activity
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            // overridden on click method to return a result to the starter of this activity
            public void onClick(View v) {
                    // we create an explicit intent that takes us from this activity to the ListActivity (ListActivity)
                    Toast.makeText(EditActivity.this, " Action canceled",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, ListActivity.class);
                    // method fires up the intent
                    startActivity(intent);

            }
        });
        //save button listener
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_field.getText().toString();
                if(!name.equals("") || !name.isEmpty() || name.length() > 3){
                    dbHelper.updateItem(itemID, name, numberPicker.getValue());
                    Toast.makeText(EditActivity.this, et_field.getText().toString()+" has been updated",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(EditActivity.this,ListActivity.class);
                    startActivity(intent1);
                }else{
                    Toast.makeText(EditActivity.this,"text cannot be empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        //button clear listener
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_field.getText().clear();
                numberPicker.setValue(1);

            }
        });

    }


}
