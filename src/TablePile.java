/**
 * Created by ilya on 21.08.17.
 */
import java.awt.Graphics;

class TablePile extends CardPile {

    TablePile(int x, int y, int c) {
        // initialize the parent class
        super(x, y);
        // then initialize our pile of cards
        for (int i = 0; i < c; i++) {
            push(Solitare.deckPile.pop());
        }
        // flip topmost card face up
        top().flip();
    }

    @Override
    public boolean canTake(Card aCard) {
        if (empty()) {
            return aCard.getRank() == 12;
        }
        Card topCard = top();
        return (aCard.getColor() != topCard.getColor())
                && (aCard.getRank() == topCard.getRank() - 1);
    }

    @Override
    public boolean includes(int tx, int ty) {
        // don't test bottom of card
        if(top()==null) {
            return x <= tx && tx <= x + Card.width && y <= ty && ty <= y+Card.height;
        }
        else {
            return cardsIncludes(top(), tx, ty);
        }
    }

    private boolean cardsIncludes(Card card, int tx,int ty){
        if(card.includes(tx,ty)){
            return true;
        }else if(card.link != null) {
            return cardsIncludes(card.link, tx,ty);
        }else{
            return false;
        }

    }

    @Override
    public void move(Card aCard, CardPile target) {
        //target.push(pop());
        CardPile movingPile = new CardPile(0,0);
        while(true){
            Card movingCard = this.pop();
            movingPile.push(movingCard);
            if(movingCard==aCard){
                break;
            }
        }
        while(!movingPile.empty()){
            target.push(movingPile.pop());
        }


        if(!this.empty()&&!this.top().isFaceUp()) this.top().flip();
    }

    @Override
    public Card preSelect(int tx, int ty) {
        if (empty()) {
            return null;
        }else{
            Card card = find(top(),tx,ty);
            if(!card.isFaceUp()) card = top();
            return card;
        }

    }

    private Card find(Card aCard, int tx, int ty){
        if(aCard.includes(tx,ty)){
            return aCard;
        }else{
            return find(aCard.link, tx,ty);
        }
    }



    @Override
    public void select(int tx, int ty) {
        if (empty()) {
            return;
        }

        // if face down, then flip
        Card topCard = top();
        if (!topCard.isFaceUp()) {
            topCard.flip();
            return;
        }

        // else see if any suit pile can take card
        topCard = pop();
        for (int i = 0; i < 4; i++) {
            if (Solitare.suitPile[i].canTake(topCard)) {
                Solitare.suitPile[i].push(topCard);
                if(!this.empty()&&!this.top().isFaceUp()) this.top().flip();
                return;
            }
        }
        // else see if any other table pile can take card
        for (int i = 0; i < 7; i++) {
            if (Solitare.tableau[i].canTake(topCard)) {
                Solitare.tableau[i].push(topCard);
                if(!this.empty()&&!this.top().isFaceUp()) this.top().flip();
                return;
            }
        }
        // else put it back on our pile
        push(topCard);
    }

    private int stackDisplay(Graphics g, Card aCard) {
        int localy;
        if (aCard == null) {
            return y;
        }
        localy = stackDisplay(g, aCard.link);
        aCard.draw(g, x, localy);
        return localy + 35;
    }

    @Override
    public void display(Graphics g) {
        stackDisplay(g, top());
    }

}
