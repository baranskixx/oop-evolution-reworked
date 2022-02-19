import oop.evolution.Maps.NormalMap;
import oop.evolution.OnMapPositioning.Vector2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapCreationTest {

    @Test
    public void TestMapCreation(){
        NormalMap map1 = new NormalMap(10, 10, 0.3);
        NormalMap map2 = new NormalMap(5, 5, 0.4);
        NormalMap map3 = new NormalMap(4, 4, 0.5);
        NormalMap map4 = new NormalMap(20, 20, 0.2);
        NormalMap map5 = new NormalMap(10, 10, 1);

        Assertions.assertEquals(map1.jungleLowerLeft, new Vector2d(3, 3));
        Assertions.assertEquals(map1.jungleUpperRight, new Vector2d(5, 5));

        Assertions.assertEquals(map2.jungleLowerLeft, new Vector2d(1, 1));
        Assertions.assertEquals(map2.jungleUpperRight, new Vector2d(2, 2));

        Assertions.assertEquals(map3.jungleLowerLeft, new Vector2d(1 , 1));
        Assertions.assertEquals(map3.jungleUpperRight, new Vector2d(2, 2));

        Assertions.assertEquals(map4.jungleLowerLeft, new Vector2d(8, 8));
        Assertions.assertEquals(map4.jungleUpperRight, new Vector2d(11, 11));

        Assertions.assertEquals(map5.jungleLowerLeft, new Vector2d(1, 1));
        Assertions.assertEquals(map5.jungleUpperRight, new Vector2d(8, 8));
    }
}
