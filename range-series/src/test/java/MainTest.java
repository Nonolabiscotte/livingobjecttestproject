import com.range.RangeMain;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainTest {
    // Mock
    RangeMain rangeMainClassMock = mock(RangeMain.class);

    @Test
    public void test_bad_argument_input() {
        // Input simulation
        String stringToConvertInArray = randomStringGenerator();

        List<com.range.entities.Range> ranges = rangeMainClassMock.convertArgumentsInRanges(stringToConvertInArray);

        Assert.assertNotNull(ranges);
        Assert.assertEquals(0, ranges.size());
    }

    @Test
    public void test_performance() {
        Random random = new Random();
        List<com.range.entities.Range> ranges = generateMultipleRanges(10000000);
        int firstNumber = random.nextInt(10001);
        rangeMainClassMock.setRanges(ranges);
        long start = System.currentTimeMillis();
        rangeMainClassMock.matchingLabels(firstNumber);
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
        for (int i = 1; i <= numberOfRanges; i++) {
            com.range.entities.Range range = new com.range.entities.Range(
                    generateRandomValidNameForRange(),
                    generateRandomNumber()[0],
                    generateRandomNumber()[1]);

            rangelist.add(range);
        }

        return rangelist;
    }
}
