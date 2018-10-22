package com.koma.filforever.RootFolder.Data.Firework;

import java.util.Random;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle.ParticleSystemItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle.ParticleSystem_Single;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.PivotPoint;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class CubedFirework extends ParticleSystem_Single {
    private static Random rand = new Random();

    public float ToBlow;
    public Vector2 LastTrailPos;

    public Color[] colors = new Color[]{Color.Red, Color.Blue, Color.Orange, Color.Green};

    public CubedFirework(Vector2 position, Vector2 Direction, float power) {
        super(position, 40, 60, 1);

        BetweenTrailItems = 2.0f;

        Size = new Vector2(6);
        ThirdPartyEffectVector = Direction;
        ThirdPartyEffectPower = power;

        ToBlow = (float) rand.nextInt(8) * 0.1f;

        TranslateRotation(10, -0.4f + (float) rand.nextInt(8) * 0.1f);

        LastTrailPos = position;
        Mass = 1.0f;
    }

    @Override
    public void DoBlow() {
        if (Charges > 0 || Charges == -1) {
            SpriteAdapter.SelfPointer.PlaySound("FireworkBlow", 1);

            int itemsCount = rand.nextInt(ItemsPerBlowMax - ItemsPerBlowMin) + ItemsPerBlowMin;
            float rotationStep = (float) (Math.PI * 2) / (float) itemsCount;

            Color col = colors[rand.nextInt(colors.length)];

            for (int i = 0; i < itemsCount; i++) {
                Vector2 thirdPartyVector = EngineService.I.GetRotatedVectorR(rotationStep * i);
                float thirdPartyPower = rand.nextInt(5) + 1;

                items.add(new CubedFireworkItem(Position, new Vector2(5), thirdPartyVector, thirdPartyPower, col));
            }

            Charges--;

            if (Charges <= 0) {
                TranslateScale(new Vector2(0), 5.0f);
                TranslateAlpha(0, 5.0f);
            }
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        if (Charges > 0) {
            if (Vector2.Distance(Position, LastTrailPos) > BetweenTrailItems) {
                LastTrailPos = Position;
                Trail.add(new CubeFireworkTrailtem(Position, new Vector2(4), Color.Gray));
            }
        }

        if (GravitySpeed >= ThirdPartyEffectPower) {
            ToBlow -= timeDelta;

            if (ToBlow <= 0) {
                DoBlow();
            }
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        for (ParticleSystemItem item : Trail) {
            item.Draw(spriteBatch, layer - 1);
        }

        for (ParticleSystemItem item : items) {
            item.Draw(spriteBatch, layer);
        }

        spriteBatch.FillRectangle(Position, Size.mul(Scale), Color.IndianRed, PivotPoint.CenterXCenterY, Opacity, Rotation, layer);
    }
}
