package com.professmessiah.intercession.professmessiah25.service;

import com.professmessiah.intercession.professmessiah25.model.IntercessionRequest;
import com.professmessiah.intercession.professmessiah25.model.TrackerItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IntercessionService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Constants for Redis key prefixes
    private static final String PREFIX = "counter:";
    private static final String HOLY_MASS = "Holy Mass";
    private static final String CREED = "Creed";
    private static final String MEMORARE = "Memorare";
    private static final String ROSARY = "Rosary";
    private static final String MERCY_CHAPLET = "Mercy Chaplet";
    private static final String PROFESS_MESSIAH_PRAYER = "Profess Messiah Prayer";
    private static final String ADORATION_TIME = "Adoration Time";

    public List<TrackerItem> getTrackerItems() {
        List<TrackerItem> trackerItems = new ArrayList<>();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        trackerItems.add(new TrackerItem(1, HOLY_MASS, getCount(ops, HOLY_MASS)));
        trackerItems.add(new TrackerItem(2, CREED, getCount(ops, CREED)));
        trackerItems.add(new TrackerItem(3, MEMORARE, getCount(ops, MEMORARE)));
        trackerItems.add(new TrackerItem(4, ROSARY, getCount(ops, ROSARY)));
        trackerItems.add(new TrackerItem(5, MERCY_CHAPLET, getCount(ops, MERCY_CHAPLET)));
        trackerItems.add(new TrackerItem(6, PROFESS_MESSIAH_PRAYER, getCount(ops, PROFESS_MESSIAH_PRAYER)));
        trackerItems.add(new TrackerItem(7, ADORATION_TIME, getCount(ops, ADORATION_TIME)));

        return trackerItems;
    }

    public void updateTrackerItems(IntercessionRequest request) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        for (TrackerItem item : request.trackerItems()) {
            int currentCount = getCount(ops, item.label());
            int newCount = currentCount + item.count();
            ops.set(PREFIX + item.label(), String.valueOf(newCount));
        }
    }

    private Integer getCount(ValueOperations<String, String> ops, String label) {
        String value = ops.get(PREFIX + label);
        return value != null ? Integer.parseInt(value) : 0;
    }
}
