package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;


import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;


public class UI_CircleButtonControll extends UI_IMenuControll {
    public FloatController HoverController;

    public Color ButtonColor;

    public Vector2 Position;
    public float Size;

    public Delegate IcoDrawEvent;

    public UI_CircleButtonControll(Vector2 position, Color buttonColor, float size, int layer) {
        super(position, new Vector2(size * 2));

        Layer = layer;

        HoverController = new FloatController(5.0f);

        ControllSize = new Vector2(size * 2);
        ControllPosition = position.sub(ControllSize.div(2));

        ButtonColor = buttonColor;

        Position = position;
        Size = size;
    }

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInCircle(SomePosition, Position, Size);
    }

    @Override
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
        if (InUnderControll(ToouchPosition)) {
            SpriteAdapter.SelfPointer.PlaySound("Tap2", 1);
            ControllTouchEventCall(ToouchPosition, TouchEventName, null);
            //HoverController.Value = 1;
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = HoverController.Update(timeDelta);

        if (InUnderControll(TouchService.I.TouchPosition)) {
            //HoverController.GoUp();
        } else {
            //HoverController.GoDown();
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        float TSize = Size * (1 + HoverController.GetRegularValue() * 0.1f);

        spriteBatch.FillCircle(Position.add(Offset), TSize, 50, Color.Black, 1, layer);
        spriteBatch.FillCircle(Position.add(Offset), TSize - 2, 50, ButtonColor, 1, layer + 1);

        DrawIco(spriteBatch, Position.add(Offset), new Vector2(TSize), layer);
    }

    public void DrawIco(SpriteAdapter spriteBatch, Vector2 localPosition, Vector2 TSize, int layer) {
        if (IcoDrawEvent != null) {
            IcoDrawEvent.IcoDraw(spriteBatch, localPosition, TSize.mul(2.0f), layer);
        }
    }

    public void ClearEvent() {
        IcoDrawEvent = null;
    }
}
