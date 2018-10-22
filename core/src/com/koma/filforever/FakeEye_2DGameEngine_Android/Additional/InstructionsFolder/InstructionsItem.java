package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.InstructionsFolder;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;

public class InstructionsItem extends NodeItem {
    public Color ItemColor;

    public InstructionsItem(Vector2 position, Color color) {
        super(position, new Vector2(0), new Vector2(1, 1), 0, 1);
        ItemColor = color;
    }
}
