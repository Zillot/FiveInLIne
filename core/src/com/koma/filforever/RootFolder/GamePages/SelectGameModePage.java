package com.koma.filforever.RootFolder.GamePages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder.InstructionBase;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeStatus;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_ButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_CircleButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_CustomItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_GridContainerControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_IMenuControll;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.GameType;
import com.koma.filforever.RootFolder.Data.SaveLoadService;
import com.koma.filforever.RootFolder.Data.TabsDot;

public class SelectGameModePage extends IGameState {
    public static SelectGameModePage SelfPointer;

    public List<InstructionBase> Instructions;
    public int InstructionIndex;

    public List<TabsDot> dots;
    public List<UI_GridContainerControll> Pages;
    public int CurrentPage;

    public Boolean ManipulationStarted;
    public Vector2 StartPosition;
    public Vector2 DeltaPos;

    public Vector2 PageOffset;

    public SelectGameModePage() {
        Instructions = new LinkedList<InstructionBase>();
        InstructionIndex = 0;

        Controlls.put("MenuButton", new UI_CircleButtonControll(new Vector2(60, 60), Color.LightGray, 25, 30));
        Controlls.get("MenuButton").onTap[0] = MenuButtonHandler;
        ((UI_CircleButtonControll) Controlls.get("MenuButton")).IcoDrawEvent = MenuButtonIco;


        PageOffset = new Vector2(0);
        DeltaPos = new Vector2(0);
        StartPosition = new Vector2(0);
        ManipulationStarted = false;
    }

