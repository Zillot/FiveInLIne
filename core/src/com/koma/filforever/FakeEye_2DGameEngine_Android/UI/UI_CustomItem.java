package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

import java.util.TreeMap;

public class UI_CustomItem extends UI_IMenuControll {
    public int Layer;
    public Delegate OnExtend;
    public TreeMap<String, Object> Values;

    public UI_CustomItem(int layer, TreeMap<String, Object> values, Delegate onExtend) {
        super(new Vector2(0, 0), new Vector2(0,0));
        ControllPosition = new Vector2(0, 0);
        ControllSize = new Vector2(0, 0);

        Values = values;
        OnExtend = onExtend;

        Layer = layer;
    }

    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
        OnExtend.ControllTouchDelegate(ToouchPosition, TouchEventName);
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | OnExtend.ExtendUpdate(timeDelta, this);

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        this.Position = Offset;
        OnExtend.ExtendDraw(spriteBatch, this, layer);
    }
}
