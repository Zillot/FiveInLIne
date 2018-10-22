package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional;

import java.util.LinkedList;
import java.util.List;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class FloatMessageControll {
    public List<FloatMessage> messeges;

    public FloatMessageControll() {
        messeges = new LinkedList<FloatMessage>();
    }

    public void addMessage(String text, Vector2 position, Color fontColor, String fontName, float fontSize, Boolean useTranslate) {
        messeges.add(new FloatMessage(text, position, fontColor, fontName, fontSize, useTranslate));
    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        for (int i = 0; i < messeges.size(); i++) {
            toRet = toRet | messeges.get(i).Update(timeDelta);

            if (messeges.get(i).IsDead()) {
                messeges.remove(i--);
            }
        }

        return toRet;
    }

    public void Draw(SpriteAdapter spriteBatch, int layer) {
        for (FloatMessage item : messeges) {
            item.Draw(spriteBatch, layer);
        }
    }
}
