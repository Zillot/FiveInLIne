package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;

public class BlankScreen {
    public FloatController floatController;

    public Boolean HidedEventCalled;
    public Boolean ShowedEventCalled;

    private Color BlancColor;

    public Delegate ShowedEvent;

    public Delegate HidedEvent;

    public BlankScreen() {
        BlancColor = Color.Black;

        floatController = new FloatController(1.0f);

        HidedEventCalled = true;
        ShowedEventCalled = true;
    }

    public void SetBlankColor(Color color) {
        BlancColor = color;
    }

    public void Start(float speed) {
        HidedEventCalled = true;
        ShowedEventCalled = false;

        floatController = new FloatController(speed);
        floatController.GoUp();

        HidedEvent = null;
        ShowedEvent = null;
    }

    public void SetAsFinal() {
        HidedEventCalled = true;
        ShowedEventCalled = false;

        floatController.Stop();

        if (ShowedEvent != null) {
            ShowedEvent.VoidDelegate();
        }
        if (HidedEvent != null) {
            HidedEvent.VoidDelegate();
        }

        floatController.Finished = true;

        HidedEvent = null;
        ShowedEvent = null;
    }

    public void Stop() {
        floatController.Stop();
    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = floatController.Update(timeDelta);

        if (floatController.GetRegularValue() == 1) {
            if (!ShowedEventCalled) {
                ShowedEventCalled = true;
                if (ShowedEvent != null) {
                    ShowedEvent.VoidDelegate();
                }
            }

            HidedEventCalled = false;
            floatController.GoDown();
        }

        if (floatController.GetRegularValue() == 0) {
            if (!HidedEventCalled) {
                HidedEventCalled = true;
                if (HidedEvent != null) {
                    HidedEvent.VoidDelegate();
                }

                floatController.Finished = true;
            }

            ShowedEventCalled = false;
        }

        return toRet;
    }

    public void Draw(SpriteAdapter spriteBatch) {
        Draw(spriteBatch, Vector2.Zero);
    }

    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset) {
        spriteBatch.FillRectangle(new Vector2(0), new Vector2(480, 800), BlancColor, floatController.GetRegularValue(), 0, 99);
    }
}
