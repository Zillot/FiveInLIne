package com.koma.filforever.RootFolder.Data;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;

public class ScoreHistory {
    public int Score;
    public String Name;

    public FloatController apearAnimation;

    public ScoreHistory() {
        init();
    }

    public void init() {
        apearAnimation = new FloatController(2.0f);
        apearAnimation.GoUp();

        if (Name == null) {
            Name = "<NoName>";
        }
    }

    public void Show() {
        apearAnimation.GoUp();
    }

    public void Hide() {
        apearAnimation.GoDown();
    }

    public Boolean Update(float timeDelta) {
        return apearAnimation.Update(timeDelta);
    }

    public void Draw(SpriteAdapter spriteBatch, Vector2 position, int Index) {
        if (Index % 2 == 0) {
            spriteBatch.FillRectangle(new Vector2(0, position.Y - 20), new Vector2(480, 40), Color.Black, 0.12f * apearAnimation.Value, 0, 19);
        }

        spriteBatch.DrawText(Index + "", position.add(0, 0), Color.Black, apearAnimation.Value, "Arial16", 1, TextAlign.CenterXY, 0, 20);
        spriteBatch.DrawText(Name, position.add(30, 0), Color.Black, apearAnimation.Value, "Arial16", 1, TextAlign.LeftXCenterY, 0, 20);

        spriteBatch.DrawText(Score + "", position.add(380, 0), Color.Black, apearAnimation.Value, "Arial16", 1, TextAlign.RightXCenterY, 0, 20);
    }
}
