package com.luv2code.aopdemo.aspect;

import com.luv2code.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
    @Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint theProceedingJoinPoint) throws Throwable{

        // print out method we are advising on
        String method = theProceedingJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing the @Around on method: "+method);

        // get begin timestamp
        long begin = System.currentTimeMillis();

        // now, let's execute the method
        Object result =null;
        try {
           result = theProceedingJoinPoint.proceed();
        }catch (Exception exc){
            // og the exception
            System.out.println(exc.getMessage());

            // give a user custom message
            //result = "Major accident! But no worries, your private AOP helicopter is on the way!";

            // 386 rethrow exception
            throw exc;
        }

        // get and timestamp
        long end = System.currentTimeMillis();

        // compute duration and display it
        long duration = end - begin;
        System.out.println("\n======> Duration: "+duration/1000.0 +"seconds");

        return result;
    }

    @After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint){
        // print out wish method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing the @After (finally) on method: "+method);
    }

    @AfterThrowing(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc" )
    public void afterThrowingFindAccountsAdvice(
            JoinPoint theJoinPoint, Throwable theExc){
        // print out wish method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing the @AfterThrowing on method: "+method);
        // log the exception
        System.out.println("\n=====>>> The exception is : "+theExc);

    }



    @AfterReturning(
            pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result")
    public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result){

        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing the @AfterReturning on method: "+method);

        // print out the result of the method call
        System.out.println("\n=====>>> result is: "+result);

        // let's post-process the data ... let's modify it :-)

        // convert the account name to uppercase
        convertAccountNameToUppercase(result);


    }

    private void convertAccountNameToUppercase(List<Account> result) {
        // loop through accounts
        for(Account tempAccount: result) {
            //get uppercase version of name
            String theUpperName = tempAccount.getName().toUpperCase();

            // update the name on the account
            tempAccount.setName(theUpperName);
            System.out.println("\n=====>>> result is: "+result);

        }

    }


    @Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJointPoint){
        System.out.println("\n======> Executing @Before advice on method");

        // display method signature
        MethodSignature methodSignature = (MethodSignature) theJointPoint.getSignature();
        System.out.println("Method: "+methodSignature);

        // display method arguments
        // get args
        Object[] args = theJointPoint.getArgs();

        // loop through args
        for(Object tempArg: args){
            System.out.println(tempArg);

            if(tempArg instanceof Account){
                // downcast and print account specific stuff
                Account theAccount = (Account) tempArg;

                System.out.println("Account name: "+theAccount.getName());
                System.out.println("Account level: "+theAccount.getLevel());
            }
        }


    }

}
