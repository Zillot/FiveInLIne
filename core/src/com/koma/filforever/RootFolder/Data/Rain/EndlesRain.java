package com.koma.filforever.RootFolder.Data.Rain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.Particle.ParticleSystem_Single;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class EndlesRain {
    private static Random rand = new Random();

    private Color dropColor;
    private List<DropParticleSystem> Drops;

    private float toNewDropBase;
    private float toNewDropLeft;

    private float Rate;

    public Boolean Online;

    public EndlesRain() {
        dropColor = Color.DarkBlue;
        Drops = new LinkedList<DropParticleSystem>();

        Disable();
        Restart();
    }

    public void SetColor(Color color) {
        dropColor = color;
    }

    public void Enable() {
        Online = true;
        Restart();
    }

    public void Disable() {
        Online = false;
    }

    public void Restart() {
        Rate = 0.01f;

        toNewDropBase = Rate;
        toNewDropLeft = toNewDropBase;
    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        SpriteAdapter.SelfPointer.PlaySound("Rain", 1, true);

        for (int i = 0; i < Drops.size(); i++) {
            toRet = toRet | Drops.get(i).Update(timeDelta);

            if (Drops.get(i).isDead()) {
                Drops.remove(i--);
            }
        }

        if (!Online) {
            SpriteAdapter.SelfPointer.StopSound("Rain");
            return toRet;
        }

        toNewDropLeft -= timeDelta;

        if (toNewDropLeft <= 0) {
            toNewDropLeft = toNewDropBase;

            Vector2 dir = new Vector2(-1.0f, 0);
            Drops.add(new DropParticleSystem(new Vector2(rand.nextInt(580), -10), dir, 3 + rand.nextInt(3), 10 + rand.nextInt(15), 720, 3 + rand.nextInt(3)));
            Drops.get(Drops.size() - 1).color = dropColor;
        }

        return toRet;
    }

    public void Draw(SpriteAdapter spriteBatch, int layer) {
        for (ParticleSystem_Single item : Drops) {
            item.Draw(spriteBatch, layer);
        }
    }
}
