package river;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphical interface for the River application
 *
 * @author Dhanush Dinesh
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================

    private final Rectangle FarmerButtonRect = new Rectangle(240, 120, 150, 30);
    private Rectangle MonsterButtonRect = new Rectangle(460, 120, 150, 30);

    private Rectangle BoatRect = new Rectangle(140, 335, 150, 50);

    Map<Item, Rectangle> itemRectangleMap = new HashMap<>();

    // ==========================================================
    // Private Fields
    // ==========================================================

    private AbstractGameEngine engine; // Model
    private boolean farmer = false;
    private boolean monster = false;

    private Graphics g;
    private int baseX = 20;
    private int baseY = 275;
    private int w = 50, h = 50;
    private boolean seatOne = false;
    private boolean seatTwo = false;

    int[] dx = {0, 60, 0, 60, 0, 60};
    int[] dy = {0, 0, -60, -60, -120, -120};


    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {

        engine = new FarmerGameEngine();
        addMouseListener(this);
        itemRectangleMap = new HashMap<>();
        // adding the items
        itemRectangleMap.put(Item.ITEM_0, new Rectangle(baseX + 60, baseY, w, h));
        itemRectangleMap.put(Item.ITEM_1, new Rectangle(baseX, baseY, w, h));
        itemRectangleMap.put(Item.ITEM_2, new Rectangle(baseX, baseY - 60, w, h));
        itemRectangleMap.put(Item.ITEM_3, new Rectangle(baseX + 60, baseY - 60, w, h));

    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics gArg) {
        seatOne = false;
        seatTwo = false;
        g = gArg;
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            farmer = true;
            monster = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            farmer = true;
        }
        paintMessage(message);
        if (farmer) {
            paintFarmerButton();
            paintMonsterButton();
        }

        int number_item = engine.numberOfItems();
        for (Item it : Item.values()) {
            if (number_item == 0)
                break;
            paintItems(it);
            number_item--;
        }
        paintBoat();

    }

    public void paintBoat() {
        Color color = Color.ORANGE;
        String label = "BOAT";

        int x = 0, y = 0, w = 110, h = 50;
        if (engine.getBoatLocation() == Location.START) {
            x = 140;
            y = 335;
        } else {
            x = 550;
            y = 335;
        }
        BoatRect = new Rectangle(x, y, w, h);
        g.setColor(color);
        g.fillRect(BoatRect.x, BoatRect.y, BoatRect.width, BoatRect.height);
        paintStringInRectangle(label, BoatRect.x, BoatRect.y, BoatRect.width, BoatRect.height);
    }

    public void paintItems(Item item) {
        Rectangle rect = getItemRectangle(item);
        printItemRect(rect, item);
    }

    private void printItemRect(Rectangle rec, Item item) {
        Color color = engine.getItemColor(item);
        String label = engine.getItemLabel(item);
        g.setColor(color);
        g.fillRect(rec.x, rec.y, rec.width, rec.height);
        paintStringInRectangle(label, rec.x, rec.y, rec.width, rec.height);
    }

    private Rectangle getItemRectangle(Item item) {
        Rectangle res;
        int w = 50, h = 50;
        int x = getItemX(item), y = getItemY(item);
        res = new Rectangle(x, y, w, h);
        itemRectangleMap.put(item, res);
        return res;

    }

    private int getItemX(Item item) {
        if (engine.getItemLocation(item) == Location.START || engine.getItemLocation(item) == Location.FINISH)
            return getBaseX(item) + dx[item.ordinal()];
        else if (engine.getBoatLocation() != engine.getItemLocation(item)) {
            return getBaseX(item) + dx[item.ordinal()] + offsetFunction();
        } else
            return itemRectangleMap.get(item).x;
    }

    private int offsetFunction() {
        if (!seatOne) {
            seatOne = true;
            return 0;
        } else
            return 60;
    }

    private int getBaseX(Item item) {
        if (engine.getItemLocation(item) == Location.START) {
            return 20;
        } else if (engine.getItemLocation(item) == Location.FINISH) {
            return 670;
        } else {
            if (engine.getBoatLocation() == Location.START) {
                return 140 - dx[item.ordinal()];
            } else {
                return 550 - dx[item.ordinal()];
            }
        }
    }

    private int getItemY(Item item) {
        return getBaseY(item) + dy[item.ordinal()];
    }

    private int getBaseY(Item item) {
        if (engine.getItemLocation(item) == Location.BOAT)
            return 275 - dy[item.ordinal()];
        return 275;
    }


    public void paintStringInRectangle(String str, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        int fontSize = (height >= 40) ? 32 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = x + width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = y + height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    public void paintMessage(String message) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public void paintFarmerButton() {
        g.setColor(Color.BLACK);
        paintBorder(FarmerButtonRect, 3);
        g.setColor(Color.PINK);
        paintRectangle(FarmerButtonRect);
        paintStringInRectangle("Farmer Game", FarmerButtonRect.x, FarmerButtonRect.y, FarmerButtonRect.width,
                FarmerButtonRect.height);
    }

    public void paintMonsterButton() {
        g.setColor(Color.BLACK);
        paintBorder(MonsterButtonRect, 3);
        g.setColor(Color.PINK);
        paintRectangle(MonsterButtonRect);
        paintStringInRectangle("Monster Game", MonsterButtonRect.x, MonsterButtonRect.y, MonsterButtonRect.width,
                MonsterButtonRect.height);
    }

    public void paintBorder(Rectangle r, int thickness) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
    }

    public void paintRectangle(Rectangle r) {
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {

        if (MonsterButtonRect.contains(e.getPoint())) {
            engine = new MonsterGameEngine();

            engine.resetGame();
            farmer = false;
            repaint();
        } else if (FarmerButtonRect.contains(e.getPoint())) {
            engine = new FarmerGameEngine();
            engine.resetGame();
            farmer = false;
            repaint();
        } else if(engine.gameIsLost() || engine.gameIsWon()) {
            return;
        } else if (BoatRect.contains(e.getPoint())) {
            engine.rowBoat();
        } else {
            int number_item = engine.numberOfItems();
            for (Item it : Item.values()) {
                if (number_item == 0)
                    break;

                if (itemRectangleMap.get(it).contains(e.getPoint())) {
                    if (engine.getItemLocation(it) == Location.START || engine.getItemLocation(it) == Location.FINISH)
                        engine.loadBoat(it);
                    else
                        engine.unloadBoat(it);
                }

                number_item--;
            }
        }
        repaint();
    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}