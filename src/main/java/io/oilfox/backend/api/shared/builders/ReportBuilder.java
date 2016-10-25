package io.oilfox.backend.api.shared.builders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by ipusic on 2/8/16.
 */
public class ReportBuilder {
    private static Logger logger = LoggerFactory.getLogger(ReportBuilder.class);

    public class ReportResult {
        public Map<String, String> selects = new LinkedHashMap<>();
        public List<HashMap<String, Object>> rows = new ArrayList<>();
    }

    private Map<String, String> selects = new LinkedHashMap<>();
    private List<String> joins = new ArrayList<>();
    private String fromSql = "";
    private String orderSql = "";
    private String limitSql = "";
    private String groupBySql = "";

    public void addSelect(String dbField, String as) {
        selects.put(dbField, as);
    }

    public void addJoin(String sql) {
        joins.add(sql);
    }

    public void setFrom(String from) {
        fromSql = from;
    }

    public void setGroupBy(String sql) {
        groupBySql = sql;
    }

    public void setOrder(String sql) {
        orderSql = sql;
    }

    public void setLimit(String sql) {
        limitSql = sql;
    }

    private String buildSelectsSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");

        for (Map.Entry<String, String> entry : selects.entrySet()) {
            builder.append(entry.getKey() + " as " + entry.getValue() + ", ");
        }

        String sql = builder.toString();
        if (sql.endsWith(", ")) {
            sql = sql.substring(0, sql.length() - 2);
        }

        return sql;
    }

    private String buildJoinSql() {
        StringBuilder stringBuilder = new StringBuilder(200);

        for (String joinSql : joins) {
            stringBuilder.append(" " + joinSql + " ");
        }

        return stringBuilder.toString();
    }

    public String buildSql() {
        String selectSql = buildSelectsSql();
        String joinSql = buildJoinSql();

        String sql = selectSql + " " + fromSql + " " + joinSql + " " + groupBySql + " " + orderSql + " " + limitSql;
        logger.debug("Generated report SQL: " + sql);

        return sql;
    }

    public ReportResult buildResult(List<HashMap<String, Object>> results) {
        ReportResult reportResult = new ReportResult();
        reportResult.selects = selects;
        reportResult.rows = results;

        return reportResult;
    }
}
