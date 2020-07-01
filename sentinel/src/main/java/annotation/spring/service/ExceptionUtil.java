package annotation.spring.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class ExceptionUtil {


    public static void handleException(BlockException e) {
        System.out.println("Oops: " + e.getClass().getCanonicalName());
    }
}
