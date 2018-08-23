package org.personal.blackcat.test.enumm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

/**
 * @author blackcat
 * @since 2018/8/15
 **/
@Slf4j
public class EnumTest {

    @Test
    public void testEquals() {
        MartRepayStatus status = MartRepayStatus.getMartRepayStatus(2);
        switch (status) {
            case SUCCESS:
                log.info("yep");
                break;
            case FAIL:
                log.info("nope");
                break;
        }
    }

    private enum MartRepayStatus {
        INIT(0, "初始状态"),
        PROCESSING(1, "扣款中"),
        SUCCESS(2, "成功"),
        FAIL(3, "失败")
        ;

        MartRepayStatus(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public static MartRepayStatus getMartRepayStatus(Object var) {
            if (var instanceof Integer) {
                return INIT.getCode().equals(var) ? INIT :
                        PROCESSING.getCode().equals(var) ? PROCESSING :
                                SUCCESS.getCode().equals(var) ? SUCCESS :
                                        FAIL.getCode().equals(var) ? FAIL : null;
            }
            if (var instanceof String) {
                return INIT.getMsg().equals(var) ? INIT :
                        PROCESSING.getMsg().equals(var) ? PROCESSING :
                                SUCCESS.getMsg().equals(var) ? SUCCESS :
                                        FAIL.getMsg().equals(var) ? FAIL : null;
            }
            return null;
        }

        private Integer code;
        private String msg;

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
