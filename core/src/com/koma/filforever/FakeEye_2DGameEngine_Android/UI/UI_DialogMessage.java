package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;

import java.util.LinkedList;
import java.util.List;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.FloatController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

public class UI_DialogMessage extends UI_IMenuControll {
    public String Header;
    public String Text;

    public FloatController HoverController;

    public Color BackColor;
    public Color TextColor;
    public List<String> lines;

    public UI_DialogMessage(Color backColor, Color textColor, String header, String text, Vector2 windowsize, int layer) {
        super(Vector2.Zero, windowsize);
        HoverController = new FloatController(1.3f);
        HoverController.Value = 0;

        BackColor = backColor;
        TextColor = textColor;

        SetText(text, windowsize, header);

        Layer = layer;
    }

    public void SetText(String text, Vector2 windowsize, String header) {
        lines = new LinkedList<String>();
        int lastLine = 0;
        String line = "";

        for (int i = 0; i < text.length(); i++) {
            lastLine++;
            line += text.charAt(i);

            if (lastLine > 30 && text.charAt(i) == ' ') {
                lines.add(line);
                lastLine = 0;
                line = "";
            }
        }

        if (line.length() > 0) {
            lines.add(line);
        }

        Size = new Vector2(windowsize.X - 40, lines.size() * 25 + 40);
        ControllSize = windowsize;
        ControllPosition = windowsize.div(2).sub(Size.div(2));

        Header = header;
        Text = text;
    }

    public Boolean InUnderControll(Vector2 SomePosition) {
        return EngineService.I.isInRect(SomePosition, ControllPosition, Size);
    }

    @Override
    public void TouchEventCheck(Vector2 ToouchPosition, TouchEventType TouchEventName) {
        Hide();
    }

    public void Show() {
        HoverController.GoUp();
    }

    public void Hide() {
        HoverController.GoDown();
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);
        toRet = toRet | HoverController.Update(timeDelta);

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        spriteBatch.FillRectangle(Vector2.Zero, ControllSize, Color.Black, 0.6f * HoverController.Value, 0, layer);
        spriteBatch.FillRectangle(ControllPosition.sub(0, 600 * HoverController.GetInvertedValue()), Size, BackColor, HoverController.Value, 0, layer + 1);

        int i = 0;

        for (String item : lines) {
            spriteBatch.DrawText(item, new Vector2(ControllSize.X / 2, ControllPosition.Y + 32.5f + (25 * i++) - 600 * HoverController.GetInvertedValue()), TextColor, HoverController.Value, "Arial20", 1, TextAlign.CenterXY, 0, layer + 2);
        }
    }
}
