package com.koma.filforever.RootFolder.GamePages;

import com.badlogic.gdx.Gdx;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder.InstructionBase;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_ButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_CircleButtonControll;
import com.koma.filforever.FakeEye_2DGameEngine_Android.UI.UI_IMenuControll;
import com.koma.filforever.RootFolder.Data.DataControll;
import com.koma.filforever.RootFolder.Data.SaveLoadService;
import com.koma.filforever.game.MyTextInputListener;

public class ProfilePage extends IGameState {
    private static Random rand = new Random();

    public List<InstructionBase> Instructions;
    public int InstructionIndex;


    public ProfilePage() {
        Instructions = new LinkedList<InstructionBase>();
        InstructionIndex = 0;

        Controlls.put("MenuButton", new UI_CircleButtonControll(new Vector2(60, 60), Color.LightGray, 25, 30));
        Controlls.get("MenuButton").onTap[0] = MenuButtonHandler;
        ((UI_CircleButtonControll) Controlls.get("MenuButton")).IcoDrawEvent = MenuButtonIco;

        Controlls.put("NameChangeButton", new UI_ButtonControll(new Vector2(80, 200), Color.DarkGray, Color.Black, "", new Vector2(80, 80), "", 34));
        Controlls.get("NameChangeButton").onTap[0] = NameChangeButtonHandler;
        ((UI_ButtonControll) Controlls.get("NameChangeButton")).IcoDrawEvent = NameChangeButtonIco;

        Controlls.put("RepeatTutorial", new UI_ButtonControll(new Vector2(DataControll.WindowWidth / 2, 360), Color.DarkGray, Color.Black, "Repeat tutorial", new Vector2(320, 80), "", 34));
        Controlls.get("RepeatTutorial").onTap[0] = RepeatTutorialButton;

        Controlls.put("ClearLines", new UI_CircleButtonControll(new Vector2(DataControll.WindowWidth / 2 - 100, 580), Color.LightGray, 40, 30));
        Controlls.get("ClearLines").onTap[0] = ClearLinesHandler;
        ((UI_CircleButtonControll) Controlls.get("ClearLines")).IcoDrawEvent = ClearLinesIco;

        Controlls.put("ClearSquares", new UI_CircleButtonControll(new Vector2(DataControll.WindowWidth / 2, 580), Color.LightGray, 40, 30));
        Controlls.get("ClearSquares").onTap[0] = ClearSquaresHandler;
        ((UI_CircleButtonControll) Controlls.get("ClearSquares")).IcoDrawEvent = ClearSquaresIco;

        Controlls.put("ClearSnakes", new UI_CircleButtonControll(new Vector2(DataControll.WindowWidth / 2 + 100, 580), Color.LightGray, 40, 30));
        Controlls.get("ClearSnakes").onTap[0] = ClearSnakesHandler;
        ((UI_CircleButtonControll) Controlls.get("ClearSnakes")).IcoDrawEvent = ClearSnakesIco;
    }

    public Delegate ClearLinesIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(DataControll.I.GameModes.get(0)), position.sub(size.mul(0.7f).div(2)), size.mul(0.7f), 40);
        }
    };

    public Delegate ClearSquaresIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(DataControll.I.GameModes.get(1)), position.sub(size.mul(0.8f).div(2)), size.mul(0.8f), 40);
        }
    };

    public Delegate ClearSnakesIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get(DataControll.I.GameModes.get(2)), position.sub(size.mul(0.8f).div(2)), size.mul(0.8f), 40);
        }
    };

    public Delegate MenuButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("MenuIco"), position.sub(size.div(2)), size, 40);
        }
    };

    public Delegate ClearLinesHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
        }
    };

    public Delegate ClearSquaresHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
        }
    };

    public Delegate ClearSnakesHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
        }
    };

    public Delegate MenuButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("MainMenu", "", false);
        }
    };

    public Delegate NameChangeButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            Vector2 LeftUpCorner = position.sub(size.mul(0.9f).div(2));
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("NameChangeIco"), LeftUpCorner, size.mul(0.9f), layer + 10);

            if (layer <= 60 && DataControll.I.PlayerName != null) {
                if (DataControll.I.PlayerName.contains("DefaultName")) {
                    spriteBatch.DrawText("Change your name", position.add(200, -20), Color.Black, 1, "BArial20", 1.0f, TextAlign.CenterXY, 0, layer - 5);

                    String line = DataControll.I.PlayerName;
                    spriteBatch.DrawText(line, position.add(200, 20), Color.Black, 1, "Arial20", 0.8f, TextAlign.CenterXY, 0, layer - 5, false);
                } else {
                    String line = DataControll.I.PlayerName;
                    spriteBatch.DrawText(line, position.add(200, 0), Color.Black, 1, "Arial20", 0.8f, TextAlign.CenterXY, 0, layer - 5, false);
                }
            }
        }
    };

    public Delegate NameButtonIco = new Delegate() {
        @Override
        public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
            DataControll.I.DrawArrayIco(spriteBatch, DataControll.I.Icons.get("BackIco"), position.sub(size.div(2)), size, layer + 10);
        }
    };

    public Delegate RepeatTutorialButton = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            EngineService.I.NavigateTo("LoadingScreen", "RepeatTutorial", false);
        }
    };

    public Delegate NameChangeButtonHandler = new Delegate() {
        @Override
        public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
            String defaultText = DataControll.I.PlayerName;

            if (defaultText.contains("DefaultName")) {
                defaultText = "";
            }

            MyTextInputListener listener = new MyTextInputListener();
            Gdx.input.getTextInput(listener, SpriteAdapter.SelfPointer.LocalizeParts("Player name"), defaultText, SpriteAdapter.SelfPointer.LocalizeParts("Enter your name or nickname"));
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
    public void PrivateLocalTouchEventCheck(TouchEventType TouchEventName, Vector2 position) {
        if (InstructionIndex >= Instructions.size()) {
            super.PrivateLocalTouchEventCheck(TouchEventName, position);
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

        if (InstructionIndex < Instructions.size()) {
            toRet = toRet | Instructions.get(InstructionIndex).Update(timeDelta);
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch) {
        spriteBatch.Clear(Color.Beige);

        spriteBatch.FillRectangle(Vector2.Zero, new Vector2(DataControll.WindowWidth, 120), Color.LightGray, 1, 0, 2);

        spriteBatch.DrawText("Profile", new Vector2(240, 40), Color.Black, 1, "BArial32", 1.0f, TextAlign.CenterXY, 0, 40);
        spriteBatch.DrawText("Setups", new Vector2(240, 90), Color.Black, 1, "BArial20", 1.0f, TextAlign.CenterXY, 0, 40);

        spriteBatch.DrawText("Clear data", new Vector2(240, 500), Color.Black, 1, "BArial20", 1, TextAlign.CenterXY, 0, 40);

        super.Draw(spriteBatch);

        if (InstructionIndex < Instructions.size()) {
            Instructions.get(InstructionIndex).Draw(spriteBatch);
        }
    }
}
