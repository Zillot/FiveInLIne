package com.koma.filforever.RootFolder.GamePages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_ButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_CircleButtonControll;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.SaveLoadService;
import com.koma.filforever.RootFolder.Data.ScoreHistory;

public class LeaderbordPage extends IGameState {
    public static LeaderbordPage SelfPointer;

    private final int ItemPerPage = 10;
    private int TotalPagesCount;
    private int CurrentPage;

    public String text;
    public List<ScoreHistory> Scores;
    private FloatController LoaderAnimation;
    private FloatController ToNextPage;

    private Boolean internetConnection;

    private int SelectedId;
    private int dir;

    public LeaderbordPage() {
        TotalPagesCount = 1;
        CurrentPage = 0;

        SelfPointer = this;
        SelectedId = 0;

        internetConnection = true;
        Scores = new ArrayList<ScoreHistory>();

        ToNextPage = new FloatController(2.0f);
        LoaderAnimation = new FloatController(2.6f);
        LoaderAnimation.GoUp();

        Controlls.put("LeftArrowButton", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2 - 150, 620), Color.DarkGray, Color.Black, "", new Vector2(80, 80), "", 34));
        Controlls.put("SelectButton", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2 - 50, 620), Color.DarkGray, Color.Black, "", new Vector2(80, 80), "BArial20", 34));
        Controlls.put("BackButton", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2 + 50, 620), Color.DarkGray, Color.Black, "", new Vector2(80, 80), "BArial20", 34));
        Controlls.put("RightArrowButton", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2 + 150, 620), Color.DarkGray, Color.Black, "", new Vector2(80, 80), "", 34));

        Controlls.get("LeftArrowButton").onTap[0] = LeftArrowButtonHandler;
        Controlls.get("SelectButton").onTap[0] = SelectButtonHandler;
        Controlls.get("BackButton").onTap[0] = StartGameButtonTap;
        Controlls.get("RightArrowButton").onTap[0] = RightArrowButtonHandler;

        ((UI_ButtonControll) Controlls.get("LeftArrowButton")).IcoDrawEvent = LeftArrowButtonIco;
        ((UI_ButtonControll) Controlls.get("SelectButton")).IcoDrawEvent = SelectButtonIco;
        ((UI_ButtonControll) Controlls.get("BackButton")).IcoDrawEvent = BackButtonIco;
        ((UI_ButtonControll) Controlls.get("RightArrowButton")).IcoDrawEvent = RightArrowButtonIco;

        Controlls.put("MenuButton", new UI_CircleButtonControll(new Vector2(60, 60), Color.LightGray, 25, 30));
        Controlls.get("MenuButton").onTap[0] = MenuButtonHandler;
        ((UI_CircleButtonControll) Controlls.get("MenuButton")).IcoDrawEvent = MenuButtonIco;
    }

    private Delegate LeftArrowButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            ToNextPage.GoUp();
            dir = CurrentPage - 1;

            if (dir < 0) {
                dir = TotalPagesCount - 1;
            }

            for (ScoreHistory item : Scores) {
                item.Hide();
            }
        }
    };
    private Delegate RightArrowButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            ToNextPage.GoUp();
            dir = CurrentPage + 1;

            if (dir >= TotalPagesCount) {
                dir = 0;
            }

            for (ScoreHistory item : Scores) {
                item.Hide();
            }
        }
    };

    private Delegate SelectButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            SelectedId++;

            if (SelectedId >= DataControll.I.GameModes.size()) {
                SelectedId = 0;
            }
        }
    };

    private Delegate LeftArrowButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("ArrowLeft"), position.sub(size.div(2)), size, layer);
        }
    };

    private Delegate SelectButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            Vector2 LeftUpCorner = position.sub(size.div(2));
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(DataControll.I.GameModes.get(SelectedId)), LeftUpCorner, size, layer);
        }
    };

    private Delegate BackButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("BackIco"), position.sub(size.div(2)), size, layer);
        }
    };

    private Delegate RightArrowButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("ArrowRight"), position.sub(size.div(2)), size, layer);
        }
    };

    private Delegate StartGameButtonTap = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("MainMenu", "", false);
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

    public void CaclulatePages() {
        TotalPagesCount = Scores.size() / ItemPerPage + 1;

        if (Scores.size() % ItemPerPage == 0) {
            TotalPagesCount--;
        }
    }

    private static Delegate top10_DownloadStringCompleted = new Delegate() {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
            List<ScoreHistory> rootObject = new ArrayList<ScoreHistory>();

            ArrayList<JsonValue> list = new Json().fromJson(ArrayList.class, httpResponse.getResultAsString());
            for (JsonValue v : list) {
                rootObject.add(new Json().readValue(ScoreHistory.class, v));
            }

            for (ScoreHistory item : rootObject)
                item.init();

            if (rootObject.size() == 0) {
                SelfPointer.text = "Empty";
            }

            SelfPointer.Scores = rootObject;
            SelfPointer.CaclulatePages();
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
    }

    @Override
    public void OnNavigatetTo(Object data) {
        super.OnNavigatetTo(data);

        SelectedId = DataControll.GameModeIndex;
        CurrentPage = 0;
        TotalPagesCount = 1;

        text = "";
        Scores.clear();
        //SaveLoadService.I.getTopList(DataControll.GameModeIndex, 100, top10_DownloadStringCompleted);
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = super.Update(timeDelta);

        if (Scores.size() == 0 && text.compareTo("Empty") != 0) {
            toRet = toRet | LoaderAnimation.Update(timeDelta);

            if (LoaderAnimation.GetRegularValue() == 1) {
                toRet = true;

                LoaderAnimation.Value = 0;
                LoaderAnimation.GoUp();

                if (text.length() <= 4 && text.compareTo("Connecting") != 0) {
                    text += " ";
                    text = "Connecting";
                } else {
                    text += ".";
                }
            }

            if (text.compareTo("Connecting....") == 0) {
                text = "Connecting";
            }
        }

        for (int i = 0; i < Scores.size(); i++) {
            toRet = toRet | Scores.get(i).Update(timeDelta);
        }

        toRet = toRet | ToNextPage.Update(timeDelta);

        if (ToNextPage.Value == 1) {
            ToNextPage.Stop();
            ToNextPage.Value = 0;
            CurrentPage = dir;

            for (ScoreHistory item : Scores) {
                item.Show();
            }
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.Clear(Color.Beige);

        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 120), Color.LightGray, 1, 0, 2);

        spriteBatch.DrawText("Leaderboards", new Vector2(240, 40), Color.Black, 1, "BArial32", 1.0f, TextAlign.CenterXY, 0, 40);

        float opacity = 0;
        int size = Scores.size() - CurrentPage * ItemPerPage;
        if (size > ItemPerPage) {
            size = ItemPerPage;
        }

        for (int i = CurrentPage * ItemPerPage, j = 1; i < CurrentPage * ItemPerPage + size; i++, j++) {
            Scores.get(i).Draw(spriteBatch, new Vector2(50, 100 + 40 * (j - 1)), j);
            opacity = Scores.get(i).apearAnimation.Value;
        }

        spriteBatch.FillRectangle(new Vector2(35, 0), new Vector2(30, 800), Color.Black, 0.12f * opacity, 0, 19);
        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 80), Color.LightGray, 1, 0, 2);

        spriteBatch.DrawText(DataControll.I.GameModes.get(SelectedId).replace("Mode", ""), new Vector2(240, 80), Color.Black, 1, "BArial32", 1.0f, TextAlign.CenterXY, 0, 40);

        if (Scores.size() <= 0 && internetConnection) {
            if (text.compareTo("Empty") == 0) {
                spriteBatch.DrawText("No scores(", new Vector2(240, 400), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
            } else {
                spriteBatch.DrawText(text, new Vector2(240, 400), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
            }
        }

        if (Scores.size() > 0) {
            spriteBatch.DrawText((CurrentPage + 1) + "/" + TotalPagesCount, new Vector2(240, 550), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
        }

        if (!internetConnection) {
            spriteBatch.DrawText("Can't load leaderbord", new Vector2(240, 600), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
            spriteBatch.DrawText("Please check your internet connection", new Vector2(240, 630), Color.Black, 1, "", 1, TextAlign.CenterXY, 0, 40);
        }

        super.Draw(spriteBatch);
    }
}
