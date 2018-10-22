package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;

import java.util.ArrayList;
import java.util.List;

public class TouchService {
    public static TouchService I = new TouchService();

    private List<TouchItem> TouchMemory;
    private Boolean TouchOnline;
    private Boolean TouchBlock;
    private Boolean DragEnabled;
    private float TouchOnliteTime;

    private List<List<TouchDebugItem>> DebugPoints;
    private String DebugaddInfo;
    private Boolean DebagMode;

    public Vector2 DragStartPos;
    public Vector2 SwapStartPos;
    public Vector2 SwapEndPos;
    public Vector2 TouchPosition;

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

    public Delegate[] onContinuesTouch;
    public Delegate[] onNoTouch;

    public TouchService() {
        TouchDown = false;

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

        onContinuesTouch = new Delegate[6];
        onNoTouch = new Delegate[6];

        DragStartPos = Vector2.Zero;
        SwapStartPos = Vector2.Zero;
        SwapEndPos = Vector2.Zero;
        TouchPosition = Vector2.Zero;

        DebagMode = false;
        DebugPoints = new ArrayList<List<TouchDebugItem>>();

        TouchMemory = new ArrayList<TouchItem>();
        TouchOnliteTime = 0;
        TouchOnline = false;
        TouchBlock = false;
        DragEnabled = false;

        DebugaddInfo = "";
    }

    public void DisableDebug() {
        if (!DebagMode)
            return;

        DebugPoints = new ArrayList<List<TouchDebugItem>>();
        DebagMode = false;

        onTap[5] = null;
        onDoubleTap[5] = null;
        onHold[5] = null;
        onRightSwap[5] = null;
        onLeftSwap[5] = null;
        onDownSwap[5] = null;
        onUpSwap[5] = null;
        onDragStarted[5] = null;
        onDragDelta[5] = null;
        onDragEnded[5] = null;
    }

    public void EnableDebug() {
        if (DebagMode) {
            return;
        }

        DebugPoints = new ArrayList<List<TouchDebugItem>>();
        DebagMode = true;

        onTap[5] = TouchService_onTapAndDoubleTapAndHold;
        onDoubleTap[5] = TouchService_onTapAndDoubleTapAndHold;
        onHold[5] = TouchService_onTapAndDoubleTapAndHold;
        onRightSwap[5] = TouchService_onAllSwap;
        onLeftSwap[5] = TouchService_onAllSwap;
        onDownSwap[5] = TouchService_onAllSwap;
        onUpSwap[5] = TouchService_onAllSwap;
        onDragStarted[5] = TouchService_onDragStart;
        onDragDelta[5] = TouchService_onDragDelta;
        onDragEnded[5] = TouchService_onDragStop;
    }

    protected Delegate TouchService_onTapAndDoubleTapAndHold = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            String text = "";
            switch (TouchEventName) {
                case Tap:
                    text = "Tap";
                    break;
                case DoubleTap:
                    text = "DoubleTap";
                    break;
                case Hold:
                    text = "Hold";
                    break;
            }

            TouchDebugItem temp = new TouchDebugItem(position, 1.0f, false);
            temp.Text = text;
            temp.addText = DebugaddInfo;

            List<TouchDebugItem> TempLst = new ArrayList<TouchDebugItem>();
            TempLst.add(temp);

