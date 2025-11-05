package core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Player implements Serializable, Comparable<Player> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    private final LocalDateTime localDateTime;
    private int score;
    private final String name;

    public Player(int score, LocalDateTime localDateTime, String name) {
        this.score = score;
        this.localDateTime = localDateTime;
        this.name = name;
    }

    public Player(int score, String name) {
        this.score = score;
        localDateTime = LocalDateTime.now( ZoneId.of("Asia/Ho_Chi_Minh"));
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        String formateDate = localDateTime.format(DATE_FORMAT);
        return name + " - " + score;
    }


    @Override
    public int compareTo(Player other) {
        return Integer.compare(score, other.getScore());
    }
}
