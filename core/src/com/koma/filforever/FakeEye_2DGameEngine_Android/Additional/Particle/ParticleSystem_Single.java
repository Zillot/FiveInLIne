package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class ParticleSystem_Single extends NodeItem {
    private static Random rand = new Random();
    public float BetweenTrailItems;

    public int ItemsPerBlowMin;
    public int ItemsPerBlowMax;

    public List<ParticleSystemItem> items;
    public List<ParticleSystemItem> Trail;

    public int Charges;

    public ParticleSystem_Single(Vector2 position, int itemsRepBlowMin, int itemsRepBlowMax, int charges) {
        super(position, new Vector2(0), new Vector2(1, 1), 0, 1);

        Charges = charges;

        BetweenTrailItems = 5.0f;

        ItemsPerBlowMin = itemsRepBlowMin;
        ItemsPerBlowMax = itemsRepBlowMax;

        Trail = new LinkedList<ParticleSystemItem>();
        items = new LinkedList<ParticleSystemItem>();

        this.EnableGravity();
    }

    public Boolean isDead() {
        return Charges == 0 && items.size() == 0;
    }

    public void DoBlow() {
        if (Charges > 0 || Charges == -1) {
            Charges--;

            if (Charges <= 0) {
                TranslateScale(new Vector2(0), 0.1f);
                TranslateAlpha(0, 0.1f);
            }
        }
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        toRet = toRet | super.Update(timeDelta);

        for (int i = 0; i < Trail.size(); i++) {
            toRet = toRet | Trail.get(i).Update(timeDelta);

            if (Trail.get(i).isDead()) {
                Trail.remove(i--);
            }
        }

        for (int i = 0; i < items.size(); i++) {
            toRet = toRet | items.get(i).Update(timeDelta);

            if (items.get(i).isDead()) {
                items.remove(i--);
            }
        }

        return toRet;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        super.Draw(spriteBatch);

        for (ParticleSystemItem item : Trail) {
            item.Draw(spriteBatch, layer - 1);
        }

        for (ParticleSystemItem item : items) {
            item.Draw(spriteBatch, layer);
        }

        spriteBatch.FillRectangle(Position, Size.mul(Scale), Color.Blue, Opacity, Rotation, layer);
    }
}
