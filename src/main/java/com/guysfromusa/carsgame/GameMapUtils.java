package com.guysfromusa.carsgame;

import io.vavr.Tuple2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import static io.vavr.API.Tuple;

public class GameMapUtils {

    private static final String NEW_ROW_MAP_SEPARATOR = "\\\\n|\n";

    private static final String COLUMN_MAP_SEPARATOR = ",";

    public static Integer[][] getMapMatrixContent(String content) {
        String[] gameMapRows = content.split(NEW_ROW_MAP_SEPARATOR);

        return Stream.of(gameMapRows).map(row -> {
            String[] columnsRow = row.split(COLUMN_MAP_SEPARATOR);
            return Stream.of(columnsRow)
                    .map(v -> "0".equals(v) ? 0 : 1)
                    .toArray(Integer[]::new);
        }).toArray(Integer[][]::new);
    }

    /**
     * Check if each point is reachable from all other points on map.
     * (Solving connected components problem for undirected graph with BFS)
     *
     * @param map N x N array
     * @return true if for each point is reachable from all other
     */
    public static boolean isReachable(Integer[][] map) {
        // create visited array (mark walls as visited)
        // TODO: change map & visited to primitive arrays
        // TODO: cleanup: change access to map[x][y] to map[y][x] to be consistent with rest of the code
        Boolean[][] visited = Stream.of(map)
                .map(row -> Stream.of(row).map(v -> v == 0).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        // queue of point to visit - tuple of indexes (x,y)
        Queue<Tuple2<Integer, Integer>> queue = new LinkedList<>();

        // find root: any valid point on map
        Tuple2<Integer, Integer> root = findValidPoint(map);
        if (root == null) {
            return false;
        }
        queue.offer(root);
        visited[root._1][root._2] = true;

        // Breadth-first search
        while (!queue.isEmpty()) {
            Tuple2<Integer, Integer> indexes = queue.poll();
            int x = indexes._1;
            int y = indexes._2;

            // add not visited neighbors
            addNeighbor(x, y + 1, map, visited, queue);
            addNeighbor(x, y - 1, map, visited, queue);
            addNeighbor(x + 1, y, map, visited, queue);
            addNeighbor(x - 1, y, map, visited, queue);
        }

        // all points should be visited
        return Stream.of(visited)
                .flatMap(Stream::of)
                .allMatch(v -> v);
    }

    private static Tuple2<Integer, Integer> findValidPoint(Integer[][] map) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y] != 0) {
                    return Tuple(x, y);
                }
            }
        }
        return null;
    }

    private static void addNeighbor(int x, int y, Integer[][] map, Boolean[][] visited, Queue<Tuple2<Integer, Integer>> queue) {
        if (isOnMap(x, y, map) && !visited[x][y]) {
            queue.offer(Tuple(x, y));
            visited[x][y] = true;
        }
    }

    private static boolean isOnMap(int x, int y, Integer[][] map) {
        return isValidIndex(x, map) && isValidIndex(y, map[x]);
    }

    private static <T> boolean isValidIndex(int index, T[] array) {
        return index >= 0 && index < array.length;
    }


}
