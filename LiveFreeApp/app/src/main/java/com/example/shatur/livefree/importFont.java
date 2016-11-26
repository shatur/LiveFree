/* This Source Code  is a part of LiveFree project
 * https://github.com/shatur/LiveFreeApp.*/

package com.example.shatur.livefree;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Class to import external fonts;
 */

public class importFont {
    static Typeface roboto_light, roboto_regular, roboto_thin, roboto_medium;

    importFont(Context context) {
        roboto_light = Typeface.createFromAsset(context.getAssets(), "Fonts/Roboto-Light.ttf");
        roboto_regular = Typeface.createFromAsset(context.getAssets(), "Fonts/Roboto-Regular.ttf");
        roboto_thin = Typeface.createFromAsset(context.getAssets(), "Fonts/Roboto-Thin.ttf");
        roboto_medium = Typeface.createFromAsset(context.getAssets(), "Fonts/Roboto-Medium.ttf");
    }
}
