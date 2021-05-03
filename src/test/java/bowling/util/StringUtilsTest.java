package bowling.util;

import bowling.domain.Player;
import org.junit.jupiter.api.Test;

import static bowling.domain.frame.FramesTest.lastFrames;
import static bowling.util.StringUtils.convertFrames;
import static bowling.util.StringUtils.convertName;

public class StringUtilsTest {

    @Test
    void 이름확인() {
        System.out.println(convertName(Player.of("확인용")));
    }

    @Test
    void 프레임확인() {
        System.out.println(convertFrames(lastFrames()));
    }
}
