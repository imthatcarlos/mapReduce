package model.card;

import context.GameState;
import control.Control;
import junit.framework.Assert;
import model.PlayerModel;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class RobCardTest {
    @Test
    public void useCard() throws Exception {
        Control control = new Control();
        PlayerModel pModel = new PlayerModel(5, control);
        RobCard rCard = new RobCard(pModel);
        int res = rCard.useCard();
        Assert.assertEquals(res, GameState.CARD_ROB);
    }
}
