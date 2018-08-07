package jaop;

import java.lang.reflect.Method;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:07 2018/8/7 2018
 * @Modify:
 */
public abstract class BeforeHandler extends AbstractHandler {

    /**
     * Handles before execution of actual method.
     *
     * @param proxy the proxy
     * @param method the method
     * @param args the args
     */
    public abstract void handleBefore(Object proxy, Method method, Object[] args);

    /* (non-Javadoc)
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        handleBefore(proxy, method, args);
        return method.invoke(getTargetObject(), args);
    }
}
