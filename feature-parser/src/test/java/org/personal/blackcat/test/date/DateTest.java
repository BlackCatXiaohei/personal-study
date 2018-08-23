package org.personal.blackcat.test.date;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author lifan.li
 * @since 2018/8/6
 **/
@Slf4j
public class DateTest {
    @Test
    public void test() {

        log.info("{}", Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3}$", "2018-11-02 12:22:00.0"));
    }
}
