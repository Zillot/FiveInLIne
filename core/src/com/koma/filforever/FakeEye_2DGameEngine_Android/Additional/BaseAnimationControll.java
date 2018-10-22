package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class BaseAnimationControll extends NodeItem {
    public String SheetName;
    public Vector2 SheetArrSize;
    public float curFrame;
    public int FramesCount;
    public float AnimationSpeed;
    public int TimesDone;

    public BaseAnimationControll(String sheetName, Vector2 position, Vector2 size, int startFrame, int framesCount, float animationSpeed, Vector2 sheetRowsCols) {
        super(position, size, new Vector2(1, 1), 0, 1);
        TimesDone = 0;

        SheetArrSize = sheetRowsCols;
        FramesCount = framesCount;
        curFrame = startFrame;
        AnimationSpeed = animationSpeed;
        SheetName = sheetName;
    }

    public void OffsetFrame(float offset) {
        curFrame = offset;
        curFrame = curFrame % FramesCount;
    }

    public Object GetDublicate() {
        return new BaseAnimationControll(SheetName, Position, Size, 0, FramesCount, AnimationSpeed, SheetArrSize);
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = true;
        toRet = toRet | super.Update(timeDelta);
        return toRet;
    }

    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {
        Position = Position.add(Offset);
        Draw(spriteBatch, layer);
        Position = Position.sub(Offset);
    }
}
