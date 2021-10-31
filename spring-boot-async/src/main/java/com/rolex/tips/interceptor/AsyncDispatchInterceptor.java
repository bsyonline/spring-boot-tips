package com.rolex.tips.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolex.tips.annotation.AsyncTransfer;
import com.rolex.tips.event.AsyncEvent;
import com.rolex.tips.model.Job;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Slf4j
@Component
@Scope("prototype")
public class AsyncDispatchInterceptor implements HandlerInterceptor {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("{}", requestURI);

        //获取请求参数
        String queryString = request.getQueryString();
        log.info("请求参数:{}", queryString);

        //获取请求body
        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        String body = new String(bodyBytes, request.getCharacterEncoding());

        log.info("请求体：{}", body);

        ObjectMapper mapper = new ObjectMapper();
        Job job = mapper.readValue(body, Job.class);
        boolean flag = true;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String methodName = handlerMethod.getMethod().getName();
            log.info("methodName={}", methodName);
            AsyncTransfer annotation = handlerMethod.getMethodAnnotation(AsyncTransfer.class);
            if (annotation != null) {
                String asyncMethod = annotation.asyncMethod();
                String condition = annotation.condition();
                log.info("asyncMethod={}, condition={}", asyncMethod, condition);
                ExpressionParser parser = new SpelExpressionParser();
                Expression expression = parser.parseExpression(condition);
                EvaluationContext context = new StandardEvaluationContext();
                Field[] declaredFields = Job.class.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    Object value = PropertyUtils.getProperty(job, declaredField.getName());
                    context.setVariable(declaredField.getName(), value);
                }
                String key = expression.getValue(context).toString();
                log.info("key={}", key);
                if(key!=null && new Boolean(key)){
                    flag = false;
                    applicationContext.publishEvent(new AsyncEvent("args"));
                }
            }
        }
        log.info("=========");
        return flag;
    }
}
