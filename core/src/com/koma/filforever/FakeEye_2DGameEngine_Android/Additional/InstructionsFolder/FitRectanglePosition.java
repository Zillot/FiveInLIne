package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

public class FitRectanglePosition extends FitPosition {
    public Vector2 Position;
    public Vector2 Size;

    public FitRectanglePosition(Vector2 position, Vector2 size) {
        Position = position;
        Size = size;
    }

    @Override
    public Boolean CheckIsFit(TouchEventType TouchEventName, Vector2 position) {
        return EngineService.I.isInRect(position, Position, Size);
    }
}
