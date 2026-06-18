import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProductManager {
    List<Product> productList = new LinkedList<>();
    Map<String, Product> nameMap = new HashMap<>();
    Map<String, Product> idMap = new HashMap<>();

    // 상품 추가
    public boolean registerProduct(Product product) {
        if (idMap.containsKey(product.pro_id()) || nameMap.containsKey(product.pro_name())) {
            return false;
        }

        productList.add(product);
        nameMap.put(product.pro_name(), product);
        idMap.put(product.pro_id(), product);
        return true;
    }

    // 상품 삭제
    public Product deleteProduct(String productId) {
        Product product = idMap.get(productId);
        if (product == null) {
            return null;
        }

        productList.remove(product);
        nameMap.remove(product.pro_name());
        idMap.remove(product.pro_id());

        return product;
    }

    // 상품 가격 수정
    public boolean updateProductPrice(String productId, int newPrice) {
        Product product = idMap.get(productId);
        if (product == null || newPrice < 0) {
            return false;
        }

        Product updatedProduct = new Product(
                product.pro_id(),
                product.pro_name(),
                product.pro_category(),
                newPrice,
                product.stock_count(),
                product.sold_out()
        );

        replaceProduct(product, updatedProduct);
        return true;
    }

    // 전체 상품 출력
    public void printAllProducts() {
        printProducts(productList);
    }

    // 상품명으로 검색
    public Product searchProductByName(String productName) {
        return nameMap.get(productName);
    }

    // 상품 ID로 검색
    public Product searchProductById(String productId) {
        return idMap.get(productId);
    }

    // 상품 ID 존재 여부 확인
    public boolean existsProductId(String productId) {
        return idMap.containsKey(productId);
    }

    // 카테고리별 상품 정보 출력
    public void printProductsByCategory(String category) {
        List<Product> categoryProducts = new ArrayList<>();

        for (Product product : productList) {
            if (product.pro_category().equals(category)) {
                categoryProducts.add(product);
            }
        }

        printProducts(categoryProducts);
    }

    // 품절 여부 설정
    public boolean setSoldOut(String productId, boolean soldOut) {
        Product product = idMap.get(productId);
        if (product == null) {
            return false;
        }

        Product updatedProduct = new Product(
                product.pro_id(),
                product.pro_name(),
                product.pro_category(),
                product.pro_price(),
                product.stock_count(),
                soldOut
        );

        replaceProduct(product, updatedProduct);
        return true;
    }

    // 재고 수량 수정
    public boolean updateStockCount(String productId, int newStockCount) {
        Product product = idMap.get(productId);
        if (product == null || newStockCount < 0) {
            return false;
        }

        Product updatedProduct = new Product(
                product.pro_id(),
                product.pro_name(),
                product.pro_category(),
                product.pro_price(),
                newStockCount,
                newStockCount == 0
        );

        replaceProduct(product, updatedProduct);
        return true;
    }

    // 품절 상품만 출력
    public void printSoldOutProducts() {
        List<Product> soldOutProducts = new ArrayList<>();

        for (Product product : productList) {
            if (product.sold_out()) {
                soldOutProducts.add(product);
            }
        }

        printProducts(soldOutProducts);
    }

    // 가격 낮은 순으로 정렬해서 출력
    public void printProductsOrderByPrice() {
        List<Product> sortedProducts = new ArrayList<>(productList);
        sortedProducts.sort(Comparator.comparingInt(Product::pro_price));
        printProducts(sortedProducts);
    }

    // 재고가 n개 이하인 상품 정보 출력
    public void printProductsByStockLimit(int stockLimit) {
        List<Product> result = new ArrayList<>();

        for (Product product : productList) {
            if (product.stock_count() <= stockLimit) {
                result.add(product);
            }
        }

        printProducts(result);
    }

    // 카테고리별 상품 가격 평균 출력
    public void printAveragePriceByCategory() {
        if (productList.isEmpty()) {
            System.out.println("등록된 상품이 없습니다.");
            return;
        }

        Map<String, Integer> totalPriceMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (Product product : productList) {
            String category = product.pro_category();
            totalPriceMap.put(category, totalPriceMap.getOrDefault(category, 0) + product.pro_price());
            countMap.put(category, countMap.getOrDefault(category, 0) + 1);
        }

        for (String category : totalPriceMap.keySet()) {
            double average = (double) totalPriceMap.get(category) / countMap.get(category);
            System.out.printf("%s 평균 가격: %.2f원%n", category, average);
        }
    }

    private void replaceProduct(Product oldProduct, Product newProduct) {
        int index = productList.indexOf(oldProduct);
        productList.set(index, newProduct);
        nameMap.remove(oldProduct.pro_name());
        nameMap.put(newProduct.pro_name(), newProduct);
        idMap.put(newProduct.pro_id(), newProduct);
    }

    private void printProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("출력할 상품이 없습니다.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }
}
