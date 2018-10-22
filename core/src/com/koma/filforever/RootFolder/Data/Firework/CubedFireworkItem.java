package com.koma.filforever.RootFolder.Data.Firework;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle.ParticleSystemItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.RootFolder.Data.DataControll;

public class CubedFireworkItem extends ParticleSystemItem {
    public float FullPower;
    public Color Color;

    public CubedFireworkItem(Vector2 position, Vector2 Size, Vector2 thirdPartyVector, float power, Color color) {
        super(position, Size, thirdPartyVector, power);

        Color = color;
        FullPower = power;

        Mass = 0.3f;
        EnableGravity();
    }

    @Override
    public Boolean isDead() {
        return ThirdPartyEffectPower <= 1.5f ||
                Position.X < -10 || Position.X > DataControll.WindowWidth + 10 ||
                Position.Y < -10 || Position.Y > DataControll.WindowHeight + 10;
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        spriteBatch.FillRectangle(Position, Size.mul(Scale.mul(ThirdPartyEffectPower / FullPower)), Color, Opacity * ThirdPartyEffectPower / FullPower, Rotation, layer);
    }
}
