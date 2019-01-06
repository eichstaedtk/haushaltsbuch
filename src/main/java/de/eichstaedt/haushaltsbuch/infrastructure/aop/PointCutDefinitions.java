package de.eichstaedt.haushaltsbuch.infrastructure.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by konrad.eichstaedt@gmx.de on 06.01.19.
 */

@Aspect
@Component
public class PointCutDefinitions {

  @Pointcut("within(de.eichstaedt.haushaltsbuch.domain.services.*)")
  public void serviceExecution() {}

}
