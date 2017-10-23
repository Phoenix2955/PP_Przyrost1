package example;

import java.util.ArrayList;
import java.util.List;

public class SqlValidator {

    public boolean validate(String sql) {
        String[] words = sql.split(" ");
        List<String> trimmedUpperWords = new ArrayList<>();
        for (String word : words) {
            trimmedUpperWords.add(word.trim().toUpperCase());
        }
        int selectIndex = trimmedUpperWords.indexOf("SELECT");
        int fromIndex = trimmedUpperWords.indexOf("FROM");
        int whereIndex = trimmedUpperWords.indexOf("WHERE");

        if (selectIndex == -1 || fromIndex == -1 || whereIndex == -1) {
            return false;
        }

        if (selectIndex < fromIndex && fromIndex < whereIndex) {
            return true;
        }

        return false;
    }
}
