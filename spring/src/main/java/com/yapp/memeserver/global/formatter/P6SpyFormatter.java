package com.yapp.memeserver.global.formatter;


import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.util.Arrays.stream;

@Component
public class P6SpyFormatter extends JdbcEventListener implements MessageFormattingStrategy {
    private static final String NEW_LINE = System.lineSeparator();
    private static final String P6SPY_FORMATTER = "P6SpyFormatter";
    private static final String PACKAGE = "com.yapp.memeserver";
    private static final String CREATE = "create";
    private static final String ALTER = "alter";
    private static final String COMMENT = "comment";

    @Override
    public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return sqlFormatToUpper(sql, category, getMessage(connectionId, elapsed, getStackBuilder()));
    }

    private String sqlFormatToUpper(String sql, String category, String message) {
        if (Objects.isNull(sql.trim()) || sql.trim().isEmpty()) {
            return "";
        }
        return new StringBuilder()
                .append(NEW_LINE)
                .append(highlight(sqlFormatToUpper(sql, category)))
                .append(message)
                .toString();
    }

    private String sqlFormatToUpper(String sql, String category) {
        if (isStatementDDL(sql, category)) {
            return FormatStyle.DDL
                    .getFormatter()
                    .format(sql)
                    .toUpperCase(Locale.ROOT)
                    .replace("+0900", "");
        }
        return FormatStyle.BASIC
                .getFormatter()
                .format(sql)
                .toUpperCase(Locale.ROOT)
                .replace("+0900", "");
    }

    private String highlight(String sql) {
        return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
    }

    private boolean isStatementDDL(String sql, String category) {
        return isStatement(category) && isDDL(sql.trim().toLowerCase(Locale.ROOT));
    }

    private boolean isStatement(String category) {
        return Category.STATEMENT.getName().equals(category);
    }

    private boolean isDDL(String lowerSql) {
        return lowerSql.startsWith(CREATE) || lowerSql.startsWith(ALTER) || lowerSql.startsWith(COMMENT);
    }

    private String getMessage(int connectionId, long elapsed, StringBuilder callStackBuilder) {
        return new StringBuilder()
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("\t").append(format("Connection ID: %s", connectionId))
                .append(NEW_LINE)
                .append("\t").append(format("Execution Time: %s ms", elapsed))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("\t").append(format("Call Stack (number 1 is entry point): %s", callStackBuilder))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("----------------------------------------------------------------------------------------------------")
                .toString();
    }

    private StringBuilder getStackBuilder() {
        Stack<String> callStack = new Stack<>();
        stream(new Throwable().getStackTrace())
                .map(StackTraceElement::toString)
                .filter(isExcludeWords())
                .forEach(callStack::push);

        int order = 1;
        StringBuilder callStackBuilder = new StringBuilder();
        while (!callStack.empty()) {
            callStackBuilder.append(MessageFormat.format("{0}\t\t{1}. {2}", NEW_LINE, order++, callStack.pop()));
        }
        return callStackBuilder;
    }

    private Predicate<String> isExcludeWords() {
        return charSequence -> charSequence.startsWith(PACKAGE) && !charSequence.contains(P6SPY_FORMATTER);
    }
}