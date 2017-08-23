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

    static Card cardToMove;
    static CardPile moveFromPile;



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
                allPiles[i].select(x, y);
                repaint();
                return true;
            }
        }
        return true;

        /*if(cardToMove==null) {
            if (discardPile.includes(x, y)) {
                cardToMove = discardPile.preSelect(x, y);
                moveFromPile = discardPile;
                repaint();
                return true;
            }

            if (deckPile.includes(x, y)) {
                deckPile.select(x, y);
                repaint();
                return true;
            }

            for (int i = 0; i < 7; i++) {
                if (tableau[i].includes(x, y)) {
                    cardToMove = tableau[i].preSelect(x, y);
                    moveFromPile = tableau[i];
                    repaint();
                    return true;
                }
            }
            return true;
        }else {
            for (int i = 2; i <13; i++) {
                if(allPiles[i].includes(x,y)&&allPiles[i].canTake(cardToMove)){
                    moveFromPile.move(cardToMove,allPiles[i]);
                }
            }
            moveFromPile = null;
            cardToMove = null;
            repaint();
            return true;
        }*/
    }
}

