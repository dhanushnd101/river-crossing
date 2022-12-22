package river;

import java.awt.*;

public class MonsterGameEngine extends AbstractGameEngine {

    private GameObject M1;
    private GameObject M2;
    private GameObject M3;
    private GameObject K1;
    private GameObject K2;
    private GameObject K3;
    private GameObject Boat;

    public MonsterGameEngine() {
        M1 = new GameObject("M1", Location.START, Color.RED);
        M2 = new GameObject("M2", Location.START, Color.RED);
        M3 = new GameObject("M3", Location.START, Color.RED);
        K1 = new GameObject("K1", Location.START, Color.GREEN);
        K2 = new GameObject("K2", Location.START, Color.GREEN);
        K3 = new GameObject("K3", Location.START, Color.GREEN);
        Boat = new GameObject("BOAT", Location.START, Color.ORANGE);
        boatLocation = Location.START;

        itemMap.put(Item.ITEM_0, M1);
        itemMap.put(Item.ITEM_2, M2);
        itemMap.put(Item.ITEM_4, M3);
        itemMap.put(Item.ITEM_1, K1);
        itemMap.put(Item.ITEM_3, K2);
        itemMap.put(Item.ITEM_5, K3);

        ItemOnTheBoat = 0;
    }

    public int numberOfItems() {
        return 6;
    }

    @Override
    public void rowBoat() {
        if (ItemOnTheBoat == 0)
            return;
        else
            super.rowBoat();
    }

    public boolean gameIsLost() {
        int monstersLeft = 0;
        int monstersRight = 0;
        int munchkinsLeft = 0;
        int munchkinsRight = 0;

        for (Item it : Item.values()) {
            if (it.ordinal() % 2 == 0) {
                if (getItemLocation(it) == Location.START || (getItemLocation(it) == Location.BOAT && boatLocation == Location.START))
                    monstersLeft++;
                else
                    monstersRight++;
            } else {
                if (getItemLocation(it) == Location.START || (getItemLocation(it) == Location.BOAT && boatLocation == Location.START))
                    munchkinsLeft++;
                else
                    munchkinsRight++;
            }
        }
        if (monstersLeft > munchkinsLeft && munchkinsLeft != 0)
            return true;
        else if (monstersRight > munchkinsRight && munchkinsRight != 0)
            return true;
        else
            return false;
    }

    public void transportWithMonster(Item id) {
        loadBoat(id);
        loadBoat(Item.ITEM_0);
        rowBoat();
    }

    public void transportWithMunchkins(Item id) {
        loadBoat(id);
        loadBoat(Item.ITEM_1);
        rowBoat();
    }
}