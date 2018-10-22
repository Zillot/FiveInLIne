package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.DelayController;

public class DelayOperation {
    public Delegate DelayedOperation;

    public DelayController toCall;
    public Boolean Called;

    public DelayOperation(Delegate opeartion, float timeToCall) {
        DelayedOperation = opeartion;
        toCall = new DelayController();
        toCall.Start(timeToCall);
        Called = false;
    }

    public void ReCall(float time) {
        toCall.Start(time);
        Called = false;
    }

    public Boolean Update(float timeDelta) {
        toCall.Update(timeDelta);

        if (toCall.Value == 0 && !Called) {
            Called = true;
            if (DelayedOperation != null) {
                DelayedOperation.VoidDelegate();
            }

            return true;
        }

        return false;
    }
}
