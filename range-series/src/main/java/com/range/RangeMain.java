package com.range;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RangeMain {

    // Logger definition
    private static final Logger logger = Logger.getLogger(RangeMain.class.getName());


    // ranges variable accessible to the whole Main class
    @Getter
    @Setter
    public static List<com.range.entities.Range> ranges;


    // item variable accessible to the whole Main class
    @Getter
    @Setter
    public static int item;

    // Regex to identify a range.
    private static final String regexLabel = "([a-zA-Z]+)\\s*->\\s*\\{(\\d+),\\s*(\\d+)\\}";

    /**
     * Executable
     */
    public static void main(String[] args) {

        // ========================================= Input management ========================================= //
        Scanner scanner = new Scanner(System.in);

        // Labels
        System.out.println("Please enter your labels with the following patterns, A -> {x, y}, B -> {u, v} .... :");
        String userArgsLabelsInput = scanner.nextLine();

        if (StringUtils.isAllBlank(userArgsLabelsInput)) {
            throw new IllegalArgumentException("Your range list is weather null or empty.");
        }

        // Item
        System.out.println("Please enter your item (must be an integer) : ");
        String userItem = scanner.nextLine();

        try {
            setItem(Integer.valueOf(userItem));
        } catch (NumberFormatException ex) {
            System.out.println("Your input must be an integer");
            throw ex;
        }

        scanner.close();

        // ========================================= Treatment ========================================= //
        logger.info("Start processing your information");

        setRanges(convertArgumentsInRanges(userArgsLabelsInput));
        // print out solution with marchingLabels
        System.out.println("The item is present in the following range(s) : " + matchingLabels(getItem()));

        logger.info("End processing");
    }

    /**
     * Convert the input arguments in Range objects.
     * @param args : Inputs from the user.
     * @return List of Range objects.
     */
    public static List<com.range.entities.Range> convertArgumentsInRanges(String args) {
        // Output instance
        List<com.range.entities.Range> ranges = List.of();
        // Create a Matcher to find Ranges pattern in the input args
        // Regular expression definition
        Pattern pattern = Pattern.compile(regexLabel);
        Matcher matcher = pattern.matcher(args);

        while (matcher.find()) {
            // Get the letter which identify the label in the first group inside the matcher
            String rangeLabel = matcher.group(1);
            // Get the lower boundary in the second group inside the matcher
            int rangeLowerBoundary = Integer.parseInt(matcher.group(2));
            // Get the upper boundary in the third group inside the matcher
            int rangeUpperBoundary = Integer.parseInt(matcher.group(3));

            // Range object instance
            com.range.entities.Range range = new com.range.entities.Range(rangeLabel, rangeLowerBoundary, rangeUpperBoundary);
            ranges.add(range);
        }

        return ranges;
    }

    /**
     * Get a list of all the label's ranges containing item in their range.
     * @param item : item we are working with.
     * @return A list of String containing the name of the ranges.
     */
    public static List<String> matchingLabels(int item) {
        return getRanges()
                // NEW : Parallel Stream
                .parallelStream()
                .filter(range -> range != null && range.isItemInBoundaries(item))
                .map(com.range.entities.Range::label)
                // NEW : toList from JAVA 17
                .toList();
    }
}