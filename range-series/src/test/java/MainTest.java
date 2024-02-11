import com.range.Range;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainTest {
    // Mock
    Range rangeClassMock = mock(Range.class);
    private static final Logger logger = Logger.getLogger(MainTest.class.getName());

    @Test
    public void test_convert_arguments_in_ranges() {
        // Input simulation
        String stringToConvertInArray = "A -> {0, 6}, b->{5,7}";

        List<com.range.entities.Range> ranges = rangeClassMock.convertArgumentsInRanges(stringToConvertInArray);

        Assert.assertNotNull(ranges);

        com.range.entities.Range firstRange = new com.range.entities.Range();
        firstRange.setUpperBoundary(6);
        firstRange.setLowerBoundary(0);
        firstRange.setLabel("A");

        com.range.entities.Range secondRange = new com.range.entities.Range();
        secondRange.setUpperBoundary(7);
        secondRange.setLowerBoundary(5);
        secondRange.setLabel("b");

        Assert.assertEquals(firstRange, ranges.get(0));
        Assert.assertEquals(secondRange, ranges.get(1));
    }

    @Test
    public void test_matching_labels_multiple_ranges() {

        List<com.range.entities.Range> ranges = new ArrayList<>();
        com.range.entities.Range firstRange = new com.range.entities.Range();
        firstRange.setUpperBoundary(6);
        firstRange.setLowerBoundary(0);
        firstRange.setLabel("A");

        com.range.entities.Range secondRange = new com.range.entities.Range();
        secondRange.setUpperBoundary(1000);
        secondRange.setLowerBoundary(5);
        secondRange.setLabel("B");

        com.range.entities.Range thirdRange = new com.range.entities.Range();
        thirdRange.setUpperBoundary(5);
        thirdRange.setLowerBoundary(2);
        thirdRange.setLabel("C");

        ranges.add(firstRange);
        ranges.add(secondRange);

        rangeClassMock.ranges = ranges;

        Assert.assertEquals(2, rangeClassMock.matchingLabels(5).size());
        Assert.assertEquals(firstRange, ranges.get(0));
        Assert.assertEquals(secondRange, ranges.get(1));
    }

    @Test
    public void test_bad_argument_input() {
        // Input simulation
        String stringToConvertInArray = randomStringGenerator();

        List<com.range.entities.Range> ranges = rangeClassMock.convertArgumentsInRanges(stringToConvertInArray);

        Assert.assertNotNull(ranges);
        Assert.assertEquals(0, ranges.size());
    }

    @Test
    public void test_performance() {
        Random random = new Random();
        List<com.range.entities.Range> ranges = generateMultipleRanges(20000000);
        int firstNumber = random.nextInt(10001);
        rangeClassMock.setRanges(ranges);
        long start = System.currentTimeMillis();
        rangeClassMock.matchingLabels(firstNumber);
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    private static String randomStringGenerator() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // Generate the random string
        Random random = new Random();

        String randomString = IntStream.range(0, 100)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(String::valueOf)
                .collect(Collectors.joining());

        return randomString;
    }

    private static String generateRandomValidNameForRange() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();

        String randomName = IntStream.range(0, 10)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(String::valueOf)
                .collect(Collectors.joining());

        return randomName;
    }

    public static int[] generateRandomNumber() {
        Random random = new Random();
        int lowerBoundary = random.nextInt(10001);
        int upperBoundary = random.nextInt(10001);

        while (lowerBoundary < upperBoundary) {
             lowerBoundary = random.nextInt(10001);
             upperBoundary = random.nextInt(10001);
        }

        return new int[]{lowerBoundary, upperBoundary};
    }

    private static List<com.range.entities.Range> generateMultipleRanges(int numberOfRanges) {

        List<com.range.entities.Range> rangelist = new ArrayList<>();
        for (int i = 1; i <= numberOfRanges;i++) {
            com.range.entities.Range range = new com.range.entities.Range();
            range.setLabel(generateRandomValidNameForRange());
            range.setUpperBoundary(generateRandomNumber()[0]);
            range.setLowerBoundary(generateRandomNumber()[1]);
            rangelist.add(range);
        }

        return rangelist;
    }
}
