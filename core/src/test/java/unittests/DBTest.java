//package unittests;
//
//import org.junit.Assert;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import com.kroy.modules.DB;
//import java.util.ArrayList;
//
//
//public class DBTest {
//
//    private DB db = new DB();
//
//    private void success(String description){ System.out.println("Success - " + description); }
//    private void fail(String description) {
//        System.out.println("Failed - " +description);
//        Assert.fail(description);
//    }
//
//    @Test
//    public void fetch_amount_2(){
//        ArrayList scores = db.getLeaderboard("2");
//        try {
//            assertEquals(String.format("%d", scores.size()), "2");
//            success("scores.size() == 2");
//            return;
//        }
//        catch (AssertionError a) {
//            fail("scores.size() != 2");
//        }
//    }
//
//    @Test
//    public void insert(){
//        int num = (int) Math.floor(Math.random()*1000);
//        boolean insert = db.uploadScore("junit-com.kroy.test", num);
//
//        try{
//            assertTrue(insert);
//            success("Score successfully inserted to DB");
//            return;
//        }catch (AssertionError a){
//            fail(String.format("Failed to insert junit-com.kroy.test and score %d", num));
//        }
//    }
//}
//