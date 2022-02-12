import oop.evolution.MapDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapDirectionTest {
    @Test
    public void rotateTest(){
        MapDirection a = MapDirection.NORTH;
        MapDirection b = MapDirection.SOUTH;

        Assertions.assertEquals(a.rotate(4), MapDirection.SOUTH);
        Assertions.assertEquals(a.rotate(8), MapDirection.NORTH);
        Assertions.assertEquals(b.rotate(4), MapDirection.NORTH);
        Assertions.assertEquals(b.rotate(8), MapDirection.SOUTH);
    }
}
