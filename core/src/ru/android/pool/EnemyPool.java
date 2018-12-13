package ru.android.pool;

import ru.android.base.Sprite;
import ru.android.base.SpritesPool;
import ru.android.math.Rect;
import ru.android.sprite.Enemy;
import ru.android.sprite.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private MainShip mainShip;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, MainShip mainShip, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool,mainShip,worldBounds);
    }
}
