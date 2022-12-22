package river;

import java.awt.*;

public class FarmerGameEngine extends AbstractGameEngine {

    private GameObject Wolf;
    private GameObject Goose;
    private GameObject Beans;
    private GameObject Farmer;
    private GameObject Boat;

    public FarmerGameEngine() {
        Wolf = new GameObject("W", Location.START, Color.LIGHT_GRAY);
        Goose = new GameObject("G", Location.START, Color.PINK);
        Beans = new GameObject("B", Location.START, Color.green);
        Farmer = new GameObject("F", Location.START, Color.MAGENTA);
        Boat = new GameObject("BOAT", Location.START, Color.ORANGE);
        boatLocation = Location.START;

        itemMap.put(Item.ITEM_2, Wolf);
        itemMap.put(Item.ITEM_1, Goose);
        itemMap.put(Item.ITEM_0, Beans);
        itemMap.put(FARMER(), Farmer);

        ItemOnTheBoat = 0;
    }


    public int numberOfItems() {
        return 4;
    }

    @Override
    public void rowBoat() {
        // if the farmer is *not* in the boat, return
        // otherwise, invoke super.rowBoat();
        if (getItemLocation(Item.ITEM_3) != Location.BOAT)
            return;
        else
            super.rowBoat();
    }

    public boolean gameIsLost() {
        if (getItemLocation(Item.ITEM_1) == Location.BOAT) {
            return false;
        }
        if (getItemLocation(Item.ITEM_1) == getItemLocation(FARMER())) {
            return false;
        }
        if (getItemLocation(Item.ITEM_1) == boatLocation) {
            return false;
        }
        if (getItemLocation(Item.ITEM_1) == getItemLocation(Item.ITEM_2)) {
            return true;
        }
        if (getItemLocation(Item.ITEM_1) == getItemLocation(Item.ITEM_0)) {
            return true;
        }
        return false;
    }

    private Item FARMER() {
        return Item.ITEM_3;
    }

    public void transport(Item id) {
        loadBoat(id);
        loadBoat(FARMER());
        rowBoat();
        unloadBoat(id);
        unloadBoat(FARMER());
    }

    public void transportFarmer() {
        loadBoat(FARMER());
        rowBoat();
        unloadBoat(FARMER());
    }
}