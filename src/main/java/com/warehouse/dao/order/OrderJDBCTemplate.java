package com.warehouse.dao.order;

import com.warehouse.dao.order.mapper.OrderRowMapper;
import com.warehouse.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderJDBCTemplate implements OrderDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderJDBCTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Iterable<Order> findAll() {
        String selectQuery = "SELECT * FROM bestellingen";
        return jdbcTemplate.query(selectQuery, new OrderRowMapper());
    }

    @Override
    public Optional<Order> findById(long id) {

        String sql = "SELECT * FROM bestellingen WHERE id=?";

        Order order = jdbcTemplate.queryForObject( sql, new OrderRowMapper(), id);
        return Optional.ofNullable( order);
    }

    @Override
    public Number create(Order order) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("bestellingen")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("artikel_id", order.getArticleId());
        parameters.put("aantal", order.getQuantity());
        parameters.put("datum", order.getDate());

        Number id = jdbcInsert.executeAndReturnKey(parameters);
        return id;
    }

    @Override
    public int update(Order order) {

        String updateQuery = """
                UPDATE bestellingen SET 
                artikel_id = ?, 
                aantal = ?, 
                datum = ? 
                WHERE id = ?
                """;

        Object[] values = {
                order.getArticleId(),
                order.getQuantity(),
                order.getDate(),
                order.getId()
        };

        return jdbcTemplate.update(updateQuery, values);
    }

    @Override
    public int deleteById(long id) {
        String deleteQuery = "DELETE FROM bestellingen WHERE id = ?";
        return jdbcTemplate.update(deleteQuery, id);
    }

    @Override
    public List<Order> findByArticleId(long id) {

        String sql = "SELECT * FROM bestellingen WHERE artikel_id=?";

        List<Order> bestellingen = jdbcTemplate.query( sql, new OrderRowMapper(), id);
        return bestellingen;
    }
}
