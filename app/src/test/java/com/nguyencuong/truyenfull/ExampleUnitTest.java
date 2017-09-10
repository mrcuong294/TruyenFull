package com.nguyencuong.truyenfull;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String str = "Full -  chương";
        str = str.replaceAll("[^0-9,\\.]", "");
        int i = Integer.parseInt(str);
        System.out.println(str);
        System.out.println(i);
    }
}