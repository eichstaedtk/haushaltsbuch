package de.eichstaedt.haushaltsbuch.infrastructure.aop;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 06.01.19.
 */

@Aspect
@Component
public class UseCaseExecutionTimeAspect {

  private static final Logger logger = LoggerFactory.getLogger(UseCaseExecutionTimeAspect.class);

  @Around("de.eichstaedt.haushaltsbuch.infrastructure.aop.PointCutDefinitions.serviceExecution()")
  public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

    LocalDateTime start = LocalDateTime.now();

    Object retVal = pjp.proceed();

    LocalDateTime end = LocalDateTime.now();

    logger.info("Use Case {} within {} ms.",pjp.toLongString(), ChronoUnit.MILLIS.between(start, end));

    return retVal;

  }

}
