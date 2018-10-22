package com.koma.filforever.RootFolder.Data;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.FloatMessageControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatControllerStatus;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.RootFolder.Data.CellSatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class DataControll {
    public static Random rand = new Random();

    //размер окна игры
    public static float WindowWidth = 480;
    public static float WindowHeight = 800;

    //цвета
    public static Color[] Colors = new Color[]{Color.Orange, Color.Blue, Color.Green, Color.Red};
    public static Color[] IcoColors = new Color[]{Color.Blue, Color.Red, Color.White, Color.Yellow, Color.Green, Color.Black};

    //размер кубика предсказателя(кубик что ждет момента для появления)
    public static float NextCubeWidth = 56.25f;
    public static float NextCubeHeight = 56.25f;

    //размер клеточки
    public static float CellWidth = 60;
    public static float CellHeight = 60;

    //параметры поля, количество строки и колонок
    public static int Rows = 10;
    public static int Columns = 8;

    //начальные точки поля игры, и поля кубиков предсказателей(очеред на появление)
    public static Vector2 FieldPivotPoint = new Vector2(15, 145);
    public static Vector2 NextCubesPivotPoint = new Vector2(296.25f, 16.25f);

    //размер поля игры
    public static float FieldWidth = 450;
    public static float FieldHeight = 562.5f;

    //размеры поля игры, и поля кубиков предсказателей(очеред на появление)
    public static float NextCubesFieldWidth = 168.75f;
    public static float NextCubesFieldHeight = 112.5f;

    //скорость движения блоков
    public static float BlockMoveSpeed = 800.0f;
    //Скорость анимации блоков
    public static float BlockAnimationSpeed = 4.0f;

    //индекс режима игры
    public static int GameModeIndex = 0;
    //индекс языка
    public static int LanguageIndex = -1;

    public static int MaxUndoLenght = 3;
    public static int UndoChargesOnStart = 3;
    public static int PlusCubeChargesOnStart = 2;

    public static DataControll I = new DataControll();
    public static List<Integer> BestScores;
    public static int Score;

    public static String Version = "2.0.3.0";

    public TreeMap<String, int[][]> Icons;

    public List<String> GameModes;
    public List<String> Flags;

    public String PlayerName;
    public int GamePlayed;

    public DataControll() {
        GamePlayed = 0;
        PlayerName = "";
        GameModes = new ArrayList<String>();

        for (GameType item : GameType.values()) {
            GameModes.add(item.name());
        }

        Flags = new ArrayList<String>();

        Flags.add("ENLanguage");
        Flags.add("UALanguage");
        Flags.add("RULanguage");

        Icons = new TreeMap<String, int[][]>();
        Icons.put(GameModes.get(0), new int[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}});

        Icons.put(GameModes.get(1), new int[][]{
                {5, 5, 5},
                {5, 5, 5}});

        Icons.put(GameModes.get(2), new int[][]{
                {0, 2, 2, 2, 2, 0},
                {2, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0},
                {0, 2, 2, 2, 2, 0},
                {0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 2},
                {0, 2, 2, 2, 2, 0}});

        /*Icons.put(GameModes.get(3), new int[][] {
                { 4, 4, 4, 4 },
                { 4, 0, 0, 4 },
                { 4, 4, 4, 4 } });*/

        Icons.put("MenuIco", new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 6, 0, 0, 0, 0, 6, 0},
                {0, 0, 6, 0, 0, 6, 0, 0},
                {0, 0, 0, 6, 6, 0, 0, 0},
                {0, 0, 0, 6, 6, 0, 0, 0},
                {0, 0, 6, 0, 0, 6, 0, 0},
                {0, 6, 0, 0, 0, 0, 6, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}});

        Icons.put("BackIco", new int[][]{
                {6, 0, 0, 6, 6, 0, 0},
                {6, 0, 6, 0, 0, 6, 0},
                {6, 6, 0, 0, 0, 0, 6},
                {6, 6, 6, 6, 0, 0, 6},
                {0, 0, 0, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 6, 0},
                {0, 0, 6, 6, 6, 0, 0}});

        Icons.put("NameChangeIco", new int[][]{
                {5, 0, 0, 0, 5},
                {5, 5, 0, 0, 5},
                {5, 0, 5, 0, 5},
                {5, 0, 5, 0, 5},
                {5, 0, 0, 5, 5},
                {5, 0, 0, 0, 5}});

        Icons.put("ArrowDown", new int[][]{
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0},
                {0, 0, 1, 0, 0}});

        Icons.put("ArrowUp", new int[][]{
                {0, 0, 1, 0, 0},
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1}});

        Icons.put("ArrowLeft", new int[][]{
                {0, 0, 2},
                {0, 2, 2},
                {2, 2, 2},
                {0, 2, 2},
                {0, 0, 2}});

        Icons.put("ArrowRight", new int[][]{
                {2, 0, 0},
                {2, 2, 0},
                {2, 2, 2},
                {2, 2, 0},
                {2, 0, 0}});

        Icons.put("UALanguage", new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4}});

        Icons.put("RULanguage", new int[][]{
                {3, 3, 3, 3, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 3},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 2, 2}});

        //не используеться
        Icons.put("ENLanguage1", new int[][]{
                {6, 6, 11, 1, 1, 2, 2, 1, 1, 11, 6, 6},
                {11, 11, 6, 11, 1, 2, 2, 1, 11, 6, 11, 11},
                {1, 1, 11, 6, 11, 2, 2, 11, 6, 11, 1, 1},
                {1, 1, 1, 11, 11, 2, 2, 11, 11, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {1, 1, 1, 11, 11, 2, 2, 11, 11, 1, 1, 1},
                {1, 1, 11, 6, 11, 2, 2, 11, 6, 11, 1, 1},
                {11, 11, 6, 11, 1, 2, 2, 1, 11, 6, 11, 11},
                {6, 6, 11, 1, 1, 2, 2, 1, 1, 11, 6, 6}});

        Icons.put("ENLanguage", new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3},
                {1, 3, 1, 3, 1, 3, 1, 2, 2, 2, 2, 2},
                {1, 1, 3, 1, 3, 1, 1, 3, 3, 3, 3, 3},
                {1, 3, 1, 3, 1, 3, 1, 2, 2, 2, 2, 2},
                {1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}});


        Icons.put("SoundOn", new int[][]{
                {0, 0, 0, 0, 6, 0, 0, 0},
                {0, 0, 0, 6, 6, 0, 6, 0},
                {6, 0, 6, 6, 6, 0, 0, 6},
                {6, 0, 6, 6, 6, 6, 0, 6},
                {6, 0, 6, 6, 6, 0, 0, 6},
                {0, 0, 0, 6, 6, 0, 6, 0},
                {0, 0, 0, 0, 6, 0, 0, 0}});

        Icons.put("SoundOff", new int[][]{
                {2, 0, 0, 0, 6, 0, 0, 2},
                {0, 2, 0, 6, 6, 0, 2, 0},
                {6, 0, 2, 6, 6, 2, 0, 6},
                {6, 0, 6, 2, 2, 6, 0, 6},
                {6, 0, 2, 6, 6, 2, 0, 6},
                {0, 2, 0, 6, 6, 0, 2, 0},
                {2, 0, 0, 0, 6, 0, 0, 2}});

        Icons.put("AvatarIco", new int[][]{
                {0, 0, 0, 0, 5, 5, 0, 0, 0, 0},
                {0, 0, 0, 5, 5, 5, 5, 0, 0, 0},
                {0, 0, 0, 5, 5, 5, 5, 0, 0, 0},
                {0, 0, 0, 5, 5, 5, 5, 0, 0, 0},
                {0, 0, 0, 5, 5, 5, 5, 0, 0, 0},
                {0, 0, 0, 0, 5, 5, 0, 0, 0, 0},
                {0, 0, 0, 5, 5, 5, 5, 0, 0, 0},
                {0, 5, 5, 5, 5, 5, 5, 5, 5, 0},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5}});
    }

    //рисует иконку опираясь на масив интов
    public void DrawArrayIco(SpriteAdapter spriteBatch, int[][] ArrayIco, Vector2 Pivot, Vector2 Size, int layer) {
        float wi = (Size.X - 10) / ArrayIco.length;
        float hi = (Size.Y - 10) / ArrayIco[0].length;

        float size = wi < hi ? wi : hi;
        float space = (size / 100.0f) * 22.0f;

        Vector2 PosOffset = (Size.sub(new Vector2(size * ArrayIco[0].length - space, size * ArrayIco.length - space))).div(2).sub(space / 2, space / 2);

        for (int Yi = 0; Yi < ArrayIco[0].length; Yi++) {
            for (int Xi = 0; Xi < ArrayIco.length; Xi++) {
                int y = ArrayIco[Xi][Yi];
                if (ArrayIco[Xi][Yi] != 0) {
                    spriteBatch.FillRectangle(new Vector2(size * Yi + space / 2, size * Xi + space / 2).add(Pivot).add(PosOffset),
                            new Vector2(size).sub(space, space), IcoColors[y - 1], 1, 0, layer);
                }
            }
        }
    }

    //рисует иконку опираясь на масив интов
    public void DrawArrayIco(SpriteAdapter spriteBatch, int[][] ArrayIco, Vector2 Pivot, Vector2 Size) {
        DrawArrayIco(spriteBatch, ArrayIco, Pivot, Size, 60);
    }

    public Color GetColorAfter(Color color) {
        int index = 0;

        for (int i = 0; i < Colors.length; i++) {
            if (Colors[i] == color) {
                index = i + 1;
            }
        }

        if (index >= 4) {
            return Colors[0];
        }

        return Colors[index];
    }

    //вернет случайный цвет из возможных
    public Color GetRandomColor() {
        return Colors[rand.nextInt(Colors.length)];
    }

    //Вернет вектор - размер кубика на поле игры
    public Vector2 GetCubeSize() {
        return new Vector2(CellWidth, CellHeight);
    }

    //Вернет вектор - размер кубика на поле предсказаний
    public Vector2 GetNextCubeSize() {
        return new Vector2(NextCubeWidth, NextCubeHeight);
    }
}
