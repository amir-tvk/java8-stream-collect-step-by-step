package demo.stream.tomap;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.lang.System.out;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toMap;


public class Example {
    public static void main(String[] args) {
        List<Album>  listOfAlbums = List.of(
                new Album("Moein", "Khatereh", 1000),
                new Album("Ebi", "Asal", 2000),
                new Album("Haydeh", "Golhaye Ghorbat", 2000),
                new Album("Ghomeyshi", "Baaroon", 1200));

        // Simple toMap
        Map<String, Album> collectedMap = listOfAlbums.stream().collect(toMap(e -> e.getArtist(), e -> e));
        out.println(collectedMap.toString());

        // Same toMap but Using method reference and jdk helper methods
        collectedMap = listOfAlbums.stream().collect(toMap(Album::getArtist, Function.identity()));
        out.println(collectedMap.toString());

        // Handling conflicts by selecting first
        Map<Integer, Object> mergedMap = listOfAlbums.stream().collect(toMap(Album::getSale, e -> e, (existing, replace) -> existing));
        out.println(mergedMap.toString());

        // Handling conflicts by selecting new
        mergedMap = listOfAlbums.stream().collect(toMap(Album::getSale, Function.identity(), (existing, replace) -> replace));
        out.println(mergedMap.toString());

        // Handling conflict with more complex logic, select best sale
        var listOfAlbumsWithDuplicateArtist = new ArrayList<Album>(listOfAlbums);
        listOfAlbumsWithDuplicateArtist.add(new Album("Haydeh", "Gharibeh", 2200));
        Map<String, Album> mergeByMAx = listOfAlbumsWithDuplicateArtist.stream().collect(toMap(Album::getArtist, e -> e, BinaryOperator.maxBy(Comparator.comparing(Album::getSale))));
        out.println(mergeByMAx.toString());

        // group by ==> to have a list (no conflict)
        Map<String, List<Album>> collectGrouping = listOfAlbumsWithDuplicateArtist.stream().collect(groupingBy(Album::getArtist));
        out.println(collectGrouping.toString());

        // group by ==> count
        Map<String, Long> collectToCount = listOfAlbumsWithDuplicateArtist.stream().collect(groupingBy(Album::getArtist, counting()));
        out.println(collectToCount);

        Long count = listOfAlbumsWithDuplicateArtist.stream().collect(counting());
        out.println(count);

        IntSummaryStatistics totalSail = listOfAlbumsWithDuplicateArtist.stream().collect(summarizingInt(Album::getSale));
        out.println(totalSail);

    }
}
