package datingAppDemo;

import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class JedisTest {

    private static AtomicInteger integer = new AtomicInteger(1);
    private static List<User> users = new ArrayList<>();
    private static Random random = new Random();
    private static String sMale = "male";
    private static String sFemale = "female";
    private static String key = "users";


    public static void main(String[] args) {

        Jedis jedis = new Jedis();

        List<String> maleNames = Names.getMaleNames(); // генерируем случайных пользователей мужского пола
        List<String> femaleNames = Names.getFemaleNames(); // генерируем случайных пользователей женского пола


        for (int i = 0; i < 20; i++) { // добавляем 20 пользователей на сайт

            int maleRandomName = random.nextInt(maleNames.size());
            int femaleRandomName = random.nextInt(femaleNames.size());
            int nextId = integer.getAndIncrement();

            if (i % 2 == 0) {
                users.add(new User(nextId, maleNames.get(maleRandomName), new Date(), sMale));
            } else {
                users.add(new User(nextId, femaleNames.get(femaleRandomName), new Date(), sFemale));
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        users.sort(Comparator.comparing(User::getRegistrationDate)); // сортируем пользователей по дате регистрации

        users.forEach(u -> jedis.zadd(key, u.getRegistrationDate().getTime(), "User " + u.getId() + " " +
                u.getName())); // заносим пользователей в Redis

        printUsers(); // выводим на экран


    }

    private static void printUsers() {

        User user;

        for (int i = 0; ; i++) {

            if (integer.get() >= users.size()) {
                integer.set(0);
            }

            int nextUser = integer.getAndIncrement();
            User u = users.get(nextUser);

            if (i > 1 && i % 10 == 0) {
                int randomUserId = random.nextInt(users.size());

                for (User user1 : users) {
                    if (user1.getId() == randomUserId) {
                        u = user1;
                        break;
                    }
                }

                System.out.println("Пользователь " + u.getId() + " " + u.getName() + " оплатил платную услугу");
                System.out.println("На главной странице показываем пользователя: " + u);
                printUsers();
            }

            System.out.println("На главной странице показываем пользователя: " + u);


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
