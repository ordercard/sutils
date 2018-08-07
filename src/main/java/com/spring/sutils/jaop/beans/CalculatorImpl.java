package com.spring.sutils.jaop.beans;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:19 2018/8/7 2018
 * @Modify:
 */
public class CalculatorImpl implements Calculator {

    /* (non-Javadoc)
     * @see com.ddlab.rnd.beans.Calculator#calculate(int, int)
     */
    @Override
    public int calculate(int a, int b) {
        System.out.println("**********Actual Method Execution**********");
        return a/b;
    }

}
