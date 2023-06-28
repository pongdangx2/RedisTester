package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.resps.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisManager {
    private final String HOST = "localhost";
    private final int PORT = 6379;
    private final int TMOUT = 1000;
    private final String PASSWORD = "P@ssw0rd";

    private JedisPool jedisPool;

    public RedisManager() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        this.jedisPool = new JedisPool(jedisPoolConfig, HOST, PORT, TMOUT, PASSWORD);
    }

    // String -----------------------------------------------------------------------------
    public boolean setStringData(String key, String value){
        try (Jedis jedis = jedisPool.getResource()){
            jedis.set(key, value);
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // String -----------------------------------------------------------------------------
    public Object getStringData(String key){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.get(key);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // List -----------------------------------------------------------------------------
    public boolean setListData(String key, List<String> values) {
        try (Jedis jedis = jedisPool.getResource()){

            values.forEach(value -> {
                jedis.lpush(key, value);
            });

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean setListData(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()){

            jedis.lpush(key, value);

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // List -----------------------------------------------------------------------------
    public List<String> getListData(String key, int firstIdx, int lastIdx){
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.lrange(key, firstIdx, lastIdx);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    // Set -----------------------------------------------------------------------------
    public boolean setSetData(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()){

            jedis.sadd(key, value);

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Set -----------------------------------------------------------------------------
    public Set<String> getSetData(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.smembers(key);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // Map -----------------------------------------------------------------------------
    public boolean setMapData(String key, String mapKey, String mapValue) {
        try (Jedis jedis = jedisPool.getResource()){

            jedis.hset(key, mapKey, mapValue);

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean setMapData(String key, Map<String, String> value) {
        try (Jedis jedis = jedisPool.getResource()){

            jedis.hset(key, value);

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Map<String, String> getMapData(String key) {
        try (Jedis jedis = jedisPool.getResource()){
            return jedis.hgetAll(key);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    // Sorted Set -----------------------------------------------------------------------------
    public boolean setSortedSetData(String key, int score, String value) {
        try (Jedis jedis = jedisPool.getResource()){

            jedis.zadd(key, score, value);

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Sorted Set -----------------------------------------------------------------------------
    public List<String> getSoredSetData(String key, boolean isDesc) {
        try (Jedis jedis = jedisPool.getResource()){

            if(isDesc){
                return jedis.zrevrange(key, 0, -1);
            } else {
                return jedis.zrange(key, 0, -1);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Tuple> getSoredSetDataWithScores(String key, boolean isDesc) {
        try (Jedis jedis = jedisPool.getResource()){

            if(isDesc){
                return jedis.zrevrangeWithScores(key, 0, -1);
            } else {
                return jedis.zrangeWithScores(key, 0, -1);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
