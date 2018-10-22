package com.koma.filforever.RootFolder.GamePages;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_CircleButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_FloatMessage;

import com.koma.filforever.RootFolder.Data.Blows.BlowCheckBase;
import com.koma.filforever.RootFolder.Data.Blows.LinesBlowCheck;
import com.koma.filforever.RootFolder.Data.Blows.SnakesBlowCheck;
import com.koma.filforever.RootFolder.Data.Blows.SquaresBlowCheck;
import com.koma.filforever.RootFolder.Data.CellItem;
import com.koma.filforever.RootFolder.Data.CellSatus;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.Firework.EndlesFireworks;
import com.koma.filforever.RootFolder.Data.GameType;
import com.koma.filforever.RootFolder.Data.PredictionItem;
import com.koma.filforever.RootFolder.Data.Rain.EndlesRain;
import com.koma.filforever.RootFolder.Data.SaveLoadService;
import com.koma.filforever.RootFolder.Data.UndoService;
import com.koma.filforever.game.MainGameClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMenu extends IGameState {
    private GameMenu SelfPointer;
    public static Random rand = new Random();

    //Режим игры
    public GameType GamaType;

    public Boolean newBest;

    public EndlesFireworks firework;
    public EndlesRain rain;

    //список кубиков на поле
    public List<CellItem> Cubes;
    //список кубиков которые ждут появления
    public List<PredictionItem> CubesToCreate;

    public FloatController GameOverShade;
    public Boolean GameOver;
    public Boolean CantInteract;

    public int UndoCharges;
    public int PlusCubeCharges;
    public Boolean tryToPlaceRainbowCube;
    public BlowCheckBase blowController;

    public Boolean needToUpdate;

    public UI_FloatMessage plusCubeHint;
    public UI_FloatMessage NoChargesForUndo;
    public UI_FloatMessage NoActionForUndo;
    public UI_FloatMessage NoChargesForCubePlus;

    public GameMenu() {
        firework = new EndlesFireworks();
        rain = new EndlesRain();

        needToUpdate = false;
        CantInteract = true;
        GameOver = false;
        newBest = false;
        tryToPlaceRainbowCube = false;
        SelfPointer = this;

        plusCubeHint = new UI_FloatMessage(Color.LightGray, Color.Black, "Hint", "Tap on some free place to place rainbow cube", new Vector2(DataControll.WindowWidth, 80), 60);
        NoChargesForUndo = new UI_FloatMessage(Color.LightGray, Color.Black, "Oops", "You have no charges for undo(", new Vector2(DataControll.WindowWidth, 80), 60);
        NoActionForUndo = new UI_FloatMessage(Color.LightGray, Color.Black, "Oops", "There is nothing to undo(", new Vector2(DataControll.WindowWidth, 80), 60);
        NoChargesForCubePlus = new UI_FloatMessage(Color.LightGray, Color.Black, "Oops", "You have no charges for rainbow cube(", new Vector2(DataControll.WindowWidth, 80), 60);

        Controlls.put("Hint", plusCubeHint);
        Controlls.put("UndoOops1", NoChargesForUndo);
        Controlls.put("UndoOops2", NoActionForUndo);
        Controlls.put("UndoOops3", NoChargesForCubePlus);

        Controlls.put("MenuButton", new UI_CircleButtonControll(new Vector2(50, 40), Color.LightGray, 25, 30));
        Controlls.get("MenuButton").onTap[0] = MenuButtonHandler;
        ((UI_CircleButtonControll) Controlls.get("MenuButton")).IcoDrawEvent = MenuButtonIco;

        Controlls.put("SoundButton", new UI_CircleButtonControll(new Vector2(115, 40), Color.LightGray, 25, 30));
        Controlls.get("SoundButton").onTap[0] = SoundButtonHandler;
        ((UI_CircleButtonControll) Controlls.get("SoundButton")).IcoDrawEvent = SoundButtonIco;

        Controlls.put("UndoButton", new UI_CircleButtonControll(new Vector2(180, 40), Color.LightGray, 25, 34));
        Controlls.get("UndoButton").onTap[0] = UndoButtonHandler;
        ((UI_CircleButtonControll) Controlls.get("UndoButton")).IcoDrawEvent = UndoButtonIco;

        Controlls.put("SpecialCubeButton", new UI_CircleButtonControll(new Vector2(245, 40), Color.LightGray, 25, 34));
        Controlls.get("SpecialCubeButton").onTap[0] = SpecialCubeButtonHandler;
        ((UI_CircleButtonControll) Controlls.get("SpecialCubeButton")).IcoDrawEvent = SpecialCubeButtonIco;
    }

    public Delegate MenuButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("MenuIco"), position.sub(size.div(2)), size, 40);
        }
    };

    public Delegate SoundButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            if (SpriteAdapter.SelfPointer.Sound == 1) {
                DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("SoundOn"), position.sub(size.div(2)), size, 40);
            } else {
                DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("SoundOff"), position.sub(size.div(2)), size, 40);
            }
        }
    };

    public Delegate UndoButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("BackIco"), position.sub(size.mul(0.85f).div(2)), size.mul(0.85f), layer + 10);

            spriteBatch.FillCircle(position.add(16, 16), 12, 30, Color.Tomato, 1, layer + 11);
            spriteBatch.DrawText(UndoCharges + "", position.add(16, 16), Color.Black, 1, "Arial20", 0.6f, TextAlign.CenterXY, 0, layer + 12);
        }
    };

    public Delegate SpecialCubeButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            Color color1 = Color.Black;

            if (tryToPlaceRainbowCube) {
                color1 = Color.White;
            }

            Vector2 LeftUpCorner = position.sub(size.div(2));

            float num = size.X / 80;
            Vector2 CubeSize = (DataControll.I.GetCubeSize().sub(3, 3).div(2)).mul(num);
            Vector2 RaibowCubeOffset = position.add(2.5f, 2.5f);
            Vector2 CubeOffset = CubeSize;
            CubeSize = CubeSize.sub(5, 5);

            spriteBatch.FillRectangle(RaibowCubeOffset.sub(0, 0), CubeSize, color1, 1, 0, layer + 4);
            spriteBatch.FillRectangle(RaibowCubeOffset.sub(CubeOffset.X, 0), CubeSize, color1, 1, 0, layer + 4);
            spriteBatch.FillRectangle(RaibowCubeOffset.sub(0, CubeOffset.Y), CubeSize, color1, 1, 0, layer + 4);
            spriteBatch.FillRectangle(RaibowCubeOffset.sub(CubeOffset.X, CubeOffset.Y), CubeSize, color1, 1, 0, layer + 4);

            spriteBatch.FillRectangle(position.sub(0, 25 * num), new Vector2(35 * num), Color.LightGray, 1, 0.785f, layer + 5);
            spriteBatch.FillRectangle(position.sub(0, 19 * num), new Vector2(27 * num), color1, 1, 0.785f, layer + 6);

            spriteBatch.FillCircle(position.add(16, 16), 12, 30, Color.Tomato, 1, layer + 7);
            spriteBatch.DrawText(PlusCubeCharges + "", position.add(16, 16), Color.Black, 1, "Arial20", 0.54f, TextAlign.CenterXY, 0, layer + 8);
        }
    };

    public Delegate MenuButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("MainMenu", "", false);
        }
    };

    public Delegate SoundButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            if (SpriteAdapter.SelfPointer.Sound == 1) {
                SpriteAdapter.SelfPointer.Sound = 0;
            } else {
                SpriteAdapter.SelfPointer.Sound = 1;
            }

            SaveLoadService.I.SaveSetups();
        }
    };

    public Delegate UndoButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            if (UndoCharges > 0) {
                //пытаемя выполнить операцию undo
                if (UndoService.I.DoUndo(SelfPointer)) {
                    //если спешно, отнимаем колво ундушек и сохраняем игру
                    UndoCharges--;
                    SaveLoadService.I.SaveGame(SelfPointer, blowController);
                    SpriteAdapter.SelfPointer.PlaySound("Blow3", 1);

                    //NEWUP!
                    if (blowController.multiply > 1) {
                        blowController.multiply--;
                    }
                } else {
                    NoActionForUndo.Show(2);
                }
            } else {
                NoChargesForUndo.Show(2);
            }
        }
    };

    public Delegate SpecialCubeButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            if (PlusCubeCharges > 0) {
                plusCubeHint.Show();
                tryToPlaceRainbowCube = true;
            } else {
                NoChargesForCubePlus.Show(2);
            }
        }
    };

    @Override
    public void Initialize() {
        super.Initialize();

        rain.Disable();
        firework.Disable();
        rain.SetColor(Color.LightBlue);

        this.GamaType = GameType.valueOf(DataControll.I.GameModes.get(DataControll.GameModeIndex));

        MainGameClass.SelftPointer.ReLoadBanner();
        MainGameClass.SelftPointer.LoadBigBunner();

        SaveLoadService.I.saveStatistic("GameStarted", GamaType.toString(), null);

        if (this.GamaType == GameType.Lines) {
            blowController = new LinesBlowCheck();
        }
        else if (this.GamaType == GameType.Squares) {
            blowController = new SquaresBlowCheck();
        }
        else if (this.GamaType == GameType.Snakes) {
            blowController = new SnakesBlowCheck();
        }

        blowController.reset();

        DataControll.I.GamePlayed++;
        SaveLoadService.I.SaveSetups();

        if (DataControll.I.GamePlayed % 5 == 0) {
            MainGameClass.SelftPointer.RateRequest();
        }

        CantInteract = false;

        newBest = false;
        GameOver = false;
        Cubes = new ArrayList<CellItem>();
        CubesToCreate = new ArrayList<PredictionItem>();

        UndoCharges = DataControll.UndoChargesOnStart;
        PlusCubeCharges = DataControll.PlusCubeChargesOnStart;

        //создаем кубики для появление
        FieldNextCubesQueue(blowController.ToCreateCubesOnInit);
        //добавляем кубики на поле
        addRandomCubes();

        GameOverShade = new FloatController(1.0f);

        MainGameClass.SelftPointer.CheckMessages();
    }

    @Override
    public void Dispose() {
        super.Dispose();
    }

    @Override
    public void OnNavigatetFrom(Object data) {
        super.OnNavigatetFrom(data);

        if (rand.nextInt(100) < 20) {
            MainGameClass.SelftPointer.ShowBigBunner();
        }

        TouchService.I.onTap[0] = null;
    }

    @Override
    public void OnNavigatetTo(Object data) {
        super.OnNavigatetTo(data);

        newBest = false;
        UndoService.I.Reset();

        if (((String) data).length() > 0) {
            String[] temp = ((String) data).split(":");
            if (temp.length == 2) {
                this.GamaType = GameType.valueOf(temp[0]);

                if (temp[1].compareTo("Load") == 0) {
                    //Загрузка последней игры
                    if (SaveLoadService.I.CanLoadGame(this.GamaType)) {
                        SaveLoadService.I.LoadGame(this);

                        DataControll.I.GamePlayed--;
                        SaveLoadService.I.SaveSetups();
                    }
                }
            }
        }

        SaveLoadService.I.SaveGame(this, blowController);

        TouchService.I.onTap[0] = TapTouchEventCheck;
    }

    //обработчик тапа(кликов)
    private Delegate TapTouchEventCheck = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            if (!MainGameClass.SelftPointer.ServerMessageDialog()) {
                return;
            }

            if (GameOver) {
                RestartGame();
            }

            if (CantInteract) {
                return;
            }

            //выбраный блок
            CellItem selectedItem = null;
            //сделать перемещение
            Boolean doMove = true;

            //снимем выделение но сохраним тот блок что был выбран, если такой есть
            for (CellItem item : Cubes) {
                if (item.Selected) {
                    selectedItem = item;
                }

                needToUpdate = true;
                item.Selected = false;
            }

            //проверяем не случился ли тап(клик) на пустую клетку, и делаем движение если так оно и было
            for (CellItem item : Cubes) {
                item.TouchEventCheck(position.sub(DataControll.FieldPivotPoint), TouchEventName);

                if (item.Selected) {
                    doMove = false;
                    tryToPlaceRainbowCube = false;
                    plusCubeHint.Hide();
                }
            }

            //узнаем на какой пустой блок был тап(клик)
            Vector2 pos = position.sub(DataControll.FieldPivotPoint);
            int newX = (int) (pos.X / DataControll.CellWidth);
            int newY = (int) (pos.Y / DataControll.CellHeight);

            //если позиция в рамках массива, то делаем перемещение
            if (newX < DataControll.Columns && newY < DataControll.Rows && newX >= 0 && newY >= 0 && pos.X > 0 && pos.Y > 0) {
                //если была нажата кнопка радужного куба, ставим куб на пустую клетку
                if (tryToPlaceRainbowCube) {
                    plusCubeHint.Hide();
                    UndoService.I.SaveNewUndo(SelfPointer);
                    Cubes.add(new CellItem(Color.Violet, new Vector2(newX, newY)));
                    PlusCubeCharges--;
                    tryToPlaceRainbowCube = false;
                    SpriteAdapter.SelfPointer.PlaySound("Tap1", 1);
                    SaveLoadService.I.SaveGame(SelfPointer, blowController);
                } else {
                    if (doMove && selectedItem != null) {
                        //получаем путь к новой клеточке
                        List<Vector2> way = blowController.GetWay(Cubes, new Vector2(selectedItem.X, selectedItem.Y), new Vector2(newX, newY));
                        //сохраняем последний найденый путь
                        //LastWay = way;

                        //если путь не пустой, значит можно перемещять
                        if (way.size() > 0) {
                            //сохраняем состояние игры для операции undo
                            UndoService.I.SaveNewUndo(SelfPointer);

                            //перемещяем кубик на новую позицию
                            selectedItem.GoTo(way);
                            CantInteract = true;
                            SaveLoadService.I.SaveGame(SelfPointer, blowController);
                        } else {
                            SpriteAdapter.SelfPointer.PlaySound("IllegalMove", 1);
                        }
                    }
                }
            }
        }
    };

    //выполняем действия при гейм овере
    public void DoGameOver() {
        if (GameOver) {
            return;
        }

        SaveLoadService.I.saveScore(DataControll.GameModeIndex, DataControll.Score, null);

        SaveLoadService.I.RemoveSave(GamaType);
        SpriteAdapter.SelfPointer.PlaySound("GameOver", 1);

        GameOver = true;
        GameOverShade.GoUp();

        if (newBest) {
            firework.Enable();
        } else {
            rain.Enable();
        }

        if (rand.nextInt(100) < 50) {
            MainGameClass.SelftPointer.ShowBigBunner();
        }
    }

    public void RestartGame() {
        Initialize();
    }

    public List<Vector2> GetAllFreeSpaces() {
        List<Vector2> freeSpaces = new ArrayList<Vector2>();

        for (int i = 0; i < DataControll.Columns; i++) {
            for (int j = 0; j < DataControll.Rows; j++) {
                if (getBlock(Cubes, new Vector2(i, j)) == null) {
                    freeSpaces.add(new Vector2(i, j));
                }
            }
        }

        return freeSpaces;
    }

    //создаем кубики для появление(предсказания)
    public void FieldNextCubesQueue(int count) {
        List<Vector2> freeSpaces = GetAllFreeSpaces();

        if (CubesToCreate.size() == 0) {
            for (int i = 0; i < count; i++) {
                int Index = rand.nextInt(freeSpaces.size());
                Vector2 predictionPos = freeSpaces.get(Index);
                Vector2 RealPos = new Vector2(i % 3, (i < 3 ? 0 : 1));
                CubesToCreate.add(new PredictionItem(DataControll.I.GetRandomColor(), RealPos, predictionPos));
            }
        }
    }

    //Проверит место и вернет true если оно свободно
    public Boolean CheckIfCubePlaceFree(int X, int Y) {
        for (CellItem item : Cubes) {
            if (item.X == X && item.Y == Y) {
                return false;
            }
        }

        return true;
    }

    private static CellItem getBlock(List<CellItem> cells, Vector2 item) {
        for (CellItem localItem : cells) {
            if ((int) item.X == (int) localItem.X && (int) localItem.Y == (int) item.Y) {
                return localItem;
            }
        }

        return null;
    }

    //добавить блоки на поле
    public void addRandomCubes() {
        List<Vector2> freeSpaces = GetAllFreeSpaces();

        //если пустых мест меньше чем нужно создать то конец игры, гейм овер, капут, все, иди вешайся
        if (freeSpaces.size() >= CubesToCreate.size()) {
            //создаем все запланирование блоки
            for (int i = 0; i < CubesToCreate.size(); i++) {
                Cubes.add(new CellItem(CubesToCreate.get(i).color, CubesToCreate.get(i).PredictionPosition));
                CubesToCreate.remove(i--);
            }

            if (freeSpaces.size() == 0) {
                DoGameOver();
            }

            FieldNextCubesQueue(blowController.ToCreateCubes);
        } else {
            DoGameOver();
        }
    }

    //рисуем поле, например поле игры, или поле предсказаний
    public void DrawField(SpriteAdapter spriteBatch, Vector2 Pivot, float Width, float Height, int Rows, int Columns, Vector2 CubeSize) {
        spriteBatch.FillRectangle(Pivot.sub(new Vector2(1)), new Vector2(Width + 2, Height + 2), Color.Black, 1, 0, 2);
        spriteBatch.FillRectangle(Pivot, new Vector2(Width, Height), Color.White, 1, 0, 3);

        for (int Yi = 0; Yi < Rows + 1; Yi++) {
            if (Yi == 0 || Yi == Rows) {
                spriteBatch.DrawLine(Pivot.add(0, Yi * CubeSize.Y + (Yi == 0 ? 1 : -1)), Pivot.add(Width, Yi * CubeSize.Y + (Yi == 0 ? 1 : -1)), Color.LightGray, 0.9f, 2, 4);
            } else {
                spriteBatch.DrawLine(Pivot.add(0, Yi * CubeSize.Y - 2), Pivot.add(Width, Yi * CubeSize.Y - 2), Color.LightGray, 0.9f, 4, 4);
            }
        }

        for (int Xi = 0; Xi < Columns + 1; Xi++) {
            if (Xi == 0 || Xi == Columns) {
                spriteBatch.DrawLine(Pivot.add(Xi * CubeSize.X + (Xi == 0 ? 2 : 0), 1), Pivot.add(Xi * CubeSize.X + (Xi == 0 ? 2 : 0), Height), Color.LightGray, 0.9f, 2, 4);
            } else {
                spriteBatch.DrawLine(Pivot.add(Xi * CubeSize.X + 2, 1), Pivot.add(Xi * CubeSize.X + 2, Height), Color.LightGray, 0.9f, 4, 4);
            }
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = super.Update(timeDelta);

        for (int i = 0; i < CubesToCreate.size(); i++) {
            toRet = toRet | CubesToCreate.get(i).Update(timeDelta);
        }

        Boolean needToSound = false;
        Boolean needToadd = false;
        Boolean boom = false;
        Boolean deleted = false;

        for (int i = 0; i < Cubes.size(); i++) {
            //если блок полностью появился, делаем прощет для взрыва и ставим статус "стабилен"
            if (Cubes.get(i).Status == CellSatus.NeedToProccess) {
                needToSound = true;

                boom = boom | blowController.BlowCheck(Cubes, Cubes.get(i).X, Cubes.get(i).Y, this.GamaType);
                Cubes.get(i).Status = CellSatus.Stable;
            }

            //кто-то закончил перемещение
            if (Cubes.get(i).Status == CellSatus.JustMovedOn) {
                //кто-то закончил перемещение, значит возможно нужно добавить кубиков
                needToSound = true;
                needToadd = true;

                //проверяем нет ли необходимости взорвать пару кубиков
                if (Cubes.size() > 0) {
                    boom = boom | blowController.BlowCheck(Cubes, Cubes.get(i).X, Cubes.get(i).Y, this.GamaType);
                }

                CantInteract = false;
                Cubes.get(i).Status = CellSatus.Stable;
            }

            //обновляем куб, если нужно и удалаем если он "мертв" (взорван)
            toRet = toRet | Cubes.get(i).Update(timeDelta);
            if (Cubes.get(i).isDead()) {
                Cubes.remove(i--);
                deleted = true;
            }
        }

        if (deleted) {
            SaveLoadService.I.SaveGame(this, blowController);
        }

        //если кто-то закончил перемещение и был взрыв, то новых кубиков не добовляем
        if (needToadd && !boom) {
            blowController.multiply = 1;
            SpriteAdapter.SelfPointer.PlaySound("Blow2", 1);
            addRandomCubes();
        }

        if (needToSound && boom) {
            blowController.multiply++;
            if (!blowController.checkBestScore(this.GamaType)) {
                SpriteAdapter.SelfPointer.PlaySound("Blow1", 1);
            } else {
                if (!newBest) {
                    SpriteAdapter.SelfPointer.PlaySound("NewBestRecord", 1);
                    newBest = true;
                } else {
                    SpriteAdapter.SelfPointer.PlaySound("Blow1", 1);
                }
            }
        }
        if (needToadd || needToSound) {
            SaveLoadService.I.SaveGame(this, blowController);
        }

        if (Cubes.size() == 0) {
            addRandomCubes();
        }

        toRet = toRet | GameOverShade.Update(timeDelta);

        if (GameOver) {
            toRet = true;
        }

        toRet = toRet | firework.Update(timeDelta);
        toRet = toRet | rain.Update(timeDelta);

        toRet = toRet | blowController.floatMessageControll.Update(timeDelta);

        return toRet | needToUpdate;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.Clear(Color.Beige);

        //рисуем поле игры и поле предсказаний
        DrawField(spriteBatch, DataControll.FieldPivotPoint, DataControll.FieldWidth, DataControll.FieldHeight,
                DataControll.Rows, DataControll.Columns, DataControll.I.GetCubeSize());
        DrawField(spriteBatch, DataControll.NextCubesPivotPoint, DataControll.NextCubesFieldWidth,
                DataControll.NextCubesFieldHeight, 2, 3, DataControll.I.GetNextCubeSize());

        spriteBatch.DrawText(this.GamaType + "", new Vector2(240, 250), Color.Black, 0.05f, "Arial50", 1.0f, TextAlign.CenterXY, 0, 6);

        //рисуем блоки поля игры
        for (int i = 0; i < Cubes.size(); i++) {
            Cubes.get(i).Draw(spriteBatch, DataControll.FieldPivotPoint);
        }

        //рисуем блоки поля предсказаний
        for (int i = 0; i < CubesToCreate.size(); i++) {
            CubesToCreate.get(i).Draw(spriteBatch, DataControll.NextCubesPivotPoint);
            CubesToCreate.get(i).DrawPrediciton(spriteBatch, DataControll.FieldPivotPoint);
        }

        //лучшый щет
        spriteBatch.DrawText("BEST", new Vector2(80, 80), Color.Black, 0.5f, "Arial20", 0.6f, TextAlign.CenterXY, 0, 30);
        spriteBatch.DrawText("SCORE", new Vector2(80, 100), Color.Black, 0.5f, "Arial20", 0.6f, TextAlign.CenterXY, 0, 30);
        spriteBatch.DrawText(DataControll.BestScores.get(DataControll.I.GameModes.indexOf(this.GamaType.toString())) + "", new Vector2(80, 120), Color.Black, 1, "BArial20", 1, TextAlign.CenterXY, 0, 30);

        blowController.floatMessageControll.Draw(spriteBatch, 20);

        //текущий щет
        spriteBatch.DrawText("YOUR", new Vector2(205, 80), Color.Black, 0.5f, "Arial20", 0.6f, TextAlign.CenterXY, 0, 30);
        spriteBatch.DrawText("SCORE", new Vector2(205, 100), Color.Black, 0.5f, "Arial20", 0.6f, TextAlign.CenterXY, 0, 30);
        spriteBatch.DrawText(DataControll.Score + "", new Vector2(205, 120), Color.Black, 1, "BArial20", 1, TextAlign.CenterXY, 0, 30);

        super.Draw(spriteBatch);

        if (GameOver) {
            spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, DataControll.WindowHeight), Color.Black, 0.85f * GameOverShade.Value, 0, 50);

            firework.Draw(spriteBatch, 48);
            rain.Draw(spriteBatch, 48);

            spriteBatch.FillRectangle(new Vector2(90, 240), new Vector2(300, 150), Color.Beige, 1 * GameOverShade.Value, 0, 51);

            spriteBatch.DrawText("Final score", new Vector2(240, 280), Color.Black, 0.6f, "Arial20", 0.6f, TextAlign.CenterXY, 0, 52);
            spriteBatch.DrawText(DataControll.Score + "", new Vector2(240, 330), Color.Black, 1, "Arial32", 1.0f, TextAlign.CenterXY, 0, 52);

            spriteBatch.DrawText("Tap to restart", new Vector2(240, 560), Color.White, 0.8f, "Arial28", 0.9f, TextAlign.CenterXY, 0, 52);

            spriteBatch.DrawText("Or press back to return to main", new Vector2(240, 600), Color.White, 0.8f, "Arial28", 0.58f, TextAlign.CenterXY, 0, 52);

            if (newBest) {
                spriteBatch.FillGearCircle(new Vector2(380, 370), 38, 18, Color.LightGray, 1, 52);
                spriteBatch.DrawText("NEW", new Vector2(383, 360), Color.Red, 1, "BArial20", 0.6f, TextAlign.CenterXY, 0.34f, 54);
                spriteBatch.DrawText("BEST!", new Vector2(377, 380), Color.Red, 1, "BArial20", 0.6f, TextAlign.CenterXY, 0.34f, 54);
            }
        }

        if (blowController.multiply > 1) {
            spriteBatch.FillGearCircle(new Vector2(147.5f, 110), 20, 16, new Color(255, 113, 113), 1, 50);
            spriteBatch.DrawText("x" + blowController.multiply, new Vector2(147.5f, 110), Color.White, 1, "BArial20", 0.8f, TextAlign.CenterXY, 0, 54);
        }

        spriteBatch.FillRectangle(new Vector2(0, -100), new Vector2(DataControll.WindowWidth, 100), Color.Black, 1, 0, 200);
    }
}
