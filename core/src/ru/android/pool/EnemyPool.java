package ru.android.pool;

import com.badlogic.gdx.audio.Sound;

import ru.android.base.Sprite;
import ru.android.base.SpritesPool;
import ru.android.math.Rect;
import ru.android.sprite.Enemy;
import ru.android.sprite.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private MainShip mainShip;
    private Rect worldBounds;
    private Sound shootSound;

    public EnemyPool(BulletPool bulletPool,ExplosionPool explosionPool, MainShip mainShip, Rect worldBounds,Sound shootSound) {
        this.bulletPool = bulletPool;
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.explosionPool = explosionPool;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool,explosionPool,mainShip,worldBounds,shootSound);
    }
}
