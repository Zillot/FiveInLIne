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

public class UI_TabButtonControll extends UI_IMenuControll {
    public String Text;

    public FloatController HoverController;

    public Color ButtonColor;
    public Color TextColor;
    public String FontName;

    public Vector2 Position;
    public Vector2 Size;

    public Boolean Selected;
    public Boolean PreSelected;

    public Delegate IcoDrawEvent;

    public UI_TabButtonControll(Vector2 position, Color buttonColor, Color textColor, String text, Vector2 size, String fontName) {
        super(position, size);

        Selected = false;
        PreSelected = false;

        HoverController = new FloatController(1.5f);

        ControllPosition = position;
        ControllSize = size;

        ButtonColor = buttonColor;
        TextColor = textColor;

        Text = text;
        FontName = fontName;

        Position = position;
        Size = size;
    }

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, ControllPosition, Size);
    }

    @Override
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
        if (InUnderControll(ToouchPosition)) {
            PreSelected = true;
            SpriteAdapter.SelfPointer.PlaySound("Tap2", 1);
            ControllTouchEventCall(ToouchPosition, TouchEventName, null);
        } else {
            PreSelected = false;
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        //toRet = HoverController.Update(timeDelta);

        if (InUnderControll(TouchService.I.TouchPosition)) {
            HoverController.GoUp();
        } else {
            HoverController.GoDown();
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        Vector2 TSize = Size.mul((1 + HoverController.GetRegularValue() * 0.2f));
        ControllPosition = Position.add(Offset);

        if (Selected) {
            spriteBatch.FillRectangle(Position.add(Offset), TSize, Color.Black, 1, 0, layer);
            spriteBatch.FillRectangle(Position.add(new Vector2(4)).add(Offset), TSize.sub(new Vector2(8)), ButtonColor, 1, 0, layer + 1);
        } else {
            spriteBatch.FillRectangle(Position.add(Offset), TSize, ButtonColor, 1, 0, layer + 1);
        }

        DrawIco(spriteBatch, Position.add(Offset), TSize, layer);
        DrawText(spriteBatch, Position.add(Offset), 1 + (HoverController.GetRegularValue() * 0.2f), layer);
    }

    public void DrawIco(SpriteAdapter spriteBatch, Vector2 localPosition, Vector2 TSize, int layer) {
        if (IcoDrawEvent != null) {
            IcoDrawEvent.IcoDraw(spriteBatch, localPosition, TSize, layer);
        }
    }

    public void DrawText(SpriteAdapter spriteBatch, Vector2 localPosition, float TSize, int layer) {
        spriteBatch.DrawText(Text, localPosition, TextColor, 1, FontName, TSize, TextAlign.CenterXY, 0, layer + 2);
    }

    public void ClearEvent() {
        IcoDrawEvent = null;
    }
}
