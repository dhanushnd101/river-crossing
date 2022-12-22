package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class MonsterGameEngineTest {
    private MonsterGameEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new MonsterGameEngine();
    }

    @Test
    public void testObjectCallThroughs() {
        //TODO change the assertion
        Assert.assertEquals("M1", engine.getItemLabel(M1()));
        Assert.assertEquals(Location.START, engine.getItemLocation(M1()));
        Assert.assertEquals(Color.RED, engine.getItemColor(M1()));

        Assert.assertEquals("M2", engine.getItemLabel(M2()));
        Assert.assertEquals(Location.START, engine.getItemLocation(M2()));
        Assert.assertEquals(Color.RED, engine.getItemColor(M2()));

        Assert.assertEquals("M3", engine.getItemLabel(M3()));
        Assert.assertEquals(Location.START, engine.getItemLocation(M3()));
        Assert.assertEquals(Color.RED, engine.getItemColor(M3()));

        Assert.assertEquals("K1", engine.getItemLabel(K1()));
        Assert.assertEquals(Location.START, engine.getItemLocation(K1()));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(K1()));

        Assert.assertEquals("K2", engine.getItemLabel(K2()));
        Assert.assertEquals(Location.START, engine.getItemLocation(K2()));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(K2()));

        Assert.assertEquals("K3", engine.getItemLabel(K3()));
        Assert.assertEquals(Location.START, engine.getItemLocation(K3()));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(K3()));


    }

    private Item M1() {
        return Item.ITEM_0;
    }

    private Item M2() {
        return Item.ITEM_2;
    }

    private Item M3() {
        return Item.ITEM_4;
    }

    private Item K1() {
        return Item.ITEM_1;
    }

    private Item K2() {
        return Item.ITEM_3;
    }

    private Item K3() {
        return Item.ITEM_5;
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(M1()));
        //send Monster to the other side

        engine.transportWithMonster(M2());
        engine.unloadBoat(M2());
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(M2()));

    }

    @Test
    public void testWinningGame() {

        // transport monsters MKKK -- MM
        engine.transportWithMonster(M2());
        engine.unloadBoat(M2());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone MMKKK -- M2
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport monster again KKK -- M1 M3M2
        engine.unloadBoat(M1());
        engine.transportWithMonster(M3());
        engine.unloadBoat(M3());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone M1KKK -- M3M2
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport two K  MK -- K1 K2M3M2
        engine.unloadBoat(M1());
        engine.transportWithMunchkins(K2());
        engine.unloadBoat(K2());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //return with 1K,1M M1M2K1K3 -- K2M3
        engine.loadBoat(M2());
        engine.rowBoat();
        engine.unloadBoat(M2());
        engine.unloadBoat(K1());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon()); //done

        //transport 2K's to the other side MM -- KKKM3
        engine.transportWithMunchkins(K3());
        engine.unloadBoat(K3());
        engine.unloadBoat(K1());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //return the Monster MMM- KKK
        engine.loadBoat(M3());
        engine.rowBoat();
        engine.unloadBoat(M3());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //sent M3 to the other side MM -- KKKM
        engine.transportWithMonster(M3());
        engine.unloadBoat(M3());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //sent the M2 to the right side -- KKKMMM
        engine.rowBoat();
        engine.transportWithMonster(M2());
        engine.unloadBoat(M2());
        engine.unloadBoat(M1());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());

    }

    @Test
    public void testLosingGame() {

        // transport the M2 MKKK -- MM
        engine.transportWithMonster(M2());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone MMKKK -- M
        engine.unloadBoat(M2());
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //pick the K2
        engine.transportWithMonster(K2());
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());


    }

    @Test
    public void testError() {

        // transport the M2
        engine.transportWithMonster(M2());
        engine.unloadBoat(M2());
        engine.unloadBoat(M1());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getItemLocation(M1());
        Location midLoc = engine.getItemLocation(M2());
        Location bottomLoc = engine.getItemLocation(K1());
        Location playerLoc = engine.getItemLocation(K2());

        // This action should do nothing since the K2 is not on the same shore as the
        // boat
        engine.loadBoat(K2());

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getItemLocation(M1()));
        Assert.assertEquals(midLoc, engine.getItemLocation(M2()));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(K1()));
        Assert.assertEquals(playerLoc, engine.getItemLocation(K2()));
    }

    @Test
    public void monstersMoreLeft() {
        //two munchkins go to the right
        engine.transportWithMunchkins(K2());
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

}
