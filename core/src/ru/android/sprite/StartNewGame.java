package ru.android.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.android.base.ScaledButton;
import ru.android.base.Sprite;
import ru.android.screen.GameScreen;

public class StartNewGame extends ScaledButton {

    private GameScreen gameScreen;

    public StartNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
        setHeightProportion(0.05f);
        setTop(-0.012f);
    }

    @Override
    protected void actionPerformed() {
        gameScreen.startNewGame();
    }
}
