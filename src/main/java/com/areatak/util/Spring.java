package com.areatak.util;

import org.apache.commons.collections4.map.LRUMap;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by Cross on 2/18/2015.
 * This class is only spring context loader.
 * The Spring context can be used by Spring.context, and beans will be loaded by Spring.context.getBean(BEAN NAME).
 * After this initialization @Autowired will work and beans can be autowired.
 */
@Component
public class Spring implements ApplicationContextAware {
    private static LRUMap<String, Object>  lockObjecs = new LRUMap<>(1000);
    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static synchronized Object lock(String key) {
        lockObjecs.putIfAbsent(key, new Object());
        return lockObjecs.get(key);
    }

}
