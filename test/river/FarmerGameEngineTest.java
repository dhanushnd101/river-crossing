package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class FarmerGameEngineTest {
    private FarmerGameEngine engine;


    @Before
    public void setUp() throws Exception {
        engine = new FarmerGameEngine();
    }

    @Test
    public void testObjectCallThroughs() {
        //TODO change the assertion
        Assert.assertEquals("F", engine.getItemLabel(FARMER()));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER()));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(FARMER()));

        Assert.assertEquals("W", engine.getItemLabel(WOLF()));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF()));
        Assert.assertEquals(Color.LIGHT_GRAY, engine.getItemColor(WOLF()));

        Assert.assertEquals("G", engine.getItemLabel(GOOSE()));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE()));
        Assert.assertEquals(Color.PINK, engine.getItemColor(GOOSE()));

        Assert.assertEquals("B", engine.getItemLabel(BEANS()));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS()));
        Assert.assertEquals(Color.green, engine.getItemColor(BEANS()));

    }

    private Item WOLF() {
        return Item.ITEM_2;
    }

    private Item FARMER() {
        return Item.ITEM_3;
    }

    private Item BEANS() {
        return Item.ITEM_0;
    }

    private Item GOOSE() {
        return Item.ITEM_1;
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE()));
        //send Goose to the other side
        engine.transport(GOOSE());
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(GOOSE()));

    }

    @Test
    public void testWinningGame() {

        // transport the goose
        engine.transport(GOOSE());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.transportFarmer();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport the beans
        engine.transport(BEANS());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //go back with goose

        engine.transport(GOOSE());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport the wolf
        engine.transport(WOLF());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //go back
        engine.transportFarmer();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //transport the goose
        engine.transport(GOOSE());
        engine.unloadBoat(FARMER());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());

    }

    @Test
    public void testLosingGame() {

        // transport the goose
        engine.transport(GOOSE());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.transportFarmer();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //pick the wolf
        engine.transport(WOLF());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        //go back alone
        engine.loadBoat(FARMER());
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

    }

    @Test
    public void testError() {

        // transport the goose
        engine.transport(GOOSE());
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location topLoc = engine.getItemLocation(WOLF());
        Location midLoc = engine.getItemLocation(GOOSE());
        Location bottomLoc = engine.getItemLocation(BEANS());
        Location playerLoc = engine.getItemLocation(FARMER());

        // This action should do nothing since the wolf is not on the same shore as the
        // boat
        engine.loadBoat(WOLF());

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getItemLocation(WOLF()));
        Assert.assertEquals(midLoc, engine.getItemLocation(GOOSE()));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(BEANS()));
        Assert.assertEquals(playerLoc, engine.getItemLocation(FARMER()));
    }

    @Test
    public void testFarmNotOnBoatRow() {
        //add the goose and try to row
        Location midLoc = engine.getItemLocation(GOOSE());
        engine.loadBoat(GOOSE());
        engine.rowBoat();
        engine.unloadBoat(GOOSE());
        Assert.assertEquals(midLoc, engine.getItemLocation(GOOSE()));
    }

    @Test
    public void gameLost1() {
        engine.loadBoat(GOOSE());
        Assert.assertFalse(engine.gameIsLost());
    }

    @Test
    public void gameLost2() {
        // transport the wolf
        engine.transport(WOLF());
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

    }

    @Test
    public void TestGameObject(){
        GameObject T1;


    }

}
