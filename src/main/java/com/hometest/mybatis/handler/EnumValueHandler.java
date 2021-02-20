/**
 * 
 */
package com.hometest.mybatis.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hometest.enums.Gender;
import com.hometest.enums.UserStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author moosman
 *
 */
@MappedTypes({UserStatus.class,Gender.class})
public class EnumValueHandler<E extends Enum<E> > extends BaseTypeHandler<E> {
    private Class<E> type;
    private Map<String, E> enums;
    private Map<E, String> values;

    public EnumValueHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        E[] constants = type.getEnumConstants();
        if (constants == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
        this.enums = new HashMap<>();
        this.values = new HashMap<>();
        Arrays.stream(type.getEnumConstants()).forEach((e) -> {
        	JsonProperty enumValue = null;
            try {
                enumValue = type.getField(e.name()).getAnnotation(JsonProperty.class);
            } catch (NoSuchFieldException e1) {
                // should never happen
                e1.printStackTrace();
            }
            if (enumValue == null) {
                throw new NullPointerException("No @EnumValue annotation found: " + type.getCanonicalName());
            }
            enums.put(enumValue.value(), e);
            values.put(e, enumValue.value());
        });
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, values.get(parameter));
        } else {
            ps.setObject(i, values.get(parameter), jdbcType.TYPE_CODE); // see r3589
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                return enums.get(value.trim());
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by EnumValue.", ex);
            }
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                return enums.get(value);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by EnumValue.", ex);
            }
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            try {
                return enums.get(value);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + value + " to " + type.getSimpleName() + " by EnumValue.", ex);
            }
        }
    }
}