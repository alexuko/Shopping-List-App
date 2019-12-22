package com.example.myapplicationbsl;

/**
 * @Author: Roberto Alejandro Rivera Mejia
 * @version 1, 24 Oct 2019
 * */


/**
 * CustomItem class used to store item's information
 * like ID, name, quantity and if the items is crossed
 */
public class CustomItem {
    private int itemID;
    private String itemName;
    private int itemQty;
    private boolean isItemCrossed;

    /**
     * Default Constructor, specifying what every item needs to contain
     * @param id this is a unique number
     * @param itemName name of the item
     * @param qty quantity of the item
     * <isItemCrossed >isItemCrossed is set to false as when an CustomItem is added to the list this has not been purchased yet (extra functionality to implement)
     */
    public CustomItem(int id,String itemName,int qty) {
        this.itemID = id;
        this.itemName = itemName;
        this.itemQty = qty;
        this.isItemCrossed = false;
    }


    /**
     * method getItemID takes no parameters
     * @return the item ID of the object
     */
    public int getItemID() {return itemID;}

    /**
     * method getItemName takes no parameters
     * @return the current item's name
     */
    public String getItemName() {return itemName;}

    /**
     * method getItemQty
     * @return the current item's quantity
     */
    public int getItemQty() {return itemQty;    }

    /**
     * Gets the ID of the appointment.
     *
     * @return a boolean if the item is crossed either TRUE / FALSE.
     */
    public boolean isItemCrossed() {
        return isItemCrossed;
    }


}
