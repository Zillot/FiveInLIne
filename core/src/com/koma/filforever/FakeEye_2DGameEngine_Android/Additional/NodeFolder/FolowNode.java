package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;

public class FolowNode {
    public float LeftDistance;
    public Vector2 Value;
    public float Speed;
    public Vector2 MoveDirection;

    public FolowNode(Vector2 value, float speed) {
        LeftDistance = 0;
        Value = value;
        Speed = speed;
    }
}
