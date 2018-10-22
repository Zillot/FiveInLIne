package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional;

import java.util.Random;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.DelayController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;

public class TextForReadFloated extends FloatMessage {
    private static Random rand = new Random();

    private DelayController Delay;
    private DelayController DelayApper;
    private Vector2 toFallPos;

    private Boolean Translate;

    public TextForReadFloated(String text, Vector2 position, Color fontColor, String fontName, float fontSize, float delaytoAppear, float delay, Boolean translate) {
        super(text, position, fontColor, fontName, fontSize, translate);
        Translate = translate;
        toFallPos = position;

        DelayApper = new DelayController();
        Delay = new DelayController();

        EmergencyStopAllEffects();
        SetPosition(new Vector2(position.X, 1100));

        Mass = 6;

        Delay.Start(delay);
        DelayApper.Start(delaytoAppear);

        SetScale(new Vector2(1));
        SetAlpha(1);
    }

    @Override
    public void UnitUpdate(float timeDelta) {
        DelayApper.Update(timeDelta);

        if (DelayApper.Value == 0) {
            DelayApper.Value = -1;
            EnableGravity(new Vector2(0, -1));
        }

        if (Position.Y < toFallPos.Y) {
            DisableGravity();
            TranslatePosition(new Vector2(Position.X, -300), 60);

            Delay.Update(timeDelta);

            if (Delay.Value == 0) {
                TranslateAlpha(0, 2.0f);
            }
        }
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        spriteBatch.DrawText(Text, Position, FontColor, Opacity, FontName, FontSize * Scale.X, TextAlign.CenterXY, Rotation, layer, Translate);
    }
}
