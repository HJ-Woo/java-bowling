package bowling.util;

import bowling.domain.Player;
import bowling.domain.Score;
import bowling.domain.frame.Frames;

import java.util.List;


public class StringUtils {
    private static final int MAX_INDEX = 10;
    private static final int MAX_WIDTH = 6;
    private static final int MIN_WIDTH = 3;
    private static final String DELIMITER_LAST = "[\\[\\]]";
    private static final String DELIMITER = ",";
    private static final String NAME_FORMAT = "|  %s |";
    private static final String LAST_FORMAT = "|%s";
    private static final String SCORE_FORMAT = " %3s  |";
    private static final String BLANK_FORMAT = "      |";
    private static final String SLASH = "|";

    public static String convertName(Player player) {
        return String.format(NAME_FORMAT, player.toString());
    }

    public static String convertFrames(Frames frames) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] frameStr = removeLast(frames.toString()).split(DELIMITER);
        for (int i = 0; i < frameStr.length; i++) {
            stringBuilder.append(
                    replaceNull(String.format(" %-5s|", frameStr[i].trim())));
        }
        for (int i = 0; i < MAX_INDEX - frameStr.length; i++) {
            stringBuilder.append(BLANK_FORMAT);
        }
        return stringBuilder.toString();
    }

    public static String convertScores(List<Score> scores) {
        StringBuilder stringBuilder = new StringBuilder(SLASH + BLANK_FORMAT);
        scores.forEach(score -> stringBuilder.append(String.format(SCORE_FORMAT, score.toString())));
        for (int i = 0; i < MAX_INDEX - scores.size(); i++) {
            stringBuilder.append(BLANK_FORMAT);
        }
        return stringBuilder.toString();
    }

    private static String removeLast(String str) {
        str = str.substring(1, str.length() - 1);
        String[] splitStr = str.split(DELIMITER_LAST);
        if (splitStr.length == 1) {
            return splitStr[0];
        }
        String[] last = splitStr[1].split(DELIMITER);
        for (int i = 1; i < last.length; i++) {
            last[0] += String.format(LAST_FORMAT, last[i].trim());
        }
        return splitStr[0] + last[0];
    }

    private static String replaceNull(String str) {
        return str.contains("null") ? BLANK_FORMAT : str;
    }
}
