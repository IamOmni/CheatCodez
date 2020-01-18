import com.kroy.classes.Firetruck;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;


public class FiretruckTestNew {

    private void success(String description) {
        System.out.println("Success - " + description);
    }

    private void fail(String description) {

        System.out.println("Failed - " + description);
        Assert.fail(description);
    }
//    @Test
//    public void test_firetruck_move_up(){
//        Firetruck engine = new Firetruck();
//        engine.setDegree(90);
//        int startPos = engine.getY();
//        engine.setUp(true);
//        engine.setySpeed(100);
//        for (int i = 0; i<10; i++){
//            engine.update(100);
//        }
//        engine.update(100);
//        int newPos = engine.getY();
//        boolean movedUp = false;
//        if (newPos > startPos){
//            movedUp = true;
//        }
//
//        try {
//            assertTrue(movedUp);
//            success("firetruck has moved up");
//        }
//        catch (AssertionError a) {
//            fail("firetruck has not moved up");
//        }
//    }

}
