package com.example.myapplicationbsl;
/**
 * @Author: Roberto Alejandro Rivera Mejia
 * @version 1, 24 Oct 2019
 *
 * add imports required for this activity
 * */
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 *ListActivity class extends AppCompatActivity class, Base class for activities that use action bar features.
 * thus we can use all of its methods
 * this activity class takes care of the display of the shopping list
 * <p>
 * we declare all of our variables to private, so we can use the only within the class
 * */
public class ListActivity extends AppCompatActivity {
    /**
     *Global Variables of all of the resources we will need in this activity
     **/
    private TextView tv_display;
    private ListView lv_mainlist;
    private DataBaseHelper dbh;
    private SQLiteDatabase sql_db;
    private ArrayList<CustomItem> al_items;
    private MyCustomListAdapter myCLA;
    private String db_name = "test3.db";
    private String tbl_name = "basicSL";
    private String total_text;

    /**
     * fires when the system first creates the activity. On activity creation, the activity enters the Created state
     * set the user interface layout for this activity
     * the layout file is defined in the project res/layout/list_layout.xml file
     * pull out the views from the XML file that we are going to need to display information
     * initialize the Database helper so we can use our DB class methods in the current activity
     * initialize the ArrayList of CustomItem as the type of object to store
     * create an MyCustomListAdapter for for the current context - activity , and the previously creates arrayList dataStructure
     * we call the method getTableItems(table) to pull data from of db
     * @param savedInstanceState  reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        tv_display = findViewById(R.id.list_ly_tv_display);
        lv_mainlist = findViewById(R.id.list_ly_shopping_list);


        dbh = new DataBaseHelper(this,db_name,null,1);
        sql_db = dbh.getReadableDatabase();
        al_items = new ArrayList<CustomItem>();
        myCLA = new MyCustomListAdapter(this,al_items);
        lv_mainlist.setAdapter(myCLA);

        getTableItems(tbl_name);



    }

    /**
     * Method queries the DB table by selecting everything taking a table name as its only parameter
     * <p>
     * declare and initialize a cursor to handle the query
     * if the table has at least one item then the data will be displayed
     * query from the cursor request the specific data field that we are interested in
     * then with a for loop we get the every item, pass it to the al_items and notify to the adapter that the data has
     * changed and it needs to update it.
     * if nothing in the list a empty text will be displayed in the basket
     * @param tableName take the name of the table that we wand to get the fields from
     */
    public void getTableItems(String tableName){
        String myQuery = "SELECT * FROM " + tableName ;
        Cursor crs = sql_db.rawQuery(myQuery, null);
        crs.moveToFirst();
        if(crs.getCount() > 0){
            // the columns that we wish to retrieve from the tables
            String[] columns = {"ITEM_ID", "ITEM_NAME", "ITEM_QTY"};
            // run the query. this will give us a cursor into the database
            // that will enable us to change the table row that we are working with
            Cursor c = sql_db.query(tableName, columns, null,null,null,null,null);
            // print out some data from the cursor to the screen
            total_text = " Items in your List";
            //move to the first row
            c.moveToFirst();
            //initialize a for loop over the DB table
            for(int i = 0; i < c.getCount(); i++) {
                //add an new item based on the CustomItem for eache row in the DB
                al_items.add(new CustomItem(c.getInt(0), c.getString(1), c.getInt(2)));
                //Notify the adapter
                myCLA.notifyDataSetChanged();
                //get to the next line
                c.moveToNext();
            }
            //Set a new text once the rows have been retrieved
            tv_display.setText(total_text);
        }else {
            //if nothing in the DB shows this message in the tv_display textview
            tv_display.setText("empty basket");
        }


    }

    /**
     *This method inflates a XML layout menu to the action Bar
     * @param menu takes a menu XML layout
     * @return true to indicate that the menu was inflated correctly
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *
     * Depending on the item that was selected change to the activity or delete items of the list.
     * method takes in an ID from the item xml from the menu.xml
     * if id is equals to navBar_add_items takes you to the AddActivity with an Intent
     * if id is equals to navBar_delete_items deletes items from the DB by calling the method onDestroy()
     * clears the items from the list and tells the adapter that the list has changes and updates it
     * refreshing the activity to reset initial values
     *
     * @param item take item of the type MenuItem
     * @return return true to indicate that the menu item has been handled
     */
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.navBar_add_items) {
            intent = new Intent(ListActivity.this, AddActivity.class);
            startActivity(intent);


        }else if(id == R.id.navBar_delete_items){
            Toast.makeText(this,"List Cleared",Toast.LENGTH_SHORT).show();
            dbh.onDestroy();
            al_items.clear();
            myCLA.notifyDataSetChanged();
            Intent refresh = new Intent(this, ListActivity.class);
            startActivity(refresh);
            Toast.makeText(this,"your list has been cleaned",Toast.LENGTH_SHORT).show();
            this.finish();


        }
        // call superclass depending on the option selected
        return super.onOptionsItemSelected(item);
    }

}
