package ru.android.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.android.base.Base2DScreen;
import ru.android.math.Rect;
import ru.android.pool.BulletPool;
import ru.android.pool.EnemyPool;
import ru.android.pool.ExplosionPool;
import ru.android.sprite.Background;
import ru.android.sprite.Bullet;
import ru.android.sprite.Enemy;
import ru.android.sprite.MainShip;
import ru.android.sprite.MessageGameOver;
import ru.android.sprite.Star;
import ru.android.sprite.StartNewGame;
import ru.android.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen {

    private static final int START_COUNT = 64;

    private Texture bg;

    private TextureAtlas textureAtlas;

    private Background background;

    private Star[] star;

    private MainShip mainShip;

    private BulletPool bulletPool;

    private EnemyPool enemyPool;

    private EnemiesEmitter enemiesEmitter;

    private Music music;

    private Sound mainShipShootSound;
    private Sound enemyShipShootSound;
    private Sound explosionSound;

    private MessageGameOver messageGameOver;
    private StartNewGame startNewGame;

    private enum State {PLAYING, GAME_OVER}

    private State state;

    private ExplosionPool explosionPool;


    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        textureAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        star = new Star[START_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(textureAtlas);
        }
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(textureAtlas,explosionSound);
        mainShipShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        enemyShipShootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        mainShip = new MainShip(textureAtlas, bulletPool,explosionPool,worldBounds,mainShipShootSound);
        enemyPool = new EnemyPool(bulletPool,explosionPool, mainShip,worldBounds,enemyShipShootSound);
        enemiesEmitter = new EnemiesEmitter(worldBounds,enemyPool,textureAtlas);
        messageGameOver = new MessageGameOver(textureAtlas);
        startNewGame = new StartNewGame(textureAtlas,this);
        startNewGame();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        switch (state) {
            case PLAYING:
                if (!mainShip.isDestroyed()) {
                    mainShip.update(delta);
                }
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemiesEmitter.generate(delta);
                break;
            case GAME_OVER:
                break;
        }
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.setDestroyed(true);
                enemy.boom();
                mainShip.damage(mainShip.getHp());
                if (mainShip.isDestroyed()) {
                    state = state.GAME_OVER;
                }
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.setDestroyed(true);
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                bullet.setDestroyed(true);
                mainShip.damage(bullet.getDamage());
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        switch (state) {
            case PLAYING:
                if (!mainShip.isDestroyed()) {
                    mainShip.draw(batch);
                }
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                break;
            case GAME_OVER:
                messageGameOver.draw(batch);
                startNewGame.draw(batch);
                break;
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        textureAtlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        mainShipShootSound.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        switch (state) {
            case PLAYING:
                mainShip.touchDown(touch, pointer);
                break;
            case GAME_OVER:
                startNewGame.touchDown(touch, pointer);
                break;
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        switch (state) {
            case PLAYING:
                mainShip.touchUp(touch, pointer);
                break;
            case GAME_OVER:
                startNewGame.touchUp(touch, pointer);
                break;
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    public void startNewGame() {
        state = State.PLAYING;

        mainShip.setToNewGame();

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }
}
