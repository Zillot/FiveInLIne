package com.koma.filforever.RootFolder.Data.Blows;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.RootFolder.Data.CellItem;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.GameType;

import java.util.ArrayList;
import java.util.List;

public class CirclesBlowCheck extends BlowCheckBase {
    //удаление чегонибудь, зависит от type
    public Boolean BlowCheck(List<CellItem> cells, int XStart, int YStart, GameType gType) {
        Boolean raibow = getBlock(cells, new Vector2(XStart, YStart)).color == Color.Violet;
        Color color = getBlock(cells, new Vector2(XStart, YStart)).color;

        if (!raibow)
            return InCircle(cells, XStart, YStart, color);
        else
        {
            Boolean ret = false;
            for (int i = 0; i < 4; i++)
                ret = ret || InCircle(cells, XStart, YStart, DataControll.Colors[i]);
            return ret;
        }
    }

    //режим колец
    private Boolean InCircle(List<CellItem> cells, int XStart, int YStart, Color color) {
        List<CellItem> DeleteCell = new ArrayList<CellItem>();
        Vector2 Start = new Vector2(XStart, YStart);

        List<Vector2> temp = new ArrayList<Vector2>();
        temp.add(new Vector2(XStart, YStart));

        while (temp.size() > 0) {
            Vector2 pointer = temp.get(0);
            temp.remove(0);

            if (pointer.X != -1 && pointer.Y != -1) {
                temp.add(inCircleProcces(DeleteCell, cells, pointer.add(1, 0), color));
                temp.add(inCircleProcces(DeleteCell, cells, pointer.add(-1, 0), color));
                temp.add(inCircleProcces(DeleteCell, cells, pointer.add(0, 1), color));
                temp.add(inCircleProcces(DeleteCell, cells, pointer.add(0, -1), color));
            }
        }

        int index = 0;
        while (true) {
            if (index >= DeleteCell.size()) {
                break;
            }

            Vector2 pointer = new Vector2(DeleteCell.get(index).X, DeleteCell.get(index).Y);
            deleteNotFitCircle(DeleteCell, pointer, color);
            index++;
        }

        //TODO: проверить что это квадрат а не случайная замкнутая фигура

        Boolean deleted = false;

        //удаление
        int scoreDelta = GetScore(8, DeleteCell.size());
        if (DeleteCell.size() >= 8) {
            for (CellItem item : DeleteCell) {
                item.Delete(scoreDelta / DeleteCell.size());
                floatMessageControll.addMessage("+" + (scoreDelta / DeleteCell.size()), item.Position.add(DataControll.FieldPivotPoint).add(DataControll.I.GetCubeSize().div(2)), FloatTextColor, FloatTextName, FloatTextSize, false);
                deleted = true;
            }
        }

        cubesCountControll();
        cubesCountControllForCircles();

        return deleted;
    }

    private Vector2 inCircleProcces(List<CellItem> DeleteCell, List<CellItem> cells, Vector2 pointer, Color color) {
        if (getBlock(cells, pointer, color) != null && getBlock(DeleteCell, pointer) == null) {
            DeleteCell.add(getBlock(cells, pointer, color));
            return pointer;
        } else {
            return new Vector2(-1);
        }
    }

    public void deleteNotFitCircle(List<CellItem> cells, Vector2 pointer, Color color) {
        CellItem block = getBlock(cells, pointer, color);
        if (block != null && GetConnectionCount(cells, block, color) < 2) {
            cells.remove(block);
            deleteNotFitCircle(cells, pointer.add(1, 0), color);
            deleteNotFitCircle(cells, pointer.add(-1, 0), color);
            deleteNotFitCircle(cells, pointer.add(0, -1), color);
            deleteNotFitCircle(cells, pointer.add(0, 1), color);
        }
    }

    private int GetConnectionCount(List<CellItem> cells, CellItem item, Color color) {
        int connections = 0;
        Vector2 pointer = new Vector2(item.X, item.Y);

        if (getBlock(cells, pointer.add(1, 0), color) != null) {
            connections += 1;
        }
        if (getBlock(cells, pointer.add(-1, 0), color) != null) {
            connections += 1;
        }
        if (getBlock(cells, pointer.add(0, 1), color) != null) {
            connections += 1;
        }
        if (getBlock(cells, pointer.add(0, -1), color) != null) {
            connections += 1;
        }

        return connections;
    }
}
