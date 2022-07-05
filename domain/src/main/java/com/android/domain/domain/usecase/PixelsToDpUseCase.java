package com.android.domain.domain.usecase;

public class PixelsToDpUseCase {

    public static int execute(int countPixels, float density) {
        float countDP = countPixels / density;

        return (int) countDP;
    }
}
