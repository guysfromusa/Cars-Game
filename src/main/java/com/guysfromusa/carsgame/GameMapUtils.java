package com.guysfromusa.carsgame;

import io.vavr.Tuple2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import static io.vavr.API.Tuple;

public class GameMapUtils {

    private static final String NEW_ROW_MAP_SEPARATOR = "\\\\n|\n";

    private static final String COLUMN_MAP_SEPARATOR = ",";

    public static Integer[][] getMapMatrixFromContent(String content) {
        String[] gameMapRows = content.split(NEW_ROW_MAP_SEPARATOR);

        return Stream.of(gameMapRows).map(row -> {
            String[] columnsRow = row.split(COLUMN_MAP_SEPARATOR);
            return Stream.of(columnsRow).map(Integer::valueOf).toArray(Integer[]::new);
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
        Boolean[][] visited = Stream.of(map)
                .map(row -> Stream.of(row).map(v -> v != 0).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        // queue of point - tuple of indexes (i,j) to visit
        Queue<Tuple2<Integer, Integer>> queue = new LinkedList<>();

        // find root: first valid point on map
        Tuple2<Integer, Integer> root = findRoot(map);
        if (root == null) {
            return false;
        }
        queue.offer(root);

        while (!queue.isEmpty()) {
            Tuple2<Integer, Integer> indexes = queue.poll();
            int i = indexes._1;
            int j = indexes._2;

            visited[i][j] = true;

            // add not visited neighbor
            addNeighbor(i, j + 1, map, visited, queue);
            addNeighbor(i, j - 1, map, visited, queue);
            addNeighbor(i + 1, j, map, visited, queue);
            addNeighbor(i - 1, j, map, visited, queue);
        }

        // all points should be visited
        return Stream.of(visited)
                .flatMap(Stream::of)
                .allMatch(v -> v);
    }

    private static Tuple2<Integer, Integer> findRoot(Integer[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) {
                    return Tuple(i, j);
                }
            }
        }
        return null;
    }

    private static void addNeighbor(int i, int j, Integer[][] map, Boolean[][] visited, Queue<Tuple2<Integer, Integer>> queue) {
        if (isOnMap(i, j, map) && !visited[i][j]) {
            queue.offer(Tuple(i, j));
        }
    }

    private static boolean isOnMap(int i, int j, Integer[][] map) {
        return i >= 0 && i < map.length && j >= 0 && j < map[i].length;
    }
}
