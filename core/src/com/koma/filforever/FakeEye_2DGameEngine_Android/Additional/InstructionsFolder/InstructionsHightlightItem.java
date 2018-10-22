package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.SpriteEffects;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class InstructionsHightlightItem extends InstructionsItem {
    public Vector2 ScreenSize;
    public Vector2 RealSize;

    public InstructionsHightlightItem(Vector2 position, Color color, Vector2 size, float opacity, Vector2 screenSize) {
        super(position, color);
        ScreenSize = screenSize;

        Scale = new Vector2(size.X / 100.0f, size.Y / 100.0f);
        Size = new Vector2(150);
        RealSize = Size.mul(Scale).mul(0.5f);

        Opacity = opacity;
    }

    @Override
    public void Draw(SpriteAdapter spriteBatch, int layer) {
        Vector2 S = Size.mul(0.5f);

        Vector2 LeftUp = Position.sub(RealSize);
        Vector2 RighUp = Position.sub(RealSize.mul(new Vector2(-1, 1)));
        Vector2 LeftDown = Position.sub(RealSize.mul(new Vector2(1, -1)));
        Vector2 RightDown = Position.sub(RealSize.mul(new Vector2(-1, -1)));

        if (LeftUp.Y > 0) {
            spriteBatch.FillRectangle(Vector2.Zero, new Vector2(ScreenSize.X, LeftUp.Y), ItemColor, Opacity, Rotation, layer);
        }
        if (LeftUp.X > 0) {
            spriteBatch.FillRectangle(new Vector2(0, LeftUp.Y), new Vector2(LeftUp.X, LeftDown.Y - LeftUp.Y), ItemColor, Opacity, Rotation, layer);
        }
        if (RighUp.X < ScreenSize.X) {
            spriteBatch.FillRectangle(new Vector2(RighUp.X, LeftUp.Y), new Vector2(ScreenSize.X - RighUp.X, LeftDown.Y - LeftUp.Y), ItemColor, Opacity, Rotation, layer);
        }
        if (LeftDown.Y < ScreenSize.Y) {
            spriteBatch.FillRectangle(new Vector2(0, LeftDown.Y), new Vector2(ScreenSize.X, ScreenSize.Y - LeftDown.Y), ItemColor, Opacity, Rotation, layer);
        }

        spriteBatch.DrawTexture("HeightlightItem", Position, null, S, Scale, ItemColor, Opacity, Rotation, SpriteEffects.None, layer);
        spriteBatch.DrawTexture("HeightlightItemUper", Position, null, S, Scale, Color.White, 1, Rotation, SpriteEffects.None, layer + 1);
    }
}
