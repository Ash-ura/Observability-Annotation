package com.itconsortiumgh.ObservabilityAnnotation.aspect;


import com.itconsortiumgh.ObservabilityAnnotation.annotation.Observability;
import com.itconsortiumgh.ObservabilityAnnotation.config.JsonUtility;
import com.itconsortiumgh.ObservabilityAnnotation.model.ApplicationProperties;
import com.itconsortiumgh.ObservabilityAnnotation.model.Param;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class ObservabilityAspect {

    @Around("@annotation(com.itconsortiumgh.ObservabilityAnnotation.annotation.Observability)")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        var codeSignature = (CodeSignature) point.getSignature();
        var methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());
        var annotation = method.getAnnotation(Observability.class);
        LogLevel level = annotation.value();
        boolean showArgs = annotation.showArgs();
        Object[] methodArgs = point.getArgs();
        String[] methodParams = codeSignature.getParameterNames();
        String message = annotation.message();
        String key = annotation.key();
        String[] trier = ((MethodSignature) point.getSignature()).getParameterNames();
        System.out.println(Arrays.toString(trier));

        log(logger, level, observabilityLog(showArgs, methodParams, methodArgs, message, key));

        return point.proceed();
    }


    @Autowired
    ApplicationProperties applicationProperties;
    String observabilityLog(boolean showArgs, String[] params, Object[] args, String annotationMessage, String annotationKey) {

        List<Param> values = new ArrayList<>();
        String key = null;

        String finalMessage = null;
        if (showArgs && Objects.nonNull(params) && Objects.nonNull(args) && params.length == args.length) {

            Map<String, Object> mapValues = new HashMap<>(params.length);

            try {
                for (int i = 0; i < params.length; i++) {
                    mapValues.put(params[i], args[i]);
                    Method method = args[i].getClass().getDeclaredMethod(annotationKey);
                    key = (String) method.invoke(args[i]);
                    values.add(new Param(params[i], args[i].toString()));
                }


            } catch (Exception exception) {
                log.error("Failed to load Observability Logs. Cause: ", exception);
                return null;
            }

            switch (annotationMessage){

                case "Pre-Approval": annotationMessage = applicationProperties.getPreapproval();
                break;

                case "Debit Request": annotationMessage = applicationProperties.getDebitRequest();
                    break;

                case "Mandate-Creation Callback": annotationMessage = applicationProperties.getMandateCreationCallback();
                    break;

                case "Save to DB": annotationMessage = applicationProperties.getSaveToDB();
                    break;

                case "Update Mandate Request": annotationMessage = applicationProperties.getUpdateMandateRequest();
                    break;

                default: annotationMessage = "No message type specified";
            }


            key = key == null ? "Missing key" : key;
            String attributeList = values.stream().map(attribute -> attribute.getName().concat(":")
                    .concat(attribute.getValue())).collect(Collectors.joining(", "));
            String timestamp = LocalDateTime.now().toString();
            finalMessage = String.format(annotationMessage, key, attributeList, timestamp);
        }

        return "||=======>> Observability Logs: " + JsonUtility.toJson(finalMessage);


    }


    static void log(Logger logger, LogLevel level, String message) {
        switch (level) {
            case DEBUG:
                logger.debug(message);
            break;
            case TRACE:
                logger.trace(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            default: logger.info(message);
        }
    }
}