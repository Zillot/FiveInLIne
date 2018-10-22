package com.koma.filforever.FakeEye_2DGameEngine_Android.Engine;

import java.io.Serializable;

//Описывает вектор, кеп
public final class Vector2 implements Serializable {
    //позиция
    public float X;
    public float Y;

    //константные вектора
    public static Vector2 UP = new Vector2(0, -1);
    public static Vector2 DOWN = new Vector2(0, 1);
    public static Vector2 LEFT = new Vector2(-1, 0);
    public static Vector2 RIGHT = new Vector2(1, 0);
    public static Vector2 ZERO = new Vector2(0, 0);
    public static Vector2 Zero = new Vector2(0, 0);

    //конструктор
    public Vector2(float xy) {
        set(xy, xy);
    }

    //конструктор
    public Vector2(float x, float y) {
        set(x, y);
    }

    //конструктор
    public Vector2() {
        set(0, 0);
    }

    //копирующий конструктор
    public Vector2(Vector2 vec) {
        set(vec);
    }

    //копирует вектор
    public void set(Vector2 temp) {
        X = temp.X;
        Y = temp.Y;
    }

    //задает Х и У
    public void set(float x, float y) {
        X = x;
        Y = y;
    }

    //возвращяет длину(модуль) вектора
    public float getLen() {
        float temp = X * X + Y * Y;
        if (temp > 0) {
            return (float) Math.sqrt(temp);
        } else {
            return 0;
        }
    }

    //нормализирует и применяет к данному вектору
    public void fNormalize() {
        float len = getLen();
        X = X / len;
        Y = Y / len;
    }

    //возвращяет нормализированый вектор (арт, единичный вектор - вектор длина которого равна 1)
    public Vector2 Normilize() {
        Vector2 temp = new Vector2(X, Y);
        temp.fNormalize();

        return temp;
    }

    private void fdiv(float num) {
        X /= num;
        Y /= num;
    }

    //умножение на скаляр с применением к данному вектору
    private void fmul(float num) {
        X *= num;
        Y *= num;
    }

    //добовление вектора с применением к данному вектору
    private void fadd(Vector2 vec) {
        X += vec.X;
        Y += vec.Y;
    }

    //отнимание вектора с применением к данному вектору
    private void fsub(Vector2 vec) {
        X -= vec.X;
        Y -= vec.Y;
    }

    public Vector2 div(float num) {
        Vector2 temp = new Vector2(X, Y);
        temp.fdiv(num);

        return temp;
    }

    //умножение на скаляр без применения к данному вектору
    //результат возращяет
    public Vector2 mul(float num) {
        Vector2 temp = new Vector2(X, Y);
        temp.fmul(num);

        return temp;
    }

    public Vector2 mul(Vector2 vec) {
        return new Vector2(X * vec.X, Y * vec.Y);
    }

    //добовление вектора без применения к данному вектору
    //результат возращяет
    public Vector2 add(Vector2 vec) {
        Vector2 temp = new Vector2(X, Y);
        temp.fadd(vec);

        return temp;
    }

    //добовление скаляра без применения к данному вектору
    //результат возращяет
    public Vector2 add(float C) {
        Vector2 temp = new Vector2(X, Y);
        temp.fadd(new Vector2(C));

        return temp;
    }

    //добовление вектора без применения к данному вектору
    //результат возращяет
    public Vector2 add(float CX, float CY) {
        Vector2 temp = new Vector2(X, Y);
        temp.fadd(new Vector2(CX, CY));

        return temp;
    }

    //отнимание вектора без применения к данному вектору
    //результат возращяет
    public Vector2 sub(Vector2 vec) {
        Vector2 temp = new Vector2(X, Y);
        temp.fsub(vec);

        return temp;
    }

    //отнимание скаляра без применения к данному вектору
    //результат возращяет
    public Vector2 sub(float C) {
        Vector2 temp = new Vector2(X, Y);
        temp.fsub(new Vector2(C));

        return temp;
    }

    //отнимание вектора без применения к данному вектору
    //результат возращяет
    public Vector2 sub(float CX, float CY) {
        Vector2 temp = new Vector2(X, Y);
        temp.fsub(new Vector2(CX, CY));

        return temp;
    }

    //возвращает этот повернутый на 90 градусов по часовой стрелке
    public Vector2 GetRight() {
        return new Vector2(Y, -X);
    }

    //возвращает этот повернутый на 90 градусов против часовой стрелке
    public Vector2 GetLeft() {
        return new Vector2(-Y, X);
    }

    //возвращает нормализированый вектор (арт, единичный вектор - вектор длина которого равна 1) с заданым углом
    public static Vector2 getVectorWithAngle(float angle) {
        Vector2 temp = new Vector2(0, 1);

        float xn = (float) (-Math.sin(angle) * temp.Y + Math.cos(angle) * temp.X);
        float yn = (float) (Math.cos(angle) * temp.Y + Math.sin(angle) * temp.X);

        return new Vector2(xn, yn);
    }

    //определяет угол между векторами, от -360 до 360
    public static float GetAbsAngle(Vector2 vec1, Vector2 vec2) {
        return (float) Math.atan2(vec2.Y - vec1.Y, vec2.X - vec1.X);
    }

    //возвращает вектор с заданым углом
    public static Vector2 getRotatedVectorToAngle(Vector2 temp, float angle) {
        float xn = (float) (-Math.sin(angle) * temp.Y + Math.cos(angle) * temp.X);
        float yn = (float) (Math.cos(angle) * temp.Y + Math.sin(angle) * temp.X);

        return new Vector2(xn, yn);
    }

    //определяет угол между векторами от 0 до 360
    public static float getAngle(Vector2 vec1, Vector2 vec2) {
        float up = (vec1.X * vec2.X) + (vec1.Y * vec2.Y);
        float down = vec1.getLen() * vec2.getLen();
        return toDegree((float) Math.acos(up / down));
    }

    public static float toDegree(float rad) {
        return rad * 180 / (float) Math.PI;
    }

    //Узнает растояние между вектора
    public static float Distance(Vector2 vec1, Vector2 vec2) {
        return vec1.sub(vec2).getLen();
    }

    //Возвращяет угод данного вектора по отношению к Vector.DOWN
    public float getAngle() {
        return (float) Math.acos((X * DOWN.X + Y * DOWN.Y) / (getLen() + DOWN.getLen()));
    }

    public static float Dot(Vector2 vector1, Vector2 vector2) {
        return (vector1.X * vector2.X) + (vector1.Y * vector2.Y);
    }

    //Возврящает левый или правый вектор(смотри выше) в зависимости от того какой из них ниже
    public Vector2 GetDown() {
        Vector2 temp1 = GetLeft();
        Vector2 temp2 = GetRight();
        return temp1.Y < temp2.Y ? temp1 : temp2;
    }

    //Возврящает нормализированый (арт, единичный вектор - вектор длина которого равна 1) вектор но не меняет данный
    public static Vector2 Normalize(Vector2 vec) {
        float x = vec.X / vec.getLen();
        float y = vec.Y / vec.getLen();

        Vector2 temp = new Vector2(x, y);
        return temp;
    }
}
