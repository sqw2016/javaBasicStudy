import java.util.*;

/**
 * 泛型类的定义和使用，泛型的好处在于不需要进行类型转换
 */

class OverClass<T> {
    private T over;

    public T getOver() {
        return over;
    }

    public void setOver(T over) {
        this.over = over;
    }
}

/**
 * 多泛型类，只包含特定类型的类
 */
class MultipleOverClass<String, Boolean, Integer> {
    private String overStr;
    private Boolean overBool;
    private Integer overInt;

    public String getOverStr() {
        return overStr;
    }

    public void setOverStr(String overStr) {
        this.overStr = overStr;
    }

    public Boolean getOverBool() {
        return overBool;
    }

    public void setOverBool(Boolean overBool) {
        this.overBool = overBool;
    }

    public Integer getOverInt() {
        return overInt;
    }

    public void setOverInt(Integer overInt) {
        this.overInt = overInt;
    }
}

/* 泛型数组 */
class ArrayOverClass<T> {
    private T[] overs;

    public T[] getOvers() {
        return overs;
    }

    public void setOvers(T[] overs) {
        this.overs = overs;
    }
}

/**
 * 键值泛型
 */
class KeyValueOverClass<K, V> {
    private Map<K, V> m = new HashMap<K, V>();

    public void put(K k, V v) {
        m.put(k, v);
    }

    public V get(K k) {
        return m.get(k);
    }
}

/**
 * 限制泛型可用类型：
 * 语法：class 类名称<T extends anyClass> anyClass
 *
 * 泛型类的类型必须实现或集成了anyClass这个接口或类，泛型限制必须使用extends关键字
 */
class LimitOverClass<T extends List> {
}

/**
 * 类型通配符：创建一个泛型对象时限制这个泛型的类型实现或继承某个接口或类
 * 语法：泛型类名<？ extends List>
 */

/**
 * 继承泛型类：子类在继承父类时需要指明泛型的具体类型，否则默认为Object
 */

class ExtendsOverClass<Integer, String> extends OverClass<Integer> {

}

/**
 * 实现泛型接口：规则和继承泛型类一样
 */
interface i<T> {

}

class SubClass<T> implements i<T> {

}


public class Genericity {

    // 通配符限制传入参数的类型
    private void doSomething(OverClass<? extends List> o) {
    }

    public static void main(String[] args){
        /* 泛型类的使用 */
        OverClass<Boolean> o1 = new OverClass<Boolean>();
        OverClass<String> o2 = new OverClass<String>();
        o1.setOver(true);
        o2.setOver("李四");
        System.out.println(o2.getOver());
        System.out.println(o1.getOver());

        /* 多泛型类的使用 */
        MultipleOverClass mo = new MultipleOverClass();
        mo.setOverStr("School");
        System.out.println(mo.getOverStr());

        /* 泛型数组的使用 */
        ArrayOverClass<Integer> ao = new ArrayOverClass<Integer>();
        Integer[] arrInt = {1, 2, 3, 4, 5, 6};
        ao.setOvers(arrInt);
        for (int i = 0, len = ao.getOvers().length; i < len; i++) {
            System.out.print(ao.getOvers()[i] + "  ");
        }
        
        /* 键值对泛型的使用 */
        KeyValueOverClass<String, String> ko1 = new KeyValueOverClass<>();
        ko1.put("a", "五天");
        ko1.put("b", "一个礼拜");
        System.out.println(ko1.get("a"));
        System.out.println(ko1.get("b"));
        KeyValueOverClass<Integer, String> ko2 = new KeyValueOverClass<>();
        ko2.put(2, "一个月的花");
        System.out.println(ko2.get(2));

        /* 容器的使用 */
        ArrayList al = new ArrayList();
        /* 这里如果使用泛型就只能添加一种类型的数据了 */
        al.add(1);
        al.add("b");
        al.add(true);
        for (int i = 0, len = al.size(); i < len; i++) {
            System.out.println(al.get(i));
        }

        /* 限制泛型类型的使用 */
        LimitOverClass<ArrayList> alo = new LimitOverClass<>();
        LimitOverClass<LinkedList> llo = new LimitOverClass<>();
        // LimitOverClass<HashMap> hlo = new LimitOverClass<HashMap>(); HashMap未继承和实现List接口

        /* 泛型通配符的使用 */
        OverClass<? extends List> a = null;
        a = new OverClass<ArrayList>();
        a = new OverClass<LinkedList>();
        // a = new OverClass<HashMap>(); HashMap未继承或实现List接口

        /* 通配符在泛型的使用 */
        List<String> l1 = new ArrayList<String>();
        l1.add("韩梅梅");
        List<?> l2 = l1;
        List<?> l3 = new LinkedList<Integer>();
        System.out.println(l2.get(0));
        l1.add("李磊");
        l1.add("李雷");
        // l1.set(1, "李磊"); set为修改，不能添加，如果修改的位置没有值，会报错
        // l2.add("韩梅梅"); 使用通配符的对象不能调用add方法
        // l2.set(0, "无法"); 使用通配符的对象不能调用set方法
        System.out.println(l2.get(1));
        l2.remove(1);
        System.out.println(l2.get(0));


        // System.out.println(l2.get(0)); 删除之后不能再
    }
}
