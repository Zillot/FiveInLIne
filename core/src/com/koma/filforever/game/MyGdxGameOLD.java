package com.koma.filforever.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.TimeUtils;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;
import com.koma.filforever.RootFolder.Data.DataControll;

public class MyGdxGameOLD extends ApplicationAdapter implements ApplicationListener, InputProcessor {
    public static ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;
    private BitmapFont temp;
    private Boolean NeedToUpdate;
    private long UpdatesCount;

    private float timeDelta;
    private SpriteBatch batch;
    public MainGameClass game;

    public static Delegate GetIntenetConnectionSatus;

    public static Delegate TrakerDelegate;

    public MyGdxGameOLD() {
        game = new MainGameClass();
    }

    @Override
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        UpdatesCount = 0;
        timeDelta = 0;

        game.Initialize();
        game.LoadContent();
        NeedToUpdate = false;

        shapeRenderer.setProjectionMatrix(camera.combined);

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(this);

        Gdx.input.setInputProcessor(im);

        temp = new BitmapFont();


        lastFrameTime = TimeUtils.nanoTime();
    }

    public Boolean BackButton() {
        return game.BackPress();
    }

    public void setTouchState(Boolean state) {
        TouchService.I.TouchDown = state;
    }

    public void setTouchPosition(float X, float Y) {
        Vector2 realpos = new Vector2(X, Y).sub(SpriteAdapter.SelfPointer.ScaleOffset);
        Vector2 scaledPos = realpos.div(SpriteAdapter.SelfPointer.ScaleAmount);

        TouchService.I.Update(0, scaledPos);

        /*TouchService.I.Update(0,
                new Vector2(X, Y).div(SpriteAdapter.SelfPointer.ScaleAmount)
                        .sub(SpriteAdapter.SelfPointer.ScaleOffset.div(SpriteAdapter.SelfPointer.ScaleAmount)));*/
    }

    long lastFrameTime;

    @Override
    public void render() {
        Long DrawStart = System.currentTimeMillis();

        long frameTime = TimeUtils.nanoTime();
        float deltaTime = (frameTime - lastFrameTime) / 1000000000.0f;
        lastFrameTime = frameTime;

        NeedToUpdate = game.Update(deltaTime);

        if (NeedToUpdate) {
            UpdatesCount++;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.enableBlending();

        batch.begin();
        game.Draw(batch);

        com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color fpsColor = com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color.Green;
        int val = Gdx.graphics.getFramesPerSecond();
        if (val < 30) {
            fpsColor = com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color.Red;
        }

        //PRODUCTION: убрать все нафиг
        /*SpriteAdapter.SelfPointer.DrawText("FPS:" + String.valueOf(val), new Vector2(100, 780), fpsColor, 1, "Arial12", 1, TextAlign.CenterXY, 0, 30, false);
        SpriteAdapter.SelfPointer.DrawText("RenderCallsInFrame:" + (batch.renderCalls + 1), new Vector2(240, 760), FakeEye_2DGameEngine_Android.Engine.Color.Black, 1, "Arial12", 1, TextAlign.CenterXY, 0, 30, false);
        SpriteAdapter.SelfPointer.DrawText("TotalRenderCalls:" + batch.totalRenderCalls, new Vector2(240, 780), FakeEye_2DGameEngine_Android.Engine.Color.Black, 1, "Arial12", 1, TextAlign.CenterXY, 0, 30, false);
        SpriteAdapter.SelfPointer.DrawText("UpdatesCount:" + String.valueOf(UpdatesCount), new Vector2(380, 760), FakeEye_2DGameEngine_Android.Engine.Color.Black, 1, "Arial12", 1, TextAlign.CenterXY, 0, 30, false);
        SpriteAdapter.SelfPointer.DrawText("SpritesInBatch:" + batch.maxSpritesInBatch, new Vector2(380, 780), FakeEye_2DGameEngine_Android.Engine.Color.Black, 1, "Arial12", 1, TextAlign.CenterXY, 0, 30, false);
        */

        batch.end();

        Long DrawEnd = System.currentTimeMillis();
        timeDelta = (DrawEnd - DrawStart) / 1000.0f;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer < 5) {
            setTouchState(true);
            setTouchPosition(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer < 5) {
            setTouchState(false);
            setTouchPosition(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer < 5) {
            setTouchState(true);
            setTouchPosition(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    public static boolean IsNetworkAvailable() {
        if (GetIntenetConnectionSatus != null) {
            return GetIntenetConnectionSatus.BoolDelegate();
        } else {
            return false;
        }
    }
}
