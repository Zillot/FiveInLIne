package com.koma.filforever.RootFolder.Data.Blows;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.FloatMessageControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.RootFolder.Data.CellItem;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.GameType;
import com.koma.filforever.RootFolder.Data.SaveLoadService;
import com.koma.filforever.RootFolder.Data.ScoreItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlowCheckBase {
    public FloatMessageControll floatMessageControll;

    //количество создаваемых кубиков
    public int ToCreateCubes;
    //начальное количество кубиков
    //PRODUCTION: СДЕСЬ ДОЛЖНО СТОЯТЬ 15
    public int ToCreateCubesOnInit = 15;

    public float FloatTextSize = 1.0f;
    public String FloatTextName = "Arial32";
    public Color FloatTextColor = Color.Black;

    public int multiply;

    public void reset() {
        ToCreateCubes = 1;
        DataControll.BestScores = new ArrayList<Integer>();
        floatMessageControll = new FloatMessageControll();

        SaveLoadService.I.LoadSetups();

        if (DataControll.BestScores == null) {
            DataControll.BestScores = new ArrayList<Integer>();
        }

        if (DataControll.BestScores.size() < DataControll.I.GameModes.size()) {
            for (int i = DataControll.BestScores.size(); i < DataControll.I.GameModes.size(); i++) {
                DataControll.BestScores.add(0);
            }
        }

        SaveLoadService.I.getScore(DataControll.GameModeIndex, DataControll.GameModeIndex + "", getScore_DownloadStringCompleted);
        DataControll.Score = 0;

        multiply = 1;
    }

    public Delegate getScore_DownloadStringCompleted = new Delegate() {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            List<ScoreItem> rootObject = new ArrayList<ScoreItem>();

            ArrayList<JsonValue> list = new Json().fromJson(ArrayList.class, httpResponse.getResultAsString());
            for (JsonValue v : list) {
                rootObject.add(new Json().readValue(ScoreItem.class, v));
            }

            if (rootObject.size() == 0) {
                return;
            }

            int score = rootObject.get(0).Value;
            int index = (Integer.parseInt(UserToken.replace("getScore", "")));

            if (score > DataControll.BestScores.get(index)) {
                DataControll.BestScores.add(index + 1, score);
                DataControll.BestScores.remove(index);
            }
        }
    };

    public Boolean checkBestScore(GameType type) {
        int index = DataControll.I.GameModes.indexOf(type.toString());

        if (DataControll.Score > DataControll.BestScores.get(index)) {
            DataControll.BestScores.add(index, DataControll.Score);
            DataControll.BestScores.remove(index + 1);
            SaveLoadService.I.saveScore(DataControll.GameModeIndex, DataControll.Score, null);
            SaveLoadService.I.SaveSetups();
            return true;
        }

        return false;
    }

    public void cubesCountControll() {
        if (DataControll.Score >= 200) {
            ToCreateCubes = 2;
        }
        if (DataControll.Score >= 1000) {
            ToCreateCubes = 3;
        }
        if (DataControll.Score >= 3000) {
            ToCreateCubes = 4;
        }
        if (DataControll.Score >= 6000) {
            ToCreateCubes = 5;
        }
        if (DataControll.Score >= 10000) {
            ToCreateCubes = 6;
        }
    }

    public void cubesCountControllForCircles() {
        if (DataControll.Score >= 1000) {
            ToCreateCubes = 2;
        }
        if (DataControll.Score >= 3000) {
            ToCreateCubes = 3;
        }
        if (DataControll.Score >= 6000) {
            ToCreateCubes = 4;
        }
        if (DataControll.Score >= 10000) {
            ToCreateCubes = 5;
        }
        if (DataControll.Score >= 15000) {
            ToCreateCubes = 6;
        }
    }

    //удаление чегонибудь, зависит от type
    public Boolean BlowCheck(List<CellItem> cells, int XStart, int YStart, GameType gType) {
        return false;
    }

    //нахождение пути
    public List<Vector2> GetWay(List<CellItem> Cells, Vector2 Start, Vector2 finish) {
        //путь
        List<Vector2> way = new ArrayList<Vector2>();

        //карта
        int[][] map = new int[DataControll.Columns][DataControll.Rows];
        for (CellItem item : Cells) {
            map[item.X][item.Y] = -1;
        }

        List<Vector2> temp = new ArrayList<Vector2>();
        temp.add(Start);
        map[(int) Start.X][(int) Start.Y] = 1;
        //простивляем индексы на карте
        int index = map.length * map[0].length;
        while (map[(int) finish.X][(int) finish.Y] == 0) {
            if (index == 0) {
                break;
            }
            index--;
            List<Vector2> verytemp = new ArrayList<Vector2>();
            for (Vector2 item : temp) {
                //Вправо
                if (item.X + 1 < map.length && map[(int) item.X + 1][(int) item.Y] != -1 && map[(int) item.X + 1][(int) item.Y] == 0) //< map[(int)item.X, (int)item.Y])
                {
                    map[(int) item.X + 1][(int) item.Y] = map[(int) item.X][(int) item.Y] + 1;
                    verytemp.add(new Vector2(item.X + 1, item.Y));
                }
                //Вниз
                if (item.Y + 1 < map[0].length && map[(int) item.X][(int) item.Y + 1] != -1 && map[(int) item.X][(int) item.Y + 1] == 0) // < map[(int)item.X, (int)item.Y])
                {
                    map[(int) item.X][(int) item.Y + 1] = map[(int) item.X][(int) item.Y] + 1;
                    verytemp.add(new Vector2(item.X, item.Y + 1));
                }
                //Влево
                if (item.X - 1 >= 0 && map[(int) item.X - 1][(int) item.Y] != -1 && map[(int) item.X - 1][(int) item.Y] == 0) // < map[(int)item.X, (int)item.Y])
                {
                    map[(int) item.X - 1][(int) item.Y] = map[(int) item.X][(int) item.Y] + 1;
                    verytemp.add(new Vector2(item.X - 1, item.Y));
                }
                //Вверх
                if (item.Y - 1 >= 0 && map[(int) item.X][(int) item.Y - 1] != -1 && map[(int) item.X][(int) item.Y - 1] == 0) //< map[(int)item.X, (int)item.Y])
                {
                    map[(int) item.X][(int) item.Y - 1] = map[(int) item.X][(int) item.Y] + 1;
                    verytemp.add(new Vector2(item.X, item.Y - 1));
                }
            }

            temp = verytemp;
        }

        //пути нет
        if (map[(int) finish.X][(int) finish.Y] == 0) {
            return way;
        }

        //составление пути
        Vector2 select = finish;
        while (select != Start) {
            way.add(select);
            //Вправо
            if (select.X + 1 < map.length && map[(int) select.X + 1][(int) select.Y] != -1 && map[(int) select.X + 1][(int) select.Y] == map[(int) select.X][(int) select.Y] - 1) {
                select = new Vector2(select.X + 1, select.Y);
                continue;
            }
            //Вниз
            if (select.Y + 1 < map[0].length && map[(int) select.X][(int) select.Y + 1] != -1 && map[(int) select.X][(int) select.Y + 1] == map[(int) select.X][(int) select.Y] - 1) {
                select = new Vector2(select.X, select.Y + 1);
                continue;
            }
            //Влево
            if (select.X - 1 >= 0 && map[(int) select.X - 1][(int) select.Y] != -1 && map[(int) select.X - 1][(int) select.Y] == map[(int) select.X][(int) select.Y] - 1) {
                select = new Vector2(select.X - 1, select.Y);
                continue;
            }
            //Вверх
            if (select.Y - 1 >= 0 && map[(int) select.X][(int) select.Y - 1] != -1 && map[(int) select.X][(int) select.Y - 1] == map[(int) select.X][(int) select.Y] - 1) {
                select = new Vector2(select.X, select.Y - 1);
                continue;
            }
            break;
        }
        Collections.reverse(way);

        return way;
    }

    public CellItem getBlock(List<CellItem> cells, Vector2 item) {
        for (CellItem localItem : cells) {
            if ((int) item.X == (int) localItem.X && (int) localItem.Y == (int) item.Y) {
                return localItem;
            }
        }

        return null;
    }

    public CellItem getBlock(List<CellItem> cells, Vector2 item, Color color) {
        for (CellItem localItem : cells) {
            if ((int) item.X == (int) localItem.X && (int) localItem.Y == (int) item.Y && (localItem.color.color == color.color || localItem.color.color == Color.Violet.color)) {
                return localItem;
            }
        }

        return null;
    }

    public List<CellItem> DeleteDuplicates(List<CellItem> List) {
        List<CellItem> copy = new ArrayList<CellItem>();

        for (CellItem item : List) {
            if (!copy.contains(item)) {
                copy.add(item);
            }
        }

        return copy;
    }

    //подсчот очков
    public int GetScore(int minCount, int count) {
        if (count >= minCount) {
            int scoreDelta = 0;
            for (int i = 0; i < count; i++) {
                scoreDelta += ((i / minCount) + 1) * 10 * multiply;
            }
            DataControll.Score += scoreDelta;
            return scoreDelta;
        }
        return 0;
    }}
