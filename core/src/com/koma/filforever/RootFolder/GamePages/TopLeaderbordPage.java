package com.koma.filforever.RootFolder.GamePages;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeStatus;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_CircleButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_GridContainerControll;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.SaveLoadService;
import com.koma.filforever.RootFolder.Data.ScoreHistory;
import com.koma.filforever.RootFolder.Data.ScoresHolder;
import com.koma.filforever.RootFolder.Data.TabsDot;

public class TopLeaderbordPage extends IGameState {
    public List<TabsDot> dots;
    public List<UI_GridContainerControll> Pages;
    public int CurrentPage;

    public Boolean ManipulationStarted;
    public Vector2 StartPosition;
    public Vector2 DeltaPos;

    public Vector2 PageOffset;

    public TopLeaderbordPage() {
        dots = new ArrayList<TabsDot>();

        Pages = new ArrayList<UI_GridContainerControll>();
        UI_GridContainerControll page = new UI_GridContainerControll(new Vector2(0, 0), new Vector2(DataControll.WindowWidth, DataControll.WindowHeight - 70));
        Vector2 stPos = new Vector2(240.0f - (30.0f * (((float) DataControll.I.GameModes.size() - 1) / 2.0f)), 690);

        for (int i = 0; i < DataControll.I.GameModes.size(); i++) {
            dots.add(new TabsDot(stPos.add(new Vector2(30 * i, 0))));
            page = new UI_GridContainerControll(new Vector2(0, 0), new Vector2(DataControll.WindowWidth, DataControll.WindowHeight - 70));
            page.ItemsChildrens.add(new ScoresHolder(new ArrayList<ScoreHistory>(), "Empty", i));
            Pages.add(page);
        }

        dots.get(0).Select();

        PageOffset = new Vector2(0);
        DeltaPos = new Vector2(0);
        StartPosition = new Vector2(0);
        ManipulationStarted = false;

        Controlls.put("MenuButton", new UI_CircleButtonControll(new Vector2(60, 60), Color.LightGray, 25, 30));
        Controlls.get("MenuButton").onTap[0] = MenuButtonHandler;
        ((UI_CircleButtonControll) Controlls.get("MenuButton")).IcoDrawEvent = MenuButtonIco;
    }

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
        for (int i = 0; i < DataControll.I.GameModes.size(); i++) {
            SaveLoadService.I.getTopList(i, 3, "" + i, top10_DownloadStringCompleted);
        }

        TouchService.I.onContinuesTouch[0] = onContinuesTouch;
        TouchService.I.onNoTouch[0] = onNoTouch;
    }

    private Delegate top10_DownloadStringCompleted = new Delegate() {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            List<ScoreHistory> rootObject = new ArrayList<ScoreHistory>();

            ArrayList<JsonValue> list = new Json().fromJson(ArrayList.class, httpResponse.getResultAsString());
            for (JsonValue v : list) {
                rootObject.add(new Json().readValue(ScoreHistory.class, v));
            }

            if (rootObject.size() == 0) {
                return;
            }

            for (ScoreHistory item : rootObject) {
                item.init();
            }

            ((ScoresHolder) Pages.get(Integer.parseInt(UserToken)).ItemsChildrens.get(0)).Scores = rootObject;
        }
    };

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
        }
    };

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = super.Update(timeDelta);

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

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.Clear(Color.Beige);

        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 120), Color.LightGray, 1, 0, 2);

        spriteBatch.DrawText("Leaderboard", new Vector2(240, 90), Color.Black, 1, "BArial20", 1.0f, TextAlign.CenterXY, 0, 40);

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

        super.Draw(spriteBatch);

        for (TabsDot item : dots) {
            item.Draw(spriteBatch, 30);
        }
    }
}
