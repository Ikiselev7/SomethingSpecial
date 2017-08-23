/**
 * Created by ilya on 21.08.17.
 */
class SuitPile extends CardPile {

    SuitPile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canTake(Card aCard) {
        if (empty()) {
            return aCard.getRank() == 0;
        }
        Card topCard = top();
        return (aCard.getSuit() == topCard.getSuit())
                && (aCard.getRank() == 1 + topCard.getRank());
    }

    @Override
    public void select(int tx, int ty) {
        if(Solitare.cardToMove!=null){
            if(this.canTake(Solitare.cardToMove)){
                Solitare.moveFromPile.move(Solitare.cardToMove, this);
            }
            Solitare.cardToMove = null;
            Solitare.moveFromPile = null;

        }else{
            return;
        }
    }
}
