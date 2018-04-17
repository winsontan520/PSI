package com.winsontan520.psi;

import com.google.maps.android.ui.IconGenerator;
import com.winsontan520.psi.util.PsiUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Winson Tan on 17/4/18.
 */

public class PsiUtilTest {

    @Test
    public void test_index_below_50_expect_green(){
        int style = PsiUtil.getIconStyleByIndex(12);
        assertEquals(IconGenerator.STYLE_GREEN, style);
    }

    @Test
    public void test_index_50_expect_green(){
        int style = PsiUtil.getIconStyleByIndex(50);
        assertEquals(IconGenerator.STYLE_GREEN, style);
    }

    // TODO : more test to cover 50 to beyond 300

    @Test
    public void test_index_between_50_100_expect_blue(){
        int style = PsiUtil.getIconStyleByIndex(80);
        assertEquals(IconGenerator.STYLE_BLUE, style);
    }

    @Test
    public void test_index_exact_100_expect_blue(){
        int style = PsiUtil.getIconStyleByIndex(100);
        assertEquals(IconGenerator.STYLE_BLUE, style);
    }

    @Test
    public void test_index_between_100_200_expect_purple(){
        int style = PsiUtil.getIconStyleByIndex(134);
        assertEquals(IconGenerator.STYLE_PURPLE, style);
    }

    @Test
    public void test_index_exact_200_expect_purple(){
        int style = PsiUtil.getIconStyleByIndex(200);
        assertEquals(IconGenerator.STYLE_PURPLE, style);
    }

    @Test
    public void test_index_between_200_300_expect_orange(){
        int style = PsiUtil.getIconStyleByIndex(278);
        assertEquals(IconGenerator.STYLE_ORANGE, style);
    }

    @Test
    public void test_index_exact_300_expect_purple(){
        int style = PsiUtil.getIconStyleByIndex(200);
        assertEquals(IconGenerator.STYLE_PURPLE, style);
    }

    @Test
    public void test_index_more_than_300_expect_red(){
        int style = PsiUtil.getIconStyleByIndex(389);
        assertEquals(IconGenerator.STYLE_RED, style);
    }
}
