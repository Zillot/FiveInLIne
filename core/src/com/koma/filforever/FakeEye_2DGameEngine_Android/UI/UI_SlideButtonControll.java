package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;


import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;

public class UI_SlideButtonControll extends UI_IMenuControll {
    private static float LeftOffset = -30;

    public String Text;

    public FloatController HoverController;

    public Color ButtonColor;
    public Color TextColor;
    public String FontName;

    public Vector2 Position;
    public Vector2 Size;

    public Delegate IcoDrawEvent;

    public UI_SlideButtonControll(Vector2 position, Color buttonColor, Color textColor, String text, Vector2 size, String fontName) {
        super(position, size);

        HoverController = new FloatController(5.0f);

        ControllPosition = new Vector2(0, position.Y - size.Y / 2);
        ControllSize = size;

        ButtonColor = buttonColor;
        TextColor = textColor;

        Text = text;
        FontName = fontName;

        Position = position;
        Position.X = LeftOffset;
        Size = size;
        Size.X -= LeftOffset;
    }

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, Position.sub(0, Size.Y / 2), Size.add(new Vector2(0, 0)));
    }

    @Override
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
        if (InUnderControll(ToouchPosition)) {
            ControllTouchEventCall(ToouchPosition, TouchEventName, null);
            SpriteAdapter.SelfPointer.PlaySound("Tap2", 1);
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = HoverController.Update(timeDelta);

        if (InUnderControll(TouchService.I.TouchPosition)) {
            HoverController.GoUp();
        } else {
            HoverController.GoDown();
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        Vector2 HoverOffset = new Vector2(25, 0).mul(HoverController.GetRegularValue());

        spriteBatch.FillRectangle(Position.sub(new Vector2(0, Size.Y / 2)).add(HoverOffset).add(Offset), Size, Color.Black, 1, 0, layer);
        spriteBatch.FillRectangle(Position.sub(new Vector2(-2, Size.Y / 2 - 2)).add(HoverOffset).add(Offset), Size.sub(new Vector2(4)), ButtonColor, 1, 0, layer + 1);

        DrawIco(spriteBatch, Position.add(-LeftOffset, 0).add(HoverOffset).add(Offset), layer);
        DrawText(spriteBatch, Position.add((Size.X - LeftOffset) / 2, 0).add(HoverOffset).add(Offset), layer);
    }

    public void DrawIco(SpriteAdapter spriteBatch, Vector2 IcoPosition, int layer) {
        if (IcoDrawEvent != null)
            IcoDrawEvent.IcoDraw(spriteBatch, IcoPosition, Size, layer);
    }

    public void DrawText(SpriteAdapter spriteBatch, Vector2 TextPosition, int layer) {
        spriteBatch.DrawText(Text, TextPosition, TextColor, 1, "", 1, TextAlign.CenterXY, 0, layer + 2);
    }

    public void ClearEvent() {
        IcoDrawEvent = null;
    }
}
