package core.basesyntax;

import core.basesyntax.exception.NotEnoughFruitException;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.*;

public class FruitStorageTest {
    private static final String APPLE_NAME = "apple";
    private static final String BANANA_NAME = "banana";
    private static final LocalDate appleDate = LocalDate.of(2020, 10, 15);
    private static final LocalDate appleFreshDate = LocalDate.of(2020, 10, 25);
    private static final LocalDate bananaDate = LocalDate.of(2020, 10, 20);
    private static final Fruit apple = new Fruit("apple", appleDate);
    private static final Fruit appleFresh = new Fruit("apple", appleFreshDate);
    private static final Fruit banana = new Fruit("banana", bananaDate);

    private FruitStorage fruitStorage;

    @Before
    public void setUp() {
        fruitStorage = new FruitStorage();
    }

    @Test
    public void add() {
        fruitStorage.add(banana, 50);
        fruitStorage.add(apple, 30);
        fruitStorage.add(appleFresh, 70);
        Map<String, Integer> stock = fruitStorage.getStockBalance();
        assertEquals(2, stock.size());
        assertEquals(50, (int) stock.get(BANANA_NAME));
        assertEquals(100, (int) stock.get(APPLE_NAME));
    }

    @Test
    public void remove_ok() {
        fruitStorage.add(banana, 50);
        fruitStorage.add(apple, 30);
        fruitStorage.add(appleFresh, 70);
        fruitStorage.remove(BANANA_NAME, bananaDate, 30);
        fruitStorage.remove(BANANA_NAME, bananaDate, 10);
        fruitStorage.remove(APPLE_NAME, bananaDate, 50);
        Map<String, Integer> stock = fruitStorage.getStockBalance();
        assertEquals(2, stock.size());
        assertEquals(10, (int) stock.get(BANANA_NAME));
        assertEquals(50, (int) stock.get(APPLE_NAME));
    }

    @Test
    public void remove_fail() {
        fruitStorage.add(banana, 50);
        try {
            fruitStorage.remove(BANANA_NAME, appleFreshDate, 30);
            fail("Expected NotEnoughFruitException");
        } catch (NotEnoughFruitException e) {
            assertEquals("Asked to buy 30 banana, but have 0", e.getMessage());
        }
    }
}