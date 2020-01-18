//import com.kroy.classes.Projectile;
//import com.kroy.game.kroyGame;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import java.util.ArrayList;
//import com.kroy.classes.*;
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
//    @Before
//    public void a(){
//        kroyGame game = new kroyGame();
//    }
//
//    @Test
//    public void test_firetruck_basic_collision(){
//        //ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
//        Projectile p = new Projectile(1f, 1f, kroyGame.manager.get("Firetruck.png"), 100);
//
//        try {
//            assertTrue(p.height>2);
//            success("Firetruck has collided with p");
//        }
//        catch (AssertionError a) {
//            fail("Collision between Firetruck and p not successful");
//        }
//    }
////
////    @Test
////    public void test_firetruck_moving_collision(){
////        ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
////        Projectile p = new Projectile(1.5f, 50, kroyGame.manager.get("alienbullet.png"), 100);
////        Firetruck Firetruck = new Firetruck();
////        Firetruck.setUp(true);
////
////        try {
////            assertTrue(Firetruck.hasCollided(p));
////            success("Firetruck has collided with p");
////        }
////        catch (AssertionError a) {
////            fail("Collision between Firetruck and p not successful");
////        }
////    }
////
////    @Test
////    public void test_firetruck_move_right(){
////        Firetruck Firetruck = new Firetruck();
////        int startPos = Firetruck.getX();
////        Firetruck.setRight(true);
////        Firetruck.setxSpeed(100);
////        for (int i = 0; i<10; i++){
////            Firetruck.update(100);
////        }
////        int newPos = Firetruck.getX();
////        boolean movedRight = false;
////        if (newPos > startPos){
////            movedRight = true;
////        }
////
////        try {
////            assertTrue(movedRight);
////            success("firetruck has moved right");
////        }
////        catch (AssertionError a) {
////            fail("firetruck has not moved right");
////        }
////    }
////
////    @Test
////    public void test_firetruck_move_left(){
////        Firetruck Firetruck = new Firetruck();
////        Firetruck.setDegree(180);
////        int startPos = Firetruck.getX();
////        Firetruck.setLeft(true);
////        Firetruck.setxSpeed(-100);
////        for (int i = 0; i<10; i++){
////            Firetruck.update(100);
////        }
////        int newPos = Firetruck.getX();
////        boolean movedLeft = false;
////        if (newPos < startPos){
////            movedLeft = true;
////        }
////
////        try {
////            assertTrue(movedLeft);
////            success("firetruck has moved left");
////        }
////        catch (AssertionError a) {
////            fail("firetruck has not moved left");
////        }
////    }
////
////    @Test
////    public void test_firetruck_move_up(){
////        Firetruck Firetruck = new Firetruck();
////        Firetruck.setDegree(90);
////        int startPos = Firetruck.getY();
////        Firetruck.setUp(true);
////        Firetruck.setySpeed(100);
////        for (int i = 0; i<10; i++){
////            Firetruck.update(100);
////        }
////        Firetruck.update(100);
////        int newPos = Firetruck.getY();
////        boolean movedUp = false;
////        if (newPos > startPos){
////            movedUp = true;
////        }
////
////        try {
////            assertTrue(movedUp);
////            success("firetruck has moved up");
////        }
////        catch (AssertionError a) {
////            fail("firetruck has not moved up");
////        }
////    }
////
////    @Test
////    public void test_firetruck_move_down(){
////        Firetruck firetruck = new Firetruck();
////        //firetruck.setDegree(270);
////        int startPos = Firetruck.getY();
////        firetruck.setDown(true);
////        for (int i = 0; i<10; i++){
////            Firetruck.update(100);
////        }
////        int newPos = Firetruck.getY();
////        boolean movedDown = false;
////        if (newPos < startPos){
////            movedDown = true;
////        }
////
////        try {
////            assertTrue(movedDown);
////            success("firetruck has moved down");
////        }
////        catch (AssertionError a) {
////            fail("firetruck has not moved down");
////        }
////    }
//
//
//}
