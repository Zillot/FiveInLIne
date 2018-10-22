package com.koma.filforever.RootFolder.Data;

import com.koma.filforever.RootFolder.Data.CellItem;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class LastGame implements Serializable {
    public List<CellItem> Field;
    public List<PredictionItem> Queue;
    public GameType GameType;

    public Boolean newBest;
    public int Score;
    public int UndoCharges;
    public int PlusCubeCharges;

    public List<String> savesForUndo;

    public String GetAsString() {
        String toRet = "";

        toRet += "Version?1.0)";

        toRet += "Field?";
        for (CellItem item : Field) {
            toRet += item.getStringToWrite();
        }
        toRet += ")";

        toRet += "Queue?";
        for (CellItem item : Queue) {
            toRet += item.getStringToWrite();
        }
        toRet += ")";

        toRet += "Score?" + Score + ")";
        toRet += "GameType?" + GameType + ")";
        toRet += "UndoCharges?" + UndoCharges + ")";
        toRet += "PlusCubeCharges?" + PlusCubeCharges + ")";
        toRet += "newBest?" + newBest;

        return toRet;
    }

    public void InitWithString(String data) {
        String version = "";

        if (data.length() > 0) {
            Field = new LinkedList<CellItem>();
            Queue = new LinkedList<PredictionItem>();

            String[] info = data.split("\\)");

            if (info.length >= 1) {
                for (String item : info) {
                    if (version.compareTo("") == 0 || version.compareTo("1.0") == 0) {
                        String[] LocalData = item.split("\\?");
                        if (LocalData.length >= 1) {
                            if (LocalData[0].compareTo("Version") == 0) {
                                version = LocalData[1];
                            }
                            if (LocalData[0].compareTo("Field") == 0) {
                                String[] itemOnField = LocalData[1].split(";");
                                for (String cellItem : itemOnField) {
                                    Field.add(CellItem.createFromString(cellItem));
                                }
                            }
                            if (LocalData[0].compareTo("Queue") == 0) {
                                String[] queueToCreate = LocalData[1].split(";");
                                for (String cellItem : queueToCreate) {
                                    Queue.add(PredictionItem.createFromString(cellItem));
                                }
                            }
                            if (LocalData[0].compareTo("Score") == 0) {
                                Score = Integer.parseInt(LocalData[1]);
                            }
                            if (LocalData[0].compareTo("GameType") == 0) {
                                GameType = GameType.valueOf(LocalData[1]);
                            }
                            if (LocalData[0].compareTo("UndoCharges") == 0) {
                                UndoCharges = Integer.parseInt(LocalData[1]);
                            }
                            if (LocalData[0].compareTo("PlusCubeCharges") == 0) {
                                PlusCubeCharges = Integer.parseInt(LocalData[1]);
                            }
                            if (LocalData[0].compareTo("newBest") == 0) {
                                newBest = Boolean.parseBoolean(LocalData[1]);
                            }
                        }
                    }
                }
            }
        }
    }
}
