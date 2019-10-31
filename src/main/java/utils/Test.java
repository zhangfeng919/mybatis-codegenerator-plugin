package utils;

/**
 * @author zhangfeng
 * @create 2019-10-22-15:45
 **/
public class Test {

    public static void main(String[] args) {
        int n = 2;
        System.out.println(n >> 1);
        print(Integer.toBinaryString(-5));
        print(Integer.toOctalString(-5));
        print(8 & 16);
    }

    static void print(Object o) {
        System.out.println(o);
    }
}
