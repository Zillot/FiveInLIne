package com.koma.filforever.RootFolder.Data.Blows;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.RootFolder.Data.CellItem;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.GameType;

import java.util.ArrayList;
import java.util.List;

public class LinesBlowCheck extends BlowCheckBase {
    //удаление чегонибудь, зависит от type
    public Boolean BlowCheck(List<CellItem> cells, int XStart, int YStart, GameType gType) {
        Boolean raibow = getBlock(cells, new Vector2(XStart, YStart)).color == Color.Violet;
        Color color = getBlock(cells, new Vector2(XStart, YStart)).color;

        if (!raibow) {
            return InLine(cells, XStart, YStart, color);
        } else {
            Boolean ret = false;
            for (int i = 0; i < 4; i++) {
                ret = ret || InLine(cells, XStart, YStart, DataControll.Colors[i]);
            }
            return ret;
        }
    }

    //логика удаления линий
    private Boolean InLine(List<CellItem> cells, int XStart, int YStart, Color color) {
        List<CellItem> DeleteCell = new ArrayList<CellItem>();
        List<List<CellItem>> lines = new ArrayList<List<CellItem>>();
        CellItem tempstart = new CellItem();

        lines.add(InLineProccess(cells, XStart, YStart, color, new Vector2(-1, 0)));
        lines.add(InLineProccess(cells, XStart, YStart, color, new Vector2(0, -1)));
        lines.add(InLineProccess(cells, XStart, YStart, color, new Vector2(-1, -1)));
        lines.add(InLineProccess(cells, XStart, YStart, color, new Vector2(1, -1)));

        //сабираем все удаляемые елементы
        for (List<CellItem> item : lines) {
            if (item.size() > 4) {
                DeleteCell.addAll(item);
            }
        }

        Boolean deleted = false;

        //убираем дубликаты
        DeleteCell = DeleteDuplicates(DeleteCell);

        int scoreDelta = GetScore(5, DeleteCell.size());

        for (CellItem item : DeleteCell) {
            deleted = true;
            floatMessageControll.addMessage("+" + (scoreDelta / DeleteCell.size()), item.Position.add(DataControll.FieldPivotPoint).add(DataControll.I.GetCubeSize().div(2)), FloatTextColor, FloatTextName, FloatTextSize, false);
            item.Delete(scoreDelta / DeleteCell.size());
        }

        cubesCountControll();

        return deleted;
    }

    public List<CellItem> InLineProccess(List<CellItem> cells, int XStart, int YStart, Color color, Vector2 offset) {
        List<CellItem> Line = new ArrayList<CellItem>();

        CellItem tempstart = getBlock(cells, new Vector2(XStart, YStart));
        while (true) {
            CellItem verytemp = getBlock(cells, new Vector2(tempstart.X + offset.X, tempstart.Y + offset.Y), color);
            if (verytemp != null) {
                tempstart = verytemp;
            } else {
                break;
            }
        }

        while (true) {
            Line.add(tempstart);
            CellItem verytemp = getBlock(cells, new Vector2(tempstart.X - offset.X, tempstart.Y - offset.Y), color);
            if (verytemp != null) {
                tempstart = verytemp;
            } else {
                break;
            }
        }

        return Line;
    }
}
