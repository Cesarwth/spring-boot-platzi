package com.fundamentos.springboot.fundamentos.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{

    private final Log LOGGER = LogFactory.getLog(MyBeanWithPropertiesImplement.class);

    private MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        LOGGER.info("Estamos en el metodo printWithDependency");
        int number=9;
        LOGGER.debug("El numero enviado como parametro a la dependencia operación es: "+ number);
        System.out.println(myOperation.sum(number));
        System.out.println("Hola implementacion bean con dependencia");
    }
}
