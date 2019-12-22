package com.example.myapplicationbsl;
/**
 * @Author: Roberto Alejandro Rivera Mejia
 * @version 1, 24 Oct 2019
 *

/**
 *imports required for this class
 * */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * MyCustomListAdapter class extends the BaseAdapter Common base class
 * of common implementation for an {@link Adapter}
 * this class helps us to inflate a custom items and create an Array list data structure of custom Items
 * <p>
 * we declare all of our variables to private, so we can use the only within the class
 */
public class MyCustomListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CustomItem> arrList_items;
    private DataBaseHelper dbh;
    private String db_name = "test3.db";
    private int itemID;


    /**
     * constructor for the class MyCustomListAdapter that takes in references to a context or layout we are at
     * and an array list structure
     * @param c is the xml layout file
     * @param al is our arrayList data structure
     */
    public MyCustomListAdapter(Context c, ArrayList<CustomItem> al) {
        dbh = new DataBaseHelper(c,db_name,null,1);
        context = c;
        arrList_items = al;
    }

    /**
     * method returns the size of the array list of items
     * @return an integer that is the size number
     */
    @Override
    public int getCount() {
        return arrList_items.size();
    }

    /**
     * return a item at a determined position of the array
     * @param position the number that we want to get from the array
     * @return an object of the item in the array that we querying at determined position
     *
     */
    @Override
    public Object getItem(int position) {return arrList_items.get(position); }

    /**
     * method  responsible for returning the row id of the given row in the array list
     * @param position is the position number we want to retrieve
     * @return returns an id of an item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * is responsible for generating the Views from the data for display inside
     * the list view.
     * <p>
     * the view holder to save us from requesting references to items over and over again
     * if we do not have a view to recycle then inflate the layout and fix up the view holder then
     * we get access to the layout inflater service and inflate the XML custom item layout into a view to which we can add data
     * <p>
     *  pull all the items from the XML so we can modify them
     *  set the view holder as a tag on this convert view in case it needs to be recycled
     *  <p>
     *  set all the data on the fields before returning it
     *  valueof() allows us to set an int as text
     *  <p>
     *  Button ci_remove_btn listener
     *  set a setOnClickListener() to  Remove btn, thus we can get the item from the array list data structure
     *  with position and getItemID we can query the DB and perform deleteItem() with the id of the item
     *  we want to delete
     *  notifying the arrayList structure that a item has been deleted and we need to update our arrayList
     *  Finally to confirm that the item has been deleted from the DB a toast message is displayed to the user
     *  <p>
     *  Button edit listener
     *  button on clicked get the item id from the array list data structure,
     *  create an explicit intent from the current parent activity List_activity to EditActivity
     *  taking as a extra data a itemID that will be used to within the EditActivity
     *  and we set the activity with startActivity()
     *
     * @param position is the position of the item in the list
     * @param convertView is the View object that you must use to setup your display
     * of this list item
     * @param parent he parent that owns the view
     * @return a constructed view
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item, parent, false);

            holder.tv_name = (TextView) convertView.findViewById(R.id.ci_tv_name);
            holder.tv_qty= (TextView) convertView.findViewById(R.id.ci_tv_qty);
            holder.tv_id= (TextView) convertView.findViewById(R.id.ci_tv_ID);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_id.setText(String.valueOf(arrList_items.get(position).getItemID()));
        holder.tv_name.setText(arrList_items.get(position).getItemName());
        holder.tv_qty.setText(String.valueOf(arrList_items.get(position).getItemQty()));

        //remove btn listener
        convertView.findViewById(R.id.ci_remove_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemID = arrList_items.get(position).getItemID();
                System.out.println(itemID);
                dbh.deleteItem(itemID);
                arrList_items.remove(position);
                notifyDataSetChanged();
                Toast.makeText(parent.getContext(), holder.tv_name.getText() +"  was deleted",Toast.LENGTH_SHORT).show();
            }
        });

        //Button edit listener
        convertView.findViewById(R.id.ci_edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemID = arrList_items.get(position).getItemID();
                System.out.println(itemID);
                Intent intent = new Intent(parent.getContext(), EditActivity.class);
                intent.putExtra("itemID", itemID);
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    /**
     * Inner Class ViewHolder defined here. This is
     * designed to take advantage of the view holder pattern.
     * <p>
     * The idea here is that as we create Views for the ListView we will store a reference to the items that we extract
     * from the XML layout
     * <p> taking 3 variables that will works as holders tv_id, tv_name and tv_qty
     */
    static class ViewHolder {
        public TextView tv_id;
        public TextView tv_name;
        public TextView tv_qty;
    }
}
