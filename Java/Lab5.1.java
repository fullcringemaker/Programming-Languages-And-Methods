-------------------------------------------------- BigIntegerOperations.java --------------------------------------------------
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BigIntegerOperations {
    private int numBits;
    private int maxSize;
    public BigIntegerOperations(int numBits, int maxSize) {
        this.numBits = numBits;
        this.maxSize = maxSize;
    }
    public Stream<BigInteger> AllDigitsStreamGenerator() {
        Stream<BigInteger> infiniteRandomIntegers = Stream.generate(() -> {
            return new BigInteger(numBits, new Random());
        });
        Stream<BigInteger> filteredIntegers = infiniteRandomIntegers.filter(this::AllDigitsChecker);
        Stream<BigInteger> limitedIntegers = filteredIntegers.limit(maxSize);
        return limitedIntegers;
    }
    private boolean AllDigitsChecker(BigInteger number) {
        String numStr = number.toString();
        return "0123456789".chars().allMatch(c -> numStr.indexOf(c) != -1);
    }
    public Optional<BigInteger> MaxSum(List<BigInteger> list) {
        return list.stream().max(this::compareByDigitSum);
    }
    private int compareByDigitSum(BigInteger o1, BigInteger o2) {
        return DigitSum(o1).compareTo(DigitSum(o2));
    }
    private Integer DigitSum(BigInteger number) {
        return number.toString().chars()
                .map(Character::getNumericValue)
                .sum();
    }
    public List<BigInteger> sortByRangeLength(List<BigInteger> list) {
        Comparator<BigInteger> lengthComparator = Comparator.comparingInt(o -> o.toString().length());
        Stream<BigInteger> stream = list.stream();
        Stream<BigInteger> sortedStream = stream.sorted(lengthComparator);
        List<BigInteger> sortedList = sortedStream.collect(Collectors.toList());
        return sortedList;
    }
}


-------------------------------------------------- Test.java --------------------------------------------------
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        int numBits = 50;
        int maxSize = 25;
        BigIntegerOperations ops = new BigIntegerOperations(numBits, maxSize);
        List<BigInteger> list = ops.AllDigitsStreamGenerator().collect(Collectors.toList());
        System.out.println("Sorted numbers by length");
        List<BigInteger> sortedList = ops.sortByRangeLength(list);
        sortedList.forEach(System.out::println);
        System.out.println("Maximum of the numbers by the sum of digits");
        Optional<BigInteger> maxNum = ops.MaxSum(sortedList);
        maxNum.ifPresent(System.out::println);
    }
}
