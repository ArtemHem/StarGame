package ru.android.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.android.math.Rect;
import ru.android.pool.BulletPool;
import ru.android.sprite.Bullet;

public class Ship extends Sprite {

    protected Vector2 v = new Vector2();

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV = new Vector2();
    protected float bulletHeight;
    protected int bulletDamage;
    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Rect worldBounds;

    protected Sound shootSound;

    public Ship(TextureRegion region, int rows, int cols, int frames,Sound shootSound) {
        super(region, rows, cols, frames);
        this.shootSound = shootSound;
    }

    public Ship() {

    }

    @Override
    public void resize(Rect woldBounds) {
        super.resize(woldBounds);
        this.worldBounds = woldBounds;
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,bulletRegion,pos, bulletV,bulletHeight,worldBounds,bulletDamage );
        shootSound.play();
    }

}
