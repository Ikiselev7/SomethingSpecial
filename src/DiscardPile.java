/**
 * Created by ilya on 21.08.17.
 */
class DiscardPile extends CardPile {

    DiscardPile(int x, int y) {
        super(x, y);
    }

    @Override
    public void push(Card aCard) {
        if (!aCard.isFaceUp()) {
            aCard.flip();
        }
        super.push(aCard);
    }

    @Override
    public void move(Card aCard, CardPile targetPile) {
        targetPile.push(pop());
    }

    @Override
    public void select(int tx, int ty) {
        if(Solitare.cardToMove!=null){
            Solitare.cardToMove = null;
            Solitare.moveFromPile = null;
        }else {
            if (this.empty()) {
                return;
            }
            Solitare.cardToMove = this.top();
            this.top().select();
            Solitare.moveFromPile = this;
            return;
        }

    }
}
