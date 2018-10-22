package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Delegate;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.IGameState;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.game.MainGameClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class EngineService {
    public static EngineService I = new EngineService();
    private static int ControllIDLenght = 10;

    private static Random Rand = new Random();
    public BlankScreen BlankScrenObj;

    public Boolean OnWinPressed;
    public Map<String, IGameState> Pages;
    public List<String> NavigationPath;
    public String CurrentPageName;

    protected EngineService() {
        OnWinPressed = false;
        BlankScrenObj = new BlankScreen();
        Pages = new TreeMap<String, IGameState>();
        NavigationPath = new ArrayList<String>();
    }

    public static String sPagename;
    public static Object sExtendData;

    public void NavigateBack() {
        Boolean Remove = false;

        sPagename = NavigationPath.get(NavigationPath.size() - 2);

        if (!BlankScrenObj.floatController.Finished) {
            return;
        }
        else {
            Remove = true;
        }

        if (Remove) {
            NavigationPath.remove(NavigationPath.size() - 1);
        }

        BlankScrenObj.Start(3);

        BlankScrenObj.ShowedEvent = new Delegate() {
            public void VoidDelegate() {
                if (Pages.containsKey(sPagename)) {
                    if (Pages.containsKey(CurrentPageName)) {
                        Pages.get(CurrentPageName).OnNavigatetFrom("");
                        Pages.get(CurrentPageName).Dispose();
                        Pages.get(sPagename).OnNavigatetTo("");
                        CurrentPageName = sPagename;
                    } else {
                        Pages.get(sPagename).OnNavigatetTo("");
                        CurrentPageName = sPagename;
                    }
                }
            }
        };
    }

    public Boolean NavigateTo(String Pagename, Object extendData, Boolean FastTravel) {
        sExtendData = extendData;
        sPagename = Pagename;

        if (!BlankScrenObj.floatController.Finished && !FastTravel) {
            return false;
        }

        NavigationPath.add(Pagename);

        BlankScrenObj.Start(3);

        BlankScrenObj.ShowedEvent = new Delegate() {
            public void VoidDelegate() {
                if (Pages.containsKey(sPagename)) {
                    if (Pages.containsKey(CurrentPageName)) {
                        Pages.get(CurrentPageName).OnNavigatetFrom("");
                        Pages.get(CurrentPageName).Dispose();
                        Pages.get(sPagename).OnNavigatetTo(sExtendData);
                        CurrentPageName = sPagename;
                    } else {
                        Pages.get(sPagename).OnNavigatetTo(sExtendData);
                        CurrentPageName = sPagename;
                    }
                }
            }
        };

        if (FastTravel) {
            CurrentPageName = Pagename;
            BlankScrenObj.SetAsFinal();
            BlankScrenObj.Stop();
        }

        return true;
    }

    public Boolean Update(float TimeDelta) {
        return BlankScrenObj.Update(TimeDelta);
    }

    public void Draw(SpriteAdapter spriteBatch) {
        BlankScrenObj.Draw(spriteBatch);
    }

    public float ToRadians(float degree) {
        return degree * (float) Math.PI / 180;
    }

    public float ToDegree(float degree) {
        return degree * 180 / (float) Math.PI;
    }

    //return from 0 to Pi
    public float GetAngle(Vector2 vec1, Vector2 vec2) {
        vec1.fNormalize();
        vec2.fNormalize();
        float perpDot = vec1.X * vec2.Y - vec1.Y * vec2.X;
        return (float) Math.atan2(perpDot, Vector2.Dot(vec1, vec2));
    }

    //return from -Pi to Pi
    public float GetAbsAngle(Vector2 vec1, Vector2 vec2) {
        vec1.fNormalize();
        vec2.fNormalize();
        float perpDot = vec1.X * vec2.Y - vec1.Y * vec2.X;
        return Math.abs((float) Math.atan2(perpDot, Vector2.Dot(vec1, vec2)));
    }

    //return from 0 to 2Pi, where angle of Up vector (0, -1) is 0
    public float GetWorldAngle(Vector2 vec) {
        Vector2 vec1 = Vector2.Normalize(vec);
        Vector2 vec2 = new Vector2(0, -1);
        float perpDot = vec1.X * vec2.Y - vec1.Y * vec2.X;
        float ang = (float) Math.atan2(perpDot, Vector2.Dot(vec1, vec2));

        return ang > 0 && ang <= Math.PI ? ang : (float) Math.PI * 2 + ang;
    }

    public Vector2 GetRotatedVectorR(float angle) {
        Vector2 vec = new Vector2(1, 0);

        Vector2 result = new Vector2();
        result.X = vec.X * (float) Math.cos(angle) - vec.Y * (float) Math.sin(angle);
        result.Y = vec.X * (float) Math.sin(angle) + vec.Y * (float) Math.cos(angle);
        return result.mul(-1);
    }

    public Vector2 GetRotatedVectorU(float angle) {
        Vector2 vec = new Vector2(0, -1);

        Vector2 result = new Vector2();
        result.X = vec.X * (float) Math.cos(angle) - vec.Y * (float) Math.sin(angle);
        result.Y = vec.X * (float) Math.sin(angle) + vec.Y * (float) Math.cos(angle);
        return result.mul(-1);
    }

    public Vector2 GetRotatedVectorZ(float angle) {
        Vector2 vec = new Vector2(0, -1);

        Vector2 result = new Vector2();
        result.X = vec.X * (float) Math.cos(angle) - vec.Y * (float) Math.sin(angle);
        result.Y = 0;
        return result;
    }

    public Vector2 GetCenterPivot(SpriteAdapter SpriteBatch, String TextureName) {
        Texture temp = SpriteBatch.getTexture(TextureName);

        if (temp != null) {
            int Wi = temp.getWidth();
            int Hi = temp.getHeight();

            return new Vector2(Wi, Hi).div(2);
        }

        return new Vector2(0.5f, 0.5f);
    }

    //Only Windows Phone
    public String getDeviceId() {
        String phoneID = MainGameClass.SelftPointer.GetDeviceID.StringDelegate();

        return phoneID;
    }

    public Boolean isInRect(Vector2 position, Vector2 itemPosition, Vector2 itemSize) {
        Boolean inX = (position.X >= itemPosition.X && position.X <= itemPosition.X + itemSize.X);
        Boolean inY = (position.Y >= itemPosition.Y && position.Y <= itemPosition.Y + itemSize.Y);

        if (inX && inY) {
            return true;
        }

        return false;
    }

    public Boolean isInCircle(Vector2 position, Vector2 itemCenter, float radius) {
        return isInTube(position, itemCenter, 0, radius);
    }

    public Boolean isInTube(Vector2 position, Vector2 itemCenter, float innerRadius, float outerRadius) {
        return Vector2.Distance(position, itemCenter) > innerRadius && Vector2.Distance(position, itemCenter) < outerRadius;
    }

    public String GetRandomControllID() {
        String result = "";

        for (int i = 0; i < ControllIDLenght; i++) {
            result += Rand.nextInt(10);
        }

        return result;
    }

    public void WinPress() {
        OnWinPressed = true;
    }

    public void WinReleas() {
        OnWinPressed = false;
    }

    public List<Vector2> GetAllIntersectionPointsBetweenCircles(Vector2 CircleCenter1, float Radius1, Vector2 CircleCenter2, float Radius2) {
        List<Vector2> Points = new LinkedList<Vector2>();
        float distance = Vector2.Distance(CircleCenter1, CircleCenter2);

        if (distance > Radius1 + Radius2 || distance < Math.abs(Radius1 - Radius2)) {
            return Points;
        }

        Vector2 NormalFromC1ToC2 = Vector2.Normalize(CircleCenter2.sub(CircleCenter1));

        if (distance == Radius1 + Radius2 || distance == Math.abs(Radius1 - Radius2)) {
            Points.add(CircleCenter1.add(NormalFromC1ToC2.mul(Radius1)));
            return Points;
        }

        float rDelta = Math.abs(Radius2 + Radius1 - distance);
        float a = (float) (Math.pow(Radius1, 2) - Math.pow(Radius2, 2) + Math.pow(distance, 2)) / (2 * distance);
        float h = (float) Math.sqrt(Math.abs(a * a - Radius1 * Radius1));

        Vector2 Center = CircleCenter1.add(NormalFromC1ToC2.mul(a));
        Vector2 sideOffset = new Vector2(-NormalFromC1ToC2.Y, NormalFromC1ToC2.X).mul(h);

        Points.add(Center.add(sideOffset));
        Points.add(Center.sub(sideOffset));

        return Points;
    }

    public List<Vector2> GenerateBazierIntermediate(List<Vector2> origPoints, float smoothPower) {
        List<Vector2> points = new LinkedList<Vector2>();

        if (origPoints.size() < 3) {
            points.addAll(origPoints);
            return points;
        }

        for (int i = 0; (i + 2) < origPoints.size(); i++) {
            Vector2 p0 = origPoints.get(i);
            Vector2 p1 = origPoints.get(i + 1);
            Vector2 p2 = origPoints.get(i + 2);

            float d1 = Vector2.Distance(p0, p1);
            float d2 = Vector2.Distance(p1, p2);

            int dir = GetAngle(p0.sub(p1), p2.sub(p1)) > 0 ? -1 : 1;

            float ang = GetAbsAngle(p0.sub(p1), p2.sub(p1));
            float YAng = (float) (Math.PI - ang) / 2;
            float wang1 = -GetWorldAngle(p2.sub(p1)) - (float) Math.PI;
            float wang2 = GetWorldAngle(p0.sub(p1)) - ((float) Math.PI / 2) + wang1;

            Vector2 GoalVector1 = GetRotatedVectorR(wang1 + YAng * dir - wang2);
            Vector2 GoalVector2 = GetRotatedVectorR(wang1 - YAng * dir - wang2);

            if (points.size() == 0) {
                points.add(p0);
                points.add(p0.add(GoalVector2.mul((d1) / smoothPower)));
            }

            points.add(p1.add(GoalVector1.mul((-d1) / smoothPower)));
            points.add(p1);
            points.add(p1.add(GoalVector1.mul((d2) / smoothPower)));

            if ((i + 3) >= origPoints.size()) {
                Vector2 GoalVector3 = GoalVector1.sub(Vector2.Normalize(p0.sub(p1)).mul(2.0f * Vector2.Dot(Vector2.Normalize(p2.sub(p1)), GoalVector1)));

                points.add(p2.add(GoalVector3.mul((d2) / smoothPower)));
                points.add(p2);
            }
        }

        return points;
    }

    public List<Vector2> GenerateBazierCurve(List<Vector2> point, int stepPerSegment) {
        List<Vector2> points = new LinkedList<Vector2>();

        for (int i = 0; (i + 3) < point.size(); i += 3) {
            points.addAll(GetBazierCurve(point.get(i), point.get(i + 1), point.get(i + 2), point.get(i + 3), stepPerSegment));
        }

        return points;
    }

    public List<Vector2> GetBazierCurve(Vector2 p0, Vector2 p1, Vector2 p2, Vector2 p3, int steps) {
        List<Vector2> points = new LinkedList<Vector2>();

        for (int i = 0; i <= steps; i++) {
            float t = i / (float) steps;
            points.add(CalculateBezierPoint(t, p0, p1, p2, p3));
        }

        return points;
    }

    public List<Vector2> GetBazierCurve(Vector2 p0, Vector2 p1, Vector2 p2, int steps) {
        List<Vector2> points = new LinkedList<Vector2>();

        for (int i = 0; i <= steps; i++) {
            float t = i / (float) steps;
            points.add(CalculateBezierPoint(t, p0, p1, p2));
        }

        return points;
    }

    public Vector2 CalculateBezierPoint(float t, Vector2 p0, Vector2 p1, Vector2 p2) {
        float u = 1 - t;

        Vector2 p = p0.mul(u * u);         //first term
        p = p.add(p1.mul(2 * u * u * t));  //second term
        p = p.add(p2.mul(t * t));          //third term

        return p;
    }

    public Vector2 CalculateBezierPoint(float t, Vector2 p0, Vector2 p1, Vector2 p2, Vector2 p3) {
        float u = 1 - t;

        Vector2 p = p0.mul(u * u * u);               //first term
        p = p.add(p1.mul((float) (3 * u * u * t)));  //second term
        p = p.add(p2.mul((float) (3 * u * t * t)));  //third term
        p = p.add(p3.mul((float) (t * t * t)));      //fourth term

        return p;
    }
}
