package edu.java.scrapper.core.dao.jdbc.mapper;

import edu.java.scrapper.api.bot.dto.request.LinkViewDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class LinkViewDtoMapper implements RowMapper<LinkViewDto> {

    @Override
    public LinkViewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return LinkViewDto.builder()
            .lvalue(rs.getString("lvalue"))
            .shortName(rs.getString("short_name"))
            .build();
    }
}
