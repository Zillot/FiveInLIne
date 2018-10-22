package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;


import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

import java.util.ArrayList;
import java.util.List;

public class UI_TabsControll extends UI_IMenuControll {
    private static Vector2 Buttons_Size = new Vector2(150, 60);
    private static Vector2 Buttons_LeftOffset = new Vector2(10, 0);
    private static Vector2 Buttons_SpaceBetween = new Vector2(4, 0);
    private static Vector2 Line_Size = new Vector2(480, 10);
    private static Vector2 Filed_StartOffset = new Vector2(0, Buttons_Size.Y);

    public int SelectedPageIndex;

    public List<UI_TabButtonControll> TabButton;
    public List<UI_IMenuControll> TabsPages;

    public Vector2 Position;
    public Vector2 Size;

    public UI_TabsControll(Vector2 position, Vector2 size, int TabsCount) {
        super(position, size);

        ControllPosition = position;
        ControllSize = size.sub(Filed_StartOffset);

        Position = position;
        Size = size.sub(new Vector2(0, Buttons_Size.X));
        SelectedPageIndex = 0;

        TabsPages = new ArrayList<UI_IMenuControll>();
        TabButton = new ArrayList<UI_TabButtonControll>();

        for (int i = 0; i < TabsCount; i++) {
            TabButton.add(new UI_TabButtonControll(Buttons_LeftOffset.add(Buttons_SpaceBetween.mul(i).add(Buttons_Size.X * i, 0)),
                    Color.Orange, Color.White, "", Buttons_Size, ""));
            TabButton.get(i).onTap[4] = ButtonTap;
        }

        TabButton.get(0).Selected = true;
    }

    private Delegate ButtonTap = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 position, Object arg) {
            int i = 0;

            for (UI_TabButtonControll item : TabButton) {
                if (item.PreSelected) {
                    SelectedPageIndex = i;
                }

                item.Selected = item.PreSelected;
                item.PreSelected = false;
                i++;
            }
        }
    };

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, Position, Size);
    }

    public Boolean ContentUpdated() {
        Boolean toRet = false;

        for (UI_IMenuControll item : TabsPages) {
            toRet = toRet | item.ContentUpdated();
        }

        return toRet;
    }

    @Override
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
        for (UI_TabButtonControll item : TabButton) {
            item.TouchEventCheck(ToouchPosition, TouchEventName);
        }

        if (TabsPages.size() > SelectedPageIndex) {
            if (InUnderControll(ToouchPosition)) {
                TabsPages.get(SelectedPageIndex).TouchEventCheck(ToouchPosition, TouchEventName);
            }

            if (TouchEventName == TouchEventType.DragDelta || TouchEventName == TouchEventType.DragEnded) {
                TabsPages.get(SelectedPageIndex).TouchEventCheck(ToouchPosition, TouchEventName);
            }
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        for (UI_TabButtonControll item : TabButton) {
            toRet = toRet | item.Update(timeDelta);
        }

        for (UI_IMenuControll item : TabsPages) {
            if (item != null) {
                toRet = toRet | item.Update(timeDelta);
            }
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        spriteBatch.FillRectangle(Position.add(Filed_StartOffset).add(Offset), Size, Color.Black, 0.4f, 0, 10);

        for (UI_TabButtonControll item : TabButton) {
            item.Draw(spriteBatch, Position.add(Offset), layer);
        }

        if (TabsPages.size() > SelectedPageIndex && TabsPages.get(SelectedPageIndex) != null) {
            TabsPages.get(SelectedPageIndex).Draw(spriteBatch, Position.add(Filed_StartOffset).add(Offset).add(0, 30), layer);
        }

        spriteBatch.FillRectangle(Position.add(Filed_StartOffset).add(Offset), Line_Size, Color.Orange, 1, 0, 80);
        spriteBatch.FillRectangle(Position.add(0, Size.Y - Line_Size.Y).add(Offset).add(Filed_StartOffset), Line_Size, Color.Orange, 1, 0, 80);
    }
}