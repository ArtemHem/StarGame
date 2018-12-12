package ru.android.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.android.base.ScaledButton;
import ru.android.math.Rect;
import ru.android.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private Game game;

    public ButtonNewGame(TextureAtlas atlas,Game game) {
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.15f);
        this.game = game;
    }

    @Override
    public void resize(Rect woldBounds) {
        super.resize(woldBounds);
        setBottom(woldBounds.getBottom());
        setLeft(woldBounds.getLeft());
    }

    @Override
    protected void actionPerformed() {
        game.setScreen(new GameScreen(game));
    }
}
