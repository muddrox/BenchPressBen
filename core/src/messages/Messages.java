package messages;

/**
 * Created by wil15 on 7/7/2017.
 */

public enum Messages {
    ONE("Pft Evn Lift!"),
    TWO("Game Ovr Nerd"),
    THREE("U MAD BRO?!?");
    private final String text;

    Messages(final String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }
}
