package river;

import java.awt.*;
import java.util.HashMap;

public abstract class AbstractGameEngine implements GameEngine {

    protected HashMap<Item, GameObject> itemMap = new HashMap<>();
    protected Location boatLocation;
    protected int ItemOnTheBoat;

    @Override
    public int numberOfItems() {
        return 0;
    }

    @Override
    public String getItemLabel(Item item) {
        return itemMap.get(item).getLabel();
    }

    @Override
    public Color getItemColor(Item item) {
        return itemMap.get(item).getColor();
    }

    @Override
    public Location getItemLocation(Item item) {
        return itemMap.get(item).getLocation();
    }

    @Override
    public void setItemLocation(Item item, Location location) {
        itemMap.get(item).setLocation(location);
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }

    @Override
    public void loadBoat(Item item) {
        if (ItemOnTheBoat == 2)
            return;
        else if (itemMap.get(item).getLocation() == boatLocation) {
            itemMap.get(item).setLocation(Location.BOAT);
            ItemOnTheBoat++;
        }
    }

    @Override
    public void unloadBoat(Item item) {
        GameObject mapGO = itemMap.get(item);
        if (mapGO.getLocation() == Location.BOAT) {
            mapGO.setLocation(boatLocation);
            ItemOnTheBoat--;
        }
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    @Override
    public boolean gameIsWon() {
        int number_item = numberOfItems();
        for (Item it : Item.values()) {
            if (number_item == 0)
                break;
            if (getItemLocation(it) != Location.FINISH)
                return false;
            number_item--;
        }
        return true;
    }

    @Override
    public boolean gameIsLost() {
        return false;
    }

    @Override
    public void resetGame() {
        int number_item = numberOfItems();
        for (Item it : Item.values()) {
            if (number_item == 0)
                break;
            setItemLocation(it, Location.START);
            number_item--;
        }
        boatLocation = Location.START;
        ItemOnTheBoat = 0;
    }
}
