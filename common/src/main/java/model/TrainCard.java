package model;


public enum TrainCard {
    PURPLE ("Purple"),
    WHITE ("White"),
    BLUE ("Blue"),
    YELLOW ("Yellow"),
    ORANGE ("Orange"),
    BLACK ("Black"),
    RED ("Red"),
    GREEN ("Green"),
    WILD ("Wild");

    private String mPrettyName;

    TrainCard(String prettyName) {
        mPrettyName = prettyName;
    }

    public String getPrettyname() {
        return mPrettyName;
    }

    private static TrainCard[] values = TrainCard.values();

    public static TrainCard getTrainCard(int trainCard) {
        return values[trainCard];
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

    public static int getIntByTrainCard(TrainCard trainCard) {
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
