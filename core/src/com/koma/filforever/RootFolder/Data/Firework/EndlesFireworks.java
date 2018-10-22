package com.koma.filforever.RootFolder.Data.Firework;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class EndlesFireworks {
    private static Random rand = new Random();

    private List<CubedFirework> Rockets;

    private int lounches;
    private int repPackLounches;
    private int repLounchRockets;

    private float toLounchBase;
    private float toLounchLeft;

    private float toLounchPackBase;
    private float toLounchPackLeft;

    public Boolean Online;

    public EndlesFireworks() {
        Rockets = new LinkedList<CubedFirework>();

        Disable();
        Restart();
    }

    public void Enable() {
        Online = true;
        Restart();
    }

    public void Disable() {
        Online = false;
    }

    public void Restart() {
        repLounchRockets = 5;
        repPackLounches = 10;

        toLounchBase = 1.14f;
        toLounchLeft = 0.4f;

        toLounchPackBase = 10.0f;
        toLounchPackLeft = 0.0f;
    }

    public Boolean Update(float timeDelta) {
        Boolean toRet = false;

        for (int i = 0; i < Rockets.size(); i++) {
            toRet = toRet | Rockets.get(i).Update(timeDelta);

            if (Rockets.get(i).isDead()) {
                Rockets.remove(i--);
            }
        }

        if (!Online) {
            return toRet;
        }

        toLounchPackLeft -= timeDelta;

        if (toLounchPackLeft <= 0) {
            lounches = repPackLounches;
            toLounchPackLeft = toLounchPackBase;
        }

        if (lounches > 0) {
            toLounchLeft -= timeDelta;

            if (toLounchLeft <= 0) {
                toLounchLeft = toLounchBase;
                lounches--;

                SpriteAdapter.SelfPointer.PlaySound("FireworkLounch", 1);

                for (int i = 0; i < repLounchRockets; i++) {
                    Vector2 dir = Vector2.Normalize(new Vector2((float) rand.nextDouble() * 0.3f - 0.2f, -1));
                    Rockets.add(new CubedFirework(new Vector2(rand.nextInt(280) + 100, 900), dir, rand.nextInt(20) + 10));
                }
            }
        }

        return toRet;
    }

    public void Draw(SpriteAdapter spriteBatch, int layer) {
        for (CubedFirework item : Rockets) {
            item.Draw(spriteBatch, layer);
        }
    }
}