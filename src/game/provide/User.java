package game.provide;

import java.util.Comparator;

public class User {
    private String name;
    private String rounds;

    public User(String name, String rounds) {
        this.name = name;
        this.rounds = rounds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRounds() {
        return rounds;
    }

    public void setRounds(String rounds) {
        this.rounds = rounds;
    }
}
