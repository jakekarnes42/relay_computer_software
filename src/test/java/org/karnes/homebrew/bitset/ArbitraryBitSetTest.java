package org.karnes.homebrew.bitset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class ArbitraryBitSetTest {

    @Test
    public void testFactoryMethods0() {
        ArbitraryBitSet bString = new ArbitraryBitSet("000");
        assertEquals(bString, bString);


        ArbitraryBitSet bString2 = new ArbitraryBitSet("[000]");
        assertEquals(bString2, bString);
        assertEquals(bString, bString2);

        ArbitraryBitSet bBoolean = new ArbitraryBitSet(new boolean[]{false, false, false});
        assertEquals(bBoolean, bString);
        assertEquals(bString, bBoolean);
        assertEquals(bBoolean, bString2);
        assertEquals(bString2, bBoolean);

        ArbitraryBitSet bDefault = new ArbitraryBitSet(3);
        assertEquals(bDefault, bString);
        assertEquals(bString, bDefault);
        assertEquals(bDefault, bString2);
        assertEquals(bString2, bDefault);
        assertEquals(bDefault, bBoolean);
        assertEquals(bBoolean, bDefault);

    }

    @Test
    public void testFactoryMethods1() {
        ArbitraryBitSet bString = new ArbitraryBitSet("111");
        assertEquals(bString, bString);


        ArbitraryBitSet bString2 = new ArbitraryBitSet("[111]");
        assertEquals(bString2, bString);
        assertEquals(bString, bString2);

        ArbitraryBitSet bBoolean = new ArbitraryBitSet(new boolean[]{true, true, true});
        assertEquals(bBoolean, bString);
        assertEquals(bString, bBoolean);
        assertEquals(bBoolean, bString2);
        assertEquals(bString2, bBoolean);

        ArbitraryBitSet bOnes = ArbitraryBitSet.allOnes(3);
        assertEquals(bOnes, bString);
        assertEquals(bString, bOnes);
        assertEquals(bOnes, bString2);
        assertEquals(bString2, bOnes);
        assertEquals(bOnes, bBoolean);
        assertEquals(bBoolean, bOnes);
    }


    @ParameterizedTest
    @MethodSource("allBitSetSize3")
    public void testSize(ArbitraryBitSet bitSet) {
        assertEquals(3, bitSet.size());
    }

    @ParameterizedTest
    @MethodSource("allBitSetSize3")
    public void testClone(ArbitraryBitSet bitSet) {
        ArbitraryBitSet copy = bitSet.copy();
        assertEquals(bitSet, copy);
        assertEquals(copy, bitSet);

        assertEquals(bitSet.size(), copy.size());
        assertEquals(copy.size(), bitSet.size());
    }

    @ParameterizedTest
    @MethodSource("negateArgs")
    public void testNegate(ArbitraryBitSet bitSet, ArbitraryBitSet expected) {
        ArbitraryBitSet result = bitSet.negate();
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    public void testGet(ArbitraryBitSet bitSet, int pos, boolean expected) {
        boolean result = bitSet.get(pos);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("setArgs")
    public void testGet(ArbitraryBitSet bitSet, int pos, boolean newValue, ArbitraryBitSet expected) {
        ArbitraryBitSet result = bitSet.set(pos, newValue);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("allBitSetSize3")
    public void testIteration(ArbitraryBitSet bitSet) {
        Iterator<Boolean> iterator = bitSet.iterator();
        assertTrue(iterator.hasNext());
        //Second call shouldn't be a problem
        assertTrue(iterator.hasNext());

        assertEquals(bitSet.get(0), iterator.next());
        assertEquals(bitSet.get(1), iterator.next());
        assertEquals(bitSet.get(2), iterator.next());


        assertFalse(iterator.hasNext());
        //Second call shouldn't be a problem
        assertFalse(iterator.hasNext());
    }

    @ParameterizedTest
    @MethodSource("sliceArgs")
    public void testSlice(ArbitraryBitSet input, int from, int to, ArbitraryBitSet expected) {
        FixedBitSet slice = input.getSlice(from, to);
        assertEquals(expected, slice);
    }


    private static Stream<ArbitraryBitSet> allBitSetSize3() {
        return Stream.of(
                new ArbitraryBitSet("000"),
                new ArbitraryBitSet("001"),
                new ArbitraryBitSet("010"),
                new ArbitraryBitSet("011"),
                new ArbitraryBitSet("100"),
                new ArbitraryBitSet("101"),
                new ArbitraryBitSet("110"),
                new ArbitraryBitSet("111")
        );
    }

    private static Stream<ArbitraryBitSet> allBitSetSize3Descending() {
        //Just reverse the other collection
        Iterator<ArbitraryBitSet> descendingIterator = allBitSetSize3().collect(Collectors.toCollection(ArrayDeque::new)).descendingIterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                descendingIterator, Spliterator.ORDERED), false);

    }

    private static Stream<Arguments> negateArgs() {
        return zip(allBitSetSize3(), allBitSetSize3Descending());
    }

    private static Stream<Arguments> getArgs() {
        return Stream.of(
                Arguments.of(new ArbitraryBitSet("000"), 0, false),
                Arguments.of(new ArbitraryBitSet("000"), 1, false),
                Arguments.of(new ArbitraryBitSet("000"), 2, false),

                Arguments.of(new ArbitraryBitSet("001"), 0, true),
                Arguments.of(new ArbitraryBitSet("001"), 1, false),
                Arguments.of(new ArbitraryBitSet("001"), 2, false),

                Arguments.of(new ArbitraryBitSet("010"), 0, false),
                Arguments.of(new ArbitraryBitSet("010"), 1, true),
                Arguments.of(new ArbitraryBitSet("010"), 2, false),

                Arguments.of(new ArbitraryBitSet("011"), 0, true),
                Arguments.of(new ArbitraryBitSet("011"), 1, true),
                Arguments.of(new ArbitraryBitSet("011"), 2, false),

                Arguments.of(new ArbitraryBitSet("100"), 0, false),
                Arguments.of(new ArbitraryBitSet("100"), 1, false),
                Arguments.of(new ArbitraryBitSet("100"), 2, true),

                Arguments.of(new ArbitraryBitSet("101"), 0, true),
                Arguments.of(new ArbitraryBitSet("101"), 1, false),
                Arguments.of(new ArbitraryBitSet("101"), 2, true),

                Arguments.of(new ArbitraryBitSet("110"), 0, false),
                Arguments.of(new ArbitraryBitSet("110"), 1, true),
                Arguments.of(new ArbitraryBitSet("110"), 2, true),

                Arguments.of(new ArbitraryBitSet("111"), 0, true),
                Arguments.of(new ArbitraryBitSet("111"), 1, true),
                Arguments.of(new ArbitraryBitSet("111"), 2, true)


        );
    }

    private static Stream<Arguments> setArgs() {
        return Stream.of(
                Arguments.of(new ArbitraryBitSet("000"), 0, false, new ArbitraryBitSet("000")),
                Arguments.of(new ArbitraryBitSet("000"), 1, false, new ArbitraryBitSet("000")),
                Arguments.of(new ArbitraryBitSet("000"), 2, false, new ArbitraryBitSet("000")),

                Arguments.of(new ArbitraryBitSet("000"), 0, true, new ArbitraryBitSet("001")),
                Arguments.of(new ArbitraryBitSet("000"), 1, true, new ArbitraryBitSet("010")),
                Arguments.of(new ArbitraryBitSet("000"), 2, true, new ArbitraryBitSet("100")),

                Arguments.of(new ArbitraryBitSet("111"), 0, false, new ArbitraryBitSet("110")),
                Arguments.of(new ArbitraryBitSet("111"), 1, false, new ArbitraryBitSet("101")),
                Arguments.of(new ArbitraryBitSet("111"), 2, false, new ArbitraryBitSet("011")),

                Arguments.of(new ArbitraryBitSet("111"), 0, true, new ArbitraryBitSet("111")),
                Arguments.of(new ArbitraryBitSet("111"), 1, true, new ArbitraryBitSet("111")),
                Arguments.of(new ArbitraryBitSet("111"), 2, true, new ArbitraryBitSet("111"))

        );
    }

    private static Stream<Arguments> sliceArgs() {
        return Stream.of(
                Arguments.of(new ArbitraryBitSet("0000"), 0, 4, new ArbitraryBitSet("0000")),
                Arguments.of(new ArbitraryBitSet("1100"), 0, 2, new ArbitraryBitSet("00")),
                Arguments.of(new ArbitraryBitSet("1100"), 2, 4, new ArbitraryBitSet("11")),
                Arguments.of(new ArbitraryBitSet("110011"), 2, 4, new ArbitraryBitSet("00")),
                Arguments.of(new ArbitraryBitSet("1001"), 0, 1, new ArbitraryBitSet("1")),
                Arguments.of(new ArbitraryBitSet("101"), 0, 2, new ArbitraryBitSet("01"))
        );
    }


    private static <A, B> Stream<Arguments> zip(Stream<A> arg1, Stream<B> arg2) {
        Iterator<A> iteratorA = arg1.iterator();
        Iterator<B> iteratorB = arg2.iterator();

        Iterator<Arguments> iterator = new Iterator<Arguments>() {
            @Override
            public boolean hasNext() {
                return iteratorA.hasNext() && iteratorB.hasNext();
            }

            @Override
            public Arguments next() {
                return Arguments.of(iteratorA.next(), iteratorB.next());
            }
        };

        Iterable<Arguments> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }


}
