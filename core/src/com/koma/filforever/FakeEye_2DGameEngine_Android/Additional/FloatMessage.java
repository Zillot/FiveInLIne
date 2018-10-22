package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;

public class FloatMessage extends NodeItem {
    public String Text;
    public Color FontColor;
    public String FontName;
    public float FontSize;

    public Boolean UseTranslate;

    public FloatMessage(String text, Vector2 position, Color fontColor, String fontName, float fontSize, Boolean useTranslate) {
        super(position, new Vector2(0), new Vector2(1, 1), 0, 1);

        UseTranslate = useTranslate;

        FontColor = fontColor;
        FontName = fontName;
        FontSize = fontSize;
        Text = text;

        SetAlpha(0.1f);
        SetScale(new Vector2(0));

        TranslateScale(new Vector2(1), 5.0f);
        TranslateAlpha(1.0f, 5.0f);
    }

    public Boolean IsDead() {
        return Opacity == 0;
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);
        UnitUpdate(timeDelta);

        return toRet;
    }

    public void UnitUpdate(float timeDelta) {
        if (Opacity == 1) {
            TranslateAlpha(0.0f, 1.0f);
            TranslatePosition(Position.add(0, -200), 50);
            TranslateScale(new Vector2(0), 1.0f);
        }
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        spriteBatch.DrawText(Text, Position, FontColor, Opacity, FontName, FontSize * Scale.X, TextAlign.CenterXY, Rotation, layer, UseTranslate);
    }
}
