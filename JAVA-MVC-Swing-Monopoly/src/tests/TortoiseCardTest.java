package tests;

import junit.framework.Assert;
import model.PlayerModel;
import model.card.TortoiseCard;
import control.Control;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
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