package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class DelegateBasedOnNodeItem extends NodeItem {
    public Delegate ExtendDrawEvent;
    public Delegate ExtendUpdateEvent;

    public Vector2 BasePosition;
    public Vector2 BaseSize;
    public Vector2 BaseScale;
    public float BaseRotation;
    public float BaseOpacity;

    public int SortLayer;

    public String Data;

    public DelegateBasedOnNodeItem(Vector2 position, Vector2 size, Delegate DrawEvent, Delegate UpdateEvent, int sortLayer, Vector2 baseScale, float baseOpacity, float baseRotation) {
        super(position, size, baseScale, baseRotation, baseOpacity);

        SortLayer = sortLayer;
        ExtendDrawEvent = DrawEvent;
        ExtendUpdateEvent = UpdateEvent;

        BasePosition = position;
        BaseSize = size;
        BaseScale = baseScale;
        BaseRotation = baseRotation;
        BaseOpacity = baseOpacity;
    }

    public void SetData(String data) {
        Data = data;
    }

    public void Flush() {
        Position = BasePosition;
        Size = BaseSize;
        Scale = BaseScale;
        Rotation = BaseRotation;
        Opacity = BaseOpacity;
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        if (ExtendUpdateEvent != null) {
            toRet = toRet | ExtendUpdateEvent.ExtendUpdate(timeDelta, this);
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        if (ExtendDrawEvent != null) {
            ExtendDrawEvent.ExtendDraw(spriteBatch, this, layer);
        }
    }
}
