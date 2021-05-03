package bowling.domain.frame;

import bowling.domain.HitNumber;
import bowling.domain.Pin;
import bowling.domain.rollresult.RollResultType;

import java.util.Objects;

public class NormalFrame implements Frame{
    private static final int MAX_INDEX = 10;

    private final Pin pin;
    private final RollResultType result;

    private NormalFrame(Pin pin) {
        this(pin, null);
    }

    public NormalFrame(Pin pin, RollResultType result) {
        this.pin = pin;
        this.result = result;
    }

    public static NormalFrame of() {
        return new NormalFrame(Pin.of());
    }

    public static NormalFrame of(Pin pin) {
        return new NormalFrame(pin);
    }

    public static NormalFrame of(RollResultType result) {
        return new NormalFrame(null, result);
    }

    public static NormalFrame of(Pin pin, RollResultType result) {
        return new NormalFrame(pin, result);
    }

    @Override
    public Frame next(int index) {
        if(index + 1 == MAX_INDEX) {
            return FinalFrame.of();
        }
        return of();
    }

    @Override
    public Frame roll(HitNumber rollNumber) {
        if (result == null) {
            return of(pin, pin.firstHit(rollNumber));
        }
        return of(pin, pin.nextHit(result, rollNumber));
    }

    @Override
    public boolean isFinished() {
        return result != null && !result.hasNext();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalFrame that = (NormalFrame) o;
        return Objects.equals(pin, that.pin) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin, result);
    }

    @Override
    public String toString() {
        return "" + result + "";
    }
}
