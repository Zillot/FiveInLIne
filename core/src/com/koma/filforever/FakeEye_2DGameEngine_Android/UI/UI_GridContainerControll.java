package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;

import java.util.ArrayList;
import java.util.List;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

public class UI_GridContainerControll extends UI_IMenuControll {
    public List<UI_IMenuControll> ItemsChildrens;

    public UI_GridContainerControll(Vector2 position, Vector2 size) {
        super(Vector2.Zero, size);
        ControllPosition = position;
        ControllSize = size;

        ItemsChildrens = new ArrayList<UI_IMenuControll>();
    }

    @Override
    public void TouchEventCheck(Vector2 TouchPosition, TouchEventType TouchEventName) {
        for (UI_IMenuControll item : ItemsChildrens) {
            item.TouchEventCheck(TouchPosition, TouchEventName);
        }
    }

    @Override
    public Boolean ContentUpdated() {
        Boolean toRet = false;

        for (UI_IMenuControll item : ItemsChildrens) {
            toRet = toRet | item.ContentUpdated();
        }

        return toRet;
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        for (UI_IMenuControll item : ItemsChildrens) {
            toRet = toRet | item.Update(timeDelta);
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        for (UI_IMenuControll item : ItemsChildrens) {
            item.Draw(spriteBatch, ControllPosition.add(Offset).add(Position), layer);
        }
    }
}
