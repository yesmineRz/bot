package io.oilfox.backend.api.shared.repositories;

import io.oilfox.backend.db.db.UnitOfWork;
import io.oilfox.backend.api.shared.builders.ReportBuilder;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ipusic on 2/9/16.
 */
public class ReportRepository {
    private static Logger logger = LoggerFactory.getLogger(ReportRepository.class);

    @Inject
    UnitOfWork unitOfWork;

    @SuppressWarnings("unchecked")
    public ReportBuilder.ReportResult getMeteringReport() {
        ReportBuilder reportBuilder = new ReportBuilder();

        reportBuilder.addSelect("metering_raw.value", "meteringvalue");
        reportBuilder.addSelect("cast(\"user\".id as varchar)", "userid");
        reportBuilder.addSelect("\"user\".email", "email");
        reportBuilder.addSelect("\"user\".firstname", "firstname");
        reportBuilder.addSelect("\"user\".lastname", "lastname");
        reportBuilder.addSelect("\"user\".zipcode", "userzipcode");
        reportBuilder.addSelect("cast(tank.id as varchar)", "tankid");
        reportBuilder.addSelect("tank.shape", "shape");
        reportBuilder.addSelect("tank.volume", "volume");
        reportBuilder.addSelect("tank.length", "length");
        reportBuilder.addSelect("tank.street", "street");
        reportBuilder.addSelect("tank.city", "city");
        reportBuilder.addSelect("tank.country", "country");
        reportBuilder.addSelect("tank.zipcode", "tankzipcode");
        reportBuilder.addSelect("cast(oilfox.id as varchar)", "oilfoxid");
        reportBuilder.addSelect("oilfox.name", "oilfoxname");
        reportBuilder.addSelect("cast(oilfox.hwid as varchar)", "oilfoxhwid");
        reportBuilder.addSelect("cast(oilfox.token as varchar)", "oilfoxtoken");

        reportBuilder.setFrom("FROM metering_raw");
        reportBuilder.addJoin("LEFT JOIN oilfox ON metering_raw.token = oilfox.token");
        reportBuilder.addJoin("LEFT JOIN tank ON oilfox.tankid = tank.id");
        reportBuilder.addJoin("LEFT JOIN \"user\" ON oilfox.userid = \"user\".id");

        reportBuilder.setOrder("ORDER BY metering_raw.created_at DESC");
        reportBuilder.setLimit("LIMIT 100");

        List<HashMap<String, Object>> results = unitOfWork.db().createSQLQuery(reportBuilder.buildSql())
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();

        return reportBuilder.buildResult(results);
    }

    @SuppressWarnings("unchecked")
    public ReportBuilder.ReportResult getOverviewReport() {
        ReportBuilder reportBuilder = new ReportBuilder();

        reportBuilder.addSelect("oilfox.name", "oilfoxname");
        reportBuilder.addSelect("cast(\"user\".id as varchar)", "userid");
        reportBuilder.addSelect("\"user\".email", "email");
        reportBuilder.addSelect("\"user\".firstname", "firstname");
        reportBuilder.addSelect("\"user\".lastname", "lastname");
        reportBuilder.addSelect("\"user\".zipcode", "userzipcode");
        reportBuilder.addSelect("cast(tank.id as varchar)", "tankid");
        reportBuilder.addSelect("tank.height", "height");
        reportBuilder.addSelect("tank.shape", "shape");
        reportBuilder.addSelect("tank.volume", "volume");
        reportBuilder.addSelect("tank.length", "length");
        reportBuilder.addSelect("tank.street", "street");
        reportBuilder.addSelect("tank.city", "city");
        reportBuilder.addSelect("tank.country", "country");
        reportBuilder.addSelect("tank.zipcode", "tankzipcode");
        reportBuilder.addSelect("cast(oilfox.id as varchar)", "oilfoxid");
        reportBuilder.addSelect("cast(oilfox.token as varchar)", "oilfoxtoken");
        reportBuilder.addSelect("oilfox.hwid", "oilfoxhwid");

        reportBuilder.setFrom("FROM oilfox");
        reportBuilder.addJoin("LEFT JOIN metering_raw ON oilfox.token = metering_raw.token");
        reportBuilder.addJoin("FULL JOIN tank ON oilfox.tankid = tank.id");
        reportBuilder.addJoin("LEFT JOIN \"user\" ON oilfox.userid = \"user\".id");

        reportBuilder.setOrder("ORDER BY oilfox.created_at DESC, tank.created_at DESC");
        reportBuilder.setLimit("LIMIT 100");

        List<HashMap<String, Object>> results = unitOfWork.db().createSQLQuery(reportBuilder.buildSql())
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();

        return reportBuilder.buildResult(results);
    }
}
