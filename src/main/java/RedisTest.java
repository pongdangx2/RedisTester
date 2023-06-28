import redis.RedisManager;

import java.util.*;

public class RedisTest {
    public static void main(String[] args) {
        RedisManager redisManager = new RedisManager();

        stringTest(redisManager);
        listTest(redisManager);
        setTest(redisManager);
        mapTest(redisManager);
        sortedSetTest(redisManager);

    }
 
    private static void sortedSetTest(RedisManager redisManager) {
        System.out.println("###### Sorted Set Start ######");

        redisManager.setSortedSetData("animal", 100, "Tiger");
        redisManager.setSortedSetData("animal", 99, "Lion");
        redisManager.setSortedSetData("animal", 98, "Dog");
        redisManager.setSortedSetData("animal", 101, "Cat");

        System.out.println("Desc Start ####################");
        redisManager.getSoredSetData("animal", true).forEach(System.out::println);
        System.out.println("Asc Start ####################");
        redisManager.getSoredSetData("animal", false).forEach(System.out::println);

        System.out.println("Desc With Score Start ####################");
        redisManager.getSoredSetDataWithScores("animal", true).forEach(e -> {
            System.out.println(e.getElement() + " : " + e.getScore());
        });

        System.out.println("Asc With Score Start ####################");
        redisManager.getSoredSetDataWithScores("animal", false).forEach(e -> {
            System.out.println(e.getElement() + " : " + e.getScore());
        });

        System.out.println("###### Sorted Set End ######\n");
    }

    private static void mapTest(RedisManager redisManager) {
        System.out.println("###### Map Start ######");
        redisManager.setMapData("score", "math", "100");
        redisManager.setMapData("score", "english", "99");
        redisManager.setMapData("score", "korean", "98");
        Map<String, String> scores = new HashMap<>();
        scores.put("art", "97");
        scores.put("music", "96");
        scores.put("math", "95");
        redisManager.setMapData("score", scores);

        Map<String, String> scoreResult = redisManager.getMapData("score");

        for(String key : scoreResult.keySet()){
            System.out.println(key  + " : " + scoreResult.get(key));
        }

        System.out.println("###### Map End ######\n");
    }

    private static void setTest(RedisManager redisManager) {
        System.out.println("###### Set Start ######");
        redisManager.setSetData("fruits", "apple");
        redisManager.setSetData("fruits", "banana");
        redisManager.setSetData("fruits", "grape");
        redisManager.setSetData("fruits", "melon");
        redisManager.setSetData("fruits", "apple");

        for (String fruit : redisManager.getSetData("fruits")) {
            System.out.println(fruit);
        }
        System.out.println("###### Set End ######\n");
    }


    private static void listTest(RedisManager redisManager){
        System.out.println("###### List Start ######");
        List<String> test = new ArrayList<>();
        test.add("one");
        test.add("two");
        test.add("three");
        test.add("four");

        redisManager.setListData("numbers", test);
        redisManager.setListData("numbers", "five");
        for(String number : redisManager.getListData("numbers", 0, 4)){
            System.out.println(number);
        }
        System.out.println("###### List End ######\n");
    }


    private static void stringTest(RedisManager redisManager){
        System.out.println("###### String Start ######");
        redisManager.setStringData("testKey", "testValue");
        System.out.println(redisManager.getStringData("testKey"));

        System.out.println("###### String End ######\n");
    }
}
