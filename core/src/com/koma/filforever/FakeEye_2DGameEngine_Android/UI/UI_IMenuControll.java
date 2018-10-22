package com.koma.filforever.FakeEye_2DGameEngine_Android.UI;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

public abstract class UI_IMenuControll extends NodeItem {
    public Vector2 ControllPosition;
    public Vector2 ControllSize;
    public String ControllID;

    public int Layer;

    public Delegate[] onTap;
    public Delegate[] onDoubleTap;
    public Delegate[] onHold;
    public Delegate[] onRightSwap;
    public Delegate[] onLeftSwap;
    public Delegate[] onDownSwap;
    public Delegate[] onUpSwap;
    public Delegate[] onDragStarted;
    public Delegate[] onDragDelta;
    public Delegate[] onDragEnded;

    public UI_IMenuControll(Vector2 position, Vector2 size) {
        super(position, size, new Vector2(1, 1), 0, 1);
        onTap = new Delegate[6];
        onDoubleTap = new Delegate[6];
        onHold = new Delegate[6];
        onRightSwap = new Delegate[6];
        onLeftSwap = new Delegate[6];
        onDownSwap = new Delegate[6];
        onUpSwap = new Delegate[6];
        onDragStarted = new Delegate[6];
        onDragDelta = new Delegate[6];
        onDragEnded = new Delegate[6];

        Layer = 20;
    }

    public void ControllTouchEventCall(Vector2 ToouchPosition, TouchEventType TouchEventName, Object arg) {
        if (TouchEventName == TouchEventType.Tap) {
            for (Delegate SomeTouchItem : onTap) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.DoubleTap) {
            for (Delegate SomeTouchItem : onDoubleTap) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.Hold) {
            for (Delegate SomeTouchItem : onHold) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.RightSwap) {
            for (Delegate SomeTouchItem : onRightSwap) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.LeftSwap) {
            for (Delegate SomeTouchItem : onLeftSwap) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.DownSwap) {
            for (Delegate SomeTouchItem : onDownSwap) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.UpSwap) {
            for (Delegate SomeTouchItem : onUpSwap) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.DragStarted) {
            for (Delegate SomeTouchItem : onDragStarted) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.DragDelta) {
            for (Delegate SomeTouchItem : onDragDelta) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }

        if (TouchEventName == TouchEventType.DragEnded) {
            for (Delegate SomeTouchItem : onDragEnded) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.ControllTouchDelegate(ToouchPosition, arg);
                }
            }
        }
    }

    public Boolean ContentUpdated() {
        return false;
    }

    public abstract void TouchEventCheck(Vector2 TouchPosition, TouchEventType TouchEventName);

    @Override
    public Boolean Update(float timeDelta) {
        return super.Update(timeDelta);
    }

    public void Draw(SpriteAdapter spriteBatch) {
        Draw(spriteBatch, Vector2.Zero, Layer);
    }

    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset, int layer) {

    }
}
