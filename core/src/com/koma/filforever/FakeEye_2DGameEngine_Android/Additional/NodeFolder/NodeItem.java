package com.koma.filforever.FakeEye_2DGameEngine_Android.Additional.NodeFolder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.koma.filforever.FakeEye_2DGameEngine_Android.Engine.Vector2;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.DelayController;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.EngineService;
import com.koma.filforever.FakeEye_2DGameEngine_Android.Service.SpriteAdapter;

public class NodeItem {
    public static float FreeFallSpeedIncreas = 9.78f / 3;

    public NodeStatus Status;

    public Vector2 ThirdPartyEffectVector;
    public float ThirdPartyEffectPower;
    public float Mass;

    //вектор гравитации
    public Boolean Gravity;
    public Vector2 GravityVector;
    public float GravitySpeed;

    public Vector2 Position;
    public Vector2 Size;
    public Vector2 Scale;
    public float Opacity;
    public float Rotation;

    public Map<String, NodeItem> Childrens;

    public List<FolowNode> PathToFolow;

    public FloatFolowNode opacityGoal;
    public FloatFolowNode rotationGoal;
    public FolowNode NextStep;
    public FolowNode scaleGoal;

    public DelayController OpacityDelay;
    public DelayController RotationDelay;
    public DelayController ScaleDelay;
    public DelayController MoveDelay;
    public DelayController GravityDelay;

    public NodeItem(Vector2 position, Vector2 size, Vector2 scale, float rotation, float opacity) {
        GravityVector = new Vector2(0, 1);
        Gravity = false;

        OpacityDelay = new DelayController();
        RotationDelay = new DelayController();
        ScaleDelay = new DelayController();
        MoveDelay = new DelayController();
        GravityDelay = new DelayController();

        OpacityDelay.Value = 0;
        RotationDelay.Value = 0;
        ScaleDelay.Value = 0;
        MoveDelay.Value = 0;
        GravityDelay.Value = 0;

        Mass = 1;
        ThirdPartyEffectPower = 0;
        ThirdPartyEffectVector = new Vector2(1, 0);

        Position = position;
        Size = size;
        Scale = scale;

        Opacity = opacity;
        Rotation = rotation;

        Childrens = new TreeMap<String, NodeItem>();
    }

    public NodeItem() {
        this(new Vector2(0), new Vector2(0), new Vector2(0), 0, 0);
    }

    public void AddChild(String Key, NodeItem item) {
        Childrens.put(Key, item);
    }

    public Boolean GetGravityState() {
        return Gravity;
    }

    public void DisableGravity() {
        Gravity = false;
        GravitySpeed = 0;
    }

    public void EnableGravity(Vector2 grVector) {
        GravityDelay.Value = 0;

        GravitySpeed = FreeFallSpeedIncreas;
        GravityVector = grVector;
        Gravity = true;
    }

    public void EnableGravity() {
        EnableGravity(new Vector2(0, 1));
    }

    public void EnableGravity(float delay) {
        EnableGravity(new Vector2(0, 1));
        SetGravityDelay(delay);
    }

    public void EmergencyStopAllPhythic() {
        GravitySpeed = 0;
        ThirdPartyEffectPower = 0;
    }

    public void EmergencyStopAllEffects() {
        PathToFolow = null;
        scaleGoal = null;
        rotationGoal = null;
        opacityGoal = null;
    }

    public void RemoveAllDelay() {
        GravityDelay.Value = 0;

        MoveDelay.Value = 0;
        OpacityDelay.Value = 0;
        RotationDelay.Value = 0;
        ScaleDelay.Value = 0;
    }

    public void FolowPath(List<FolowNode> path) {
        if (Gravity) {
            return;
        }

        MoveDelay.Value = 0;

        PathToFolow = path;
        NextStep = PathToFolow.get(0);
        PathToFolow.remove(0);

        if (Vector2.Distance(Position, NextStep.Value) > 0) {
            NextStep.MoveDirection = Vector2.Normalize(NextStep.Value.sub(Position));
            NextStep.LeftDistance = NextStep.Value.sub(Position).getLen();
        } else {
            NextStep.LeftDistance = 0;
        }

        Status = NodeStatus.Acting;
    }

    public void FolowPath(List<Vector2> vpath, float speed) {
        if (Gravity) {
            return;
        }

        List<FolowNode> path = new LinkedList<FolowNode>();

        for (Vector2 item : vpath) {
            path.add(new FolowNode(item, speed));
        }

        FolowPath(path);
    }

    public void FolowCurve(List<Vector2> points, float speed, int samplingPower) {
        if (Gravity) {
            return;
        }

        List<Vector2> curve = EngineService.I.GenerateBazierCurve(EngineService.I.GenerateBazierIntermediate(points, 3), samplingPower);
        List<FolowNode> path = new LinkedList<FolowNode>();

        for (Vector2 item : curve) {
            path.add(new FolowNode(item, speed));
        }

        FolowPath(path);
    }

    public void BreakFolowPath() {
        MoveDelay.Value = 0;

        NextStep = null;
        PathToFolow = null;
        Status = NodeStatus.Stable;
    }

    public void SetGravityDelay(float value) {
        GravityDelay.Start(value);
    }

    public void SetOpacityDelay(float value) {
        OpacityDelay.Start(value);
    }

    public void SetRotationDelay(float value) {
        RotationDelay.Start(value);
    }

    public void SetScaleDelay(float value) {
        ScaleDelay.Start(value);
    }

    public void SetMoveDelay(float value) {
        MoveDelay.Start(value);
    }

    public void SetAlpha(float opacity) {
        Opacity = opacity;
        opacityGoal = null;
    }

    public void TranslateAlpha(float opacity, float speed) {
        opacityGoal = new FloatFolowNode(opacity, speed);
        Status = NodeStatus.Acting;
    }

