package com.newbie.utils;

public class DeleteMe {
    public static void main(String[] args) throws Exception {
        System.out.println(test(20, 20, 20));
    }

    private static int soda(int drinks, int caps, int bottles) {
        caps %= 3;
        bottles %= 2;
        caps += drinks;
        bottles += drinks;
        if (caps < 3 && bottles < 2) {
            return drinks;
        } else {
            return soda(caps / 3 + bottles / 2, caps, bottles) + drinks;
        }
    }

    private static int test(int drinks, int caps, int bottles) {
        int d = (caps / 3 + bottles / 2);
        caps = (caps % 3) + d;
        bottles = (bottles % 2) + d;
        drinks += d;
        if (caps < 3 && bottles < 2) {
            if (caps == 2) {
                drinks += 1;
                caps = 0;
                bottles += 1;
                System.out.println(caps + "==" + bottles);
                return test(drinks, caps, bottles);
            } else if (bottles == 1) {
                drinks += 1;
                caps += 1;
                bottles = 0;
                System.out.println(caps + "==" + bottles);
                return test(drinks, caps, bottles);
            } else {
                System.out.println(caps + "==" + bottles);
                return drinks;
            }
        } else {
            System.out.println(caps + "==" + bottles);
            return test(drinks, caps, bottles);
        }
    }
}


