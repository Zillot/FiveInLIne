package com.koma.filforever.FakeEye_2DGameEngine_Android.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IterationService {
    private int intMinValue;
    private int intMaxValue;
    private int intValue;
    private int intStep;

    private double doubleMinValue;
    private double doubleMaxValue;
    private double doubleValue;
    private double doubleStep;

    public List<Integer> Ints;
    public List<Double> Doubles;
    public List<Float> Floats;

    private static Random rand = new Random();
    private Boolean doRandom;

    public IterationService() {
        clearMemory();

        doRandom = false;

        doubleMaxValue = 1;
        doubleMinValue = 1;

        intMaxValue = 1;
        intMinValue = 1;

        intValue = 0;
        intStep = 1;

        doubleValue = 0;
        doubleStep = 1;
    }

    public void clearMemory() {
        Ints = new ArrayList<Integer>();
        Doubles = new ArrayList<Double>();
        Floats = new ArrayList<Float>();
    }

    public void setMinMaxValue(double min, double max) {
        intMinValue = (int) min;
        intMaxValue = (int) max;

        doubleMinValue = min;
        doubleMaxValue = max;
    }

    public void setValue(double value) {
        intValue = (int) value;
        doubleValue = value;
    }

    public void setStep(double step) {
        intStep = (int) step;
        doubleStep = step;
    }

    public int GetIValue() {
        Integer toRetValue = intValue;
        intValue += intStep;

        if (doRandom) {
            toRetValue = rand.nextInt(1000) * intStep;
        }

        if (toRetValue > intMaxValue) {
            while (toRetValue > intMaxValue) {
                toRetValue -= intMaxValue;
            }
        }

        if (toRetValue < intMinValue) {
            while (toRetValue < intMinValue) {
                toRetValue += (intMaxValue - intMinValue);
            }
        }

        Ints.add(toRetValue);
        return toRetValue;
    }

    public double GetDValue() {
        double toRetValue = doubleValue;
        doubleValue += doubleStep;

        if (doRandom) {
            toRetValue = (double) rand.nextInt(1000) * doubleValue;
        }

        if (toRetValue > doubleMaxValue) {
            while (toRetValue > doubleMaxValue) {
                toRetValue -= doubleMaxValue;
            }
        }

        if (toRetValue < doubleMinValue) {
            while (toRetValue < doubleMinValue) {
                toRetValue += (doubleMaxValue - doubleMinValue);
            }
        }

        Doubles.add(toRetValue);
        return toRetValue;
    }

    public float GetFValue() {
        float toRetValue = (float) doubleValue;
        doubleValue += doubleStep;

        if (doRandom) {
            toRetValue = (float) rand.nextInt(1000) * (float) doubleValue;
        }

        if (toRetValue > doubleMaxValue) {
            while (toRetValue > doubleMaxValue) {
                toRetValue -= (float) doubleMaxValue;
            }
        }

        if (toRetValue < doubleMinValue) {
            while (toRetValue < doubleMinValue) {
                toRetValue += (float) (doubleMaxValue - doubleMinValue);
            }
        }

        Floats.add(toRetValue);
        return toRetValue;
    }
}
