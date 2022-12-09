package com.mygdx.Pong.Engine.Math;

public class MathUtils {
    public static float toPercentage(float wholeNumber, float numberToConvert) {
        return (numberToConvert / wholeNumber) * 100;
    }

    public static float toValue(float percentage, float wholeNumber) {
        return (percentage / 100) * wholeNumber;
    }
}