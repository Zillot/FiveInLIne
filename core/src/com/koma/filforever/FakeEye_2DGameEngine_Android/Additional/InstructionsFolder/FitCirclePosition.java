package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

public class FitCirclePosition extends FitPosition {
    public Vector2 Position;
    public float Distance;

    public FitCirclePosition(Vector2 position, float distance) {
        Position = position;
        Distance = distance;
    }

    @Override
    public Boolean CheckIsFit(TouchEventType TouchEventName, Vector2 position) {
        return Vector2.Distance(position, Position) < Distance;
    }
}
