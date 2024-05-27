package it.ji.game.utils.redis;


import it.ji.game.utils.logging.LoggerG;
import it.ji.game.utils.settings.Settings;
import it.ji.game.utils.settings.SettingsKey;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisManager {
    private static RedisManager instance = null;
    private Jedis jedisBroker = null;
    private Jedis jedisRW = null;
    private final String brokerAddress = Settings.getInstance().getIP();

    private List<RedisMessageListener> listeners = new ArrayList<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private RedisManager() {
        jedisBroker = new Jedis(brokerAddress);
        jedisRW     = new Jedis(brokerAddress);
    }

    public static RedisManager getInstance() {
        if (instance == null) {
            instance = new RedisManager();
        }
        return instance;
    }
    public void put(String key, String value){
        jedisRW.set(key, value);
    }

    public String get(String key){
        return jedisRW.get(key);
    }

    public void hset(String key, String field, String value){
        jedisRW.hset(key, field, value);
    }

    /**
     * Set a key with a field and a value and set the time to live
     * @param key
     * @param field
     * @param value
     * @param ttl time to live in seconds
     */
    public void hset(String key, String field, String value, int ttl){
        jedisRW.hset(key, field, value);
        jedisRW.expire(key, ttl);
    }

    public void delete(String key){
        jedisRW.del(key);
    }

    /**
     *
     * @param key
     * @param ttl
     */
    public void expire(String key, int ttl){
        jedisRW.expire(key, ttl);
    }

    public Optional<String> hget(String key, String field){
        return Optional.ofNullable(jedisRW.hget(key, field));
    }
    public Map<String, String> hgetAll(String key){
        Map<String, String> hash = jedisRW.hgetAll(key);
        return hash;
    }

    public void publish(String channel, String message){
        LoggerG.setMessage("Publishing message: " + message + " to channel: " + channel).system();
        jedisBroker.publish(channel, message);
    }

    public void subscribe(RedisMessageListener listener, String ... channels){
        listeners.add(listener);
        executorService.submit(() -> {
            try {
                JedisPubSub jedisPubSub = new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        listeners.forEach(listener -> listener.onMessage(new RedisMessage(channel, message)));
                    }
                };

                // Apri una nuova connessione Jedis per la sottoscrizione
                try (Jedis jedis = new Jedis("http://152.228.218.211:200")) {
                    jedis.subscribe(jedisPubSub, channels);
                }
                Arrays.stream(channels).forEach(channel -> LoggerG.setMessage("Subscribed to channel: " + channel).system());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    //expose a method to kill the executor service
    public void kill(){
        LoggerG.setMessage("Killing the executor service").system();
        executorService.shutdown();
        LoggerG.setMessage("Executor service killed").system();
    }
    public void hDelete(String key, String field){
        LoggerG.setMessage("Deleting field: " + field + " from key: " + key).system();
        jedisRW.hdel(key, field);
    }

    public void shutdown() {

        jedisBroker.close();
        jedisRW.close();
    }
}


