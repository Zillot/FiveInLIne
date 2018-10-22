package com.koma.filforever.FakeEye_2DGameEngine_Android.Engine;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.DelayOperation;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.DelegateBasedOnNodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.FloatMessageControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder.InstructionBase;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_IMenuControll;
import com.koma.filforever.RootFolder.Data.DataControll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class IGameState {
    public TreeMap<String, UI_IMenuControll> Controlls;

    public Map<String, DelegateBasedOnNodeItem> ExtendItems;
    public FloatMessageControll floatMessageControll;
    public List<DelayOperation> delayOpearions;
    public List<InstructionBase> Instructions;
    public int InstructionIndex;

    public IGameState() {
        Instructions = new ArrayList<InstructionBase>();
        InstructionIndex = 0;

        Controlls = new TreeMap<String, UI_IMenuControll>();

        delayOpearions = new ArrayList<DelayOperation>();
        floatMessageControll = new FloatMessageControll();
        ExtendItems = new TreeMap<String, DelegateBasedOnNodeItem>();
    }

    public void DoWithDelay(DelayOperation operation) {
        delayOpearions.add(operation);
    }

    public void Initialize() {
        TouchService.I.onTap[4] = DelegatePrivateLocalTouchEventCheck;
        TouchService.I.onDoubleTap[4] = DelegatePrivateLocalTouchEventCheck;
        TouchService.I.onLeftSwap[4] = DelegatePrivateLocalTouchEventCheck;
        TouchService.I.onRightSwap[4] = DelegatePrivateLocalTouchEventCheck;
        TouchService.I.onDownSwap[4] = DelegatePrivateLocalTouchEventCheck;
        TouchService.I.onUpSwap[4] = DelegatePrivateLocalTouchEventCheck;
        TouchService.I.onDragStarted[4] = DelegatePrivateLocalTouchEventCheck;
        TouchService.I.onDragDelta[4] = DelegatePrivateLocalTouchEventCheck;
        TouchService.I.onDragEnded[4] = DelegatePrivateLocalTouchEventCheck;
    }

    public void Dispose() {
        TouchService.I.onTap[4] = null;
        TouchService.I.onDoubleTap[4] = null;
        TouchService.I.onLeftSwap[4] = null;
        TouchService.I.onRightSwap[4] = null;
        TouchService.I.onDownSwap[4] = null;
        TouchService.I.onUpSwap[4] = null;
        TouchService.I.onDragStarted[4] = null;
        TouchService.I.onDragDelta[4] = null;
        TouchService.I.onDragEnded[4] = null;
    }

    private Delegate DelegatePrivateLocalTouchEventCheck = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            PrivateLocalTouchEventCheck(TouchEventName, position);
        }
    };

    public void PrivateLocalTouchEventCheck(TouchEventType TouchEventName, Vector2 position) {
        if (InstructionIndex >= Instructions.size()) {
            List<String> keys = new ArrayList<String>();
            keys.addAll(Controlls.keySet());

            for (int i = 0; i < keys.size(); i++) {
                if (Controlls.containsKey(keys.get(i))) {
                    Controlls.get(keys.get(i)).TouchEventCheck(position, TouchEventName);
                }
            }
        } else {
            for (UI_IMenuControll item : Instructions.get(InstructionIndex).FitControlls) {
                item.TouchEventCheck(position, TouchEventName);
            }

            if (Instructions.get(InstructionIndex).CheckTapForNextInstruction(TouchEventName, position)) {
                InstructionIndex++;
            }
        }
    }

    public void OnNavigatetFrom(Object data) {
        Dispose();
    }

    public void OnNavigatetTo(Object data) {
        Initialize();
    }

    public void ContentUpdated() {
        for (Map.Entry<String, UI_IMenuControll> item : Controlls.entrySet())
            item.getValue().ContentUpdated();
    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        for (Map.Entry<String, UI_IMenuControll> item : Controlls.entrySet()) {
            toRet = toRet | item.getValue().Update(timeDelta);
        }

        List<String> keys = new ArrayList<String>();
        keys.addAll(ExtendItems.keySet());

        for (int i = 0; i < keys.size(); i++) {
            if (ExtendItems.containsKey(keys.get(i))) {
                toRet = toRet | ExtendItems.get(keys.get(i)).Update(timeDelta);
            }
        }

        for (DelayOperation item : delayOpearions) {
            toRet = toRet | item.Update(timeDelta);
        }

        toRet = toRet | floatMessageControll.Update(timeDelta);

        if (InstructionIndex < Instructions.size()) {
            toRet = toRet | Instructions.get(InstructionIndex).Update(timeDelta);
        }

        return toRet;
    }

    public void Draw(SpriteAdapter spriteBatch) {
        for (Map.Entry<String, UI_IMenuControll> item : Controlls.entrySet()) {
            item.getValue().Draw(spriteBatch);
        }

        floatMessageControll.Draw(spriteBatch, 60);
    }
}
