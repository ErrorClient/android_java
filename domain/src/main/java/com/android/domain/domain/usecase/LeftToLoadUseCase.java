package com.android.domain.domain.usecase;

public class LeftToLoadUseCase {

    public static int execute(int adapterSize, int screenSize, int countHorizontal) {
        if (adapterSize < screenSize) {
            return screenSize - adapterSize;
        } else {
            double quotientDouble = adapterSize / (double) countHorizontal;
            int quotientInt = adapterSize / countHorizontal;

            double fractionPart = 1 - ( quotientDouble - quotientInt);
            double leftToLoadHorizontal = fractionPart * countHorizontal;

            return (int) ( leftToLoadHorizontal + countHorizontal);
        }
    }
}
