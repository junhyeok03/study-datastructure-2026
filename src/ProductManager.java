import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProductManager {
    List<Product> productList = new LinkedList<>();
    Map<String, Product> nameMap = new HashMap<>();

    public void registerProduct(Product product) {
        productList.add(product);
        nameMap.put(product.pro_name(), product);
    }

    public Product deleteProduct(String name) {
        return
    }

    public
}
