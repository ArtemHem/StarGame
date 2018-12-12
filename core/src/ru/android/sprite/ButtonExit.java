package ru.android.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.android.base.ActionListener;
import ru.android.base.ScaledButton;
import ru.android.math.Rect;

public class ButtonExit extends ScaledButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect woldBounds) {
        super.resize(woldBounds);
        setBottom(woldBounds.getBottom());
        setRight(woldBounds.getRight());
    }

    @Override
    public void actionPerformed() {
        Gdx.app.exit();
    }

}
