package com.koma.filforever.RootFolder.Data.Rain;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle.ParticleSystemItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class DropParticleItem extends ParticleSystemItem {
    public float FullPower;
    public Color Color;

    public DropParticleItem(Vector2 position, Vector2 Size, Vector2 thirdPartyVector, float power, Color color) {
        super(position, Size, thirdPartyVector, power);

        Color = color;
        FullPower = power;

        Mass = 1.0f;

        TranslateScale(new Vector2(0), 2.0f);
        TranslateAlpha(0, 2.0f);

        EnableGravity();
    }

    @Override
    public Boolean isDead() {
        return Opacity <= 0;
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        return toRet;
    }

    public void Draw(SpriteAdapter spriteBatch, int layer) {
        spriteBatch.FillRectangle(Position, Size.mul(Scale), Color, Opacity, Rotation, layer);
    }
}
