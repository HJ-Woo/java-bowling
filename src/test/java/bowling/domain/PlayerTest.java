package bowling.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlayerTest {
    @Test
    void 플레이어는이름을지닌다() {
        assertThat(Player.of("이름1")).isEqualTo(Player.of("이름1"));
    }

    @Test
    void 플레이어의이름은_1에서3자사이() {
        assertThatThrownBy(() -> {
            Player.of("1");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void toString테스트() {
        System.out.println(Player.of("이름은"));
    }
}
