package model;


import java.util.HashMap;
import java.util.Map;
import static model.City.*;

public class DestCard {

    private City startCity;
    private City endCity;
    private int pointValue;

    public DestCard(City startCity, City endCity, int pointValue){
        this.startCity = startCity;
        this.endCity = endCity;
        this.pointValue = pointValue;
    }

    public City getStartCity() {
        return startCity;
    }

    public City getEndCity() {
        return endCity;
    }

    public int getPointValue() {
        return pointValue;
    }

    public static Map<Integer, DestCard> createDestCardMap() {

        Map<Integer, DestCard> destCardMap = new HashMap<>();

        destCardMap.put(0, new DestCard(BOSTON, MIAMI, 12));
        destCardMap.put(1, new DestCard(CALGARY, PHOENIX, 13));
        destCardMap.put(2, new DestCard(CALGARY, SALT_LAKE_CITY, 7));
        destCardMap.put(3, new DestCard(CHICAGO, NEW_ORLEANS, 7));
        destCardMap.put(4, new DestCard(CHICAGO, SANTA_FE, 9) );
        destCardMap.put(5, new DestCard(DALLAS, NEW_YORK, 11));
        destCardMap.put(6, new DestCard(DENVER, EL_PASO, 4));
        destCardMap.put(7, new DestCard(DENVER, PITTSBURGH, 11));
        destCardMap.put(8, new DestCard(DULUTH, EL_PASO, 10));
        destCardMap.put(9, new DestCard(DULUTH, HOUSTON, 8));
        destCardMap.put(10, new DestCard(HELENA, LOS_ANGELES, 8));
        destCardMap.put(11, new DestCard(KANSAS_CITY, HOUSTON, 5));
        destCardMap.put(12, new DestCard(LOS_ANGELES, CHICAGO, 16));
        destCardMap.put(13, new DestCard(LOS_ANGELES, MIAMI, 20));
        destCardMap.put(14, new DestCard(LOS_ANGELES, NEW_YORK, 21));
        destCardMap.put(15, new DestCard(MONTREAL, ATLANTA, 9));
        destCardMap.put(16, new DestCard(MONTREAL, NEW_ORLEANS, 13));
        destCardMap.put(17, new DestCard(NEW_YORK, ATLANTA, 6));
        destCardMap.put(18, new DestCard(PORTLAND, NASHVILLE, 17));
        destCardMap.put(19, new DestCard(PORTLAND, PHOENIX, 11));
        destCardMap.put(20, new DestCard(SAN_FRANCISCO, ATLANTA, 17));
        destCardMap.put(21, new DestCard(SAULT_ST_MARIE, NASHVILLE, 8));
        destCardMap.put(22, new DestCard(SAULT_ST_MARIE, OKLAHOMA_CITY, 9));
        destCardMap.put(23, new DestCard(SEATTLE, LOS_ANGELES, 9));
        destCardMap.put(24, new DestCard(SEATTLE, NEW_YORK, 22));
        destCardMap.put(25, new DestCard(TORONTO, MIAMI, 10));
        destCardMap.put(26, new DestCard(VANCOUVER, MONTREAL, 20));
        destCardMap.put(27, new DestCard(VANCOUVER, SANTA_FE, 13));
        destCardMap.put(28, new DestCard(WINNIPEG, HOUSTON, 12));
        destCardMap.put(29, new DestCard(WINNIPEG, LITTLE_ROCK, 11));

        return destCardMap;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        DestCard comparedCard = (DestCard)obj;
        if (this.startCity == comparedCard.startCity){
            if (this.endCity == comparedCard.endCity) {
                return true;
            }
        }
        return false;
    }
}

