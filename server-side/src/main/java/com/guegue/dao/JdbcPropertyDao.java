package com.guegue.dao;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.guegue.exception.DaoException;
import com.guegue.model.Property;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcPropertyDao implements PropertyDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcPropertyDao (DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Property getPropertyByID(int id) {
        Property property = null;
        String sql = "SELECT * FROM property WHERE id = ?;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()){
                property = mapRowToProperty(result);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the server or database", e);
        }

        return property;
    }

    @Override
    public List<Property> getProperties() {
        List<Property> properties = new ArrayList<>();

        String sql = "SELECT * FROM property;";

        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while (result.next()){
                Property property = mapRowToProperty(result);
                properties.add(property);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the server or database", e);
        }
        return properties;
    }

    @Override
    public Property updateProperty(Property updatedProperty) {
        Property property = null;

        String sql = "UPDATE property " +
                "SET title = ?, type = ?, status = ?, address = ?, city = ?, state = ?, zip_code = ?, bedroom = ?, bathroom = ?, sqft = ?, " +
                "price = ?, arv = ?, image_url = ? " +
                "WHERE id = ?;";

        try{
            int rowsAffected = jdbcTemplate.update(sql, updatedProperty.getTitle(), updatedProperty.getType(), updatedProperty.getStatus(),
                    updatedProperty.getAddress(), updatedProperty.getCity(), updatedProperty.getState(), updatedProperty.getZipCode(), updatedProperty.getBedroom(),
                    updatedProperty.getBathroom(), updatedProperty.getSqft(), updatedProperty.getPrice(), updatedProperty.getArv(),
                    updatedProperty.getImageUrl(),
                    updatedProperty.getId());
            if (rowsAffected == 0){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            property = getPropertyByID(updatedProperty.getId());
        }catch (CannotGetJdbcConnectionException e ){
            throw new DaoException("Cannot connect to server or database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return property;
    }

    @Override
    public Property createProperty(Property newProperty) {
        Property property = null;
        int newId;

        String sql = "INSERT INTO property (title, type, status, address, city, state, zip_code, bedroom, bathroom, sqft, price, arv, image_url) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id;";

        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newProperty.getTitle(), newProperty.getType(), newProperty.getStatus(),
                    newProperty.getAddress(), newProperty.getCity(), newProperty.getState(), newProperty.getZipCode(), newProperty.getBedroom(), newProperty.getBathroom(),
                    newProperty.getSqft(), newProperty.getPrice(), newProperty.getArv(),newProperty.getImageUrl());
            property = getPropertyByID(newId);
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to server or database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return property;
    }

    @Override
    public int deletePropertyByID(int id) {
        String sql = "DELETE FROM property WHERE id = ?;";

        try{
            return jdbcTemplate.update(sql, id);
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to server or database", e);
        }
        catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
    }

    private Property mapRowToProperty(SqlRowSet rowSet){
        Property property = new Property();
        property.setId(rowSet.getInt("id"));
        property.setTitle(rowSet.getString("title"));
        property.setType(rowSet.getString("type"));
        property.setStatus(rowSet.getString("status"));
        property.setAddress(rowSet.getString("address"));
        property.setCity(rowSet.getString("city"));
        property.setState(rowSet.getString("state"));
        property.setZipCode(rowSet.getString("zip_code"));
        property.setBedroom(rowSet.getInt("bedroom"));
        property.setBathroom(rowSet.getInt("bathroom"));
        property.setSqft(rowSet.getInt("sqft"));
        property.setPrice(rowSet.getBigDecimal("price"));
        property.setArv(rowSet.getBigDecimal("arv"));
        property.setImageUrl(rowSet.getString("image_url"));
        if (rowSet.getTimestamp("created_at") != null) {
            property.setCreatedAt(rowSet.getTimestamp("created_at").toLocalDateTime());
        }
        return property;
    }
}
