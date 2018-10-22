package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;

import java.util.Random;

public class TouchDebugItem {
    private static Random Rand = new Random();

    public float ItemTimeLifeLeft;
    public float ItemTimeLife;
    public Color RandomColor;
    public Vector2 TouchPosition;
    public Boolean Immortal;
    public String Text;
    public String addText;

    public TouchDebugItem(Vector2 position, float lifeTime, Boolean immortal) {
        Text = "";
        addText = "";
        ItemTimeLifeLeft = lifeTime;
        ItemTimeLife = lifeTime;
        TouchPosition = position;
        Immortal = immortal;

        int R = Rand.nextInt(10) * 25;
        RandomColor = new Color(R, R, R);
    }

    public void Update(float timeDelta) {
        if (!Immortal) {
            ItemTimeLifeLeft -= timeDelta;
        }
    }

    public void Draw(SpriteAdapter spriteBatch) {
        Draw(spriteBatch, Vector2.Zero);
    }

    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset) {
        spriteBatch.DrawText(TouchPosition.Y + "", new Vector2(25, TouchPosition.Y - 25), Color.Black, ItemTimeLifeLeft / ItemTimeLife, "", 0.8f, TextAlign.CenterXY, 0, 97);
        spriteBatch.FillRectangle(new Vector2(0, TouchPosition.Y - 1), new Vector2(480, 2), RandomColor, ItemTimeLifeLeft / ItemTimeLife, 0, 95);

        spriteBatch.DrawText(TouchPosition.Y + "", new Vector2(TouchPosition.X + 25, 25), Color.Black, ItemTimeLifeLeft / ItemTimeLife, "", 0.8f, TextAlign.CenterXY, 0, 97);
        spriteBatch.FillRectangle(new Vector2(TouchPosition.X - 1, 0), new Vector2(2, 800), RandomColor, ItemTimeLifeLeft / ItemTimeLife, 0, 95);

        spriteBatch.DrawCircle(TouchPosition, 30, 100, RandomColor, ItemTimeLifeLeft / ItemTimeLife, 2, 95);

        spriteBatch.DrawText(Text, new Vector2(240, 100), Color.Black, ItemTimeLifeLeft / ItemTimeLife, "", 1.8f, TextAlign.CenterXY, 0, 97);
        spriteBatch.DrawText(addText, new Vector2(240, 700), Color.Black, ItemTimeLifeLeft / ItemTimeLife, "", 0.8f, TextAlign.CenterXY, 0, 97);
    }
}
