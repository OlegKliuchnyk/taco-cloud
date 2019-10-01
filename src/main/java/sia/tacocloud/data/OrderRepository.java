package sia.tacocloud.data;

import sia.tacocloud.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
