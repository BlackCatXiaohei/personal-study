package org.personal.blackcat.common.annotation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * @author lifan.li
 * @since 2018/8/9
 **/
public class AnnotatedElementUtils {

    public static <A extends Annotation> A findAnnotation(AnnotatedElement element, Class<A> annotationType) {
        return element.getAnnotation(annotationType);
    }
}
