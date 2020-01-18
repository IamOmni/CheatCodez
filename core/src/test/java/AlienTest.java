import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.classes.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import com.kroy.game.Constants;
import com.kroy.game.kroyGame;

public class AlienTest {
    AssetManager manager;
    Landmark base;
    @Before
    public void loadAssets(){
        manager = new AssetManager();
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(Texture.class, new TextureLoader(resolver));
        manager.load("Firetruck_sprite_static-1.png"	, Texture.class);
        manager.load("background.png"					, Texture.class);
        manager.load("button.png"						, Texture.class);
        manager.load("alien.png"						, Texture.class);
        manager.load("alienbullet.png", Texture.class);
        manager.load("AlienBase.png", Texture.class);
        manager.finishLoading();
        manager.update();

        //Texture alienbase = new Texture("AlienBase.png");
        //Game game = new Game();
        //base = new Landmark(5, 5, 50, game.manager.get(alienbase), 0, 1, game.world);

    }


    private void success(String description){ System.out.println("Success - " + description); }
    private void fail(String description) {

        System.out.println("Failed - " +description);
        Assert.fail(description);
    }
//
//    @Test
//    public void test_alien_shoot_once(){
//        Alien alien = new Alien(base, "above", manager);
//        alien.setFiredelay(-1f);
//        alien.shoot();
//        ArrayList<Projectile> projectile = alien.getProjectiles();
//        try {
//            assertEquals(projectile.size(), 1);
//            success("alien has shot a projectile");
//            return;
//        }
//        catch (AssertionError a) {
//            fail("alien has not shot a projectile");
//            return;
//
//        }
//    }
//
//    @Test
//    public void test_alien_shoot_fivefold(){
//        Alien alien = new Alien(base, "above", manager);
//        for (int i = 0; i < 5; i++) {
//            alien.setFiredelay(-1f);
//            alien.shoot();
//        }
//        ArrayList<Projectile> projectile = alien.getProjectiles();
//        try {
//            assertEquals(projectile.size(), 5);
//            success("alien has shot 5 projectiles");
//        }
//        catch (AssertionError a) {
//            fail("alien has not shot 5 projectiles");
//        }
//    }
//
//    // This works if the second indented block in shoot() is 'else if' rather than 'if'
//    @Test
//    public void test_alien_shoot_sixfold(){
//        Alien alien = new Alien(base, "above", manager);
//        for (int i = 0; i < 6; i++) {
//            alien.setFiredelay(-1f);
//            alien.shoot();
//        }
//        ArrayList<Projectile> projectile = alien.getProjectiles();
//        try {
//            assertEquals(projectile.size(), 5);
//            success("only 5 projectiles exist");
//        }
//        catch (AssertionError a) {
//            fail("too many projectiles exist");
//        }
//    }

//    @Test
//    public void test_alien_defend_xspeed(){
//        Alien alien = new Alien(base, "above", manager);
//        alien.setAttack(false);
//        ArrayList<Player> f = new ArrayList<>();
//        Player p = new Player();
//        f.add(p);
//        //alien.update(f);
//
//        try {
//            assertEquals(alien.getxSpeed(), 0.7, 0); // The delta sets the tolerance between the floats
//            success("xspeed is 0.7");
//        }
//        catch (AssertionError a) {
//            fail("xspeed is wrong value");
//        }
//    }
//
//    @Test
//    public void test_alien_defend_yspeed(){
//        Alien alien = new Alien(base, "above",manager);
//        alien.setAttack(false);
//        ArrayList<Player> f = new ArrayList<>();
//        Player p = new Player();
//        f.add(p);
//        //alien.update(f);
//
//        try {
//            assertEquals(alien.getySpeed(), 0.7, 0); // The delta sets the tolerance between the floats
//            success("yspeed is 0.7");
//        }
//        catch (AssertionError a) {
//            fail("yspeed is wrong value");
//        }
//    }
//
//    @Test
//    public void test_alien_attack_xspeed(){
//        Alien alien = new Alien(base, "above", manager);
//        alien.setAttack(true);
//        ArrayList<Player> f = new ArrayList<>();
//        Player p = new Player();
//        f.add(p);
//        //alien.update(f);
//
//        try {
//            assertEquals(alien.getxSpeed(), 0.5, 0); // The delta sets the tolerance between the floats
//            success("xspeed is 0.5");
//        }
//        catch (AssertionError a) {
//            fail("xspeed is wrong value");
//        }
//    }
//
//    @Test
//    public void test_alien_attack_yspeed(){
//        Alien alien = new Alien(base, "above", manager);
//        alien.setAttack(true);
//        ArrayList<Player> f = new ArrayList<>();
//        Player p = new Player();
//        f.add(p);
//        //alien.update(f);
//
//        try {
//            assertEquals(alien.getxSpeed(), 0.5, 0); // The delta sets the tolerance between the floats
//            success("yspeed is 0.7");
//        }
//        catch (AssertionError a) {
//            fail("yspeed is wrong value");
//        }
//    }

//    // This probably needs work
//    @Test
//    public void test_alien_overlap_collision(){
//        Alien alien1 = new Alien(base, "above",manager);
//        Alien alien2 = new Alien(base, "above", manager);
//        boolean overlap = false;
//        if (alien1.getX() == alien2.getX() && alien1.getY() == alien2.getY()){
//            overlap = true;
//        }
//
//        try {
//            assertFalse(overlap);
//            success("no alien collision");
//        }
//        catch (AssertionError a) {
//            fail("aliens have collided");
//        }
//    }
}
