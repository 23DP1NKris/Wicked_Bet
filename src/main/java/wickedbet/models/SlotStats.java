package wickedbet.models;

import java.math.BigDecimal;

public class SlotStats {
    private int totalSpins;
    private BigDecimal totalBet;
    private BigDecimal totalWin;

    public SlotStats(int totalSpins, BigDecimal totalBet, BigDecimal totalWin) {
        this.totalSpins = totalSpins;
        this.totalBet = totalBet;
        this.totalWin = totalWin;
    }

    public int getTotalSpins() {
        return totalSpins;
    }

    public void setTotalSpins(int totalSpins) {
        this.totalSpins = totalSpins;
    }

    public BigDecimal getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(BigDecimal totalBet) {
        this.totalBet = totalBet;
    }

    public BigDecimal getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(BigDecimal totalWin) {
        this.totalWin = totalWin;
    }
}