            DebugPoints.add(TempLst);
            //DebugService.I.addFloatRight(text, Color.Black);
        }
    };

    private Delegate TouchService_onAllSwap = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            String text = "";
            switch (TouchEventName) {
                case LeftSwap:
                    text = "LeftSwap";
                    break;
                case RightSwap:
                    text = "RightSwap";
                    break;
                case DownSwap:
                    text = "DownSwap";
                    break;
                case UpSwap:
                    text = "UpSwap";
                    break;
            }

            TouchDebugItem temp1 = new TouchDebugItem(SwapStartPos, 1.0f, false);
            TouchDebugItem temp2 = new TouchDebugItem(SwapEndPos, 1.0f, false);
            temp1.Text = text;
            temp1.addText = DebugaddInfo;

            List<TouchDebugItem> TempLst = new ArrayList<TouchDebugItem>();
            TempLst.add(temp1);
            TempLst.add(temp2);

            DebugPoints.add(TempLst);
            //DebugService.I.addFloatRight(text + " X:" + position.X + "Y:" + position.Y, Color.Black);
        }
    };

    private Delegate TouchService_onDragStart = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            TouchDebugItem temp1 = new TouchDebugItem(DragStartPos, 1.0f, true);
            TouchDebugItem temp2 = new TouchDebugItem(DragStartPos, 1.0f, true);
            temp2.Text = "Drag";
            temp2.addText = DebugaddInfo;

            List<TouchDebugItem> TempLst = new ArrayList<TouchDebugItem>();
            TempLst.add(temp1);
            TempLst.add(temp2);

            DebugPoints.add(TempLst);
            //DebugService.I.addFloatRight("DragStarted X:" + position.X + "Y:" + position.Y, Color.Black);
        }
    };

    private Delegate TouchService_onDragDelta = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            List<TouchDebugItem> tempLst = DebugPoints.get(DebugPoints.size() - 1);
            tempLst.get(tempLst.size() - 1).TouchPosition = position;
            tempLst.get(tempLst.size() - 1).addText = DebugaddInfo;
        }
    };

    private Delegate TouchService_onDragStop = new Delegate() {
        @Override
        public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
            List<TouchDebugItem> tempLst = DebugPoints.get(DebugPoints.size() - 1);

            for (TouchDebugItem item : tempLst) {
                item.Immortal = false;
            }

            //DebugService.I.addFloatRight("DragEnded X:" + position.X + "Y:" + position.Y, Color.Black);
        }
    };

    public void Dispose() {

    }

    public Boolean TouchDown;

    public void Update(float timeDelta, Vector2 pos) {
        DebugaddInfo = "";
        Boolean presed = TouchDown;

        if (TouchDown) {
            presed = true;
            TouchPosition = new Vector2(pos.X, pos.Y);
        }

        //Win phone and PC
        if (presed && !TouchOnline && !TouchBlock) {
            TouchMemory.add(new TouchItem(TouchPosition));
            TouchOnline = true;
        }

        if (presed && DragEnabled) {
            DebugaddInfo = "Delta:" + Vector2.Distance(DragStartPos, TouchPosition);

            for (Delegate SomeTouchItem : onDragDelta) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.TouchEvent(TouchEventType.DragDelta, TouchPosition);
                }
            }
        }

        if (presed) {
            for (Delegate SomeTouchItem : onContinuesTouch) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.TouchEvent(TouchEventType.DragDelta, TouchPosition);
                }
            }
        } else {
            for (Delegate SomeTouchItem : onNoTouch) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.TouchEvent(TouchEventType.DragDelta, TouchPosition);
                }
            }
        }

        if (presed && TouchOnline && !TouchBlock) {
            TouchOnliteTime += timeDelta;

            TouchItem item = TouchMemory.get(TouchMemory.size() - 1);
            if (TouchMemory.size() != 0 && TouchOnliteTime < 0.9f && Vector2.Distance(item.TouthStartPos, TouchPosition) > 15 && TouchOnliteTime > 0.05f) {
                TouchOnline = false;
                TouchBlock = true;
                DragEnabled = true;

                TouchOnliteTime = 0;

                DebugaddInfo = "Delta:0";

                DragStartPos = item.TouthStartPos;
                for (Delegate SomeTouchItem : onDragStarted) {
                    if (SomeTouchItem != null) {
                        SomeTouchItem.TouchEvent(TouchEventType.DragStarted, TouchPosition);
                    }
                }

                TouchMemory.clear();
            } else if (TouchMemory.size() != 0 && TouchOnliteTime > 1.0f && Vector2.Distance(item.TouthStartPos, TouchPosition) < 15) {
                DebugaddInfo = "X:" + TouchPosition.X + " - Y:" + TouchPosition.Y;

                TouchMemory.clear();
                TouchOnline = false;
                TouchBlock = true;

                TouchOnliteTime = 0;

                for (Delegate SomeTouchItem : onHold) {
                    if (SomeTouchItem != null) {
                        SomeTouchItem.TouchEvent(TouchEventType.Hold, TouchPosition);
                    }
                }

                TouchPosition = new Vector2(0, 0);
            }
        }

        if (!presed && TouchOnline) {
            TouchItem item = TouchMemory.get(TouchMemory.size() - 1);

            item.TouthEndPos = TouchPosition;
            item.TouchOnlineTime = TouchOnliteTime;

            TouchOnline = false;
            TouchOnliteTime = 0;
        }

        if (!presed && !TouchOnline) {
            TouchBlock = false;
        }

        if (!presed && DragEnabled) {
            DragEnabled = false;

            for (Delegate SomeTouchItem : onDragEnded) {
                if (SomeTouchItem != null) {
                    SomeTouchItem.TouchEvent(TouchEventType.DragEnded, TouchPosition);
                }
            }

            TouchPosition = new Vector2(0, 0);
        }

        if (TouchMemory.size() >= 1 && !TouchOnline) {
            TouchItem item = TouchMemory.get(TouchMemory.size() - 1);
            if (item.GetDistance() < 15) {
                DebugaddInfo = "";

                for (Delegate SomeTouchItem : onTap) {
                    if (SomeTouchItem != null) {
                        SomeTouchItem.TouchEvent(TouchEventType.Tap, TouchPosition);
                    }
                }

                TouchPosition = new Vector2(0, 0);
            } else if (item.GetXDistance() < 35 && item.GetYDistance() > 80) {
                SwapStartPos = item.TouthStartPos;
                SwapEndPos = item.TouthEndPos;

                DebugaddInfo = "XD:" + item.GetXDistance() + " - YD:" + item.GetYDistance() + " - T:" + item.TouchOnlineTime;

                if (item.TouthStartPos.Y > item.TouthEndPos.Y) {
                    for (Delegate SomeTouchItem : onUpSwap) {
                        if (SomeTouchItem != null) {
                            SomeTouchItem.TouchEvent(TouchEventType.UpSwap, TouchPosition);
                        }
                    }
                } else
                    for (Delegate SomeTouchItem : onDownSwap) {
                        if (SomeTouchItem != null) {
                            SomeTouchItem.TouchEvent(TouchEventType.DownSwap, TouchPosition);
                        }
                    }

                TouchPosition = new Vector2(0, 0);
            } else if (item.GetYDistance() < 35 && item.GetXDistance() > 80) {
                SwapStartPos = item.TouthStartPos;
                SwapEndPos = item.TouthEndPos;

                DebugaddInfo = "X D:" + item.GetXDistance() + " - Y D:" + item.GetYDistance() + " - T:" + item.TouchOnlineTime;

                if (item.TouthStartPos.X > item.TouthEndPos.X) {
                    for (Delegate SomeTouchItem : onLeftSwap) {
                        if (SomeTouchItem != null) {
                            SomeTouchItem.TouchEvent(TouchEventType.LeftSwap, TouchPosition);
                        }
                    }
                } else
                    for (Delegate SomeTouchItem : onRightSwap) {
                        if (SomeTouchItem != null) {
                            SomeTouchItem.TouchEvent(TouchEventType.RightSwap, TouchPosition);
                        }
                    }

                TouchPosition = new Vector2(0, 0);
            }

            TouchMemory.clear();
        }

        for (List<TouchDebugItem> lst : DebugPoints) {
            for (TouchDebugItem item : lst) {
                item.Update(timeDelta);
            }
        }
    }

    public void Draw(SpriteAdapter spriteBatch) {
        Draw(spriteBatch, Vector2.Zero);
    }

    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset) {
        /*foreach (var lst in DebugPoints)
            for (int i = 0; i < lst.Count; i++)
            {
                if (i >= 1)
                    spriteBatch.DrawLine(lst[i].TouchPosition.add(1, 1), lst[i - 1].TouchPosition.add(1, 1), Color.Orange, lst[i].ItemTimeLifeLeft / lst[i].ItemTimeLife, 2, 99);
                lst[i].Draw(spriteBatch);
            }*/

        //spriteBatch.FillRectangle(TouchPosition, new Vector2(6), Color.Black, 1, 0, 92);
        //spriteBatch.DrawLine(TouchPosition.add(4, 2), Color.Black, 1, 12, 0.785f, 4, 92);
    }
}
