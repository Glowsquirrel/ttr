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

    public static int getTrainCardInt(TrainCard trainCard) {
        switch (trainCard) {
            case PURPLE:
                    return 0;
            case WHITE:
                    return 1;
            case BLUE:
                    return 2;
            case YELLOW:
                    return 3;
            case ORANGE:
                    return 4;
            case BLACK:
                    return 5;
            case RED:
                    return 6;
            case GREEN:
                    return 7;
            case WILD:
                    return 8;
        }
        return -1;
    }
}
