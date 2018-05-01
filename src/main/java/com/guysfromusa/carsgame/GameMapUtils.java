package com.guysfromusa.carsgame;

import java.util.stream.Stream;

public class GameMapUtils {

    private static final String NEW_ROW_MAP_SEPARATOR = "\\\\n";

    private static final String COLUMN_MAP_SEPARATOR = ",";

    public static Integer[][] getMapMatrixFromContent(String content){
        String[] gameMapRows = content.split(NEW_ROW_MAP_SEPARATOR);

        return Stream.of(gameMapRows).map(row -> {
            String[] columnsRow = row.split(COLUMN_MAP_SEPARATOR);
            return Stream.of(columnsRow).map(Integer::valueOf).toArray(Integer[]::new);
        }).toArray(Integer[][]::new);
    }


}
