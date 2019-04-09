package org.karnes.homebrew.relay.common.test;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestUtil {


    /**
     * Creates a {@link Stream} which returns pairs of the {@code aStream} and {@code bStream} Streams. If the streams
     * are of different sizes, the returned stream will stop after the shortest stream has been exhausted.
     *
     * @param aStream The stream of first elements.
     * @param bStream The stream of second elements.
     * @param <A>     The type of the first elements.
     * @param <B>     The type of the second elements.
     * @return a Stream of pairs.
     */
    public static <A, B> Stream<Arguments> zip(Stream<A> aStream, Stream<B> bStream) {
        Iterator<A> iteratorA = aStream.iterator();
        Iterator<B> iteratorB = bStream.iterator();

        Iterator<Arguments> iterator = new Iterator<>() {
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

    /**
     * Creates a cartesian product from all of the input axes, as an {@link Arguments}.
     * This is every possible list that can be formed by choosing one element from each of the given axes in order;
     * the "n-ary <a href="http://en.wikipedia.org/wiki/Cartesian_product">Cartesian product</a>" of the axes.
     *
     * <p>The result is guaranteed to be in the "traditional", lexicographical order for Cartesian
     * products that you would get from nesting for loops:
     *
     * <pre>{@code
     * for (B b0 : axes[0]) {
     *   for (B b1 : axes[1]) {
     *     ...
     *     Arguments args = Arguments.of(b0, b1, ...);
     *     // Save Arguments and continue
     *   }
     * }
     * }</pre>
     *
     * <b>Warning: </b>
     * <ul>
     * <li> There should be no input empty lists.
     * <li> The total number of elements (objects inside of Arguments inside of the returned List) is ∏(axes[i].size).
     * </ul>
     *
     * @param axes
     * @return
     */
    public static List<Arguments> cartesianProduct(List<?>... axes) {
        //Base case
        if (axes.length == 0) {
            return new ArrayList<>();
        }

        //Only one list, convert all items in the list to Arguments
        if (axes.length == 1) {
            return axes[0].stream().map(item -> Arguments.of(item)).collect(Collectors.toList());
        }

        /*
         * Intuitively, the following can be recursively defined using the above base cases. But I wanted to challenge
         * myself to find an iterative solution that reduced redundant work as much as possible. This seems like a good
         * choice because we can calculate the final size of the matrix upfront, so we can just walk through each
         * position in the matrix in one go, rather than recursively creating and concatonating lists.
         */

        //The number of rows in our matrix is ∏(axes[i].size)
        int rows = Arrays.stream(axes).mapToInt(List::size).reduce(1, (x, size) -> x * size);
        //The number of columns in our matrix is the number of axes
        int columns = axes.length;

        //Make a big matrix to hold all the values.
        //There's no point in trying to keep type information because Arguments throws it out anyway.
        var matrix = new Object[rows][columns];

        //Populate the matrix
        for (int i = 0; i < columns; i++) {
            List<?> items = axes[i];
            int numItems = items.size();

            //Calculate how many combinations would be needed if we only consider the columns to the right.
            int combosRight = Arrays.stream(axes).skip(i + 1L).mapToInt(List::size).reduce(1, (x, size) -> x * size);

            //If we're in a linear array, get the starting index for this row.
            int rowStart = (axes.length - 1 - i) * rows;

            //We know that the ith axis' elements will only appear in the (i)th column.
            for (int row = 0; row < rows; row++) {
                //Index in a linear array
                int myIndex = rowStart + row;
                //Use convert index and previous combinations into an index within our items
                int itemPosition = (myIndex / combosRight) % numItems; //While this works, I consider this line to be magic
                //Populate that position in the maxtrix
                matrix[row][i] = items.get(itemPosition);
            }
        }

        //Turn the matrix into the desired output format
        return Arrays.stream(matrix).map(Arguments::of).collect(Collectors.toList());


    }
}