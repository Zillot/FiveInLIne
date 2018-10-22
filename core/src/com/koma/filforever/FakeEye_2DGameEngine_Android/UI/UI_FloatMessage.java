package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;


import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.DelayController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;


public class UI_FloatMessage extends UI_IMenuControll {
    public String Header;
    public String Text;

    public FloatController HoverController;

    public Color BackColor;
    public Color TextColor;

    public DelayController timeLeft;

    public UI_FloatMessage(Color backColor, Color textColor, String header, String text, Vector2 size, int layer) {
        super(Vector2.Zero, size);

        timeLeft = new DelayController();
        timeLeft.Value = 0;

        HoverController = new FloatController(1.3f);
        HoverController.Value = 0;

        ControllPosition = Vector2.Zero;
        ControllSize = size;

        BackColor = backColor;
        TextColor = textColor;

        Header = header;
        Text = text;
        Layer = layer;
    }

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, ControllPosition, ControllSize);
    }

    @Override
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
    }

    public void Show() {
        timeLeft.Value = 5000;
        HoverController.GoUp();
    }

    public void Show(float time) {
        timeLeft.Value = time;
        HoverController.GoUp();
    }

    public void Hide() {
        timeLeft.Value = 0;
        HoverController.GoDown();
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | timeLeft.Update(timeDelta);
        toRet = toRet | HoverController.Update(timeDelta);

        if (timeLeft.Value == 0 && HoverController.Value == 1) {
            Hide();
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        Vector2 Tpos = new Vector2(0, -ControllSize.Y * HoverController.GetInvertedValue());
        spriteBatch.FillRectangle(Tpos, ControllSize, BackColor, 1, 0, layer);

        spriteBatch.DrawText(Header, Tpos.add(new Vector2(20, 20)), TextColor, 1, "BArial20", 1, TextAlign.LeftXCenterY, 0, layer + 1);
        spriteBatch.DrawText(Text, Tpos.add(new Vector2(20, 50)), TextColor, 1, "Arial20", 0.78f, TextAlign.LeftXCenterY, 0, layer + 2);
    }
}
