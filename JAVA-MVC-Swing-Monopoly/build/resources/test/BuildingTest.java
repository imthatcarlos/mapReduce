package tests;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import model.buildings.Building;

public class BuildingTest {
    @Test
    public void setPurchasability() throws Exception {
        Building b = new Building(0, 0);
        boolean expected_p = false;
        b.setPurchasability(expected_p);
        Assert.assertEquals(b.isPurchasability(), expected_p);
    }
}
