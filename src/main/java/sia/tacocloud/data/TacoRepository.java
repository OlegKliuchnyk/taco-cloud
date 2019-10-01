package sia.tacocloud.data;

import sia.tacocloud.model.Taco;

public interface TacoRepository {
    Taco save(Taco taco);
}
