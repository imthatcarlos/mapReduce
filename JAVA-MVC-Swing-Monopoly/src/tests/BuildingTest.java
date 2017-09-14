package tests;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import model.buildings.Building;

@RunWith(Arquillian.class)
public class BuildingTest {
    @Test
    public void setPurchasability() throws Exception {
        Building b = new Building(0, 0);
        boolean expected_p = false;
        b.setPurchasability(expected_p);
        Assert.assertEquals(b.isPurchasability(), expected_p);
    }
}
