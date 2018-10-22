package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder;

import java.util.LinkedList;
import java.util.List;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_IMenuControll;

public class InstructionBase extends NodeItem {
    public InstructionsHightlightItem hightlignt;
    public List<InstructionsItem> items;
    public List<UI_IMenuControll> FitControlls;
    public List<UI_IMenuControll> HiControlls;
    public List<FitPosition> FitTapPositions;

    public int Layer;

    public Color BackColor;
    public Vector2 ScreenSize;

    public InstructionBase(Vector2 screenSize, float baseOpacity, Color backColor, int layer) {
        super(Vector2.Zero, new Vector2(0), new Vector2(1, 1), 0, 1);
        ScreenSize = screenSize;
        Opacity = baseOpacity;
        BackColor = backColor;

        FitTapPositions = new LinkedList<FitPosition>();
        items = new LinkedList<InstructionsItem>();
        FitControlls = new LinkedList<UI_IMenuControll>();
        HiControlls = new LinkedList<UI_IMenuControll>();
        Layer = layer;
    }

    public void SetHightlight(Vector2 position, Vector2 size) {
        hightlignt = new InstructionsHightlightItem(position, BackColor, size, Opacity, ScreenSize);
    }

    public void AddInstruction(InstructionsItem item) {
        items.add(item);
    }

    public void AddFitControll(UI_IMenuControll Controll) {
        FitControlls.add(Controll);
    }

    public void AddHiControll(UI_IMenuControll Controll) {
        HiControlls.add(Controll);
    }

    public void AddFitTapPosition(FitPosition position) {
        FitTapPositions.add(position);
    }

    public Boolean CheckTapForNextInstruction(TouchEventType TouchEventName, Vector2 position) {
        if (FitTapPositions.size() == 0 && FitControlls.size() == 0) {
            return true;
        }
        for (FitPosition item : FitTapPositions) {
            if (item.CheckIsFit(TouchEventName, position)) {
                return true;
            }
        }

        for (UI_IMenuControll item : FitControlls) {
            if (EngineService.I.isInRect(position, item.ControllPosition, item.ControllSize)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        for (InstructionsItem item : items) {
            toRet = toRet | item.Update(timeDelta);
        }

        if (hightlignt != null) {
            toRet = toRet | hightlignt.Update(timeDelta);
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        if (hightlignt != null) {
            hightlignt.Draw(spriteBatch, Layer);
        } else {
            spriteBatch.FillRectangle(Vector2.Zero, ScreenSize, BackColor, Opacity, Rotation, Layer);
        }

        for (InstructionsItem item : items) {
            item.Draw(spriteBatch, Layer + 5);
        }

        for (UI_IMenuControll item : FitControlls) {
            item.Draw(spriteBatch, Vector2.Zero, Layer + 2);
        }

        for (UI_IMenuControll item : HiControlls) {
            item.Draw(spriteBatch, Vector2.Zero, Layer + 2);
        }
    }
}
