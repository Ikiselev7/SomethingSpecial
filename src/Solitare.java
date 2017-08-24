/**
 * Created by ilya on 21.08.17.
 */
import java.applet.Applet;
import java.awt.Event;
import java.awt.Graphics;

public class Solitare extends Applet {
    static DeckPile deckPile;
    static DiscardPile discardPile;
    static TablePile tableau[];
    static SuitPile suitPile[];
    static CardPile allPiles[];

    private static Card selectedCard;
    private static CardPile selectedPile;

    public static void select(Card selCard,CardPile selPile){
        selectedCard = selCard;
        selectedCard.select();
        selectedPile = selPile;
    }

    public static void deselect(){
        selectedPile = null;
        if(selectedCard!=null) selectedCard.deselect();
        selectedCard = null;
    }

    @Override
    public void init() {
        // first allocate the arrays
        allPiles = new CardPile[13];
        suitPile = new SuitPile[4];
        tableau = new TablePile[7];
        // then fill them in
        allPiles[0] = deckPile = new DeckPile(335, 5);
        allPiles[1] = discardPile = new DiscardPile(268, 5);
        for (int i = 0; i < 4; i++) {
            allPiles[2 + i] = suitPile[i] = new SuitPile(15 + 60 * i, 5);
        }
        for (int i = 0; i < 7; i++) {
            allPiles[6 + i] = tableau[i] = new TablePile(5 + 55 * i, 80, i + 1);
        }
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < 13; i++) {
            allPiles[i].display(g);
        }
    }

    @Override
    public boolean mouseDown(Event evt, int x, int y) {
        for (int i = 0; i < 13; i++) {
            if (allPiles[i].includes(x, y)) {
                if(selectedCard==null){
                    allPiles[i].select(x, y);
                }else{
                    selectedPile.move(selectedCard,allPiles[i]);
                }
                repaint();
                return true;
            }
        }
        return true;
    }
}

