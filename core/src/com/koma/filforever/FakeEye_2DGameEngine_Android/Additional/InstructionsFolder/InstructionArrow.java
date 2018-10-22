package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.PivotPoint;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class InstructionArrow extends InstructionsItem {
    public Vector2 From;
    public Vector2 To;

    public float Distance;

    public Vector2 MainDirection;
    public Vector2 LeftDirection;
    public Vector2 RightDirection;

    public Vector2 CubSize;
    public float MainAgnle;

    public InstructionArrow(Color color, Vector2 from, Vector2 to) {
        super(new Vector2(0), color);
        Distance = Vector2.Distance(from, to);

        From = from;
        To = to;

        CubSize = new Vector2(10);

        MainDirection = Vector2.Normalize(From.sub(To));

        MainAgnle = -EngineService.I.GetWorldAngle(MainDirection);

        LeftDirection = EngineService.I.GetRotatedVectorU(MainAgnle + (float) Math.PI + 0.7f);
        RightDirection = EngineService.I.GetRotatedVectorU(MainAgnle - (float) Math.PI - 0.7f);
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        int count = (int) (Distance / (CubSize.X + 4));

        for (int i = 0; i < count; i++) {
            spriteBatch.FillRectangle(To.add(MainDirection.mul((CubSize.X + 4) * i)), CubSize, ItemColor, PivotPoint.CenterXCenterY, Opacity, MainAgnle, layer);
        }

        for (int i = 1; i < 4; i++) {
            spriteBatch.FillRectangle(To.add(LeftDirection.mul(((float) Math.sqrt(((CubSize.X * 2) * 2)) * 2.8f) * i)), CubSize, ItemColor, PivotPoint.CenterXCenterY, Opacity, MainAgnle, layer);
            spriteBatch.FillRectangle(To.add(RightDirection.mul(((float) Math.sqrt(((CubSize.X * 2) * 2)) * 2.8f) * i)), CubSize, ItemColor, PivotPoint.CenterXCenterY, Opacity, MainAgnle, layer);
        }
    }
}
