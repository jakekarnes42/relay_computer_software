package org.karnes.homebrew.relay.common.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUtilTest {

    @Test
    void zip() {
        Stream<Arguments> zipped = TestUtil.zip(
                Stream.of("A", "B", "C"),
                Stream.of("X", "Y", "Z")

        );
        List<Arguments> pairs = zipped.collect(Collectors.toList());
        assertEquals(3, pairs.size());

        assertEquals("A", pairs.get(0).get()[0]);
        assertEquals("X", pairs.get(0).get()[1]);

        assertEquals("B", pairs.get(1).get()[0]);
        assertEquals("Y", pairs.get(1).get()[1]);

        assertEquals("C", pairs.get(2).get()[0]);
        assertEquals("Z", pairs.get(2).get()[1]);

    }

    @Test
    void zipAShorter() {
        Stream<Arguments> zipped = TestUtil.zip(
                Stream.of("A", "B"),
                Stream.of("X", "Y", "Z")

        );
        List<Arguments> pairs = zipped.collect(Collectors.toList());
        assertEquals(2, pairs.size());

        assertEquals("A", pairs.get(0).get()[0]);
        assertEquals("X", pairs.get(0).get()[1]);

        assertEquals("B", pairs.get(1).get()[0]);
        assertEquals("Y", pairs.get(1).get()[1]);
    }

    @Test
    void zipBShorter() {
        Stream<Arguments> zipped = TestUtil.zip(
                Stream.of("A", "B", "C"),
                Stream.of("X", "Y")

        );
        List<Arguments> pairs = zipped.collect(Collectors.toList());
        assertEquals(2, pairs.size());

        assertEquals("A", pairs.get(0).get()[0]);
        assertEquals("X", pairs.get(0).get()[1]);

        assertEquals("B", pairs.get(1).get()[0]);
        assertEquals("Y", pairs.get(1).get()[1]);

    }


    @Test
    void combinationsBase0() {
        List<Arguments> combinations = TestUtil.cartesianProduct();
        //Expecting an empty list
        assertEquals(0, combinations.size());

    }

    @Test
    void combinationsBase1() {
        List<Arguments> combinations = TestUtil.cartesianProduct(
                List.of("1", "2", "3")
        );
        //Expecting an 3 lists
        assertEquals(3, combinations.size());

        assertEquals("1", combinations.get(0).get()[0]);
        assertEquals("2", combinations.get(1).get()[0]);
        assertEquals("3", combinations.get(2).get()[0]);

    }

    @Test
    void combinations2x3() {
        List<Arguments> combinations = TestUtil.cartesianProduct(
                List.of("1", "2", "3"),
                List.of("A", "B", "C"));
        assertEquals(9, combinations.size());

        assertEquals("1", combinations.get(0).get()[0]);
        assertEquals("A", combinations.get(0).get()[1]);

        assertEquals("1", combinations.get(1).get()[0]);
        assertEquals("B", combinations.get(1).get()[1]);

        assertEquals("1", combinations.get(2).get()[0]);
        assertEquals("C", combinations.get(2).get()[1]);

        assertEquals("2", combinations.get(3).get()[0]);
        assertEquals("A", combinations.get(3).get()[1]);

        assertEquals("2", combinations.get(4).get()[0]);
        assertEquals("B", combinations.get(4).get()[1]);

        assertEquals("2", combinations.get(5).get()[0]);
        assertEquals("C", combinations.get(5).get()[1]);

        assertEquals("3", combinations.get(6).get()[0]);
        assertEquals("A", combinations.get(6).get()[1]);

        assertEquals("3", combinations.get(7).get()[0]);
        assertEquals("B", combinations.get(7).get()[1]);

        assertEquals("3", combinations.get(8).get()[0]);
        assertEquals("C", combinations.get(8).get()[1]);
    }

    @Test
    void combinationsMixObjects() {
        List<Arguments> combinations = TestUtil.cartesianProduct(
                List.of(1, 2, 3),
                List.of("A", "B", "C"));
        assertEquals(9, combinations.size());

        assertEquals(1, combinations.get(0).get()[0]);
        assertEquals("A", combinations.get(0).get()[1]);

        assertEquals(1, combinations.get(1).get()[0]);
        assertEquals("B", combinations.get(1).get()[1]);

        assertEquals(1, combinations.get(2).get()[0]);
        assertEquals("C", combinations.get(2).get()[1]);

        assertEquals(2, combinations.get(3).get()[0]);
        assertEquals("A", combinations.get(3).get()[1]);

        assertEquals(2, combinations.get(4).get()[0]);
        assertEquals("B", combinations.get(4).get()[1]);

        assertEquals(2, combinations.get(5).get()[0]);
        assertEquals("C", combinations.get(5).get()[1]);

        assertEquals(3, combinations.get(6).get()[0]);
        assertEquals("A", combinations.get(6).get()[1]);

        assertEquals(3, combinations.get(7).get()[0]);
        assertEquals("B", combinations.get(7).get()[1]);

        assertEquals(3, combinations.get(8).get()[0]);
        assertEquals("C", combinations.get(8).get()[1]);
    }

    @Test
    void combinations123() {
        List<Arguments> combinations = TestUtil.cartesianProduct(
                List.of(1),
                List.of("A", "B"),
                List.of("X", "Y", "Z"));
        assertEquals(6, combinations.size());

        assertEquals(1, combinations.get(0).get()[0]);
        assertEquals("A", combinations.get(0).get()[1]);
        assertEquals("X", combinations.get(0).get()[2]);

        assertEquals(1, combinations.get(1).get()[0]);
        assertEquals("A", combinations.get(1).get()[1]);
        assertEquals("Y", combinations.get(1).get()[2]);

        assertEquals(1, combinations.get(2).get()[0]);
        assertEquals("A", combinations.get(2).get()[1]);
        assertEquals("Z", combinations.get(2).get()[2]);

        assertEquals(1, combinations.get(3).get()[0]);
        assertEquals("B", combinations.get(3).get()[1]);
        assertEquals("X", combinations.get(3).get()[2]);

        assertEquals(1, combinations.get(4).get()[0]);
        assertEquals("B", combinations.get(4).get()[1]);
        assertEquals("Y", combinations.get(4).get()[2]);

        assertEquals(1, combinations.get(5).get()[0]);
        assertEquals("B", combinations.get(5).get()[1]);
        assertEquals("Z", combinations.get(5).get()[2]);


    }

    @Test
    void combinations321() {
        List<Arguments> combinations = TestUtil.cartesianProduct(
                List.of(1, 2, 3),
                List.of("A", "B"),
                List.of("X"));
        assertEquals(6, combinations.size());

        assertEquals(1, combinations.get(0).get()[0]);
        assertEquals("A", combinations.get(0).get()[1]);
        assertEquals("X", combinations.get(0).get()[2]);

        assertEquals(1, combinations.get(1).get()[0]);
        assertEquals("B", combinations.get(1).get()[1]);
        assertEquals("X", combinations.get(1).get()[2]);

        assertEquals(2, combinations.get(2).get()[0]);
        assertEquals("A", combinations.get(2).get()[1]);
        assertEquals("X", combinations.get(2).get()[2]);

        assertEquals(2, combinations.get(3).get()[0]);
        assertEquals("B", combinations.get(3).get()[1]);
        assertEquals("X", combinations.get(3).get()[2]);

        assertEquals(3, combinations.get(4).get()[0]);
        assertEquals("A", combinations.get(4).get()[1]);
        assertEquals("X", combinations.get(4).get()[2]);

        assertEquals(3, combinations.get(5).get()[0]);
        assertEquals("B", combinations.get(5).get()[1]);
        assertEquals("X", combinations.get(5).get()[2]);
    }

}