    public Delegate onContinuesTouch = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            if (Pages.get(CurrentPage).Status == NodeStatus.Stable) {
                if (!ManipulationStarted) {
                    ManipulationStarted = true;
                    StartPosition = position;
                    DeltaPos = position;
                } else {
                    DeltaPos = position;

                    int dir = 1;
                    if (StartPosition.X > DeltaPos.X) {
                        dir = -1;
                    }

                    PageOffset = new Vector2(dir, 0).mul(Math.abs(StartPosition.X - DeltaPos.X));
                }
            }
        }
    };

    public Delegate onNoTouch = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            if (ManipulationStarted) {
                float speed = 1000; //Math.Abs((800 / Math.Abs(PageOffset.X)) * 150);

                if (PageOffset.X < -100) {
                    CurrentPage++;
                    PageOffset = PageOffset.add(DataControll.WindowWidth, 0);

                    if (CurrentPage >= Pages.size()) {
                        CurrentPage = 0;
                    }

                    Pages.get(CurrentPage).Position = Pages.get(CurrentPage).Position.add(PageOffset);
                    Pages.get(CurrentPage).TranslatePosition(new Vector2(0, 0), speed);
                } else if (PageOffset.X > 100) {
                    CurrentPage--;
                    PageOffset = PageOffset.sub(DataControll.WindowWidth, 0);

                    if (CurrentPage < 0) {
                        CurrentPage = Pages.size() - 1;
                    }

                    Pages.get(CurrentPage).Position = Pages.get(CurrentPage).Position.add(PageOffset);
                    Pages.get(CurrentPage).TranslatePosition(new Vector2(0, 0), speed);
                } else {
                    Pages.get(CurrentPage).Position = Pages.get(CurrentPage).Position.add(PageOffset);
                    Pages.get(CurrentPage).TranslatePosition(new Vector2(0, 0), speed);
                }

                for (TabsDot item : dots) {
                    item.deSelect();
                }

                dots.get(CurrentPage).Select();

                if (CurrentPage > 0) {
                    Pages.get(CurrentPage - 1).Position = new Vector2(0);
                }

                if (CurrentPage < Pages.size() - 1) {
                    Pages.get(CurrentPage + 1).Position = new Vector2(0);
                }

                PageOffset = new Vector2(0);
                ManipulationStarted = false;
                StartPosition = new Vector2(0);
                DeltaPos = new Vector2(0);
            }

            DataControll.GameModeIndex = CurrentPage;
        }
    };

    public Delegate MenuButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("MenuIco"), position.sub(size.div(2)), size, 40);
        }
    };

    public Delegate MenuButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("MainMenu", "", false);
        }
    };

    public Delegate ContinueLastGame = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("GameMenu", DataControll.I.GameModes.get(DataControll.GameModeIndex) + ":Load", false);
        }
    };

    public Delegate StartGame = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("GameMenu", DataControll.I.GameModes.get(DataControll.GameModeIndex) + ":New", false);
        }
    };

    @Override
    public void Initialize() {
        super.Initialize();
    }

    @Override
    public void Dispose() {
        super.Dispose();
    }

    @Override
    public void OnNavigatetFrom(Object data) {
        super.OnNavigatetFrom(data);

        TouchService.I.onContinuesTouch[0] = null;
        TouchService.I.onNoTouch[0] = null;
    }

    @Override
    public void OnNavigatetTo(Object data) {
        super.OnNavigatetTo(data);

        CurrentPage = 0;
        TouchService.I.onContinuesTouch[0] = onContinuesTouch;
        TouchService.I.onNoTouch[0] = onNoTouch;

        dots = new ArrayList<TabsDot>();

        Pages = new ArrayList<UI_GridContainerControll>();
        UI_GridContainerControll page = new UI_GridContainerControll(new Vector2(0, 0), new Vector2(DataControll.WindowWidth, DataControll.WindowHeight - 70));
        Vector2 stPos = new Vector2(240.0f - (30.0f * (((float) DataControll.I.GameModes.size() - 1) / 2.0f)), 690);

        for (int i = 0; i < DataControll.I.GameModes.size(); i++) {
            dots.add(new TabsDot(stPos.add(new Vector2(30 * i, 0))));
            page = new UI_GridContainerControll(new Vector2(0, 0), new Vector2(DataControll.WindowWidth, DataControll.WindowHeight - 70));

            TreeMap<String, Object> values = new TreeMap<String, Object>();
            values.put("gameMode", DataControll.I.GameModes.get(i));

            UI_GridContainerControll grid = new UI_GridContainerControll(new Vector2(0,0), new Vector2(DataControll.WindowWidth, DataControll.WindowHeight - 70));
            grid.ItemsChildrens.add(
                    new UI_CustomItem(90, values,
                            new Delegate() {
                                @Override
                                public void ExtendDraw(SpriteAdapter spriteBatch, NodeItem parent, int layer) {
                                    Vector2 position = parent.Position.add(new Vector2(240, 320));
                                    Vector2 size = new Vector2(150, 150);
                                    String GameModeName = (String) ((UI_CustomItem)parent).Values.get("gameMode");

                                    spriteBatch.DrawText(GameModeName.replace("Mode", ""), parent.Position.add(new Vector2(240, 40)),
                                            Color.Black, 1, "BArial32", 1.0f, TextAlign.CenterXY, 0, 40);
                                    spriteBatch.FillCircle(position, 180, 100, Color.LightGray, 1, 10);
                                    DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(GameModeName), position.sub(size.div(2)), size);
                                }
                            }));

            boolean canLoadGame = SaveLoadService.I.CanLoadGame(GameType.valueOf(DataControll.I.GameModes.get(DataControll.GameModeIndex)));
            if (!canLoadGame) {
                UI_ButtonControll StartButton = new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2, 560), Color.DarkGray, Color.Black, "Start Game", new Vector2(200, 80), "BArial20", 34);
                StartButton.onTap[0] = StartGame;
                grid.ItemsChildrens.add(StartButton);
            }
            else {
                UI_ButtonControll continueButton = new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2 - 110, 560), Color.DarkGray, Color.Black, "Continue", new Vector2(200, 80), "BArial20", 34);
                continueButton.onTap[0] = ContinueLastGame;
                grid.ItemsChildrens.add(continueButton);

                UI_ButtonControll StartButton = new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2 + 110, 560), Color.DarkGray, Color.Black, "Start Game", new Vector2(200, 80), "BArial20", 34);
                StartButton.onTap[0] = StartGame;
                grid.ItemsChildrens.add(StartButton);
            }

            page.ItemsChildrens.add(grid);
            Pages.add(page);
        }

        dots.get(0).Select();
    }

    @Override
    public void PrivateLocalTouchEventCheck(TouchEventType TouchEventName, Vector2 position) {
        if (InstructionIndex >= Instructions.size()) {
            super.PrivateLocalTouchEventCheck(TouchEventName, position);

            for (UI_GridContainerControll grid : this.Pages) {
                for (UI_IMenuControll children : grid.ItemsChildrens) {
                    children.TouchEventCheck(position, TouchEventName);
                }
            }
        } else {
            for (UI_IMenuControll item : Instructions.get(InstructionIndex).FitControlls) {
                item.TouchEventCheck(position, TouchEventName);
            }

            if (Instructions.get(InstructionIndex).CheckTapForNextInstruction(TouchEventName, position)) {
                InstructionIndex++;
            }
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        if (Pages.size() > 0) {
            toRet = toRet | Pages.get(CurrentPage).Update(timeDelta);

            if (CurrentPage > 0) {
                toRet = toRet | Pages.get(CurrentPage - 1).Update(timeDelta);
            }

            if (CurrentPage < Pages.size() - 1) {
                toRet = toRet | Pages.get(CurrentPage + 1).Update(timeDelta);
            }
        }

        for (TabsDot item : dots) {
            toRet = toRet | item.Update(timeDelta);
        }

        if (InstructionIndex < Instructions.size()) {
            toRet = toRet | Instructions.get(InstructionIndex).Update(timeDelta);
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.Clear(Color.Beige);

        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 120), Color.LightGray, 1, 0, 2);
        spriteBatch.DrawText("SelModLine", new Vector2(240, 90), Color.Black, 1, "BArial20", 1.0f, TextAlign.CenterXY, 0, 40);

        if (Pages.size() > 0) {
            if (PageOffset.X != 0 || Pages.get(CurrentPage).PathToFolow != null) {
                if (CurrentPage > 0) {
                    Pages.get(CurrentPage - 1).Draw(spriteBatch, Pages.get(CurrentPage).Position.add(PageOffset.sub(DataControll.WindowWidth, 0)), 20);
                } else {
                    Pages.get(Pages.size() - 1).Draw(spriteBatch, Pages.get(CurrentPage).Position.add(PageOffset.sub(DataControll.WindowWidth, 0)), 20);
                }
            }

            Pages.get(CurrentPage).Draw(spriteBatch, PageOffset, 20);

            if ((PageOffset.X != 0 || Pages.get(CurrentPage).PathToFolow != null)) {
                if (CurrentPage < Pages.size() - 1) {
                    Pages.get(CurrentPage + 1).Draw(spriteBatch, Pages.get(CurrentPage).Position.add(PageOffset.add(DataControll.WindowWidth, 0)), 20);
                } else {
                    Pages.get(0).Draw(spriteBatch, Pages.get(CurrentPage).Position.add(PageOffset.add(DataControll.WindowWidth, 0)), 20);
                }
            }
        }

        for (TabsDot item : dots) {
            item.Draw(spriteBatch, 30);
        }

        super.Draw(spriteBatch);

        if (InstructionIndex < Instructions.size()) {
            Instructions.get(InstructionIndex).Draw(spriteBatch);
        }
    }
}
