package bowling.domain.rollresult;

import java.util.Objects;

public class Miss implements RollResultType{
    private static final String INVALID_MISS = "miss는 두 점수의 합이 10 미만이어야합니다.";
    private final RollResultType oneHit;
    private final RollResultType secondHit;

    private Miss(RollResultType oneHit, RollResultType secondHit) {
        this.oneHit = oneHit;
        this.secondHit = secondHit;
    }

    public static Miss of() {
        return new Miss(Gutter.of(), Gutter.of());
    }

    public static Miss of(int firstScore, int secondScore) {
        valid(firstScore, secondScore);
        return new Miss(OneHit.of(firstScore), OneHit.of(secondScore));
    }

    public static Miss of(RollResultType oneHit, RollResultType secondHit) {
        valid(oneHit.eval(), secondHit.eval());
        return new Miss(oneHit, secondHit);
    }

    private static void valid(int firstScore, int secondScore) {
        if (firstScore + secondScore >= DEFAULT_MAX_SCORE) {
            throw new IllegalArgumentException(INVALID_MISS);
        }
    }

    @Override
    public boolean isStrike() {
        return false;
    }

    @Override
    public boolean isSpare() {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public int eval() {
        return DEFAULT_MIN_SCORE;
    }

    @Override
    public RollResultType next(int nextScore) {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Miss miss = (Miss) o;
        return Objects.equals(oneHit, miss.oneHit) && Objects.equals(secondHit, miss.secondHit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oneHit, secondHit);
    }

    @Override
    public String toString() {
        return oneHit.toString() + "|-";
    }
}
