package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;

public class DelayController {
    public float Value;

    public DelayController() {
        Value = 0;
    }

    public void Start(float seconds) {
        Value = seconds;
    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        if (Value > 0) {
            Value -= timeDelta;
            if (Value < 0) {
                Value = 0;
            }
        }

        return toRet;
    }
}
