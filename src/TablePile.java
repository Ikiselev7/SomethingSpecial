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
    public Card pop() {
        Card poped =  super.pop();

        if(!this.empty()&&!this.top().isFaceUp()) this.top().flip();

        return poped;
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
    public void move(Card aCard, CardPile targetPile) {
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
            targetPile.push(movingPile.pop());
            if(movingPile.top()!=null&&!targetPile.canTake(movingPile.top())){
                movingPile.push(targetPile.pop());
                while (!movingPile.empty()){
                    this.push(movingPile.pop());
                }
            }
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
        if(Solitare.cardToMove==null){
            if (this.empty()) {
                return;
            }else{
                Card selectedCard = find(top(),tx,ty);
                if(!selectedCard.isFaceUp()) selectedCard = top();
                Solitare.cardToMove = selectedCard;
                selectedCard.select();
                Solitare.moveFromPile = this;
            }
        }else{
            if(this.canTake(Solitare.cardToMove)){
                Solitare.moveFromPile.move(Solitare.cardToMove,this);
            }
            Solitare.cardToMove = null;
            Solitare.moveFromPile = null;
            return;
        }

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
