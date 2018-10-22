package com.koma.filforever.RootFolder.Data;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatControllerStatus;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

import java.io.Serializable;
import java.util.List;

public class PredictionItem extends CellItem {
    public Vector2 PredictionPosition;
    private Vector2 PredictionRealPosition;

    public PredictionItem() {

    }
    public PredictionItem(Color color, Vector2 pos, Vector2 predPos) {
        super(color, pos);
        PredictionRealPosition = DataControll.I.GetCubeSize().mul(new Vector2(predPos.X, predPos.Y));
        PredictionPosition = predPos;
    }

    public String getStringToWrite() {
        String toRet = "";

        toRet += "Status:" + Status.toString() + "&";
        toRet += "Pos:" + X + "%" + Y + "&";
        toRet += "PrPos:" + PredictionPosition.X + "%" + PredictionPosition.Y + "&";
        toRet += "Color:" + color.R + "%" + color.G + "%" + color.B + "%" + color.A + ";";

        return toRet;
    }

    public static PredictionItem createFromString(String str) {
        PredictionItem item = new PredictionItem();
        String[] data = str.split("&");
        String[] localParts;

        if (data.length >= 1) {
            for (String part : data) {
                if (part.length() > 0) {
                    String[] KeyValue = part.split(":");

                    if (KeyValue.length >= 2) {
                        if (KeyValue[0].compareTo("Status") == 0) {
                            item.Status = CellSatus.valueOf(KeyValue[1]);
                        }
                    }
                    if (KeyValue[0].compareTo("Pos") == 0) {
                        localParts = KeyValue[1].split("%");
                        item.X = Integer.parseInt(localParts[0]);
                        item.Y = Integer.parseInt(localParts[1]);
                    }
                    if (KeyValue[0].compareTo("PrPos") == 0) {
                        localParts = KeyValue[1].split("%");
                        item.PredictionPosition = new Vector2(0, 0);
                        item.PredictionPosition.X = Integer.parseInt(localParts[0]);
                        item.PredictionPosition.Y = Integer.parseInt(localParts[1]);
                        item.PredictionRealPosition = DataControll.I.GetCubeSize().mul(new Vector2(item.PredictionPosition.X, item.PredictionPosition.Y));
                    }
                    if (KeyValue[0].compareTo("Color") == 0) {
                        localParts = KeyValue[1].split("%");

                        int R = (int) Float.parseFloat(localParts[0]);
                        int G = (int) Float.parseFloat(localParts[1]);
                        int B = (int) Float.parseFloat(localParts[2]);
                        int A = (int) Float.parseFloat(localParts[3]);

                        item.color = new Color(R, G, B, A);
                    }
                    if (KeyValue[0].compareTo("ScoreOnDel") == 0) {
                        item.ScoreOnDelete = Integer.parseInt(KeyValue[1]);
                    }
                    if (KeyValue[0].compareTo("ColorIndex") == 0) {
                        item.ScoreOnDelete = Integer.parseInt(KeyValue[1]);
                    }
                }
            }
        }

        switch (item.Status) {
            case Apearing:
                break;
            case Disapearing:
                item.animationDelta.Value = 1;
                break;
            case Moving:
                item.Status = CellSatus.NeedToProccess;
                item.animationDelta.Value = 1;
                break;
            case NeedToProccess:
            case Stable:
            case JustMovedOn:
                item.animationDelta.Value = 1;
                break;
        }

        item.Position = DataControll.I.GetCubeSize().mul(new Vector2(item.X, item.Y));

        return item;
    }

    public void DrawPrediciton(SpriteAdapter spritebacth, Vector2 offset) {
        Vector2 cellSize = DataControll.I.GetCubeSize();
        Vector2 Size = cellSize.sub(4, 4).mul(0.2f);
        Vector2 pos = PredictionRealPosition.add(cellSize.X / 2 - Size.X / 2 , cellSize.Y / 2 - Size.Y / 2);

        if (color.color != Color.Violet.color) {
            //рисуем тело блока
            spritebacth.FillRectangle(offset.add(pos).add(Size.mul(animationDelta.GetInvertedValue()).div(2)), Size.mul(animationDelta.Value), Color.Black, animationDelta.Value * 0.5f, 0, 10);
        }
    }
}

