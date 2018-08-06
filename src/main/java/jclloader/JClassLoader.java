package jclloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Auther :huiqiang
 * @Description :  所有的自定义类都是java.lang.c=ClassLoader的直接子类或者是间接子类
 * @Date: Create in 下午3:04 2018/8/3 2018
 * @Modify:
 */
public class JClassLoader extends ClassLoader  {
    private final  static Path DEFAULT_CLASS_DIR = Paths.get("/Users/hq","Downloads");
    private final Path classDir;

    public JClassLoader() {
        super();
        this.classDir=DEFAULT_CLASS_DIR;
    }


    public JClassLoader(String classDir) {
        this.classDir = Paths.get(classDir);
    }

    /**
     * 同时指定了父加载器和class路径
     * @param parent
     * @param classDir
     */
    public JClassLoader(ClassLoader parent, String classDir) {
        super(parent);
        this.classDir =Paths.get(classDir);
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {

        //read the class
         byte[] bytes = this.readClassBytes(name);
         if (null ==bytes || bytes.length==0){

             throw  new ClassNotFoundException("不能加载这个类 classname="+name);

         }

        return  this.defineClass(name,bytes,0,bytes.length);


    }
     /**
      *
      *@描述 根据指定路径返回  byte数组
      *@参数
      *@返回值 byte[]    使用应用类的父亲加载器    设置父为空
      *@创建人 慧强
      *@创建时间 2018/8/3
      *@修改人和其它信息

      */
    private byte[] readClassBytes(String name) throws ClassNotFoundException {

        String path =name.replace(".","/");
        Path  sp =classDir.resolve(path+".class");
        if (!sp.toFile().exists()){
            throw new ClassNotFoundException("类没有找到"+name);

        }
        try(ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream()){
            Files.copy(sp,byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException("类没有找到"+name);

        }


    }

    @Override
    protected  Class<?> loadClass(String name,boolean resovle) throws ClassNotFoundException {

        synchronized (getClassLoadingLock(name)){
            Class<?> klass = findLoadedClass(name);
            if (klass==null){

                if (name.startsWith("java.") ||name.startsWith("javax")){

                    try {
                        klass= getSystemClassLoader().loadClass(name);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        klass=this.findClass(name);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (klass==null){
                        if (getParent()!=null){

                            try {
                                klass=getParent().loadClass(name);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                        }else {


                            klass= getSystemClassLoader().loadClass(name);

                        }


                    }


                }
            }

            if (klass==null){

                throw new ClassNotFoundException("该类没有被找到");

            }
            if (resovle){
                resolveClass(klass);
            }
            return klass;
        }



    }




}
