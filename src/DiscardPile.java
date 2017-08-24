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
        if(targetPile.canTake(this.top())) {
            targetPile.push(pop());
            Solitare.deselect();
        }else{
            Solitare.deselect();
        }
    }

    @Override
    public void select(int tx, int ty) {
        if (this.empty()) {
            return;
        }
        Solitare.select(this.top(),this);
    }
}
