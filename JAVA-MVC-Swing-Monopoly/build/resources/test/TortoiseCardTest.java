package tests;

import junit.framework.Assert;
import model.PlayerModel;
import model.card.TortoiseCard;
import control.Control;

import static org.junit.Assert.*;

public class TortoiseCardTest {
    @org.junit.Test
    public void setLife() throws Exception {
        Control control = new Control();
        PlayerModel pModel = new PlayerModel(5, control);
        TortoiseCard tCard = new TortoiseCard(pModel);
        int inputLife = 5;
        tCard.setLife(inputLife);
        Assert.assertEquals(inputLife, tCard.getLife());
    }
}