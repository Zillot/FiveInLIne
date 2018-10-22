package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;

public class InstructionTextRectange extends InstructionsItem {
    public float LineHeight;

    public List<String> lines;
    public String FontName;
    public float FontSize;
    public TextAlign Align;

    public InstructionTextRectange(Vector2 position, Color color, String Text, float Width, TextAlign align, String fontName, float fontSize) {
        super(position, color);
        Align = align;

        FontName = fontName;
        FontSize = fontSize;

        BitmapFont font = SpriteAdapter.SelfPointer.getFont(fontName);
        GlyphLayout glyphLayout = new GlyphLayout();

        glyphLayout.setText(font, "i");
        Vector2 temp = new Vector2(glyphLayout.width, glyphLayout.height);

        LineHeight = (temp.Y + temp.Y * 0.1f) * fontSize;

        lines = new LinkedList<String>();
        String[] words = Text.split(" ");
        String line = "";

        for (int i = 0; i < words.length; i++) {
            glyphLayout.setText(font, line + " " + words[i]);

            if (glyphLayout.width * fontSize < Width * SpriteAdapter.SelfPointer.ScaleAmount) {
                line += words[i] + " ";
            } else {
                lines.add(line + "");
                line = "";
                line += words[i] + " ";
            }
        }

        if (line.compareTo("") != 0) {
            lines.add(line + "");
        }
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int Layer) {
        for (int i = 0; i < lines.size(); i++) {
            spriteBatch.DrawText(lines.get(i), Position.add(new Vector2(0, LineHeight * i)), ItemColor, Opacity, FontName, FontSize, Align, Rotation, Layer, false);
        }
    }
}
