package model;


import java.util.HashMap;
import java.util.Map;

import static model.City.*;
import static model.TrainCard.BLACK;
import static model.TrainCard.BLUE;
import static model.TrainCard.GREEN;
import static model.TrainCard.ORANGE;
import static model.TrainCard.PURPLE;
import static model.TrainCard.RED;
import static model.TrainCard.WHITE;
import static model.TrainCard.WILD;
import static model.TrainCard.YELLOW;


public class Route {

    private City startCity;
    private City endCity;
    private int length;
    private TrainCard originalColor;
    private PlayerColor claimedColor;
    private boolean doubleRoute;
    private boolean claimed = false;
    private int pointValue;

    public Route(City startCity, City endCity, int length, TrainCard color, boolean doubleRoute) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.length = length;
        this.originalColor = color;
        this.doubleRoute = doubleRoute;
        setPointValue();
    }

    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }

    public void setLength(int length) {
        this.length = length;
    }



    public City getStartCity() {
        return startCity;
    }

    public City getEndCity() {
        return endCity;
    }

    public int getLength() {
        return length;
    }

    public TrainCard getColor() {
        return originalColor;
    }

    public int getPointValue() {
        return pointValue;
    }
    public boolean isDoubleRoute() {
        return doubleRoute;
    }

    public boolean isClaimed(){
        return isClaimed();
    }

    public void claimRoute(PlayerColor playerColor) {
        claimedColor = playerColor;
        claimed = true;
    }

    public static Map<Integer, Route> createRouteMap() {
        Map<Integer, Route> routeMap = new HashMap<>();

        routeMap.put(0, new Route(ATLANTA, CHARLESTON, 2, WILD, false));
        routeMap.put(1, new Route(ATLANTA, MIAMI, 5, BLUE, false));
        routeMap.put(2, new Route(ATLANTA, NASHVILLE, 1, WILD, false));
        routeMap.put(3, new Route(ATLANTA, NEW_ORLEANS, 4, YELLOW, true));
        routeMap.put(4, new Route(ATLANTA, NEW_ORLEANS, 4, ORANGE, true));
        routeMap.put(5, new Route(ATLANTA, RALEIGH, 2, WILD, true));
        routeMap.put(6, new Route(ATLANTA, RALEIGH, 2, WILD, true));
        routeMap.put(7, new Route(BOSTON, MONTREAL, 2, WILD, true));
        routeMap.put(8, new Route(BOSTON, MONTREAL, 2, WILD, true));
        routeMap.put(9, new Route(BOSTON, NEW_YORK, 2, ORANGE, true));
        routeMap.put(10, new Route(BOSTON, NEW_YORK, 2, BLACK, true));
        routeMap.put(11, new Route(CALGARY, HELENA, 4, WILD, false));
        routeMap.put(12, new Route(CALGARY, SEATTLE, 4, WILD, false));
        routeMap.put(13, new Route(CALGARY, VANCOUVER, 3, WILD, false));
        routeMap.put(14, new Route(CALGARY, WINNIPEG, 6, WHITE, false));
        routeMap.put(15, new Route(CHARLESTON, MIAMI, 4, PURPLE, false));
        routeMap.put(16, new Route(CHARLESTON, RALEIGH, 2, WILD, false));
        routeMap.put(17, new Route(CHICAGO, DULUTH, 3, RED, false));
        routeMap.put(18, new Route(CHICAGO, ST_LOUIS, 2, GREEN, true));
        routeMap.put(19, new Route(CHICAGO, ST_LOUIS, 2, WHITE, true));
        routeMap.put(20, new Route(CHICAGO, OMAHA, 4, BLUE, false));
        routeMap.put(21, new Route(CHICAGO, PITTSBURGH, 3, ORANGE, true));
        routeMap.put(22, new Route(CHICAGO, PITTSBURGH, 3, BLACK, true));
        routeMap.put(23, new Route(CHICAGO, TORONTO, 4, WHITE, false));
        routeMap.put(24, new Route(DALLAS, EL_PASO, 4, RED, false));
        routeMap.put(25, new Route(DALLAS, HOUSTON, 1, WILD, true));
        routeMap.put(26, new Route(DALLAS, HOUSTON, 1, WILD, true));
        routeMap.put(27, new Route(DALLAS, LITTLE_ROCK, 2, WILD, false));
        routeMap.put(28, new Route(DALLAS, OKLAHOMA_CITY, 2, WILD, true));
        routeMap.put(29, new Route(DALLAS, OKLAHOMA_CITY, 2, WILD, true));
        routeMap.put(30, new Route(DENVER, HELENA, 4, GREEN, false));
        routeMap.put(31, new Route(DENVER, KANSAS_CITY, 4, BLACK, true));
        routeMap.put(32, new Route(DENVER, KANSAS_CITY, 4, ORANGE, true));
        routeMap.put(33, new Route(DENVER, OKLAHOMA_CITY, 4, RED, false));
        routeMap.put(34, new Route(DENVER, OMAHA, 4, PURPLE, false));
        routeMap.put(35, new Route(DENVER, PHOENIX, 5, WHITE, false));
        routeMap.put(36, new Route(DENVER, SALT_LAKE_CITY, 3, RED, true));
        routeMap.put(37, new Route(DENVER, SALT_LAKE_CITY, 3, YELLOW, true));
        routeMap.put(38, new Route(DENVER, SANTA_FE, 2, WILD, false));
        routeMap.put(39, new Route(DULUTH, HELENA, 6, ORANGE, false));
        routeMap.put(40, new Route(DULUTH, OMAHA, 2, WILD, true));
        routeMap.put(41, new Route(DULUTH, OMAHA, 2, WILD, true));
        routeMap.put(42, new Route(DULUTH, SAULT_ST_MARIE, 3, WILD, false));
        routeMap.put(43, new Route(DULUTH, TORONTO, 6, PURPLE, false));
        routeMap.put(44, new Route(DULUTH, WINNIPEG, 5, BLACK, false));
        routeMap.put(45, new Route(EL_PASO, HOUSTON, 3, GREEN, false));
        routeMap.put(46, new Route(EL_PASO, LOS_ANGELES, 6, BLACK, false));
        routeMap.put(47, new Route(EL_PASO, OKLAHOMA_CITY, 6, YELLOW, false));
        routeMap.put(48, new Route(EL_PASO, PHOENIX, 3, WILD, false));
        routeMap.put(49, new Route(EL_PASO, SANTA_FE, 6, GREEN, false));
        routeMap.put(50, new Route(HELENA, OMAHA, 5, RED, false));
        routeMap.put(51, new Route(HELENA, SALT_LAKE_CITY, 3, PURPLE, false));
        routeMap.put(52, new Route(HELENA, SEATTLE, 6, YELLOW, false));
        routeMap.put(53, new Route(HELENA, WINNIPEG, 4, BLUE, false));
        routeMap.put(54, new Route(HOUSTON, NEW_ORLEANS, 2, WILD, false));
        routeMap.put(55, new Route(KANSAS_CITY, OKLAHOMA_CITY, 2, WILD, true));
        routeMap.put(56, new Route(KANSAS_CITY, OKLAHOMA_CITY, 2, WILD, true));
        routeMap.put(57, new Route(KANSAS_CITY, OMAHA, 2, WILD, true));
        routeMap.put(58, new Route(KANSAS_CITY, OMAHA, 2, WILD, true));
        routeMap.put(59, new Route(KANSAS_CITY, ST_LOUIS, 2, BLUE, true));
        routeMap.put(60, new Route(KANSAS_CITY, ST_LOUIS, 2, PURPLE, true));
        routeMap.put(61, new Route(LAS_VEGAS, LOS_ANGELES, 2, WILD, false));
        routeMap.put(62, new Route(LAS_VEGAS, SALT_LAKE_CITY, 3, ORANGE, false));
        routeMap.put(63, new Route(LITTLE_ROCK, NASHVILLE, 3, WHITE, false));
        routeMap.put(64, new Route(LITTLE_ROCK, NEW_ORLEANS, 3, GREEN, false));
        routeMap.put(65, new Route(LITTLE_ROCK, OKLAHOMA_CITY, 2, WILD, false));
        routeMap.put(66, new Route(LITTLE_ROCK, ST_LOUIS, 2, WILD, false));
        routeMap.put(67, new Route(LOS_ANGELES, PHOENIX, 3, WILD, false));
        routeMap.put(68, new Route(LOS_ANGELES, SAN_FRANCISCO, 3, YELLOW, true));
        routeMap.put(69, new Route(LOS_ANGELES, SAN_FRANCISCO, 3, PURPLE, true));
        routeMap.put(70, new Route(MIAMI, NEW_ORLEANS, 6, RED, false));
        routeMap.put(71, new Route(MONTREAL, NEW_YORK, 3, BLUE, false));
        routeMap.put(72, new Route(MONTREAL, SAULT_ST_MARIE, 5, BLACK, false));
        routeMap.put(73, new Route(MONTREAL, TORONTO, 3, WILD, false));
        routeMap.put(74, new Route(NASHVILLE, PITTSBURGH, 4, YELLOW, false));
        routeMap.put(75, new Route(NASHVILLE, RALEIGH, 3, BLACK, false));
        routeMap.put(76, new Route(NASHVILLE, ST_LOUIS, 2, WILD, false));
        routeMap.put(77, new Route(NEW_YORK, PITTSBURGH, 2, WHITE, true));
        routeMap.put(78, new Route(NEW_YORK, PITTSBURGH, 2, GREEN, true));
        routeMap.put(79, new Route(NEW_YORK, WASHINGTON, 2, ORANGE, true));
        routeMap.put(80, new Route(NEW_YORK, WASHINGTON, 2, BLACK, true));
        routeMap.put(81, new Route(OKLAHOMA_CITY, SANTA_FE, 3, BLUE, false));
        routeMap.put(82, new Route(PHOENIX, SANTA_FE, 3, WILD, false));
        routeMap.put(83, new Route(PITTSBURGH, RALEIGH, 2, WILD, false));
        routeMap.put(84, new Route(PITTSBURGH, ST_LOUIS, 4, YELLOW, false));
        routeMap.put(85, new Route(PITTSBURGH, TORONTO, 2, WILD, false));
        routeMap.put(86, new Route(PITTSBURGH, WASHINGTON, 2, WILD, false));
        routeMap.put(87, new Route(PORTLAND, SALT_LAKE_CITY, 6, BLUE, false));
        routeMap.put(88, new Route(PORTLAND, SAN_FRANCISCO, 5, GREEN, true));
        routeMap.put(89, new Route(PORTLAND, SAN_FRANCISCO, 5, PURPLE, true));
        routeMap.put(90, new Route(PORTLAND, SEATTLE, 1, WILD, true));
        routeMap.put(91, new Route(PORTLAND, SEATTLE, 1, WILD, true));
        routeMap.put(92, new Route(RALEIGH, WASHINGTON, 2, WILD, true));
        routeMap.put(93, new Route(RALEIGH, WASHINGTON, 2, WILD, true));
        routeMap.put(94, new Route(SALT_LAKE_CITY, SAN_FRANCISCO, 5, ORANGE, true));
        routeMap.put(95, new Route(SALT_LAKE_CITY, SAN_FRANCISCO, 5, ORANGE, true));
        routeMap.put(96, new Route(SAULT_ST_MARIE, TORONTO, 2, WILD, false));
        routeMap.put(97, new Route(SAULT_ST_MARIE, WINNIPEG, 6, WILD, false));
        routeMap.put(98, new Route(SEATTLE, VANCOUVER, 1, WILD, true));
        routeMap.put(99, new Route(SEATTLE, VANCOUVER, 1, WILD, true));

        return routeMap;
    }

    private void setPointValue() {
        if (length == 1) {
            pointValue = 1;
        }
        else if (length == 2) {
            pointValue = 2;
        }
        else if (length == 3) {
            pointValue = 4;
        }
        else if (length == 4) {
            pointValue = 7;
        }
        else if (length == 5) {
            pointValue = 10;
        }
        else if (length == 6) {
            pointValue = 15;
        }
    }


}
