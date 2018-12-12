package ru.android.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.android.base.Sprite;
import ru.android.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect woldBounds) {
        setHeightProportion(woldBounds.getHeight());
        pos.set(woldBounds.pos);
    }
}
