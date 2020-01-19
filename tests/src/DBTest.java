
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import com.kroy.modules.DB;
import java.util.ArrayList;


public class DBTest {

    private DB db = new DB();

    private void success(String description){ System.out.println("Success - " + description); }
    private void fail(String description) {
        System.out.println("Failed - " +description);
        Assert.fail(description);
    }


    @Test
    public void local_fetch_amount_2(){
        ArrayList scores = db.local_getLeaderboard("2");
        try {
            assertEquals(String.format("%d", scores.size()), "2");
            success("scores.size() == 2 fetched from local db");
            return;
        }
        catch (AssertionError a) {
            fail("scores.size() != 2 fetching from local db");
        }
    }

    @Test
    public void local_insert(){
        int num = (int) Math.floor(Math.random()*1000);
        boolean insert = db.local_uploadScore("junit-test", num);
        try{
            assertTrue(insert);
            success("Score successfully inserted to local DB");
            return;
        }catch (AssertionError a){
            fail(String.format("Failed to insert junit-test and score %d", num));
        }
    }

//    @Test
//    public void server_insert_wrong_type(){
//        //int num = (int) Math.floor(Math.random()*1000);
//        String wrong = "this shouldn't be inserted";
//        boolean insert = db.uploadScore("junit-com.kroy.test", wrong);
//
//        try{
//            assertFalse(insert);
//            success("Success - bad score was not inserted to DB");
//            return;
//        }catch (AssertionError a){
//            fail("Failure - bad score inserted to DB");
//        }
//    }

}

