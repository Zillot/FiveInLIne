package com.koma.filforever.RootFolder.Data.Rain;

import java.util.Random;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle.ParticleSystemItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle.ParticleSystem_Single;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.PivotPoint;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class DropParticleSystem extends ParticleSystem_Single {
    private static Random rand = new Random();

    public Color color;

    public Vector2 Wind;
    public float WindPower;

    public float DropSpeed;
    public float YGoal;

    public DropParticleSystem(Vector2 position, Vector2 wind, float windPower, float dropSpeed, float yGoal, float DropSize) {
        super(position, 3, 6, 1);

        Size = new Vector2(DropSize);
        color = Color.Blue;

        YGoal = yGoal;
        DropSpeed = dropSpeed;
        WindPower = windPower;
        Wind = wind;

        SetRotation(0.5f);
        Mass = 3.0f;
    }

    public void DoBlow() {
        if (Charges > 0 || Charges == -1) {
            SpriteAdapter.SelfPointer.PlaySound("FireworkBlow", 1);

            int itemsCount = rand.nextInt(ItemsPerBlowMax - ItemsPerBlowMin) + ItemsPerBlowMin;
            float rotationStep = (float) Math.PI / (float) itemsCount;

            for (int i = 0; i < itemsCount; i++) {
                Vector2 thirdPartyVector = EngineService.I.GetRotatedVectorR(rotationStep * i);
                float thirdPartyPower = rand.nextInt(7) + 3;

                items.add(new DropParticleItem(new Vector2(Position.X, YGoal), new Vector2(3), thirdPartyVector, thirdPartyPower, color));
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

        if (Position.Y >= YGoal) {
            DoBlow();
        }

        Position = Position.add(Wind.mul(WindPower));
        Position = Position.add(new Vector2(0, 1).mul(DropSpeed));

        toRet = toRet | super.Update(timeDelta);

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        for (ParticleSystemItem item : items) {
            item.Draw(spriteBatch, layer);
        }

        if (Charges > 0) {
            spriteBatch.FillRectangle(Position, Size.mul(Scale), color, PivotPoint.CenterXCenterY, Opacity, Rotation, layer);
        }
    }
}
