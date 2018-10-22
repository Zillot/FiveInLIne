package com.koma.filforever.FakeEye_2DGameEngine_Android.DebugTools;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Color;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TextAlign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DebugService {
    public static DebugService I = new DebugService();
    public static float MSGLifeTime = 3;

    private Boolean clientEnable;

    private Boolean DebagMode;
    private Boolean NeedToUpdate;

    private List<String> LogMemory;
    private List<DebugMSG> FloatListLeft;
    private List<DebugMSG> FloatListRight;
    private TreeMap<String, DebugMSG> StaticList;

    public DebugService() {
        NeedToUpdate = false;
        DebagMode = false;
        LogMemory = new ArrayList<String>();

        FloatListLeft = new ArrayList<DebugMSG>();
        FloatListRight = new ArrayList<DebugMSG>();
        StaticList = new TreeMap<String, DebugMSG>();

        clientEnable = false;
    }

    public void ConnectToDebugToolServer() {
        //var Tread = new Thread(ConnectToServer);
        //Tread.Start();
    }

    public void SendToDebugTool(String data) {
        //var Tread = new Thread(delegate() { Send(data); });
        //Tread.Start();
    }

    private void ConnectToServer() {
        //while (true)
        //{
        //    try
        //    {
        //        ipaddress = new IPaddress(new Byte[] { 127, 0, 0, 1 });
        //        addr = new IPEndPoint(ipaddress, 5300);
        //        socket.Connect(addr);
        //        clientEnable = true;
        //        break;
        //    }
        //    catch(Exception)
        //    {
        //        Thread.Sleep(1000);
        //    }
        //}
    }

    private void Send(String data) {
        //try
        //{
        //    if (clientEnable)
        //    {
        //        byte[] msg = Encoding.UTF8.GetBytes(data);
        //        socket.Send(msg);
        //    }
        //}
        //catch (Exception)
        //{
        //    socket.Close();
        //    clientEnable = false;
        //    ConnectToDebugToolServer();
        //}
    }

    public void DisableDebug() {
        if (!DebagMode) {
            return;
        }

        DebagMode = false;
    }

    public void EnableDebug() {
        if (DebagMode) {
            return;
        }

        DebagMode = true;
    }

    public void addToLog(String Text) {
        LogMemory.add(Text);

        if (clientEnable) {
            Send("Log(&)" + Text + "(;)");
        }
    }

    public void addFloatLeft(String Text, Color Color) {
        FloatListLeft.add(0, new DebugMSG(Vector2.Zero, Text, Color, 1));

        if (clientEnable) {
            Send("FloatLeft(&)" + Text + "(%)" + "lol" + "(;)");
        }
    }

    public void addFloatRight(String Text, Color Color) {
        FloatListRight.add(0, new DebugMSG(Vector2.Zero, Text, Color, 1));

        if (clientEnable) {
            Send("FloatRight(&)" + Text + "(%)" + "lol" + "(;)");
        }

        NeedToUpdate = true;
    }

    public void addOrUpdateStatic(String Key, String Text, Vector2 Position, Color Color, float FontSize) {
        if (StaticList.containsKey(Key)) {
            StaticList.remove(Key);
        }

        StaticList.put(Key, new DebugMSG(Position, Text, Color, FontSize));

        if (clientEnable) {
            Send("Static(&)" + Key + "(%)" + Text + "(%)" + Position.X + "(@)" + Position.Y + "(%)" + "lol" + "(%)" + FontSize + "(;)");
        }
    }

    public Boolean Update(float TimeDelta) {
        Boolean toRet = false;

        for (DebugMSG item : FloatListLeft) {
            toRet = toRet | item.Update(TimeDelta);
        }

        for (DebugMSG item : FloatListRight) {
            toRet = toRet | item.Update(TimeDelta);
        }

        return (toRet | NeedToUpdate);
    }

    public void Draw(SpriteAdapter spriteBatch) {
        Draw(spriteBatch, Vector2.Zero);
    }

    public void Draw(SpriteAdapter spriteBatch, Vector2 Offset) {
        for (int i = 0; i < FloatListLeft.size(); i++) {
            if (FloatListLeft.get(i).LifeTime > 0) {
                spriteBatch.DrawText(FloatListLeft.get(i).text, new Vector2(0, spriteBatch.GetWindowSize().Y).sub(new Vector2(0, 40).mul(i).add(new Vector2(-10, 20))),
                        FloatListLeft.get(i).color, (FloatListLeft.get(i).LifeTime / MSGLifeTime), "", 1, TextAlign.LeftXCenterY, 0, 96);
            } else {
                break;
            }
        }

        for (int i = 0; i < FloatListRight.size(); i++) {
            if (FloatListRight.get(i).LifeTime > 0) {
                spriteBatch.DrawText(FloatListRight.get(i).text, spriteBatch.GetWindowSize().sub(new Vector2(0, 40).mul(i).add(new Vector2(10, 20))),
                        FloatListRight.get(i).color, (FloatListRight.get(i).LifeTime / MSGLifeTime), "", 1, TextAlign.RightXCenterY, 0, 96);
            } else {
                break;
            }
        }

        for (Map.Entry<String, DebugMSG> item : StaticList.entrySet()) {
            spriteBatch.DrawText(item.getValue().text, item.getValue().position, item.getValue().color, 1, "", item.getValue().fontSize, TextAlign.CenterXY, 0, 96);
        }
    }
}
