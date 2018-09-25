package com.spring.sutils.design.pattern.single;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午5:36 2018/8/8 2018
 * @Modify:
 */
class Singleton {
    //ensure this is static status  the  single  is unsafe when  mult thread
    private  static  Singleton singleton =null;    //在这儿实例化的是恶汉的 放在方法里面是懒汉模式的单例

    //as  up
     public static Singleton getSingleton() {
         /*
         懒汉模式的写法
          */
         if (singleton != null) {

             singleton= new Singleton();
         }
         return singleton;
     }
}
class  TwoSingle{













}