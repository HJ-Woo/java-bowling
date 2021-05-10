package bowling.domain.state;

import bowling.domain.Score;

import java.util.Objects;

public class Miss extends Finished {
    private static final String INVALID_MISS = "miss는 두 점수의 합이 10 미만이어야합니다.";
    private final State oneHit;
    private final State secondHit;

    private Miss(State oneHit, State secondHit) {
        this.oneHit = oneHit;
        this.secondHit = secondHit;
    }

    public static Miss of() {
        return of(Gutter.of(), Gutter.of());
    }

    public static Miss of(int firstScore, int secondScore) {
        return of(OneHit.of(firstScore), OneHit.of(secondScore));
    }

    public static Miss of(State oneHit, State secondHit) {
        valid(oneHit.eval(), secondHit.eval());
        return new Miss(oneHit, secondHit);
    }

    @Override
    public boolean canAccumulate() {
        return false;
    }

    @Override
    public Score eval() {
        return oneHit.eval().calculate(secondHit.eval());
    }

    @Override
    public State next(int nextScore) {
        return this;
    }

    private static void valid(Score firstScore, Score secondScore) {
        if (firstScore.calculate(secondScore).isClear()) {
            throw new IllegalArgumentException(INVALID_MISS);
        }
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
