package com.koma.filforever.RootFolder.Data.Firework;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle.ParticleSystemItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.RootFolder.Data.DataControll;

public class CubeFireworkTrailtem extends ParticleSystemItem {
    public Color Color;

    public CubeFireworkTrailtem(Vector2 position, Vector2 Size, Color color) {
        super(position, Size, new Vector2(0, 1), 0);

        TranslateAlpha(0, 2.5f);
        TranslateScale(new Vector2(0), 1.0f);

        Color = color;
    }

    @Override
    public Boolean isDead() {
        return Opacity == 0 || Size.X == 0 || Size.Y == 0;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        spriteBatch.FillRectangle(Position, Size.mul(Scale), Color, Opacity, Rotation, layer);
    }
}
