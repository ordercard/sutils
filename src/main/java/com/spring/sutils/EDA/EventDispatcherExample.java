package com.spring.sutils.EDA;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:41 2018/9/28 2018
 * @Modify:
 */
public class EventDispatcherExample {


    static  class  InputEvent extends  Event{

        private final int x;
        private final int y;

        public InputEvent(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }


    static  class  ResultEvent extends  Event{

        private final int res;


        public ResultEvent(int res) {
            this.res = res;
        }

        public int getRes() {
            return res;
        }
    }
    static  class  ResultEventHander implements  Channel<ResultEvent>{


        @Override
        public void dispatch(ResultEvent msg) {
            System.out.println(msg.getRes()+"is a result");
        }
    }

    static  class  InputEventHandler implements  Channel<InputEvent>{

        private  final EventDispatcher dispatcher;

        public InputEventHandler(EventDispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        @Override
        public void dispatch(InputEvent msg) {
            System.out.println(msg.getX()+"-----"+msg.getY());
            int res= msg.getX()+msg.getY();
            dispatcher.dispatch(new ResultEvent(res));
        }
    }

    public static void main(String[] args) {

        EventDispatcher dispatcher =  new EventDispatcher();
        dispatcher.registerChannel(InputEvent.class,new InputEventHandler(dispatcher));
        dispatcher.registerChannel(ResultEvent.class,new ResultEventHander());
        dispatcher.dispatch(new InputEvent(1,2));


    }

}
