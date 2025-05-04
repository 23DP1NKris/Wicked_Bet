package models_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wickedbet.models.SlotStats;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SlotStatsTest {
    private SlotStats stats;

    @BeforeEach
    public void setUp() {
        stats = new SlotStats(100, new BigDecimal("250.50"), new BigDecimal("300.75"));
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(100, stats.getTotalSpins());
        assertEquals(new BigDecimal("250.50"), stats.getTotalBet());
        assertEquals(new BigDecimal("300.75"), stats.getTotalWin());
    }

    @Test
    public void testSetTotalSpins() {
        stats.setTotalSpins(200);
        assertEquals(200, stats.getTotalSpins());
        stats.setTotalSpins(0);
        assertEquals(0, stats.getTotalSpins());
    }

    @Test
    public void testSetTotalBet() {
        BigDecimal newBet = new BigDecimal("500.00");
        stats.setTotalBet(newBet);
        assertEquals(newBet, stats.getTotalBet());
        stats.setTotalBet(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, stats.getTotalBet());
    }

    @Test
    public void testSetTotalWin() {
        BigDecimal newWin = new BigDecimal("1000.25");
        stats.setTotalWin(newWin);
        assertEquals(newWin, stats.getTotalWin());
        stats.setTotalWin(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, stats.getTotalWin());
    }
}
