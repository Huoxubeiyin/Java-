package rtti;

import java.lang.reflect.*;

public class TestToy {

    public static void main(String[] args) {

//        fun1();
//        fun2();
//        fun3();
//        fun4();
//        fun5();
//        fun6();
        fun7();

    }


    private static void fun6() {
        try {
            Class c = Class.forName("rtti.Toy"); // 反射内部类,使用$

            System.out.println("getConstructors");
            // 获取类全部公有构造器
            Constructor[] constructors = c.getConstructors();
            for (Constructor c1 : constructors) {
                System.out.println(c1.toString());
            }

            System.out.println("getConstructor(int)");
            // 获取指定参数类型的构造器，必须是public
            Constructor constructor1 = c.getConstructor(int.class);
            System.out.println(constructor1.toString());

            System.out.println("getDeclaredConstructors");
            // 获取类所有构造器
            Constructor[] declaredConstructors = c.getDeclaredConstructors();
            for (Constructor c1 : declaredConstructors) {
                System.out.println(c1.toString());
            }

            System.out.println("getDeclaredConstructors(int,String)");
            // 获取类声明的指定的构造器
            Constructor declaredConstructor = c.getDeclaredConstructor(int.class, String.class);
            System.out.println(declaredConstructor.toString());


        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }


    private static void fun5() {
        try {
            Class c = Class.forName("rtti.Toy"); // 反射内部类,使用$

            // 获取类指定的构造器，生成实例
            Constructor constructor = c.getDeclaredConstructor(int.class, String.class);
            constructor.setAccessible(true);// 构造器属性是私有的，需要打开私有属性
            Object instance = constructor.newInstance(12, "Constructor_Toy");// 依次填入构造器所需参数
            Field names = c.getDeclaredField("name");
            names.setAccessible(true);
            Object o = names.get(instance);
            System.out.println(o);

            Method method = c.getDeclaredMethod("setName", String.class);// 方法中，都需要传入对应参数，没有则为null
            method.setAccessible(true);
            method.invoke(instance, new Object[]{"TOYTOY"});

            // 调用方法含有多个参数
            method = c.getDeclaredMethod("print", String.class, int.class);
            method.setAccessible(true);
            method.invoke(instance, new Object[]{"TOYTOY", 123});

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    static void fun1() {
        try {
            // 通过包路径路径获取类，使用较多
            // 当前包：package rtti;
            Class c = Class.forName("rtti.FancyToy");// 内部类使用$间隔
            printInfo(c);
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        // 通过导入的类，直接获取
        // 编译期间确定，无需try catch
        Class c = FancyToy.class;
        printInfo(c);

        // 通过对象，获取类属性
        String s = new String("test");
        Class sClass = s.getClass();
        printInfo(sClass);

    }


    static void printInfo(Class cc) {
        System.out.println(
                "Class name: " + cc.getName() + "\n" +
                        "SimpleName: " + cc.getSimpleName() + "\n" +
                        "Superclass: " + cc.getSuperclass() + "\n" +
                        " is interface? [" +
                        cc.isInterface() + "]");

        Class[] interfaces = cc.getInterfaces();
        for (Class i : interfaces
                ) {
            System.out.println(i.getName());
        }
        System.out.println("------");
    }

    static void fun2() {
        Class o1 = null;
        try {
            o1 = Class.forName("rtti.Toy");

            System.out.println("getMethods");
            Method[] methods = o1.getMethods();// 只能获取public属性的方法,含有Object相关方法
            for (Method m : methods
                    ) {
                System.out.println(m);
            }

            System.out.println("getDeclaredMethods");
            Method[] declaredMethods = o1.getDeclaredMethods();// 获取方法内部自己声明的所有方法（常用）
            for (Method m : declaredMethods
                    ) {
                System.out.println(m);
            }

            // 获取指定方法,会报找不到方法的异常
            Method getI = o1.getDeclaredMethod("getI");
            System.out.println("getDeclaredMethod: " + getI);
            System.out.println("getModifiers: " + Modifier.toString(getI.getModifiers()));


            System.out.println("getFields");
            Field[] fields = o1.getFields();// 只能获取public属性的成员
            for (Field f : fields
                    ) {
                System.out.println(f);
            }

            System.out.println("getDeclaredFields");
            Field[] declaredFields = o1.getDeclaredFields();
            for (Field f : declaredFields
                    ) {
                System.out.println(f);
            }

            // 获取指定的成员
            Field name = o1.getDeclaredField("name");
            System.out.println("getDeclaredField: " + name);


        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    static void fun3() {

        // 获取内部类
        try {
            Class c1 = Class.forName("rtti.Toy$InnerToy1");
            printInfo(c1);
            Class c2 = Class.forName("rtti.Toy$InnerToy2");
            printInfo(c2);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    static void fun4() {

        try {
            Class c = Class.forName("rtti.Toy"); // 反射内部类,使用$

            // 获取Class的所有的内部类,并获取其类属性
            Class[] declaredClasses = c.getDeclaredClasses();
            for (Class cl :
                    declaredClasses) {
                System.out.println(cl);
                int mod = cl.getModifiers();
                String modifer = Modifier.toString(mod);
                System.out.println(modifer);

            }

            // 获取类内部私有成员的属性
            Field male = c.getDeclaredField("male"); // 反射获取类成员属性值
            male.setAccessible(true); // 打开私有属性

            // 使用默认的午餐构造器，获取其成员属性
            Object newInstance = c.newInstance();// 实例对象，调用无参构造器(默认male属性true)
            Object o = male.get(newInstance);// 获取属性值，属性名称.get(对象)
            System.out.println("newInstance male: " + o); // true

            // 反射调用私有参数实例化对象
            Constructor declaredConstructor = c.getDeclaredConstructor(int.class, String.class);
            declaredConstructor.setAccessible(true); // 设置属性
            newInstance = declaredConstructor.newInstance(new Object[]{26, "ALLIES"});// 实例化对象(26,ALLIES)

            Field id = c.getDeclaredField("id");
            id.setAccessible(true);
            o = id.get(newInstance);
            System.out.println("getDeclaredConstructor male: " + o); // false

            // 调用方法
            Method declaredMethod = c.getDeclaredMethod("print", null);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(newInstance, null); // 反射调用print
            System.out.println("getDeclaredMethod print ...");


        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     *
    1、由于成员内部类对象的创建依赖于外部类对象，持有指向外部类对象的引用。所以在反射构造成员内部类的时候一定要通过获取
    构造器再调用构造器的newInstance方法，其中必须要传入外部类的Class和实例。对于私有构造器，需要使用
    getDeclaredConstructor方法获取并使用setAccessible(true)来设置为可以获取的。

    2、静态内部类不持有外部类的引用，所以当其提供了无参显式的构造器的时候，可以直接在调用其class的newInstance()方法获得
    实例。如果构造器为private的则处理同1中。如果没有提供显式的的无参构造器，只提供了有参构造器，处理也同1中。

    3、如果内部类没有提供显式的构造器，则通过上面提到的方法构造内部类对象会抛出java.lang.IllegalAccessException错误。
    即要通过上面提到的方法使用反射机制创建内部类对象，内部类一定要提供显式的构造函数！
     */
    public static void fun7() {

        try {

            // 反射例程：静态内部类
            Class c = Class.forName("rtti.Toy$InnerToy1"); // 反射Toy内部静态类，不需要Toy类的存在
            Field field = c.getDeclaredField("name1"); // 私有属性，使用getDeclaredField获取
            field.setAccessible(true); // 静态内部类的私有成员，需要设置打开私有属性
            Object o = field.get(c.newInstance()); // 无参构建器
            System.out.println("getField name1: " + o);

            Field field2 = c.getField("name2"); // 非私有属性，使用getField即可获取，不需要setAccessible
            o = field2.get(c.newInstance());// 静态内部类随着类存在，不需要实例化内部类InnerToy1，此处传递Toy类实例即可
            System.out.println("getField name2: " + o);

            System.out.println("*****");


            // 反射例程：私有内部类
            c = Class.forName("rtti.Toy");
            Object subClass = c.newInstance();
            Class inner = Class.forName("rtti.Toy$InnerToy2");

            // 获取私有内部类指定构造器
            // 需要传入外部类，构造器参数类型
            Constructor constructor = inner.getDeclaredConstructor(c, String.class, int.class);
            constructor.setAccessible(true);// 打开私有属性
            // 构建私有内部类实例对象,首先需要构造一个外部类的实例对象，然后是构造器参数
            Object instance1 = constructor.newInstance(subClass,"Allies", 27);

            Method m = inner.getDeclaredMethod("setId",int.class);// 获取是由内部类成员
            m.setAccessible(true);// 打开属性
            m.invoke(instance1,28);

        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException |
                InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}




