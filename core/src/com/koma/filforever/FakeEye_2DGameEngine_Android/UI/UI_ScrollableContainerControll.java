package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;


import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

import java.util.ArrayList;
import java.util.List;

public class UI_ScrollableContainerControll extends UI_IMenuControll {
    public static float scrollCineticSlowdownSpeed = 0.02f;
    public static float scrollCineticMaxSpeed = 30.0f;
    public static float scrollCineticMinSpeed = 2.0f;
    public static float SpaceBetween = 15.0f;

    public List<UI_IMenuControll> Childrens;
    public Vector2 ContainerScrollOffset;

    public Vector2 ScrollSpeed;

    public Vector2 ManipulationStart;
    public Vector2 ManipulationDelta;
    public Vector2 ManipulationOffset;
    public Vector2 ManipulationLastOffset;

    public Boolean isOnDrugs;

    public UI_ScrollableContainerControll(Vector2 position, Vector2 size) {
        super(position, size);

        isOnDrugs = false;

        ManipulationStart = Vector2.Zero;
        ManipulationDelta = Vector2.Zero;
        ManipulationOffset = Vector2.Zero;
        ManipulationLastOffset = Vector2.Zero;

        ContainerScrollOffset = new Vector2(0);
        ControllPosition = position;
        ControllSize = size;

        Childrens = new ArrayList<UI_IMenuControll>();
        ContentUpdated();
    }

    @Override
    public Boolean ContentUpdated() {
        Boolean toRet = false;

        for (UI_IMenuControll item : Childrens) {
            toRet = toRet | item.ContentUpdated();
        }

        return toRet;
    }

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, ControllPosition, ControllSize);
    }

    public float GetHeight() {
        float toRet = SpaceBetween;

        for (UI_IMenuControll item : Childrens) {
            toRet += (item.ControllSize.Y + SpaceBetween);
        }

        return toRet - ControllSize.Y + 135;
    }

    @Override
    public void TouchEventCheck(Vector2 TouchPosition, TouchEventType TouchEventName) {
        if (TouchEventName == TouchEventType.DragStarted && InUnderControll(TouchPosition)) {
            isOnDrugs = true;
            ManipulationStart = TouchPosition;
            ManipulationDelta = TouchPosition;
        }

        if (isOnDrugs && TouchEventName == TouchEventType.DragDelta) {
            ManipulationDelta = TouchPosition;
        }

        if (isOnDrugs && TouchEventName == TouchEventType.DragEnded) {
            ContainerScrollOffset = ContainerScrollOffset.add(ManipulationOffset);
            ContainerScrollOffset.X = 0;

            ManipulationOffset.Y = 0;

            ManipulationStart = Vector2.Zero;
            ManipulationDelta = Vector2.Zero;

            isOnDrugs = false;
        }

        if (!isOnDrugs && ScrollSpeed.Y == 0) {
            for (UI_IMenuControll item : Childrens) {
                item.TouchEventCheck(TouchPosition, TouchEventName);
            }
        }

        if (ScrollSpeed.Y != 0 && TouchEventName == TouchEventType.Tap) {
            ScrollSpeed.Y = 0;
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        for (UI_IMenuControll item : Childrens) {
            item.Update(timeDelta);
        }

        if (isOnDrugs) {
            ManipulationLastOffset = ManipulationOffset;
            ManipulationOffset = ManipulationDelta.sub(ManipulationStart);
            ManipulationLastOffset.X = 0;
            ManipulationOffset.X = 0;

            ScrollSpeed = ManipulationOffset.sub(ManipulationLastOffset);

            if (ScrollSpeed.Y > scrollCineticMaxSpeed)
                ScrollSpeed.Y = scrollCineticMaxSpeed;
            if (ScrollSpeed.Y < -scrollCineticMaxSpeed)
                ScrollSpeed.Y = -scrollCineticMaxSpeed;
        } else {
            ContainerScrollOffset = ContainerScrollOffset.add(ScrollSpeed);
            ScrollSpeed = ScrollSpeed.sub(ScrollSpeed).mul(scrollCineticSlowdownSpeed);

            if ((float) Math.abs(ScrollSpeed.Y) <= scrollCineticMinSpeed) {
                ScrollSpeed.Y = 0;
            }
        }

        if (ContainerScrollOffset.Y > 0) {
            ContainerScrollOffset.Y = 0;
            ScrollSpeed.Y = 0;
        }

        float height = -GetHeight();
        if (ContainerScrollOffset.Y < height) {
            ContainerScrollOffset.Y = height;
            ScrollSpeed.Y = 0;
        }

        return true;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        Vector2 PreFullOffset = ContainerScrollOffset.add(ManipulationOffset);

        if (PreFullOffset.Y > 0) {
            PreFullOffset.Y = 0;
        }
        float height = -GetHeight();
        if (PreFullOffset.Y < height) {
            PreFullOffset.Y = height;
        }

        Vector2 PreOffset = new Vector2(0, SpaceBetween);

        for (UI_IMenuControll item : Childrens) {
            item.Draw(spriteBatch, ControllPosition.add(Offset).add(PreFullOffset).add(PreOffset), layer);
            PreOffset.Y += item.ControllSize.Y + SpaceBetween;
        }
    }
}
