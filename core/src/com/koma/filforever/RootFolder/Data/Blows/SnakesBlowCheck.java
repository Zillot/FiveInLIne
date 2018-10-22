package com.koma.filforever.RootFolder.Data.Blows;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.RootFolder.Data.CellItem;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.GameType;

import java.util.ArrayList;
import java.util.List;

public class SnakesBlowCheck extends BlowCheckBase {
    //удаление чегонибудь, зависит от type
    public Boolean BlowCheck(List<CellItem> cells, int XStart, int YStart, GameType gType) {
        Boolean raibow = getBlock(cells, new Vector2(XStart, YStart)).color == Color.Violet;
        Color color = getBlock(cells, new Vector2(XStart, YStart)).color;

        if (!raibow) {
            return InSnake(cells, XStart, YStart, color);
        } else {
            Boolean ret = false;
            for (int i = 0; i < 4; i++) {
                ret = ret || InSnake(cells, XStart, YStart, DataControll.Colors[i]);
            }
            return ret;
        }
    }

    //режим змейки
    private Boolean InSnake(List<CellItem> cells, int XStart, int YStart, Color color) {
        List<CellItem> DeleteCell = new ArrayList<CellItem>();
        Vector2 Start = new Vector2(XStart, YStart);

        List<Vector2> temp = new ArrayList<Vector2>();
        temp.add(new Vector2(XStart, YStart));

        while (temp.size() > 0) {
            Vector2 pointer = temp.get(0);
            temp.remove(0);

            if (pointer.X != -1 && pointer.Y != -1) {
                temp.add(inSnakeProcces(DeleteCell, cells, pointer.add(1, 0), color));
                temp.add(inSnakeProcces(DeleteCell, cells, pointer.add(-1, 0), color));
                temp.add(inSnakeProcces(DeleteCell, cells, pointer.add(0, 1), color));
                temp.add(inSnakeProcces(DeleteCell, cells, pointer.add(0, -1), color));
            }
        }

        Boolean deleted = false;

        //удаление
        int scoreDelta = GetScore(7, DeleteCell.size());
        if (DeleteCell.size() >= 7) {
            for (CellItem item : DeleteCell) {
                item.Delete(scoreDelta / DeleteCell.size());
                floatMessageControll.addMessage("+" + (scoreDelta / DeleteCell.size()), item.Position.add(DataControll.FieldPivotPoint).add(DataControll.I.GetCubeSize().div(2)), FloatTextColor, FloatTextName, FloatTextSize, false);
                deleted = true;
            }
        }

        cubesCountControll();

        return deleted;
    }

    private Vector2 inSnakeProcces(List<CellItem> DeleteCell, List<CellItem> cells, Vector2 pointer, Color color) {
        if (getBlock(cells, pointer, color) != null && getBlock(DeleteCell, pointer) == null) {
            DeleteCell.add(getBlock(cells, pointer, color));
            return pointer;
        } else {
            return new Vector2(-1);
        }
    }
}
