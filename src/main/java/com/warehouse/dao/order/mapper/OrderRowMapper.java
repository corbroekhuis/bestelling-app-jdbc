package com.warehouse.dao.order.mapper;

import com.warehouse.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order order = new Order();

        order.setId( rs.getLong("id"));
        order.setArticleId( rs.getLong("artikel_id"));
        order.setQuantity( rs.getInt("aantal"));
        order.setDate( rs.getDate("datum").toLocalDate());

        return order;
    }

    public LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static void main(String[] args) {
        OrderRowMapper orderRowMapper = new OrderRowMapper();
        orderRowMapper.toLocalDate( new Date());
    }
}
