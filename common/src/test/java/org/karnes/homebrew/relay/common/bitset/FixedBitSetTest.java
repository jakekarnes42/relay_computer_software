package org.karnes.homebrew.relay.common.bitset;

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
        assertEquals(bString, bString2);

        FixedBitSet bBoolean = new FixedBitSet(new boolean[]{true, true, true});
        assertEquals(bBoolean, bString);
        assertEquals(bString, bBoolean);
        assertEquals(bBoolean, bString2);
        assertEquals(bString2, bBoolean);

        FixedBitSet bOnes = FixedBitSet.allOnes(3);
        assertEquals(bOnes, bString);
        assertEquals(bString, bOnes);
        assertEquals(bOnes, bString2);
        assertEquals(bString2, bOnes);
        assertEquals(bOnes, bBoolean);
        assertEquals(bBoolean, bOnes);
    }

    @Test
    public void testBitStringConstructors() {
        assertEquals(new FixedBitSet(new boolean[]{true, false, true}), new FixedBitSet("101"));

        assertEquals(new FixedBitSet(new boolean[]{true, false, true, false, true, false}), new FixedBitSet("101 010"));

        assertEquals(new FixedBitSet(new boolean[]{true, true}), new FixedBitSet("1abc1"));

        assertEquals(new FixedBitSet(new boolean[]{true, true, true, false, false, false, true, true, true}), new FixedBitSet("111_000_111"));

        assertEquals(new FixedBitSet(new boolean[]{false, true, false}), new FixedBitSet("[010]"));
    }

    @Test
    public void testConstructorFromToString() {
        boolean[] values = new boolean[]{true, false, true};
        FixedBitSet fixedBitSet = new FixedBitSet(values);
        assertEquals(fixedBitSet, new FixedBitSet(fixedBitSet.toString()));

        values = new boolean[]{true, false, true, false, true, false};
        fixedBitSet = new FixedBitSet(values);
        assertEquals(fixedBitSet, new FixedBitSet(fixedBitSet.toString()));


        values = new boolean[]{true, true,};
        fixedBitSet = new FixedBitSet(values);
        assertEquals(fixedBitSet, new FixedBitSet(fixedBitSet.toString()));

    }

    @ParameterizedTest
    @MethodSource("allBitSetSize3")
    public void testSize(FixedBitSet bitSet) {
        assertEquals(3, bitSet.size());
    }

    @ParameterizedTest
    @MethodSource("allBitSetSize3")
    public void testClone(FixedBitSet bitSet) {
        FixedBitSet copy = bitSet.copy();
        assertEquals(bitSet, copy);
        assertEquals(copy, bitSet);

        assertEquals(bitSet.size(), copy.size());
        assertEquals(copy.size(), bitSet.size());
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

    @ParameterizedTest
    @MethodSource("sliceArgs")
    public void testSlice(FixedBitSet input, int from, int to, FixedBitSet expected) {
        FixedBitSet slice = input.getSlice(from, to);
        assertEquals(expected, slice);
    }

    @Test
    public void testBytes() {
        byte zero = (byte) 0;
        FixedBitSet zeroBitSet = FixedBitSet.fromByte(zero);
        zeroBitSet.forEach(val -> assertFalse(val));
        assertEquals(zero, zeroBitSet.toByte());

        byte one = (byte) 1;
        FixedBitSet oneBitSet = FixedBitSet.fromByte(one);
        assertTrue(oneBitSet.get(0));
        for (int i = 1; i < oneBitSet.size(); i++) {
            assertFalse(oneBitSet.get(i));
        }
        assertEquals(one, oneBitSet.toByte());

        byte minusOne = (byte) -1;
        FixedBitSet minusOneBitSet = FixedBitSet.fromByte(minusOne);
        minusOneBitSet.forEach(val -> assertTrue(val));
        assertEquals(minusOne, minusOneBitSet.toByte());


        byte min = Byte.MIN_VALUE;
        FixedBitSet minBitSet = FixedBitSet.fromByte(min);
        assertTrue(minBitSet.get(7));
        for (int i = 0; i < minBitSet.size() - 1; i++) {
            assertFalse(minBitSet.get(i));
        }
        assertEquals(min, minBitSet.toByte());


        byte max = Byte.MAX_VALUE;
        FixedBitSet maxBitSet = FixedBitSet.fromByte(max);
        assertFalse(maxBitSet.get(7));
        for (int i = 0; i < maxBitSet.size() - 1; i++) {
            assertTrue(maxBitSet.get(i));
        }
        assertEquals(max, maxBitSet.toByte());
    }

    @Test
    public void testMyShorts() {
        short zero = (short) 0;
        FixedBitSet zeroBitSet = FixedBitSet.fromShort(zero);
        zeroBitSet.forEach(val -> assertFalse(val));
        assertEquals(zero, zeroBitSet.toShort());

        short one = (short) 1;
        FixedBitSet oneBitSet = FixedBitSet.fromShort(one);
        assertTrue(oneBitSet.get(0));
        for (int i = 1; i < oneBitSet.size(); i++) {
            assertFalse(oneBitSet.get(i));
        }
        assertEquals(one, oneBitSet.toShort());

        short minusOne = (short) -1;
        FixedBitSet minusOneBitSet = FixedBitSet.fromShort(minusOne);
        minusOneBitSet.forEach(val -> assertTrue(val));
        assertEquals(minusOne, minusOneBitSet.toShort());


        short min = Short.MIN_VALUE;
        FixedBitSet minBitSet = FixedBitSet.fromShort(min);
        assertTrue(minBitSet.get(15));
        for (int i = 0; i < minBitSet.size() - 1; i++) {
            assertFalse(minBitSet.get(i));
        }
        assertEquals(min, minBitSet.toShort());


        short max = Short.MAX_VALUE;
        FixedBitSet maxBitSet = FixedBitSet.fromShort(max);
        assertFalse(maxBitSet.get(15));
        for (int i = 0; i < maxBitSet.size() - 1; i++) {
            assertTrue(maxBitSet.get(i));
        }
        assertEquals(max, maxBitSet.toShort());
    }

    @Test
    public void testChars() {
        char zero = (char) 0;
        FixedBitSet zeroBitSet = FixedBitSet.fromChar(zero);
        zeroBitSet.forEach(val -> assertFalse(val));
        assertEquals(zero, zeroBitSet.toChar());

        char one = (char) 1;
        FixedBitSet oneBitSet = FixedBitSet.fromChar(one);
        assertTrue(oneBitSet.get(0));
        for (int i = 1; i < oneBitSet.size(); i++) {
            assertFalse(oneBitSet.get(i));
        }
        assertEquals(one, oneBitSet.toChar());


        char min = Character.MIN_VALUE;
        FixedBitSet minBitSet = FixedBitSet.fromChar(min);
        minBitSet.forEach(val -> assertFalse(val));
        assertEquals(min, minBitSet.toChar());


        char max = Character.MAX_VALUE;
        FixedBitSet maxBitSet = FixedBitSet.fromChar(max);
        maxBitSet.forEach(val -> assertTrue(val));
        assertEquals(max, maxBitSet.toChar());
    }

    @Test
    public void testShortCharExchange() {
        FixedBitSet zeroBitSet = new FixedBitSet(Short.SIZE);
        assertEquals((short) 0, zeroBitSet.toShort());
        assertEquals((char) 0, zeroBitSet.toChar());

        FixedBitSet oneBitSet = new FixedBitSet("0000 0000 0000 0001");
        assertEquals((short) 1, oneBitSet.toShort());
        assertEquals((char) 1, oneBitSet.toChar());

        FixedBitSet onesBitSet = new FixedBitSet("1111 1111 1111 1111");
        assertEquals((short) -1, onesBitSet.toShort());
        assertEquals(Character.MAX_VALUE, onesBitSet.toChar());

        FixedBitSet shortMinBitSet = new FixedBitSet("1000 0000 0000 0000");
        assertEquals(Short.MIN_VALUE, shortMinBitSet.toShort());
        assertEquals((char) 32768, shortMinBitSet.toChar());

        FixedBitSet shortMaxBitSet = new FixedBitSet("0111 1111 1111 1111");
        assertEquals(Short.MAX_VALUE, shortMaxBitSet.toShort());
        assertEquals((char) 32767, shortMaxBitSet.toChar());
    }

    @Test
    public void testCopy() {
        FixedBitSet zeroBitSet = new FixedBitSet(Short.SIZE);
        assertEquals((short) 0, zeroBitSet.copy().toShort());
        assertEquals((char) 0, zeroBitSet.copy().toChar());
        assertEquals(zeroBitSet, zeroBitSet.copy());

        //If we change the copy, the original should not be changed
        FixedBitSet negated = zeroBitSet.copy().negate();
        assertNotEquals(negated, zeroBitSet);
        assertEquals((short) 0, zeroBitSet.toShort());
        assertEquals((char) 0, zeroBitSet.toChar());
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

    private static Stream<Arguments> sliceArgs() {
        return Stream.of(
                Arguments.of(new FixedBitSet("0000"), 0, 4, new FixedBitSet("0000")),
                Arguments.of(new FixedBitSet("1100"), 0, 2, new FixedBitSet("00")),
                Arguments.of(new FixedBitSet("1100"), 2, 4, new FixedBitSet("11")),
                Arguments.of(new FixedBitSet("110011"), 2, 4, new FixedBitSet("00")),
                Arguments.of(new FixedBitSet("1001"), 0, 1, new FixedBitSet("1")),
                Arguments.of(new FixedBitSet("101"), 0, 2, new FixedBitSet("01"))
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
