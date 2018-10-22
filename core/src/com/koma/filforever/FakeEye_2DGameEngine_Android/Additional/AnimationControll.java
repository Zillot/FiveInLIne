package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Rectangle;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.SpriteEffects;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class AnimationControll extends BaseAnimationControll {
    public AnimationControll(String sheetName, Vector2 position, Vector2 size, int startFrame, int framesCount, float animationSpeed, Vector2 sheetRowsCols) {
        super(sheetName, position, size, startFrame, framesCount, animationSpeed, sheetRowsCols);
    }

    @Override
    public Boolean Update(float timeDelta) {
        Boolean toRet = true;

        toRet = toRet | super.Update(timeDelta);
        curFrame += timeDelta * AnimationSpeed;

        if ((int) curFrame >= FramesCount) {
            curFrame = 0;
            TimesDone++;
        }

        return toRet;
    }

    @Override
    public Object GetDublicate() {
        return new AnimationControll(SheetName, Position, Size, 0, FramesCount, AnimationSpeed, SheetArrSize);
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        int Column = (int) curFrame % (int) SheetArrSize.X;
        int Row = (int) curFrame / (int) SheetArrSize.X;

        spriteBatch.DrawTexture(SheetName, Position, new Rectangle(Column * (int) Size.X, Row * (int) Size.Y, (int) Size.X, (int) Size.Y), Size.div(2), Scale, Color.White, Opacity, Rotation, SpriteEffects.None, layer);
    }
}
