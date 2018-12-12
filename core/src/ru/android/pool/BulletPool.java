package ru.android.pool;

import ru.android.base.SpritesPool;
import ru.android.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
