package jaop;

import java.lang.reflect.InvocationHandler;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:06 2018/8/7 2018
 * @Modify:
 */
public abstract class AbstractHandler   implements InvocationHandler {

    /** The target object. */
    private Object targetObject;

    /**
     * Sets the target object.
     *
     * @param targetObject the new target object
     */
    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * Gets the target object.
     *
     * @return the target object
     */
    public Object getTargetObject() {
        return targetObject;
    }
}
