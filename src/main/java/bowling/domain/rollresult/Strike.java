package bowling.domain.rollresult;

import java.util.Objects;

public class Strike implements RollResultType {
    private static final String INVALID_SCORE = "스트라이크의 값은 10 이상이어야합니다.";
    private final OneHit firstHit;

    private Strike() {
        firstHit = OneHit.ofOne(DEFAULT_MAX_SCORE);
    }

    private Strike(int score) {
        firstHit = OneHit.ofOne(score);
    }

    private Strike(OneHit firstHit) {
        this.firstHit = firstHit;
    }

    public static Strike of() {
        return new Strike();
    }

    public static Strike of(int score) {
        valid(score);
        return new Strike(score);
    }

    public static Strike of(OneHit firstHit) {
        valid(firstHit.eval());
        return new Strike(firstHit);
    }

    private static void valid(int score) {
        if (score < DEFAULT_MAX_SCORE) {
            throw new IllegalArgumentException(INVALID_SCORE);
        }
    }

    @Override
    public boolean isStrike() {
        return true;
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
        return firstHit.eval();
    }

    @Override
    public RollResultType next(int nextScore) {
        return of(firstHit.add(nextScore));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Strike strike = (Strike) o;
        return Objects.equals(firstHit, strike.firstHit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstHit);
    }

    @Override
    public String toString() {
        return "X";
    }
}
