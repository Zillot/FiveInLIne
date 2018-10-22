package com.koma.filforever.RootFolder.Data;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.PivotPoint;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class TabsDot extends NodeItem {
    public TabsDot(Vector2 position) {
        super(position, new Vector2(1), new Vector2(1, 1), 0, 1);
        SetAlpha(0.3f);
        SetScale(new Vector2(0.4f));
    }

    public void Select() {
        EmergencyStopAllEffects();

        TranslateAlpha(0.6f, 2.0f);
        TranslateScale(new Vector2(1.2f), 3.0f);
        TranslateRotation(0.78f, 3.0f);
    }

    public void deSelect() {
        EmergencyStopAllEffects();

        TranslateAlpha(0.3f, 2.0f);
        TranslateScale(new Vector2(0.4f), 3.0f);
        TranslateRotation(0, 3.0f);
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        Vector2 s = new Vector2(Size.X * Scale.X * 15);
        spriteBatch.FillRectangle(Position, s, Color.Black, PivotPoint.CenterXCenterY, Opacity, Rotation, 30);
        //spriteBatch.DrawCircle(Position, Size.X * Scale.X * 15, 20, Color.Black, Opacity, 3, 30);
    }
}
