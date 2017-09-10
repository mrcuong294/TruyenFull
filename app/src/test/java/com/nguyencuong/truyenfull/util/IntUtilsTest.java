package com.nguyencuong.truyenfull.util;

import org.junit.Test;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 9/10/2017.
 * Email: vancuong2941989@gmail.com
 */
public class IntUtilsTest {

    @Test
    public void convertStringToInt() throws Exception {
        String[] ss = {"asdas 123 .43dgfd, - + ;", "asdas asd", "12312", "012de"};
        for (String s : ss) {
            int i = IntUtils.convertStringToInt(s);
            System.out.println(i);
        }
    }

}