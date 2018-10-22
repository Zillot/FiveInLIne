package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional;

import java.util.Random;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.DelayController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;

public class RotatedHeadMSG extends FloatMessage {
    private static Random rand = new Random();

    private DelayController Delay;

    public RotatedHeadMSG(String text, Vector2 position, Color fontColor, String fontName, float fontSize, float delay) {
        super(text, position, fontColor, fontName, fontSize, true);

        Delay = new DelayController();
        EmergencyStopAllEffects();

        SetAlpha(0.01f);
        SetRotation((8.4f + (float) rand.nextDouble() - 0.5f) * (rand.nextInt(10) > 5 ? 1 : -1));
        SetScale(new Vector2(0));

        Delay.Start(delay);
    }

    @Override
    public void UnitUpdate(float timeDelta) {
        Delay.Update(timeDelta);

        if (Delay.Value == 0) {
            TranslateAlpha(1, 2.0f);
            TranslateScale(new Vector2(1.0f), 4.0f);
            TranslateRotation(0.6f, 5.2f);
        }

        if (Scale.X == 1.0f) {
            EmergencyStopAllEffects();
            TranslateAlpha(0.0f, 1.0f);
        }
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        spriteBatch.DrawText(Text, Position, FontColor, Opacity, FontName, FontSize * Scale.X, TextAlign.CenterXY, Rotation, layer);
    }
}
