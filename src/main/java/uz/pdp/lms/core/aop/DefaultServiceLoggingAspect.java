package uz.pdp.lms.core.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DefaultServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceLoggingAspect.class);

    // Barcha xizmatlar uchun oldin log yozish
    @Before("execution(* uz.pdp.lms.modules.*.service.*.*(..))")
    public void logBeforeMethod() {
        logger.info("Metod chaqirilyapti...");
    }

    // Barcha xizmatlar uchun metoddan keyin log yozish
    @After("execution(* uz.pdp.lms.modules.*.service.*.*(..))")
    public void logAfterMethod() {
        logger.info("Metod bajarildi...");
    }

    // Xatolik yuz bersa log yozish
    @AfterThrowing(pointcut = "execution(* uz.pdp.lms.modules.*.service.*.*(..))", throwing = "ex")
    public void logExceptions(Exception ex) {
        logger.error("Metodda xatolik yuz berdi: {}", ex.getMessage());
    }

    // Metodlarning bajarilish vaqtini hisoblash
    @Around("execution(* uz.pdp.lms.modules.*.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // Metodni chaqirish
        long endTime = System.currentTimeMillis();
        logger.info("{} metodi bajarilish vaqti: {} ms", joinPoint.getSignature(), (endTime - startTime));
        return result;
    }
}

