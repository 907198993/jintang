package com.sk.jintang;


import com.sk.jintang.tools.AndroidUtils;

import org.junit.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void sadfa() throws Exception {
        String a="12345";
        System.out.println(a.substring(0,a.length()-1));
    }
    @Test
    public void adsff() throws Exception {
        minScale(750,400);
        int m=750;
        int n=400;
        if(m<n){
            int temp=n;
            n=m;
            m=temp;
        }
        System.out.println("最大公约数是："+caculate(m,n));
        System.out.println("最小公被数是："+m*n/caculate(m,n));
    }
    public static void minScale(int a, int b) {
        int tmp = a;
        if(a > b) {
            tmp = b;
        }
        for(int i = tmp; i > 0; i--) {
            if(a % i == 0 && b % i ==0) {
                System.out.println(a + ":" + b + "=" + (a / i) + ":" + (b / i));
                break;
            }
        }
    }
    public static  int caculate(int m,int n){
        int temp ;
        if(m%n==0){
            temp=n;
            ;
        }else{
            temp=caculate(n,m%n);
        }
        return temp;
    }
    @Test
    public void asdf() throws Exception {
        System.out.println(
                AndroidUtils.jiaFa(2.1,AndroidUtils.chengFa(1.16,3)));
    }
    @Test
    public void calculateMethodNum() throws Exception {
        Method[] declaredMethods1 = com.sk.jintang.module.community.network.IRequest.class.getDeclaredMethods();
        Method[] declaredMethods2 = com.sk.jintang.module.home.network.IRequest.class.getDeclaredMethods();
        Method[] declaredMethods3 = com.sk.jintang.module.my.network.IRequest.class.getDeclaredMethods();
        Method[] declaredMethods4 = com.sk.jintang.module.orderclass.network.IRequest.class.getDeclaredMethods();
        Method[] declaredMethods5 = com.sk.jintang.module.shoppingcart.network.IRequest.class.getDeclaredMethods();
        int methodSize = declaredMethods1.length + declaredMethods2.length + declaredMethods3.length + declaredMethods4.length + declaredMethods5.length;
        System.out.println("方法数量:"+methodSize);
    }

    @Test
    public void asfd() throws Exception {

        BigDecimal total=new BigDecimal(0);
        total= total.add(new BigDecimal(2));
        System.out.println(total+"");
        total= total.add(new BigDecimal(1));
        System.out.println(total+"");
        total= total.add(new BigDecimal(3));
        System.out.println(total+"");
    }
    @Test
    public void asaasfdf() throws Exception {
        double v = AndroidUtils.chuFa(1.1, 0.5, 2);
        System.out.println(v);
        BigDecimal bd = new BigDecimal("1100.1515");
        DecimalFormat df=new DecimalFormat("#.00");
        System.out.println(df.format(bd));
        System.out.println(AndroidUtils.round(11.0143));
    }
    @Test
    public void asadf() throws Exception {
        int num=15;
        double price=3790.0;
        for (int i = 0; i <num; i++) {
            double v = AndroidUtils.chengFa(price, 0.1);
            price=AndroidUtils.jiaFa(price,v);
        }
        System.out.println(price);
        System.out.println(price-3790);
    }
    @Test
    public void a() throws Exception {

        int beforeMonthLastDay = getMonthLastDay(Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.MONTH) - 1);
        System.out.println(beforeMonthLastDay);
        Calendar current = Calendar.getInstance();
        System.out.println(current.get(Calendar.MONTH));
        current.set(Calendar.YEAR,2017);
        current.set(Calendar.MONTH,7);
        current.set(Calendar.DATE, 1);//把日期设置为当月第一天
        System.out.println(current.get(Calendar.DAY_OF_WEEK) - 1);
        current.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        System.out.println(current.get(Calendar.DAY_OF_WEEK) - 1);
    }
    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

}