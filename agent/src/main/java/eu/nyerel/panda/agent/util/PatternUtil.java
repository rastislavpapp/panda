package eu.nyerel.panda.agent.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PatternUtil {

    private static final Map<String, String> ESCAPE_RULES = new HashMap<>();

    static {
        ESCAPE_RULES.put("*", ".*");
        ESCAPE_RULES.put("?", ".");
    }

    public static Pattern getPattern(String mask) {
        for (Map.Entry<String, String> ruleEntry : ESCAPE_RULES.entrySet()) {
            mask = mask.replace(ruleEntry.getKey(), ruleEntry.getValue());
        }
        return Pattern.compile(mask);
    }

}
