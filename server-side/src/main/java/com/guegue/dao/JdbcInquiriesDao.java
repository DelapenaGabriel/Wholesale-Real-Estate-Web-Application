package com.guegue.dao;

import com.guegue.model.Inquiries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcInquiriesDao implements InquiriesDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcInquiriesDao (DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Inquiries> getInquiries() {
        List<Inquiries> inquiries = new ArrayList<>();
        String sql = "SELECT * FROM inquiries ORDER BY created_at DESC";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            inquiries.add(mapRowToInquiry(results));
        }
        return inquiries;
    }

    @Override
    public Inquiries getInquiryById(int id) {
        String sql = "SELECT * FROM inquiries WHERE id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToInquiry(results);
        }
        return null;
    }

    public Inquiries createInquiry(Inquiries newInquiry) {
        String sql = "INSERT INTO inquiries (" +
                "name, email, phone, address, city, state, zip_code, " +
                "property_type, bedrooms, bathrooms, sqft, condition, timeline, " +
                "additional_details, status, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";

        int newId = jdbcTemplate.queryForObject(sql, Integer.class,
                newInquiry.getFullName(),
                newInquiry.getEmail(),
                newInquiry.getPhone(),
                newInquiry.getAddress(),
                newInquiry.getCity(),
                newInquiry.getState(),
                newInquiry.getZipCode(),
                newInquiry.getPropertyType(),
                newInquiry.getBedrooms(),
                newInquiry.getBathrooms(),
                newInquiry.getSqft(),
                newInquiry.getCondition(),
                newInquiry.getTimeline(),
                newInquiry.getAdditionalDetails(),
                newInquiry.getStatus(),
                newInquiry.getRole()
        );

        return getInquiryById(newId);
    }

    // 🟧 Update an existing inquiry
    @Override
    public Inquiries updateInquiry(Inquiries updatedInquiry) {
        String sql = "UPDATE inquiries SET " +
                "name = ?, email = ?, phone = ?, address = ?, city = ?, state = ?, zip_code = ?, " +
                "property_type = ?, bedrooms = ?, bathrooms = ?, sqft = ?, condition = ?, timeline = ?, " +
                "additional_details = ?, status = ?, role = ?, updated_at = CURRENT_TIMESTAMP " +
                "WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
                updatedInquiry.getFullName(),
                updatedInquiry.getEmail(),
                updatedInquiry.getPhone(),
                updatedInquiry.getAddress(),
                updatedInquiry.getCity(),
                updatedInquiry.getState(),
                updatedInquiry.getZipCode(),
                updatedInquiry.getPropertyType(),
                updatedInquiry.getBedrooms(),
                updatedInquiry.getBathrooms(),
                updatedInquiry.getSqft(),
                updatedInquiry.getCondition(),
                updatedInquiry.getTimeline(),
                updatedInquiry.getAdditionalDetails(),
                updatedInquiry.getStatus(),
                updatedInquiry.getRole(),
                updatedInquiry.getId()
        );

        if (rowsAffected > 0) {
            return getInquiryById(updatedInquiry.getId());
        } else {
            return null;
        }
    }

    // 🟥 Delete inquiry
    @Override
    public int deleteInquiry(int id) {
        String sql = "DELETE FROM inquiries WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private Inquiries mapRowToInquiry(SqlRowSet rs) {
        Inquiries inquiry = new Inquiries();
        inquiry.setId(rs.getInt("id"));
        inquiry.setFullName(rs.getString("name"));
        inquiry.setEmail(rs.getString("email"));
        inquiry.setPhone(rs.getString("phone"));
        inquiry.setAddress(rs.getString("address"));
        inquiry.setCity(rs.getString("city"));
        inquiry.setState(rs.getString("state"));
        inquiry.setZipCode(rs.getString("zip_code"));
        inquiry.setPropertyType(rs.getString("property_type"));
        inquiry.setBedrooms(rs.getInt("bedrooms"));
        inquiry.setBathrooms(rs.getInt("bathrooms"));
        inquiry.setSqft(rs.getInt("sqft"));
        inquiry.setCondition(rs.getString("condition"));
        inquiry.setTimeline(rs.getString("timeline"));
        inquiry.setAdditionalDetails(rs.getString("additional_details"));
        inquiry.setStatus(rs.getString("status"));
        inquiry.setRole(rs.getString("role"));

        if (rs.getTimestamp("created_at") != null) {
            inquiry.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            inquiry.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }

        return inquiry;
    }
}
