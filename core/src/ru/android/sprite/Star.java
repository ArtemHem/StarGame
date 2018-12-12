package ru.android.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.android.base.Sprite;
import ru.android.math.Rect;
import ru.android.math.Rnd;

public class Star extends Sprite {

    private Rect worldBounds;

    private Vector2 v = new Vector2();

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(0.01f);
        v.set(Rnd.nextFloat(-0.005f, -0.005f), Rnd.nextFloat(-0.5f, -0.1f));
    }

    @Override
    public void resize(Rect woldBounds) {
        super.resize(woldBounds);
        this.worldBounds = woldBounds;
        float posX = Rnd.nextFloat(woldBounds.getLeft(), woldBounds.getRight());
        float posY = Rnd.nextFloat(woldBounds.getBottom(), woldBounds.getTop());
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
        if(getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if(getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if(getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if(getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
