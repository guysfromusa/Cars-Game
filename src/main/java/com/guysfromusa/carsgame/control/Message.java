package com.guysfromusa.carsgame.control;

import lombok.Data;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Data
public class Message implements Comparable<Message> {

    private LocalDateTime dateTime = now();
    private String gameName;

    @Override
    public int compareTo(Message o) {
        return this.dateTime.compareTo(o.getDateTime());
    }
}
