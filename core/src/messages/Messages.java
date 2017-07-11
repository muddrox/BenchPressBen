package messages;

import java.util.Random;

/**
 * Created by wil15 on 7/7/2017.
 */

enum Messages {
    ONE("    Evn Lift!?!"),
    TWO("Game Ovr, SON!"),
    THREE("  U MAD BRO?"),
    FOUR("    GIT GUD!!"),
    FIVE("  TEAR 4U :'("),
    SIX("HE BROKE IT..."),
    SEVEN("  SUCK LESS!"),
    EIGHT("  OH NOES!!"),
    NINE("      Pffft!!!");
    private final String text;

    Messages(final String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }

    public static Messages randomMess(){
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}


