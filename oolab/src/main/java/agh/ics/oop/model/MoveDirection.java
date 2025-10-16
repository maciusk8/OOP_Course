package agh.ics.oop.model;

public enum MoveDirection
{
    FORWARD("f"),
    BACKWARD("b"),
    LEFT("l"),
    RIGHT("r");

    private final String label;
    MoveDirection(String label) {
        this.label = label;
    }

    public static MoveDirection initializeFromArgs(String text) {
        for (MoveDirection k : MoveDirection.values()) {
            if (k.label.equals(text))
            {
                return k;
            }
        }
        return null;
    }
}
