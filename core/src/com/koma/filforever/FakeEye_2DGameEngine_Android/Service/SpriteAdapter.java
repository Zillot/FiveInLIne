package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Rectangle;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.SpriteEffects;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.RootFolder.Data.DataControll;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SpriteAdapter {
    public static SpriteAdapter SelfPointer;

    private float TwoPI = (float) Math.PI * 2;
    private TreeMap<String, List<Vector2>> circleCache;

    public static float pixelSize = 56.0f;

    public SpriteBatch spriteBatch;
    public Matrix4 mx4Font;
    Matrix4 textRotation;
    public Sprite sprite;

    public Map<String, Sound> Sounds;
    public Map<String, String> Strings;
    public Map<String, BitmapFont> Fonts;
    public Map<String, Texture> Textures;

    public Map<String, String> StringsCache;

    public List<String> AvailableLanguages;
    public String Language;

    public Vector2 WindwoSize;
    public Vector2 RealWindwoSize;
    public Vector2 ScaleOffset;
    public float ScaleAmount;

    public float Sound;

    public SpriteAdapter() {
        Sound = 1;

        mx4Font = new Matrix4();
        textRotation = new Matrix4();
        ScaleAmount = 1;
        WindwoSize = new Vector2(480, 800);
        RealWindwoSize = new Vector2(480, 800);
        ScaleOffset = new Vector2();

        StringsCache = new TreeMap<String, String>();
        circleCache = new TreeMap<String, List<Vector2>>();
    }

    public void Initialize() {
        AvailableLanguages = new ArrayList<String>();

        Sounds = new TreeMap<String, Sound>();
        Strings = new TreeMap<String, String>();
        Textures = new TreeMap<String, Texture>();
    }

    public void addLanguageString(String key, String... values) {
        int count = values.length > AvailableLanguages.size() ? AvailableLanguages.size() : values.length;

        for (int i = 0; i < count; i++) {
            Strings.put(AvailableLanguages.get(i) + "_" + key, values[i]);
        }
    }

    public Vector2 GetWindowSize() {
        return new Vector2(WindwoSize.X, WindwoSize.Y);
    }

    public Vector2 GetRealWindowSize() {
        return RealWindwoSize;
    }

    public void calculateScaleAmount() {
        Vector2 realSize = GetRealWindowSize();
        Vector2 windowSize = GetWindowSize();

        float Xscale = realSize.X / windowSize.X;
        float Yscale = realSize.Y / windowSize.Y;

        ScaleAmount = Xscale > Yscale ? Yscale : Xscale;

        float temp = realSize.Y - windowSize.Y * ScaleAmount;

        ScaleOffset = new Vector2(realSize.X - windowSize.X * ScaleAmount, 0).div(2);
    }

    public void StopSound(String SoundName) {

    }

    public void PlaySound(String SoundName, float volume) {
        PlaySound(SoundName, volume, false);
    }

    public void PlaySound(String SoundName, float volume, Boolean looped) {
        if (Sounds.containsKey(SoundName)) {
            long id = Sounds.get(SoundName).play(volume * Sound);
            //Sounds.get(SoundName).setLooping(id, looped);
        }
    }

    public String getString(String StringName) {
        if (Strings.containsKey(Language + "_" + StringName)) {
            return Strings.get(Language + "_" + StringName);
        } else if (Strings.containsKey(StringName)) {
            return Strings.get(StringName);
        }

        return StringName;
    }

    public String LocalizeParts(String line) {
        if (StringsCache.containsKey(line)) {
            return StringsCache.get(line);
        }
        String toRet = line.toString();

        for (Map.Entry<String, String> item : Strings.entrySet()) {
            String toLook = item.getKey().split("_")[1];

            if (toRet.contains(toLook)) {
                toRet = toRet.replace(toLook, getString(toLook));
            }
        }

        StringsCache.put(line, toRet);
        return toRet;
    }

    public Texture getTexture(String TexName) {
        if (Textures.containsKey(TexName)) {
            return Textures.get(TexName);
        }
        return Textures.get("Pixel");
    }

    public BitmapFont getFont(String FontName) {
        if (Fonts.containsKey(FontName)) {
            return Fonts.get(FontName);
        }
        return Fonts.get("Arial20");
    }

    public void Clear(Color color) {
        Vector2 size = GetWindowSize();
        FillRectangle(Vector2.Zero, new Vector2(size.X, size.Y), color, 1, 0, 1);
    }

    private List<Vector2> CreateCircle(double radius, int sides) {
        String circleKey = radius + "x" + sides;
        if (circleCache.containsKey(circleKey)) {
            return circleCache.get(circleKey);
        }

        List<Vector2> vectors = new ArrayList<Vector2>();
        double max = 2.0 * Math.PI;
        double step = max / sides;

        for (double theta = 0.0; theta < max; theta += step) {
            vectors.add(new Vector2((float) (radius * Math.cos(theta)), (float) (radius * Math.sin(theta))));
        }

        vectors.add(new Vector2((float) (radius * Math.cos(0)), (float) (radius * Math.sin(0))));
        circleCache.put(circleKey, vectors);

        return vectors;
    }

    private List<Vector2> CreateArc(float radius, int sides, float startingAngle, float angle) {
        List<Vector2> points = new ArrayList<Vector2>();
        points.addAll(CreateCircle(radius, sides));
        points.remove(points.size() - 1);

        double curAngle = 0.0;
        double anglePerSide = TwoPI / sides;

        while ((curAngle + (anglePerSide / 2.0)) < startingAngle) {
            curAngle += anglePerSide;

            points.add(points.get(0));
            points.remove(0);
        }

        points.add(points.get(0));

        int sidesInArc = (int) ((angle / anglePerSide) + 0.5);
        for (int i = sidesInArc + 1; i < points.size() - sidesInArc - 1; i++) {
            points.remove(i);
        }

        return points;
    }

    private void DrawPoints(Vector2 position, List<Vector2> points, Color color, float opacity, float thickness, float layer) {
        if (points.size() < 2) {
            return;
        }

        for (int i = 0; i < points.size() - 1; i++) {
            DrawLine(points.get(i).add(position), points.get(i + 1).add(position), color, opacity, thickness, layer);
        }
    }

    public void DrawLine(Vector2 position, Color color, float opacity, float length, float angle, float thickness, float layer) {
        Vector2 PivotPoint = new Vector2(0, 1);

        com.badlogic.gdx.graphics.Color col = color.GetColor();
        col.a = opacity;

        Texture Pixel = getTexture("Pixel");
        spriteBatch.setColor(col);
        spriteBatch.draw(Pixel,
                position.X * ScaleAmount + ScaleOffset.X,
                (RealWindwoSize.Y - position.Y * ScaleAmount) - ScaleOffset.Y,
                PivotPoint.X,
                PivotPoint.Y,
                1.0f,
                1.0f,
                length * ScaleAmount,
                thickness * ScaleAmount,
                EngineService.I.ToDegree(angle),
                0, 0, 1, 1, false, false);
    }

    public void DrawLine(Vector2 position1, Vector2 position2, Color color, float opacity, float thickness, float layer) {
        /*float dx = position2.X - position1.X;
        float dy = position2.Y - position1.Y;
        float rad = (float)Math.atan2(dy, dx);*/

        DrawLine(position1, color, opacity, Vector2.Distance(position1, position2), -Vector2.GetAbsAngle(position1, position2), thickness, layer);
    }

    public void FillRectangle(Vector2 position, Vector2 size, Color color, float opacity, float angle, float layer) {
        FillRectangle(position, size, color, PivotPoint.LeftXUpY, opacity, angle, layer);
    }

    public void FillRectangle(Vector2 position, Vector2 size, Color color, PivotPoint pivot, float opacity, float angle, float layer) {
        float H = pixelSize / 2;
        float F = pixelSize;

        Vector2 PivotPoint = new Vector2(0, F);
        switch (pivot) {
            case CenterXCenterY:
                PivotPoint = new Vector2(H, H);
                break;
            case CenterXDownY:
                PivotPoint = new Vector2(H, 0);
                break;
            case CenterXUpY:
                PivotPoint = new Vector2(H, F);
                break;

            case LeftXCenterY:
                PivotPoint = new Vector2(0, H);
                break;

            case LeftXDownY:
                PivotPoint = new Vector2(0, 0);
                break;
            case LeftXUpY:
                PivotPoint = new Vector2(0, F);
                break;

            case RightXCenterY:
                PivotPoint = new Vector2(F, H);
                break;

            case RightXDownY:
                PivotPoint = new Vector2(F, 0);
                break;
            case RightXUpY:
                PivotPoint = new Vector2(F, F);
                break;
        }

        com.badlogic.gdx.graphics.Color col = color.GetColor();
        col.a = opacity;

        Texture Pixel = getTexture("PixelForRect");
        spriteBatch.setColor(col);
        spriteBatch.draw(Pixel,
                (position.X * ScaleAmount - PivotPoint.X) + ScaleOffset.X,
                (RealWindwoSize.Y - position.Y * ScaleAmount - PivotPoint.Y) - ScaleOffset.Y,
                PivotPoint.X,
                PivotPoint.Y,
                pixelSize,
                pixelSize,
                (size.X * ScaleAmount) / pixelSize,
                (size.Y * ScaleAmount) / pixelSize,
                EngineService.I.ToDegree(-angle),
                0, 0, 1, 1, false, false);

        //float Y = position.Y * ScaleAmount + ScaleOffset.Y;

        /*sprite = new Sprite(Pixel, 0, 0, 1, 1);
        sprite.setColor(col);
        sprite.setBounds(position.X * ScaleAmount + ScaleOffset.X, Y, 1.0f, 1.0f);
        sprite.setScale(size.X * ScaleAmount, size.Y * ScaleAmount);
        sprite.setRotation(EngineService.I.ToDegree(angle));
        sprite.setOrigin(PivotPoint.X, PivotPoint.Y);

        sprite.draw(spriteBatch);*/

        //spriteBatch.draw(sprite, 0, 0);

        /*MyGdxGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        MyGdxGame.shapeRenderer.setColor(col.r, col.g, col.b, opacity);
        //MyGdxGame.shapeRenderer.translate(0, 0, 0);
        //MyGdxGame.shapeRenderer.rotate(0, 0, 1, EngineService.I.ToDegree(angle));
        MyGdxGame.shapeRenderer.rect((position.X - (size.X * PivotPoint.X)) * ScaleAmount + ScaleOffset.X, (position.Y - (size.Y * PivotPoint.Y)) * ScaleAmount + ScaleOffset.Y, size.X * ScaleAmount, size.Y * ScaleAmount);
        MyGdxGame.shapeRenderer.end();*/
    }

    private void DrawRectangle(Rectangle rect, Color color, float opacity, float thickness, float layer) {
        DrawLine(new Vector2(rect.Left, rect.Top), new Vector2(rect.Right, rect.Top), color, opacity, thickness, layer);
        DrawLine(new Vector2(rect.Left + 1f, rect.Top), new Vector2(rect.Left + 1f, rect.Bottom + thickness), color, opacity, thickness, layer);
        DrawLine(new Vector2(rect.Left, rect.Bottom), new Vector2(rect.Right, rect.Bottom), color, opacity, thickness, layer);
        DrawLine(new Vector2(rect.Right + 1f, rect.Top), new Vector2(rect.Right + 1f, rect.Bottom + thickness), color, opacity, thickness, layer);
    }

    public void DrawRectangle(Vector2 position, Vector2 size, Color color, float opacity, float thickness, float layer) {
        DrawRectangle(new Rectangle((int) position.X, (int) position.Y, (int) size.X, (int) size.Y), color, opacity, thickness, layer);
    }

    public void DrawTexture(String textureName, Vector2 position, Rectangle rect, Vector2 pivot, float size, Color color, float opacity,
                            float angle, SpriteEffects effect, float layer) {
        DrawTexture(textureName, position, rect, pivot, new Vector2(size), color, opacity, angle, effect, layer);
    }

    public void DrawTexture(String textureName, Vector2 position, Rectangle rect, Vector2 pivot, Vector2 size, Color color, float opacity,
                            float angle, SpriteEffects effect, float layer) {
        Texture texture = getTexture(textureName);

        com.badlogic.gdx.graphics.Color col = color.GetColor();
        col.a = opacity;

        Vector2 s = new Vector2(texture.getWidth() * ScaleAmount * size.X, texture.getHeight() * ScaleAmount * size.Y);

        spriteBatch.setColor(col);

        spriteBatch.draw(texture,
                position.X * ScaleAmount + ScaleOffset.X - s.X / 2,
                (RealWindwoSize.Y - position.Y * ScaleAmount - s.Y / 2) - ScaleOffset.Y,
                s.X, s.Y);

        /*spriteBatch.draw(texture,
                position.X * ScaleAmount + ScaleOffset.X - pivot.X,
                RealWindwoSize.Y - position.Y * ScaleAmount + ScaleOffset.Y - pivot.Y,
                pivot.X,
                pivot.Y,
                texture.getWidth(),
                texture.getHeight(),
                (size.X * ScaleAmount) / texture.getWidth(),
                (size.Y * ScaleAmount) / texture.getHeight(),
                EngineService.I.ToDegree(angle),
                0, 0, 1, 1, false, false);*/
    }

    public void FillArrow(Vector2 position1, Vector2 position2, Color color, float opacity, float thickness, float ToGoal, float layer) {
        float distance = Vector2.Distance(position1, position2);
        float angle = (float) Math.atan2(position2.Y - position1.Y, position2.X - position1.X);
        DrawLine(position1, color, opacity, distance - ToGoal, angle, thickness, layer);
    }

    public void DrawSoftLine(Vector2 position, Color color, float opacity, float length, float angle, float thickness, float layer) {
        FillCircle(position, thickness / 2, 50, Color.Black, opacity, layer);
        DrawLine(position.sub(new Vector2(0, thickness / 2)), color, opacity, length, angle, thickness, layer);
        FillCircle(position.add(new Vector2(1, 0).mul(length)), thickness / 2, 50, Color.Black, opacity, layer);
    }

    public void DrawStrokeLine(Vector2 position, Color color, float opacity, float length, float angle, float thickness, float space, float stroke, float layer) {
        Vector2 dir = GetRotatedVectorR(-angle);
        Vector2 pos = position;

        int count = (int) ((length + space) / (stroke + space));
        float step = stroke + space;

        pos.sub(dir.mul((step / 4 + thickness / 4)));
        for (int i = 0; i < count; i++) {
            DrawLine(pos, color, opacity, step - space, angle, thickness, layer);
            pos = pos.add(dir.mul(step));
        }
    }

    public void DrawDotLine(Vector2 position, Color color, float opacity, float length, float angle, float thickness, float layer) {
        Vector2 dir = GetRotatedVectorR(angle);
        Vector2 pos = position;

        int count = (int) ((length) / 20);
        float step = (length) / count;

        for (int i = 0; i < count + 2; i++) {
            FillCircle(pos, thickness / 2, 50, Color.Black, opacity, layer);
            pos = pos.add(dir.mul(step));
        }
    }

    public void DrawCircle(Vector2 center, float radius, int sides, Color color, float opacity, float thickness, float layer) {
        com.badlogic.gdx.graphics.Color col = color.GetColor();
        col.a = opacity;

        /*MyGdxGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        MyGdxGame.shapeRenderer.setColor(col);
        MyGdxGame.shapeRenderer.circle(center.X * ScaleAmount + ScaleOffset.X, center.Y * ScaleAmount + ScaleOffset.Y, radius * ScaleAmount);
        MyGdxGame.shapeRenderer.end();*/

        DrawPoints(center, CreateArc(radius, sides, 0, TwoPI), color, opacity, thickness, layer);
        //MyCanvas.drawCircle(center.X * ScaleAmount + ScaleOffset.X, center.Y * ScaleAmount + ScaleOffset.Y, radius * ScaleAmount, PaintBase);
    }

    public void FillCircle(Vector2 center, float radius, int sides, Color color, float opacity, float layer) {
        DrawPoints(center, CreateArc(radius, sides, 0, TwoPI), color, opacity, radius, layer);
        //MyCanvas.drawCircle(center.X * ScaleAmount + ScaleOffset.X, center.Y * ScaleAmount + ScaleOffset.Y, radius * ScaleAmount, PaintBase);
    }

    public void FillGearCircle(Vector2 center, float radius, int sides, Color color, float opacity, float layer) {
        List<Vector2> points = CreateCircle(radius, sides);

        float len = (float) Math.PI * (radius * 2) / sides;
        float startAngle = (float) Math.PI / 4;
        float angleStep = ((float) Math.PI * 2) / sides;

        if (points.size() < 2) {
            return;
        }

        for (int i = 0; i < points.size() - 1; i++) {
            FillRectangle(points.get(i).add(center), new Vector2(len), color, PivotPoint.CenterXCenterY, opacity, startAngle - angleStep * i, layer);
        }

        FillCircle(center, radius, sides, color, opacity, layer + 1);
    }

    public void FillSawCircle(Vector2 center, float radius, int sides, Color color, float opacity, float layer) {
        List<Vector2> points = CreateCircle(radius, sides);

        float len = (float) Math.PI * (radius * 2) / sides;
        float startAngle = (float) Math.PI / 4 - (float) Math.PI / 10;
        float angleStep = ((float) Math.PI * 2) / sides;

        if (points.size() < 2) {
            return;
        }

        for (int i = 0; i < points.size() - 1; i++) {
            FillRectangle(points.get(i).add(center), new Vector2(len), color, PivotPoint.CenterXCenterY, opacity, startAngle - angleStep * i, layer);
        }

        FillCircle(center, radius, sides, color, opacity, layer + 1);
    }

    public void FillSoftGearCircle(Vector2 center, float radius, int sides, Color color, float opacity, float layer) {
        List<Vector2> points = CreateCircle(radius, sides);

        float len = (float) Math.PI * (radius * 2) / sides;

        if (points.size() < 2) {
            return;
        }

        for (int i = 0; i < points.size() - 1; i++) {
            FillCircle(points.get(i).add(center), len / 4 + len / 8, sides, color, opacity, layer + 1);
        }

        FillCircle(center, radius, sides, color, opacity, layer + 1);
    }

    public void FillAlphaCircle(Vector2 center, float radius, Color color, float opacity, float layer) {
        FillCircle(center, radius, 150, color, opacity, layer);
    }

    public void DrawArc(Vector2 center, float radius, int sides, float startingAngle, float angle, Color color, float opacity, float layer) {
        List<Vector2> arc = CreateArc(radius, sides, startingAngle, angle);
        DrawPoints(center, arc, color, opacity, radius, layer);
    }

    public void DrawPartArc(Vector2 center, float radius, int sides, float startingAngle, float angle, Color color, float opacity, float thickness, float layer) {
        List<Vector2> arc = CreateArc(radius, sides, startingAngle, angle);
        DrawPoints(center, arc, color, opacity, thickness, layer);
    }

    public void DrawText(String text, Vector2 position, Color FontColor, float opacity, String fontName, float size, TextAlign align, float angle, float layer) {
        DrawText(text, position, FontColor, opacity, fontName, size, align, angle, layer, true);
    }

    public void DrawText(String text, Vector2 position, Color FontColor, float opacity, String fontName, float size, TextAlign align, float angle, float layer, Boolean useLocalize) {
        if (text == null) {
            text = "";
        }

        if (useLocalize) {
            text = LocalizeParts(text);
        }

        BitmapFont font = getFont(fontName);
        GlyphLayout glyphLayout = new GlyphLayout();

        glyphLayout.setText(font, text);
        Vector2 Measure = new Vector2(glyphLayout.width, glyphLayout.height).mul(size);

        switch (align) {
            case CenterXY:
                Measure = Measure.div(2);
                break;
            case LeftX:
                Measure.X = 0;
                break;
            case LeftXCenterY:
                Measure.X = 0;
                Measure.Y = (Measure.div(2)).Y;
                break;
            case RightX:
                break;
            case RightXCenterY:
                Measure.Y = (Measure.div(2)).Y;
                break;
        }

        com.badlogic.gdx.graphics.Color col = FontColor.GetColor();
        col.a = opacity;

        if (size == 0) {
            return;
        }

        font.getData().setScale(size);
        font.setColor(col);

        spriteBatch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Vector2 pos = new Vector2(position.X * ScaleAmount + ScaleOffset.X - Measure.X,
                (RealWindwoSize.Y - position.Y * ScaleAmount + Measure.Y) - ScaleOffset.Y);

        Vector2 pos2 = new Vector2(position.X * ScaleAmount + ScaleOffset.X,
                (RealWindwoSize.Y - position.Y * ScaleAmount) - ScaleOffset.Y);

        if (angle != 0) {
            textRotation.idt();
            textRotation.translate(pos2.X, pos2.Y, 0);
            textRotation.rotate(0, 0, 1, EngineService.I.ToDegree(-angle));
            //textRotation.scale(ScaleAmount, ScaleAmount, 1);

            textRotation.translate(-pos2.X, -pos2.Y, 0);

            //textRotation.translate(ScaleOffset.X - Measure.X,  Measure.Y- ScaleOffset.Y, 0);

            //textRotation.setToRotation(new Vector3(pos.X, pos.Y, 0), EngineService.I.ToDegree(0.05f));
            spriteBatch.setTransformMatrix(textRotation);
        }

        spriteBatch.begin();
        font.draw(spriteBatch, text, pos.X, pos.Y);
        spriteBatch.end();

        if (angle != 0) {
            textRotation.idt();
            spriteBatch.setTransformMatrix(textRotation);
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.begin();

        font.getData().setScale(1);

        //mx4Font.setToRotation(new Vector3(position.X, position.Y, 0), 0);
        //spriteBatch.setTransformMatrix(mx4Font);
    }

    static public Vector2 GetRotatedVectorR(float angle) {
        Matrix4 mx = new Matrix4();

        mx.setToRotation(new Vector3(0, 0, 0), angle);
        Vector3 temp = mx.getTranslation(new Vector3(1, 1, 0));

        return new Vector2(temp.x, temp.y);
    }
}
