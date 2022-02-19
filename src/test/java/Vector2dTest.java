import oop.evolution.OnMapPositioning.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test
    public void testEquality(){
        Vector2d a = new Vector2d(0, 0);
        Vector2d b = new Vector2d(1, -1);
        Vector2d c = new Vector2d(3, 4);
        Vector2d d = new Vector2d(-7 ,1);
        Vector2d e = new Vector2d(7, 1);
        Vector2d f = new Vector2d(1, 7);
        Vector2d g = new Vector2d(3, 4);

        Assertions.assertNotEquals(a, b);
        Assertions.assertNotEquals(b, c);
        Assertions.assertNotEquals(d, e);
        Assertions.assertNotEquals(d, f);
        Assertions.assertNotEquals(e, f);
        Assertions.assertNotEquals(d, g);

        Assertions.assertEquals(c, g);
    }

    @Test
    public void testAdd(){
        Vector2d a = new Vector2d(0, 0);
        Vector2d b = new Vector2d(1, -1);
        Vector2d c = new Vector2d(3, 4);
        Vector2d d = new Vector2d(-7 ,1);

        Vector2d ab = new Vector2d(1, -1);
        Vector2d ac = new Vector2d(3, 4);
        Vector2d ad = new Vector2d(-7, 1);
        Vector2d bc = new Vector2d(4, 3);
        Vector2d bd = new Vector2d(-6, 0);
        Vector2d cd = new Vector2d(-4, 5);

        Assertions.assertEquals(a.add(b), ab);
        Assertions.assertEquals(a.add(c), ac);
        Assertions.assertEquals(a.add(d), ad);
        Assertions.assertEquals(b.add(c), bc);
        Assertions.assertEquals(b.add(d), bd);
        Assertions.assertEquals(c.add(d), cd);
    }

    @Test
    public void testSubtract(){
        Vector2d a = new Vector2d(0, 0);
        Vector2d b = new Vector2d(1, -1);
        Vector2d c = new Vector2d(3, 4);

        Vector2d ab = new Vector2d(-1, 1);
        Vector2d ac = new Vector2d(-3, -4);
        Vector2d ba = new Vector2d(1, -1);
        Vector2d bc = new Vector2d(-2, -5);
        Vector2d ca = new Vector2d(3, 4);
        Vector2d cb = new Vector2d(2, 5);

        Assertions.assertEquals(a.subtract(b), ab);
        Assertions.assertEquals(a.subtract(c), ac);
        Assertions.assertEquals(b.subtract(a), ba);
        Assertions.assertEquals(b.subtract(c), bc);
        Assertions.assertEquals(c.subtract(a), ca);
        Assertions.assertEquals(c.subtract(b), cb);
    }

    @Test
    public void testOpposite(){
        Vector2d a = new Vector2d(0, 0);
        Vector2d b = new Vector2d(1, -1);
        Vector2d c = new Vector2d(3, 4);

        Vector2d oppA = new Vector2d(0, 0);
        Vector2d oppB = new Vector2d(-1, 1);
        Vector2d oppC = new Vector2d(-3, -4);

        Assertions.assertEquals(a, oppA.opposite());
        Assertions.assertEquals(a.opposite(), oppA);
        Assertions.assertEquals(b, oppB.opposite());
        Assertions.assertEquals(b.opposite(), oppB);
        Assertions.assertEquals(c, oppC.opposite());
        Assertions.assertEquals(c.opposite(), oppC);
    }
}
