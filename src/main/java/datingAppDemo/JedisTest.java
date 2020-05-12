package datingAppDemo;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Client;
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


    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = new Jedis();

        List<String> maleNames = Names.getMaleNames(); // генерируем случайных пользователей мужского пола
        List<String> femaleNames = Names.getFemaleNames(); // генерируем случайных пользователей женского пола

        jedis.del(key);

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


        users.forEach(u -> jedis.rpush(key, "User " + u.getId() + " " + u.getName()));



        for (int i = 0; ; i++) {

            if (i >= 20) {
                i = 0;
            }
            String user = jedis.lindex(key, i);

            int randomUserId = random.nextInt(20);

            if (i > 1 && i % 10 == 0) {

                String randomUser = jedis.lindex(key, randomUserId);
                System.out.println("Пользователь " + randomUser + " оплатил платную услугу");
                System.out.println("На главной странице показываем " + randomUser);

                jedis.lrem(key, randomUserId, randomUser);
                jedis.linsert(key, BinaryClient.LIST_POSITION.BEFORE, user, randomUser);
                Thread.sleep(2000);
            }

            System.out.println("На главной странице показываем: " + user);
            Thread.sleep(500);

        }


    }
}
