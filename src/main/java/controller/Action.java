package controller;

import java.lang.reflect.Method;

public class Action {

    protected Class<?> actionClass;// 接口类
    protected Method method; // 接口对应方法

    public Action(Class<?> actionClass, Method method) {
        this.actionClass = actionClass;
        this.method = method;
    }

    public Class<?> getActionClass() {
        return actionClass;
    }

    public void setActionClass(Class<?> actionClass) {
        this.actionClass = actionClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
