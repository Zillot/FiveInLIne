package com.koma.filforever.RootFolder.Data;

import com.badlogic.gdx.Gdx;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatControllerStatus;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.RootFolder.Data.CellSatus;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class CellItem implements Serializable {
    //объект управляющий анимацией
    public FloatController animationDelta;
    //состояние блока (Нуждаеться в прощете для взрыва, Стабилен, Появляеться, Изчезает, в процессе перемещения, как раз переместился)
    public CellSatus Status;
    //Позиция блока
    public Vector2 Position;
    //Цвет блока
    public Color color;
    //Флаг указывающий на выбраное состояние блока
    public Boolean Selected;

    public int ScoreOnDelete;

    //Путь для перемещения
    public float DistanceToChangeDirection;
    public List<Vector2> PathToMove;
    public Vector2 MoveDirection;
    public Vector2 NextStep;

    public int ColorIndex;
    public float ColorIndexChange;
    public float ColorIndexChangeBase = 0.1f;

    //позиция в массиве
    public int X;
    public int Y;

    public CellItem(Color color, Vector2 pos) {
        ScoreOnDelete = 0;
        ColorIndex = 0;

        PathToMove = null;
        animationDelta = new FloatController(DataControll.BlockAnimationSpeed);
        animationDelta.GoUp();
        animationDelta.Value = 0.01f;

        this.color = color;

        Selected = false;
        Status = CellSatus.Apearing;
        Position = DataControll.I.GetCubeSize().mul(new Vector2(pos.X, pos.Y));

        X = (int) pos.X;
        Y = (int) pos.Y;
    }

    public CellItem(Color color) {
        this(color, new Vector2(0));
    }

    public CellItem() {
        this(Color.Red);
    }

    public void ReApear() {
        animationDelta = new FloatController(DataControll.BlockAnimationSpeed);
        animationDelta.GoUp();
        animationDelta.Value = 0.01f;
        Status = CellSatus.Apearing;
        Selected = false;
    }

    //вернет true если SomePosition находиться над блоком
    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, Position, DataControll.I.GetCubeSize());
    }

    //проверка на попадание тапа(клика) на блок
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
        if (Status == CellSatus.Stable && TouchEventName == TouchEventType.Tap) {
            if (InUnderControll(ToouchPosition)) {
                Selected = true;
                SpriteAdapter.SelfPointer.PlaySound("Tap2", 1);
            }
        }
    }

    //вернет цвет блока в виде индекса
    public int GetColorKey() {
        if (Status == CellSatus.Disapearing) {
            return 0;
        }

        return 1;
    }

    //начинаем удалять(взрывать) блок
    public void Delete(int score) {
        ScoreOnDelete = score;

        Status = CellSatus.Disapearing;
        animationDelta.GoDown();
    }

    //перемещяем блок в новую точку, согласно путии
    public void GoTo(List<Vector2> Path)// int Xi, int Yi)
    {
        Status = CellSatus.Moving;

        X = (int) Path.get(Path.size() - 1).X;
        Y = (int) Path.get(Path.size() - 1).Y;

        PathToMove = Path;
        NextStep = PathToMove.get(0).mul(DataControll.I.GetCubeSize());
        PathToMove.remove(0);
        MoveDirection = Vector2.Normalize(NextStep.sub(Position));
        DistanceToChangeDirection = NextStep.sub(Position).getLen();
    }

    //Вернет true если блок уже "мертв" (взрован)
    public Boolean isDead() {
        return (Status == CellSatus.Disapearing && animationDelta.Operation == FloatControllerStatus.Stop) || animationDelta.Value == 0;
    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        if (color.color == Color.Violet.color) {
            ColorIndexChange -= timeDelta;
        }

        if (ColorIndexChange <= 0) {
            toRet = true;

            ColorIndexChange = ColorIndexChangeBase;
            ColorIndex++;

            if (ColorIndex >= 4) {
                ColorIndex = 0;
            }
        }

        if (Status != CellSatus.Disapearing && animationDelta == null) {
            animationDelta = new FloatController(DataControll.BlockAnimationSpeed);
            animationDelta.GoUp();
            animationDelta.Value = 1f;
        }

        if (Status == CellSatus.Moving) {
            Vector2 Offset = MoveDirection.mul(DataControll.BlockMoveSpeed * timeDelta);
            Position = Position.add(Offset);
            DistanceToChangeDirection -= Offset.getLen();

            if (DistanceToChangeDirection <= 0) {
                Position = NextStep;
                if (PathToMove.size() > 0) {
                    NextStep = PathToMove.get(0).mul(DataControll.I.GetCubeSize());
                    PathToMove.remove(0);
                    MoveDirection = Vector2.Normalize(NextStep.sub(Position));
                    DistanceToChangeDirection = NextStep.sub(Position).getLen();
                } else {
                    Status = CellSatus.JustMovedOn;
                }
            }
        } else {
            animationDelta.Update(timeDelta);

            if (animationDelta.GetRegularValue() == 1 && Status == CellSatus.Apearing) {
                Status = CellSatus.NeedToProccess;
            }
        }

        if (Status != CellSatus.Stable && Status != CellSatus.NeedToProccess) {
            toRet = true;
        }

        return toRet;
    }

    public String getStringToWrite() {
        String toRet = "";

        toRet += "Status:" + Status.toString() + "&";
        toRet += "Pos:" + X + "%" + Y + "&";
        toRet += "Color:" + color.R + "%" + color.G + "%" + color.B + "%" + color.A + ";";

        return toRet;
    }

    public static CellItem createFromString(String str) {
        CellItem item = new CellItem();
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

    public void Draw(SpriteAdapter spritebacth, Vector2 offset) {
        Vector2 Size = DataControll.I.GetCubeSize().sub(4, 4);
        Vector2 pos = Position.add(2, 2);

        if (color.color != Color.Violet.color) {
            //рисуем тело блока
            spritebacth.FillRectangle(offset.add(pos).add(Size.mul(animationDelta.GetInvertedValue()).div(2)), Size.mul(animationDelta.Value), color, animationDelta.Value, 0, 10);
        } else {
            //рисуем радужный блок
            if (color.color == Color.Violet.color) {
                Vector2 CubeSize = DataControll.I.GetCubeSize().sub(3, 3).div(2);
                Vector2 CubeOffset = CubeSize;
                Vector2 RaibowCubeOffset = offset.add(pos).add(Size.div(2)).add(CubeSize.div(2).mul(animationDelta.GetInvertedValue())).add(2.5f, 2.5f);
                CubeSize = CubeSize.sub(5, 5).mul(animationDelta.Value);

                spritebacth.FillRectangle(RaibowCubeOffset.sub(0, 0), CubeSize, DataControll.Colors[0], animationDelta.Value, 0, 10);
                spritebacth.FillRectangle(RaibowCubeOffset.sub(CubeOffset.X, 0), CubeSize, DataControll.Colors[1], animationDelta.Value, 0, 10);
                spritebacth.FillRectangle(RaibowCubeOffset.sub(0, CubeOffset.Y), CubeSize, DataControll.Colors[2], animationDelta.Value, 0, 10);
                spritebacth.FillRectangle(RaibowCubeOffset.sub(CubeOffset.X, CubeOffset.Y), CubeSize, DataControll.Colors[3], animationDelta.Value, 0, 10);

                spritebacth.FillRectangle(offset.add(pos).add(Size.div(2)).sub(0, 25 * animationDelta.Value), new Vector2(35).mul(animationDelta.Value), Color.White, animationDelta.Value, 0.785f, 11);
                spritebacth.FillRectangle(offset.add(pos).add(Size.div(2)).sub(0, 19 * animationDelta.Value), new Vector2(27).mul(animationDelta.Value), DataControll.Colors[ColorIndex], animationDelta.Value, 0.785f, 12);
            }
        }

        /*if (Status == CellSatus.Disapearing) {
            spritebacth.DrawText("+" + ScoreOnDelete, offset.add(pos).add(DataControll.I.GetCubeSize().div(2)), DataControll.I.GetColorAfter(color), 0.5f + 0.5f * animationDelta.GetRegularValue(), "BArial20", 0.5f + 0.5f + animationDelta.GetInvertedValue(), TextAlign.CenterXY, 0, 18);
        }*/

        //рисуем полупрозрачный маркер поверх, говорящий о том что блок выделен
        if (Selected) {
            spritebacth.FillRectangle(offset.add(pos).add(Size.mul(animationDelta.GetInvertedValue()).div(2)), Size.mul(animationDelta.Value), Color.Black, 0.6f, 0, 16);
        }

        //PRODUCTION: убрать для релиза
        //spriteBatch.FillRectangle(offset.add(pos).add(Size.div(2).sub(3, 3)), new Vector2(6), Color.Black, 1, 0, 9);
    }
}

