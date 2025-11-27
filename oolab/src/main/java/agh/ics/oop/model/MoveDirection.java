package agh.ics.oop.model;

public enum MoveDirection
{
    FORWARD,
    BACKWARD,
    LEFT,
    RIGHT;
    public static MoveDirection initializeFromArgs(String text) throws IllegalArgumentException{
        return switch (text) {
            case "f" -> MoveDirection.FORWARD;
            case "b" -> MoveDirection.BACKWARD;
            case "l" -> MoveDirection.LEFT;
            case "r" -> MoveDirection.RIGHT;
            default -> throw new IllegalArgumentException(text + " is not legal move specification");
        };
    }
}
