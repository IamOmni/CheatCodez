import com.kroy.classes.HighScoreBar;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import java.util.ArrayList;

public class HighScoreBarTest {

    private void success(String description){ System.out.println("Success - " + description); }
    private void fail(String description) {

        System.out.println("Failed - " +description);
        Assert.fail(description);
    }

    @Test
    public void test_highscore_score(){
        ArrayList<String> data = new ArrayList<String>();
        data.add("Bob");
        data.add("200");
        HighScoreBar hbar = new HighScoreBar(data);

        try {
            assertEquals(hbar.getScore(), "200");
            success("score recorded successfully");
            return;
        }
        catch (AssertionError a) {
            fail("score not recorded successfully");
            return;

        }
    }

    @Test
    public void test_highscore_short_name() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("Bob");
        data.add("200");
        HighScoreBar hbar = new HighScoreBar(data);

        try {
            assertEquals(hbar.name, "Bob");
            success("short name recorded successfully");
            return;
        } catch (AssertionError a) {
            fail("short name not recorded successfully");
            return;

        }
    }

    @Test
    public void test_highscore_long_name() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("ThisNameWillBeTooLong");
        data.add("200");
        HighScoreBar hbar = new HighScoreBar(data);

        try {
            assertEquals(hbar.name, "ThisNameWi");
            success("long name recorded successfully");
            return;
        } catch (AssertionError a) {
            fail("long name not recorded successfully");
            return;

        }
    }



}