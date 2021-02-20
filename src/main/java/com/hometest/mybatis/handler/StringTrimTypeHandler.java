/**
 * 
 */
package com.hometest.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author moosman
 *
 */
//@MappedJdbcTypes({JdbcType.VARCHAR})
public class StringTrimTypeHandler extends BaseTypeHandler<String>  {

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getString(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getString(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getString(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        parameter = parameter.trim();
        ps.setString(i, parameter);
    }

    private String convert(String value) {
        return value!=null ? value.trim() : null;
    }
}

