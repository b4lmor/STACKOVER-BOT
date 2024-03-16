package edu.java.scrapper.core.dao.mapper;

import edu.java.scrapper.entity.Link;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class LinkMapper implements RowMapper<Link> {

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        var link = new Link();

        link.setId(rs.getLong("id"));
        link.setValue(rs.getString("value"));
        link.setHashsum(rs.getInt("hashsum"));

        return link;
    }
}
