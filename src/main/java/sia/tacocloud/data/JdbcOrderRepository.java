package sia.tacocloud.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import sia.tacocloud.model.Order;
import sia.tacocloud.model.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper mapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");

        mapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlaceAt(new Date());
        final long orderId = saveOrderDetails(order);
        order.setId(orderId);
        final List<Taco> tacos = order.getDesignList();
        tacos.forEach(taco -> saveTacoToOrder(taco, orderId));

        return order;
    }

    @SuppressWarnings("unchecked")
    private long saveOrderDetails(Order order) {
        Map<String, Object> values = mapper.convertValue(order, Map.class);
        values.put("placeAt", order.getPlaceAt());

        final Number id = orderInserter.executeAndReturnKey(values);
        return id
                .longValue();
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());orderTacoInserter.execute(values);
    }
}
