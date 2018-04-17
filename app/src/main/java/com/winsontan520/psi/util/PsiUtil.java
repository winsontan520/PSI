package com.winsontan520.psi.util;

import com.google.maps.android.ui.IconGenerator;

/**
 * Created by Winson Tan on 17/4/18.
 */

public class PsiUtil {

    public static int getIconStyleByIndex(int index) {
        if (index <= 50) {
            return IconGenerator.STYLE_GREEN;
        } else if (index <= 100) {
            return IconGenerator.STYLE_BLUE;
        } else if (index <= 200) {
            return IconGenerator.STYLE_PURPLE;
        } else if (index <= 300) {
            return IconGenerator.STYLE_ORANGE;
        } else {
            return IconGenerator.STYLE_RED;
        }
    }
}
