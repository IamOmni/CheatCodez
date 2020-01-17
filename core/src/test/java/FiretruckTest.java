//import com.kroy.classes.Projectile;
//import org.junit.Assert;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import java.util.ArrayList;
//
//
//public class FiretruckTest {
//
//    private void success(String description){ System.out.println("Success - " + description); }
//    private void fail(String description) {
//
//        System.out.println("Failed - " +description);
//        Assert.fail(description);
//    }
//
//    @Test
//    public void test_firetruck_basic_collision(){
//        //ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
//        Projectile p = new Projectile(1.0f, 100, 100, 100);
//        Player player = new Player();
//
//        try {
//            assertTrue(player.hasCollided(p));
//            success("player has collided with p");
//        }
//        catch (AssertionError a) {
//            fail("Collision between player and p not successful");
//        }
//    }
//
//    @Test
//    public void test_firetruck_moving_collision(){
//        ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
//        Projectile p = new Projectile(1.5f, 50, 50, 100);
//        Player player = new Player();
//        player.setUp(true);
//
//        try {
//            assertTrue(player.hasCollided(p));
//            success("player has collided with p");
//        }
//        catch (AssertionError a) {
//            fail("Collision between player and p not successful");
//        }
//    }
//
//    @Test
//    public void test_firetruck_move_right(){
//        Player player = new Player();
//        int startPos = player.getX();
//        player.setRight(true);
//        player.setxSpeed(100);
//        for (int i = 0; i<10; i++){
//            player.update(100);
//        }
//        int newPos = player.getX();
//        boolean movedRight = false;
//        if (newPos > startPos){
//            movedRight = true;
//        }
//
//        try {
//            assertTrue(movedRight);
//            success("firetruck has moved right");
//        }
//        catch (AssertionError a) {
//            fail("firetruck has not moved right");
//        }
//    }
//
//    @Test
//    public void test_firetruck_move_left(){
//        Player player = new Player();
//        player.setDegree(180);
//        int startPos = player.getX();
//        player.setLeft(true);
//        player.setxSpeed(-100);
//        for (int i = 0; i<10; i++){
//            player.update(100);
//        }
//        int newPos = player.getX();
//        boolean movedLeft = false;
//        if (newPos < startPos){
//            movedLeft = true;
//        }
//
//        try {
//            assertTrue(movedLeft);
//            success("firetruck has moved left");
//        }
//        catch (AssertionError a) {
//            fail("firetruck has not moved left");
//        }
//    }
//
//    @Test
//    public void test_firetruck_move_up(){
//        Player player = new Player();
//        player.setDegree(90);
//        int startPos = player.getY();
//        player.setUp(true);
//        player.setySpeed(100);
//        for (int i = 0; i<10; i++){
//            player.update(100);
//        }
//        player.update(100);
//        int newPos = player.getY();
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
//
//    @Test
//    public void test_firetruck_move_down(){
//        Player player = new Player();
//        player.setDegree(270);
//        int startPos = player.getY();
//        player.setDown(true);
//        player.setySpeed(100);
//        for (int i = 0; i<10; i++){
//            player.update(100);
//        }
//        int newPos = player.getY();
//        boolean movedDown = false;
//        if (newPos < startPos){
//            movedDown = true;
//        }
//
//        try {
//            assertTrue(movedDown);
//            success("firetruck has moved down");
//        }
//        catch (AssertionError a) {
//            fail("firetruck has not moved down");
//        }
//    }
//
//
//}
