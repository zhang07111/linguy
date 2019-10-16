package cn.linguy.controller;


import cn.linguy.domain.SysLog;
import cn.linguy.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;

    private Date visitTime;//开始的时间
    private Class clazz;//访问的类
    private Method method;//访问的方法

    //前置通知
    @Before("execution(* cn.linguy.controller.*.*(..))")
    public void doBefore(JoinPoint jp) {
        visitTime = new Date();//开始时间就是开始访问的时间
        clazz = jp.getTarget().getClass();//具体 访问的类
        String methodName = jp.getSignature().getName();//获取方法的名字
        Object[] args = jp.getArgs();//获取方法的参数

        //获取具体执行的方法的Method对象
        if (args == null || args.length == 0) {
            try {
                method = clazz.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            try {
                method = clazz.getMethod(methodName, classArgs);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

    }

    //后置通知
    @After("execution(* cn.linguy.controller.*.*(..))")
    public void doAfter(JoinPoint jp) {
        //获取访问的时长
        Long time = new Date().getTime() - visitTime.getTime();

        String url = "";
        //获取url
        if (clazz != null && method != null && clazz != LogAop.class) {
            //获取类上的RequestMapping
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classValue = classAnnotation.value();

                //获取方法上的RequestMapping
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValue = methodAnnotation.value();

                    url = classValue[0] + methodValue[0];
                }
            }
            // 获取访问的ip
            String ip = request.getRemoteAddr();

            //获取当前操作用户
            SecurityContext context = SecurityContextHolder.getContext();
            User user = (User) context.getAuthentication().getPrincipal();
            String username = user.getUsername();

            //封装数据到 对象中
            SysLog sysLog = new SysLog();
            sysLog.setExecutionTime(time);//执行的时长
            sysLog.setVisitTime(visitTime);//开始的时间
            sysLog.setIp(ip);//访问的ip
            sysLog.setUrl(url);//访问的链接
            sysLog.setUsername(username);//访问的用户名
            sysLog.setMethod("[类名]" + clazz.getName() + "[方法名]" + method.getName());

            //调用service 完成操作
            if (clazz != SysLog.class) {
                sysLogService.save(sysLog);
            }

        }
    }
}
