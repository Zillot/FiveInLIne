package com.koma.filforever.RootFolder.Data;

import java.util.List;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_IMenuControll;

public class ScoresHolder extends UI_IMenuControll {
    public String text;
    public List<ScoreHistory> Scores;

    private FloatController LoaderAnimation;
    private Boolean internetConnection;
    public int ID;

    public ScoresHolder(List<ScoreHistory> scores, String alt, int id) {
        super(new Vector2(0), new Vector2(1));
        ID = id;
        ControllPosition = new Vector2(0);
        ControllSize = new Vector2(1);

        text = alt;
        Scores = scores;

        internetConnection = true;

        LoaderAnimation = new FloatController(2.6f);
        LoaderAnimation.GoUp();
    }

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, Position.sub(Size.div(2)), Size);
    }

    @Override
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {

    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        if (Scores.size() == 0 && text.compareTo("Empty") != 0) {
            toRet = toRet | LoaderAnimation.Update(timeDelta);

            if (LoaderAnimation.GetRegularValue() == 1) {
                toRet = true;

                LoaderAnimation.Value = 0;
                LoaderAnimation.GoUp();

                if (text.length() <= 4 && text.compareTo("Connecting") != 0) {
                    text += " ";
                    text = "Connecting";
                } else {
                    text += ".";
                }
            }

            if (text.compareTo("Connecting....") == 0) {
                text = "Connecting";
            }
        }

        for (int i = 0; i < Scores.size(); i++) {
            toRet = toRet | Scores.get(i).Update(timeDelta);
        }

        toRet = super.Update(timeDelta);

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        float opacity = 0;

        for (int i = 0; i < Scores.size(); i++) {
            Scores.get(i).Draw(spriteBatch, new Vector2(50, 170 + 45 * (i - 1)).add(Offset), i + 1);
            opacity = Scores.get(i).apearAnimation.Value;
        }

        spriteBatch.DrawText(DataControll.I.GameModes.get(ID).replace("Mode", ""), new Vector2(240, 40).add(Offset), Color.Black, 1, "BArial32", 1.0f, TextAlign.CenterXY, 0, 40);

        spriteBatch.FillRectangle(new Vector2(35, 0).add(Offset), new Vector2(30, 800), Color.Black, 0.12f * opacity, 0, 19);

        if (Scores.size() <= 0 && internetConnection) {
            if (text.compareTo("Empty") == 0) {
                spriteBatch.DrawText("No scores(", new Vector2(240, 400).add(Offset), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
            } else {
                spriteBatch.DrawText(text, new Vector2(240, 400).add(Offset), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
            }
        }

        if (!internetConnection) {
            spriteBatch.DrawText("Can't load leaderbord", new Vector2(240, 600).add(Offset), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
            spriteBatch.DrawText("Please check your internet connection", new Vector2(240, 630).add(Offset), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
        }
    }
}
