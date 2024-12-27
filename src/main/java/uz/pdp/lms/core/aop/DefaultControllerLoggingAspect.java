package uz.pdp.lms.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DefaultControllerLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(DefaultControllerLoggingAspect.class);

    // Barcha Controller metodlari uchun
    @Around("execution(* uz.pdp.lms.modules.*.controller.*.*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        logger.info("Controllerga so‘rov keldi: {}.{}()", className, methodName);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // Asosiy metodni chaqirish
        long endTime = System.currentTimeMillis();

        logger.info("{}.{}() bajarildi, vaqt: {} ms", className, methodName, (endTime - startTime));
        return result;
    }

    // Xatolik yuz berganda log yozish
    @AfterThrowing(pointcut = "execution(* uz.pdp.lms.modules.*.controller.*.*(..))", throwing = "ex")
    public void logControllerException(Exception ex) {
        logger.error("Controllerda xatolik yuz berdi: {}", ex.getMessage());
    }
}
