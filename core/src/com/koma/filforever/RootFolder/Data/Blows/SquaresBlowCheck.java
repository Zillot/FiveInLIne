package com.koma.filforever.RootFolder.Data.Blows;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.RootFolder.Data.CellItem;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.GameType;

import java.util.ArrayList;
import java.util.List;

public class SquaresBlowCheck extends BlowCheckBase {
    //удаление чегонибудь, зависит от type
    public Boolean BlowCheck(List<CellItem> cells, int XStart, int YStart, GameType gType) {
        Boolean raibow = getBlock(cells, new Vector2(XStart, YStart)).color == Color.Violet;
        Color color = getBlock(cells, new Vector2(XStart, YStart)).color;

        if (!raibow) {
            return InSquare(cells, XStart, YStart, color);
        } else {
            Boolean ret = false;
            for (int i = 0; i < 4; i++) {
                ret = ret || InSquare(cells, XStart, YStart, DataControll.Colors[i]);
            }
            return ret;
        }
    }

    //логика удаления квадратов и прямоугольников
    private Boolean InSquare(List<CellItem> cells, int XStart, int YStart, Color color) {
        List<CellItem> DeleteCell = new ArrayList<CellItem>();
        Vector2 Start = new Vector2(XStart, YStart);

        //в лево
        Boolean left = true;
        while (left) {
            left = false;
            if ((getBlock(cells, Start.add(-1, 0), color) != null &&
                    getBlock(cells, Start.add(-1, -1), color) != null &&
                    getBlock(cells, Start.add(0, -1), color) != null) ||
                    (getBlock(cells, Start.add(-1, 0), color) != null &&
                            getBlock(cells, Start.add(-1, 1), color) != null &&
                            getBlock(cells, Start.add(0, 1), color) != null)) {
                left = true;
                Start = new Vector2(Start.X - 1, Start.Y);
            }
        }
        //в верх
        Boolean up = true;
        while (up) {
            up = false;
            if ((getBlock(cells, Start.add(0, -1), color) != null &&
                    getBlock(cells, Start.add(1, 0), color) != null &&
                    getBlock(cells, Start.add(1, -1), color) != null)) {
                up = true;
                Start = new Vector2(Start.X, Start.Y - 1);
            }
        }
        List<Vector2> temp = new ArrayList<Vector2>();
        temp.add(Start);
        //на удаление
        while (temp.size() != 0) {
            List<Vector2> verytemp = new ArrayList<Vector2>();
            for (Vector2 item : temp) {
                DeleteCell.add(getBlock(cells, item));
                if (getBlock(cells, item.add(1, 0), color) != null &&
                        getBlock(cells, item.add(1, 1), color) != null &&
                        getBlock(cells, item.add(0, 1), color) != null) {
                    verytemp.add(new Vector2(item.X + 1, item.Y));
                    verytemp.add(new Vector2(item.X + 1, item.Y + 1));
                    verytemp.add(new Vector2(item.X, item.Y + 1));
                }
            }
            temp = new ArrayList<Vector2>(verytemp);
        }

        Boolean deleted = false;

        //удаление
        int scoreDelta = GetScore(4, DeleteCell.size());
        if (DeleteCell.size() > 3) {
            DeleteCell = DeleteDuplicates(DeleteCell);

            for (CellItem item : DeleteCell) {
                item.Delete(scoreDelta / DeleteCell.size());
                floatMessageControll.addMessage("+" + (scoreDelta / DeleteCell.size()), item.Position.add(DataControll.FieldPivotPoint).add(DataControll.I.GetCubeSize().div(2)), FloatTextColor, FloatTextName, FloatTextSize, false);
                deleted = true;
            }
        }

        cubesCountControll();

        return deleted;
    }
}
