package com.lebron.transfer;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        test1();
//        test2();
    }

    // 方式1
    public static void test1() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Account account = new Account("zhangsan", "张三");
        for (int i = 0; i < 100; i++) {
            new Thread(new Transfer(countDownLatch, account, 1)).start();
        }
        countDownLatch.countDown();
    }

    // 方式2
    // 本意是想用生产者消费者
    // 感觉没实现好
    public static void test2() throws InterruptedException {
        Account account = new Account("zhangsan", "张三");
        final List<Account> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                synchronized (list) {
                    list.add(account);
                    list.notify();
                }
            }).start();
        }
        Thread.sleep(1000);
        new Consumer(list).start();
    }

    public static class Transfer implements Runnable {
        private Account account;
        private int money;
        private CountDownLatch countDownLatch;

        public Transfer(CountDownLatch countDownLatch, Account account, int money) {
            this.account = account;
            this.money = money;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account.transfer(money);
//            account.transferCas(money);
        }
    }


    static class Consumer extends Thread {
        List<Account> list;

        public Consumer(List<Account> list) {
            this.list = list;
        }

        @Override
        public void run() {
            synchronized (list) {
                while (true) {
                    if (list.isEmpty()) {
                        try {
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    list.remove(list.size() - 1).transferNoLock(1);
                }
            }
        }
    }


    public static class Account {
        private String id;
        private String name;
        private volatile AtomicInteger balance = new AtomicInteger(0);

        public Account(String id, String name) {
            this.id = id;
            this.name = name;
        }

        // 悲观锁
        public synchronized int transfer(int money) {
            int newBalance = balance.addAndGet(money);
            System.out.println("收到1元转账，余额:" + newBalance);
            return newBalance;
        }

        public int transferNoLock(int money) {
            int newBalance = balance.addAndGet(money);
            System.out.println("收到1元转账，余额:" + newBalance);
            return newBalance;
        }

        // 乐观锁
        public int transferCas(int money) {
            for (; ; ) {
                int current = balance.get();
                if (balance.compareAndSet(current, current + money)) {
                    System.out.println("收到1元转账，余额:" + (current + money));
                    return current + money;
                }
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBalance() {
            return balance.get();
        }
    }


}
