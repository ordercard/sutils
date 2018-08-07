package jaop;

import jaop.beans.Calculator;
import jaop.beans.CalculatorImpl;
import jaop.proxy.ProxyFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:20 2018/8/7 2018
 * @Modify:
 */
public class test {
    public static void main(String[] args) {
        CalculatorImpl calcImpl = new CalculatorImpl();
        BeforeHandler before = new BeforeHandlerImpl();
        AfterHandler after = new AfterHandlerImpl();
        List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();
        handlers.add(before);
        handlers.add(after);
        Calculator proxy = (Calculator) ProxyFactory.getProxy(calcImpl,
                handlers);
        int result = proxy.calculate(20, 10);
        System.out.println("FInal Result :::" + result);
    }

}
