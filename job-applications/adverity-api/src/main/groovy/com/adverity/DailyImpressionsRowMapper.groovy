package com.adverity

import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

class DailyImpressionsRowMapper implements RowMapper<DailyImpressions>{

    @Override
    DailyImpressions mapRow(ResultSet rs, int rowNum) throws SQLException {
        new DailyImpressions().tap{
            campaign = rs.getString("campaign")
            datasource = rs.getString("datasource")
            impressions = rs.getInt("impressions")
            setDaily(rs.getDate("daily"))
        }
    }
}
