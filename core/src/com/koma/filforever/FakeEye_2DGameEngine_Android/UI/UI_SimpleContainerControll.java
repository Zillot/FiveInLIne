package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;


import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

import java.util.ArrayList;
import java.util.List;

public class UI_SimpleContainerControll extends UI_IMenuControll {
    public List<UI_IMenuControll> Childrens;

    public UI_SimpleContainerControll(Vector2 position, Vector2 size) {
        super(position, size);

        ControllPosition = position;
        ControllSize = size;

        Childrens = new ArrayList<UI_IMenuControll>();
    }

    @Override
    public void TouchEventCheck(Vector2 TouchPosition, TouchEventType TouchEventName) {
        for (UI_IMenuControll item : Childrens) {
            item.TouchEventCheck(TouchPosition, TouchEventName);
        }
    }

    @Override
    public Boolean ContentUpdated() {
        Boolean toRet = false;

        for (UI_IMenuControll item : Childrens) {
            toRet = toRet | item.ContentUpdated();
        }

        return toRet;
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        for (UI_IMenuControll item : Childrens) {
            toRet = toRet | item.Update(timeDelta);
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        Vector2 PreOffset = new Vector2(0);

        for (UI_IMenuControll item : Childrens) {
            item.Draw(spriteBatch, ControllPosition.add(Offset).add(PreOffset), layer + 2);
            PreOffset.Y += item.ControllSize.Y;
        }
    }
}
