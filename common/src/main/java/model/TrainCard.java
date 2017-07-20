package model;


public enum TrainCard {
    PURPLE,
    WHITE,
    BLUE,
    YELLOW,
    ORANGE,
    BLACK,
    RED,
    GREEN,
    WILD;

    private static TrainCard[] values = TrainCard.values();

    public static TrainCard getTrainCard(int trainCard) {
        return values[trainCard];
    }
}
