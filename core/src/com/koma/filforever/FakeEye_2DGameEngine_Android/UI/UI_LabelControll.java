package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;

public class UI_LabelControll extends UI_IMenuControll {
    public String Text;

    public FloatController HoverController;

    public Color HightlightTextColor;
    public Color TextColor;
    public String FontName;

    public Vector2 Position;
    public TextAlign Align;

    public UI_LabelControll(Vector2 position, Color textColor, String text, TextAlign align, String fontName) {
        super(Vector2.Zero, new Vector2(10));
        HightlightTextColor = new Color(255, 0, 0);
        HoverController = new FloatController(1.5f);

        Align = align;
        BitmapFont font = SpriteAdapter.SelfPointer.getFont(fontName);
        GlyphLayout glyphLayout = new GlyphLayout();

        glyphLayout.setText(font, text);
        ControllSize = new Vector2(glyphLayout.width, glyphLayout.height);

        switch (align) {
            case CenterXY:
                ControllPosition = position.sub(ControllSize.div(2));
                break;
            case LeftX:
                ControllPosition = position;
                break;
            case LeftXCenterY:
                ControllPosition = new Vector2(0, position.Y - ControllSize.Y / 2);
                break;
            case RightX:
                ControllPosition = new Vector2(position.X, 0);
                break;
            case RightXCenterY:
                ControllPosition = new Vector2(position.X, position.Y - ControllSize.Y / 2);
                break;
        }

        FontName = fontName;
        TextColor = textColor;
        Text = text;

        Position = position;
    }

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, ControllPosition, ControllSize);
    }

    @Override
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
        if (InUnderControll(ToouchPosition)) {
            ControllTouchEventCall(ToouchPosition, TouchEventName, null);
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
        ControllPosition = Position.add(Offset);

        spriteBatch.DrawText(Text, Position.add(Offset), TextColor, HoverController.GetInvertedValue(), FontName, 1, Align, 0, layer);
        spriteBatch.DrawText(Text, Position.add(Offset), HightlightTextColor, HoverController.GetRegularValue(), FontName, 1, Align, 0, layer);
    }
}
