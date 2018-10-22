package com.koma.filforever.RootFolder.GamePages;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_ButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_CircleButtonControll;
import com.koma.filforever.RootFolder.Data.DataControll;

public class AchievementsPage extends IGameState {
    public AchievementsPage() {
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
    }

    @Override
    public void OnNavigatetTo(Object data) {
        super.OnNavigatetTo(data);

    }

    @Override
    public Boolean Update(float timeDelta) {
        return super.Update(timeDelta);
    }

    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.Clear(Color.Beige);

        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 120), Color.LightGray, 1, 0, 2);

        spriteBatch.DrawText("Achievements", new Vector2(240, 60), Color.Black, 1, "BArial32", 1.0f, TextAlign.CenterXY, 0, 40);

        spriteBatch.DrawText("Still in development", new Vector2(DataControll.WindowWidth / 2, 500), Color.Black, 1, "Arial24", 1, TextAlign.CenterXY, 0, 30);
        spriteBatch.DrawText("Stay tuned)", new Vector2(DataControll.WindowWidth / 2, 540), Color.Black, 1, "Arial24", 1, TextAlign.CenterXY, 0, 30);

        super.Draw(spriteBatch);
    }
}
