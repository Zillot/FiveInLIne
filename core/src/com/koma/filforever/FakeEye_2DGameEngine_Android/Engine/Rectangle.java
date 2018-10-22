package com.koma.filforever.FakeEye_2DGameEngine_Android.Engine;

public class Rectangle {
    public float X;
    public float Y;
    public float Left;
    public float Right;
    public float Top;
    public float Bottom;

    public Rectangle(float X, float Y, float Top, float Bottom) {
        this.X = X;
        this.Y = Y;
        this.Left = X;
        this.Right = Y;
        this.Top = Top;
        this.Bottom = Bottom;
    }
}
