package tests;

import context.GameState;
import control.Control;
import junit.framework.Assert;
import model.PlayerModel;
import model.card.Card;
import model.card.RobCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobCardTest {
    @Test
    public void useCard() throws Exception {
        Control control = new Control();
        PlayerModel pModel = new PlayerModel(5, control);
        RobCard rCard = new RobCard(pModel);
        int res = rCard.useCard();
        Assert.assertEquals(res, GameState.CARD_ROB);
    }

    @Test
    public void setOwner() throws Exception {
        Control control = new Control();
        PlayerModel firstOwner = new PlayerModel(5, control);
        PlayerModel secondOwner = new PlayerModel(8, control);

        RobCard card = new RobCard(firstOwner);
        card.setOwner(secondOwner);

        Assert.assertEquals(card.getOwner(), secondOwner);
    }
}
