package model;


public enum TrainCard {
    RED ("Red"),
    BLUE ("Blue"),
    GREEN ("Green"),
    YELLOW ("Yellow"),
    BLACK ("Black"),
    PURPLE ("Purple"),
    ORANGE ("Orange"),
    WHITE ("White"),
    WILD ("Wild");

    private String mPrettyName;
    private static TrainCard[] values = TrainCard.values();

    TrainCard(String prettyName) {
        mPrettyName = prettyName;
    }

    public static TrainCard getTrainCardTypeByInt(int trainCardID){
        if (trainCardID < 12)
            return PURPLE;
        else if (trainCardID < 24)
            return WHITE;
        else if (trainCardID < 36)
            return BLUE;
        else if (trainCardID < 48)
            return YELLOW;
        else if (trainCardID < 60)
            return ORANGE;
        else if (trainCardID < 72)
            return BLACK;
        else if (trainCardID < 84)
            return RED;
        else if (trainCardID < 96)
            return GREEN;
        else
            return WILD;
    }

    public static int getTrainCardKey(TrainCard trainCard) {
        switch (trainCard) {
            case RED:
                    return 0;
            case BLUE:
                    return 1;
            case GREEN:
                    return 2;
            case YELLOW:
                    return 3;
            case BLACK:
                    return 4;
            case PURPLE:
                    return 5;
            case ORANGE:
                    return 6;
            case WHITE:
                    return 7;
            case WILD:
                    return 8;
        }
        return -1;
    }

    public String getPrettyname() {
        return mPrettyName;
    }

    public static TrainCard getTrainCard(int trainCard) {
        return values[trainCard];
    }
}
