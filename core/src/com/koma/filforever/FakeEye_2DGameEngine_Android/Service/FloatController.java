package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;

import java.io.Serializable;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;

public class FloatController implements Serializable {
    public Delegate DownEvent;
    public Delegate UpEvent;

    public Boolean UpCalled;
    public Boolean DownCalled;

    public float Min;
    public float Max;
    public float Value;

    public float UpSpeed;
    public float DownSpeed;
    public FloatControllerStatus Operation;

    public Boolean Finished;

    public FloatController() {
        this(1.0f);
    }

    public FloatController(float speed) {
        Finished = false;
        Value = 0;
        Min = 0;
        Max = 1;

        UpSpeed = speed;
        DownSpeed = speed;
        Operation = FloatControllerStatus.Stop;

        UpCalled = true;
        DownCalled = true;
    }

    public float GetRegularValue() {
        return Value;
    }

    public float GetInvertedValue() {
        return Max - Value;
    }

    public FloatController(float min, float max, float upSpeed, float downSpeed) {
        Value = min;
        Min = min;
        Max = max;

        UpSpeed = upSpeed;
        DownSpeed = downSpeed;
        Operation = FloatControllerStatus.Stop;
    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        if (Operation == FloatControllerStatus.Up) {
            toRet = true;

            if (Value + timeDelta * UpSpeed < Max) {
                Value += timeDelta * UpSpeed;
            } else {
                Value = Max;
                if (!UpCalled) {
                    UpCalled = true;
                    if (UpEvent != null) {
                        UpEvent.VoidDelegate();
                    }
                }

                Stop();
            }
        } else if (Operation == FloatControllerStatus.Down) {
            toRet = true;

            if (Value - timeDelta * DownSpeed > Min) {
                Value -= timeDelta * DownSpeed;
            } else {
                Value = Min;
                if (!DownCalled) {
                    DownCalled = true;
                    if (DownEvent != null) {
                        DownEvent.VoidDelegate();
                    }
                }

                Stop();
            }
        }

        return toRet;
    }

    public void ClearEvent(Boolean Up, Boolean Down) {
        if (Up) {
            UpEvent = null;
        }
        if (Down) {
            DownEvent = null;
        }
    }

    public void GoUp() {
        if (Value == 1) {
            return;
        }

        Operation = FloatControllerStatus.Up;
        DownCalled = false;
        UpCalled = false;
    }

    public void GoDown() {
        if (Value == 0) {
            return;
        }

        Operation = FloatControllerStatus.Down;
        DownCalled = false;
        UpCalled = false;
    }

    public void Stop() {
        Operation = FloatControllerStatus.Stop;
        DownCalled = false;
        UpCalled = false;
    }
}
