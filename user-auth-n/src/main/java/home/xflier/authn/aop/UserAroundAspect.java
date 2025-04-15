package home.xflier.authn.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAroundAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserAroundAspect.class);

    @Around("execution(* home.xflier.authn.service.UserService.partialSearch(..)) ||" +
            "execution(* home.xflier.authn.service.UserService.findById(..)) ||" +
            "execution(* home.xflier.authn.service.UserService.getUserPrincipal(..)) ||" +
            "execution(* home.xflier.authn.service.UserService.add(..))")
    public Object userAroundDao(ProceedingJoinPoint joinPoint) throws Throwable {
        // before the method, first log a message
        Object[] args = joinPoint.getArgs();
        String argString = "";
        for (Object arg : args) {
            if (argString == "")
                argString += arg;
            else {
                argString += ", " + arg;
            }
        }
        LOGGER.info("calling the method: " + joinPoint.getSignature() + " - " + argString);
        Object result;
        long start = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
            long stop = System.currentTimeMillis();
            LOGGER.info(String.format("method finished - %d ms", (stop - start)));
        } catch (Throwable e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            StackTraceElement origin = stackTrace[0];
            LOGGER.error(e.getClass() + " - " + e.getMessage() + " - originated in:");
            LOGGER.error("Class: " + origin.getClassName());
            LOGGER.error("Method: " + origin.getMethodName());
            LOGGER.error("Line: " + origin.getLineNumber());
            throw (e);
        }

        return result;
    }
}
