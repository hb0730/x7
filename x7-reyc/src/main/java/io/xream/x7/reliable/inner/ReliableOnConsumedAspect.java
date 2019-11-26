/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.xream.x7.reliable.inner;

import io.xream.x7.reliable.ReliableOnConsumed;
import io.xream.x7.reliable.api.ReliableBackend;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import x7.core.util.ExceptionUtil;
import x7.core.util.StringUtil;
import x7.core.util.VerifyUtil;


@Aspect
public class ReliableOnConsumedAspect {

    private final static Logger logger = LoggerFactory.getLogger(ReliableBackend.class);


    public ReliableOnConsumedAspect() {
        logger.info("Reliable OnConsumed Enabled");
    }

    @Autowired
    private ReliableBackend backend;

    @Pointcut("@annotation(io.xream.x7.reliable.ReliableOnConsumed))")
    public void cut() {

    }

    @Around("cut() && @annotation(reliableOnConsumed) ")
    public void around(ProceedingJoinPoint proceedingJoinPoint, ReliableOnConsumed reliableOnConsumed) {

        Object[] args = proceedingJoinPoint.getArgs();
        Object message = args[0];

        String svc = reliableOnConsumed.svc();

        if (StringUtil.isNullOrEmpty(svc)){
            Signature signature = proceedingJoinPoint.getSignature();
            String name = signature.getDeclaringTypeName() + signature.getName();
            svc = VerifyUtil.toMD5(name).substring(0,10);
        }

        this.backend.onConsumed(svc, message,
                () -> {
                    try {
                        proceedingJoinPoint.proceed();
                    } catch (Throwable e) {
                        throw new RuntimeException(ExceptionUtil.getMessage(e));
                    }
                }
        );

    }

}
