package org.personal.blackcat.test.doublee;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author lifan.li
 * @since 2018/8/14
 **/
@Slf4j
public class BigDecimalTest {

    @Test
    public void numberFormatTest() {
        String text = "98.99";
        BigDecimal bigDecimal = new BigDecimal(text);
        log.info("{}", bigDecimal);
    }
}
