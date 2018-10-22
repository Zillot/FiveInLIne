package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;

public class TouchItem {
    public float TouchOnlineTime;
    public Vector2 TouthStartPos;
    public Vector2 TouthEndPos;

    public TouchItem(Vector2 Position) {
        TouthStartPos = Position;
        TouthEndPos = Vector2.Zero;
        TouchOnlineTime = 0;
    }

    public float GetDistance() {
        return Vector2.Distance(TouthStartPos, TouthEndPos);
    }

    public float GetXDistance() {
        return (float) Math.abs(TouthStartPos.X - TouthEndPos.X);
    }

    public float GetYDistance() {
        return (float) Math.abs(TouthStartPos.Y - TouthEndPos.Y);
    }

    public Vector2 GetAWGPosition() {
        return TouthStartPos.add(TouthEndPos).div(2);
    }
}
