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

public class FixedBitSetTest {

    @Test
    public void testFactoryMethods0() {
        FixedBitSet bString = new FixedBitSet("000");
        assertEquals(bString, bString);


        FixedBitSet bString2 = new FixedBitSet("[000]");
        assertEquals(bString2, bString);
        assertEquals(bString, bString2);

        FixedBitSet bBoolean = new FixedBitSet(new boolean[]{false, false, false});
        assertEquals(bBoolean, bString);
        assertEquals(bString, bBoolean);
        assertEquals(bBoolean, bString2);
        assertEquals(bString2, bBoolean);

        FixedBitSet bDefault = new FixedBitSet(3);
        assertEquals(bDefault, bString);
        assertEquals(bString, bDefault);
        assertEquals(bDefault, bString2);
        assertEquals(bString2, bDefault);
        assertEquals(bDefault, bBoolean);
        assertEquals(bBoolean, bDefault);

    }

    @Test
    public void testFactoryMethods1() {
        FixedBitSet bString = new FixedBitSet("111");
        assertEquals(bString, bString);


        FixedBitSet bString2 = new FixedBitSet("[111]");
        assertEquals(bString2, bString);
        assertEquals(bString2, bString);

        FixedBitSet bBoolean = new FixedBitSet(new boolean[]{true, true, true});
        assertEquals(bBoolean, bString);
        assertEquals(bBoolean, bString);
        assertEquals(bBoolean, bString2);
        assertEquals(bBoolean, bString2);
    }

    @ParameterizedTest
    @MethodSource("allBitSetSize3")
    public void testSize(FixedBitSet bitSet) {
        assertEquals(3, bitSet.size());
    }

    @ParameterizedTest
    @MethodSource("allBitSetSize3")
    public void testClone(FixedBitSet bitSet) {
        FixedBitSet clone = bitSet.clone();
        assertEquals(bitSet, clone);
        assertEquals(clone, bitSet);

        assertEquals(bitSet.size(), clone.size());
        assertEquals(clone.size(), bitSet.size());
    }

    @ParameterizedTest
    @MethodSource("negateArgs")
    public void testNegate(FixedBitSet bitSet, FixedBitSet expected) {
        FixedBitSet result = bitSet.negate();
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    public void testGet(FixedBitSet bitSet, int pos, boolean expected) {
        boolean result = bitSet.get(pos);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("setArgs")
    public void testGet(FixedBitSet bitSet, int pos, boolean newValue, FixedBitSet expected) {
        FixedBitSet result = bitSet.set(pos, newValue);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("allBitSetSize3")
    public void testIteration(FixedBitSet bitSet) {
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


    private static Stream<FixedBitSet> allBitSetSize3() {
        return Stream.of(
                new FixedBitSet("000"),
                new FixedBitSet("001"),
                new FixedBitSet("010"),
                new FixedBitSet("011"),
                new FixedBitSet("100"),
                new FixedBitSet("101"),
                new FixedBitSet("110"),
                new FixedBitSet("111")
        );
    }

    private static Stream<FixedBitSet> allBitSetSize3Descending() {
        //Just reverse the other collection
        Iterator<FixedBitSet> descendingIterator = allBitSetSize3().collect(Collectors.toCollection(ArrayDeque::new)).descendingIterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                descendingIterator, Spliterator.ORDERED), false);

    }

    private static Stream<Arguments> negateArgs() {
        return zip(allBitSetSize3(), allBitSetSize3Descending());
    }

    private static Stream<Arguments> getArgs() {
        return Stream.of(
                Arguments.of(new FixedBitSet("000"), 0, false),
                Arguments.of(new FixedBitSet("000"), 1, false),
                Arguments.of(new FixedBitSet("000"), 2, false),

                Arguments.of(new FixedBitSet("001"), 0, true),
                Arguments.of(new FixedBitSet("001"), 1, false),
                Arguments.of(new FixedBitSet("001"), 2, false),

                Arguments.of(new FixedBitSet("010"), 0, false),
                Arguments.of(new FixedBitSet("010"), 1, true),
                Arguments.of(new FixedBitSet("010"), 2, false),

                Arguments.of(new FixedBitSet("011"), 0, true),
                Arguments.of(new FixedBitSet("011"), 1, true),
                Arguments.of(new FixedBitSet("011"), 2, false),

                Arguments.of(new FixedBitSet("100"), 0, false),
                Arguments.of(new FixedBitSet("100"), 1, false),
                Arguments.of(new FixedBitSet("100"), 2, true),

                Arguments.of(new FixedBitSet("101"), 0, true),
                Arguments.of(new FixedBitSet("101"), 1, false),
                Arguments.of(new FixedBitSet("101"), 2, true),

                Arguments.of(new FixedBitSet("110"), 0, false),
                Arguments.of(new FixedBitSet("110"), 1, true),
                Arguments.of(new FixedBitSet("110"), 2, true),

                Arguments.of(new FixedBitSet("111"), 0, true),
                Arguments.of(new FixedBitSet("111"), 1, true),
                Arguments.of(new FixedBitSet("111"), 2, true)


        );
    }

    private static Stream<Arguments> setArgs() {
        return Stream.of(
                Arguments.of(new FixedBitSet("000"), 0, false, new FixedBitSet("000")),
                Arguments.of(new FixedBitSet("000"), 1, false, new FixedBitSet("000")),
                Arguments.of(new FixedBitSet("000"), 2, false, new FixedBitSet("000")),

                Arguments.of(new FixedBitSet("000"), 0, true, new FixedBitSet("001")),
                Arguments.of(new FixedBitSet("000"), 1, true, new FixedBitSet("010")),
                Arguments.of(new FixedBitSet("000"), 2, true, new FixedBitSet("100")),

                Arguments.of(new FixedBitSet("111"), 0, false, new FixedBitSet("110")),
                Arguments.of(new FixedBitSet("111"), 1, false, new FixedBitSet("101")),
                Arguments.of(new FixedBitSet("111"), 2, false, new FixedBitSet("011")),

                Arguments.of(new FixedBitSet("111"), 0, true, new FixedBitSet("111")),
                Arguments.of(new FixedBitSet("111"), 1, true, new FixedBitSet("111")),
                Arguments.of(new FixedBitSet("111"), 2, true, new FixedBitSet("111"))

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
