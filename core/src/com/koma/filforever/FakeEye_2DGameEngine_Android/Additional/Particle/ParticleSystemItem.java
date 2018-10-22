package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;

public class ParticleSystemItem extends NodeItem {
    public ParticleSystemItem(Vector2 position, Vector2 Size, Vector2 thirdPartyVector, float power) {
        super(position, Size, new Vector2(1, 1), 0, 1);

        ThirdPartyEffectVector = thirdPartyVector;
        ThirdPartyEffectPower = power;
    }

    public Boolean isDead() {
        return ThirdPartyEffectPower <= 0;
    }
}
