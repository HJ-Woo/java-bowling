package bowling.util;

import bowling.domain.Player;
import bowling.domain.frame.Frames;


public class StringUtils {
    private static final int MAX_INDEX = 10;
    private static final int MAX_WIDTH = 6;
    private static final int MIN_WIDTH = 3;
    private static final String DELIMITER_LAST = "[\\[\\]]";
    private static final String DELIMITER = ",";
    private static final String NAME_FORMAT = "|  %s |";
    private static final String LAST_FORMAT = "|%s";
    private static final String FRAME_FORMAT = "      |";
    private static final String SLASH = "|";

    public static String convertName(Player player) {
        return String.format(NAME_FORMAT, player.toString());
    }

    public static String convertFrames(Frames frames) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] frameStr = removeLast(frames.toString()).split(DELIMITER);
        for (int i = 0; i < frameStr.length; i++) {
            stringBuilder.append(appendFrame(frameStr[i]));
        }
        for (int i = 0; i < MAX_INDEX - frameStr.length; i++) {
            stringBuilder.append(FRAME_FORMAT);
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

    private static String appendFrame(String str) {
        for (int i = str.length(); i < MIN_WIDTH; i++) {
            str = " " + str;
        }
        for (int i = str.length(); i < MAX_WIDTH; i++) {
            str += " ";
        }
        return replaceNull(str + SLASH);
    }

    private static String replaceNull(String str) {
        if (str.contains("null")) {
            return FRAME_FORMAT;
        }
        return str;
    }
}
