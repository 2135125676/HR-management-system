package com.group2.cms.util;

import org.springframework.beans.BeanUtils;

/**
 * @author liuzc
 */
public class BriupBeanUtils {
    public static <T> T copyProperties(Object source,Class<T> targetClass){
        //1.通过反射创建需要的target类型对象
        T target = null;
        try {
            target = targetClass.newInstance();
            //2.赋值source属性值给target对象
            BeanUtils.copyProperties(source,target);
            //返回target对象
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
