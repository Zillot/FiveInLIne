package com.koma.filforever.FakeEye_2DGameEngine_Android.Engine;

import com.badlogic.gdx.Net;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder.NodeItem;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.TouchEventType;

import java.io.Serializable;

public class Delegate implements Serializable, Net.HttpResponseListener {
    public void VoidDelegate() {
    }

    public boolean BoolDelegate() {
        return true;
    }

    public void StringDelegate(String msg) {
    }

    public String StringDelegate() {
        return "";
    }

    public void TouchEvent(TouchEventType TouchEventName, Vector2 position) {
    }

    public void ControllTouchDelegate(Vector2 ToouchPosition, Object arg) {
    }

    public void IcoDraw(SpriteAdapter spriteBatch, Vector2 position, Vector2 size, int layer) {
    }

    public void ExtendDraw(SpriteAdapter spriteBatch, NodeItem parent, int layer) {
    }

    public Boolean ExtendUpdate(float timeDelta, NodeItem parent) {
        return false;
    }

    public String UserToken;

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
    }

    @Override
    public void failed(Throwable t) {
    }

    @Override
    public void cancelled() {
    }
}
