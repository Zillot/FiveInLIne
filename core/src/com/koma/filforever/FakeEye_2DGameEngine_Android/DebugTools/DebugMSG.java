package com.koma.filforever.FakeEye_2DGameEngine_Android.DebugTools;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;

public class DebugMSG {
    public float LifeTime;
    public String text;
    public Color color;
    public float fontSize;
    public Vector2 position;

    public DebugMSG(Vector2 Position, String Text, Color Color, float FontSize) {
        LifeTime = DebugService.MSGLifeTime;
        text = Text;
        color = Color;
        fontSize = FontSize;
        position = Position;
    }

    public Boolean Update(float TimeDelta) {
        if (LifeTime > 0) {
            LifeTime -= TimeDelta;
        }
        if (LifeTime < 0) {
            LifeTime = 0;
        }

        return true;
    }
}