    public void TranslateAlpha(float opacity, float speed, float delay) {
        TranslateAlpha(opacity, speed);
        SetOpacityDelay(delay);
    }

    public void SetScale(Vector2 scale) {
        Scale = scale;
        scaleGoal = null;
    }

    public void TranslateScale(Vector2 scale, float speed) {
        if (Scale == scale) {
            return;
        }

        scaleGoal = new FolowNode(scale, speed);
        scaleGoal.MoveDirection = Vector2.Normalize(scaleGoal.Value.sub(Scale));
        scaleGoal.LeftDistance = scaleGoal.Value.sub(Scale).getLen();
        Status = NodeStatus.Acting;
    }

    public void TranslateScale(Vector2 scale, float speed, float delay) {
        if (Scale == scale) {
            return;
        }

        TranslateScale(scale, speed);
        SetScaleDelay(delay);
    }

    public void SetPosition(Vector2 position) {
        Position = position;
        PathToFolow = null;

        if (Gravity) {
            GravitySpeed = FreeFallSpeedIncreas;
        }
    }

    public void TranslatePosition(Vector2 position, float speed) {
        if (Position == position) {
            return;
        }

        List<FolowNode> temp = new LinkedList<FolowNode>();
        temp.add(new FolowNode(position, speed));

        FolowPath(temp);
    }

    public void TranslatePosition(Vector2 position, float speed, float delay) {
        if (Position == position) {
            return;
        }

        TranslatePosition(position, speed);
        SetMoveDelay(delay);
    }

    public void SetRotation(float rotation) {
        Rotation = rotation;
        rotationGoal = null;
    }

    public void TranslateRotation(float rotation, float speed) {
        rotationGoal = new FloatFolowNode(rotation, speed);
        Status = NodeStatus.Acting;
    }

    public void TranslateRotation(float rotation, float speed, float delay) {
        TranslateRotation(rotation, speed);
        SetRotationDelay(delay);
    }

    public Boolean Update(float timeDelta) {
        Boolean noacting = true;

        if (PathToFolow != null && MoveDelay.Value == 0 && NextStep.MoveDirection != null) {
            Vector2 Offset = NextStep.MoveDirection.mul(NextStep.Speed * timeDelta);
            Position = Position.add(Offset);
            NextStep.LeftDistance -= Offset.getLen();

            if (NextStep.LeftDistance <= 0) {
                Position = NextStep.Value;
                if (PathToFolow.size() > 0) {
                    NextStep = PathToFolow.get(0);
                    PathToFolow.remove(0);
                    if (Vector2.Distance(Position, NextStep.Value) > 0) {
                        NextStep.MoveDirection = Vector2.Normalize(NextStep.Value.sub(Position));
                        NextStep.LeftDistance = NextStep.Value.sub(Position).getLen();
                    } else {
                        NextStep.LeftDistance = 0;
                    }
                } else {
                    PathToFolow = null;
                }
            }

            noacting = false;
        }

        if (scaleGoal != null && ScaleDelay.Value == 0) {
            Vector2 Offset = scaleGoal.MoveDirection.mul(scaleGoal.Speed * timeDelta);
            Scale = Scale.add(Offset);
            scaleGoal.LeftDistance -= Offset.getLen();

            if (scaleGoal.LeftDistance <= 0) {
                Scale = scaleGoal.Value;
                scaleGoal = null;
            }

            noacting = false;
        }

        if (opacityGoal != null && OpacityDelay.Value == 0) {
            if (opacityGoal.Value != Opacity) {
                int dir = opacityGoal.Value > Opacity ? 1 : -1;
                float distance = dir * timeDelta * opacityGoal.Speed;
                if (distance < Math.abs(opacityGoal.Value - Opacity)) {
                    Opacity += dir * timeDelta * opacityGoal.Speed;
                }
                else {
                    Opacity = opacityGoal.Value;
                    opacityGoal = null;
                }
            } else {
                opacityGoal = null;
            }

            noacting = false;
        }

        if (rotationGoal != null && RotationDelay.Value == 0) {
            if (rotationGoal.Value != Rotation) {
                int dir = rotationGoal.Value > Rotation ? 1 : -1;
                float distance = dir * timeDelta * rotationGoal.Speed;
                if (distance < Math.abs(rotationGoal.Value - Rotation)) {
                    Rotation += dir * timeDelta * rotationGoal.Speed;
                }
                else {
                    Rotation = rotationGoal.Value;
                    rotationGoal = null;
                }
            } else {
                rotationGoal = null;
            }

            noacting = false;
        }

        if (Gravity && GravityDelay.Value == 0) {
            Position = Position.add(GravityVector.mul(GravitySpeed));
            GravitySpeed += timeDelta * FreeFallSpeedIncreas * Mass;

            noacting = false;
        }

        if (ThirdPartyEffectPower > 0) {
            Position = Position.add(ThirdPartyEffectVector.mul(ThirdPartyEffectPower));
            ThirdPartyEffectPower -= (ThirdPartyEffectPower * 0.75f * timeDelta) * Mass;

            if (ThirdPartyEffectPower <= 0.001f) {
                ThirdPartyEffectPower = 0;
            }

            noacting = false;
        }

        if (noacting) {
            Status = NodeStatus.Stable;
        }

        GravityDelay.Update(timeDelta);
        OpacityDelay.Update(timeDelta);
        RotationDelay.Update(timeDelta);
        ScaleDelay.Update(timeDelta);
        MoveDelay.Update(timeDelta);

        return !noacting;
    }

    public void Draw(SpriteAdapter spriteBatch) {
        Draw(spriteBatch, 50);
    }

    public void Draw(SpriteAdapter spriteBatch, int layer) {

    }
}
