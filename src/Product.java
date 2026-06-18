public record Product(
        String pro_id,
        String pro_name,
        String pro_category,
        int pro_price,
        int stock_count,
        boolean sold_out
) {
    @Override
    public String toString() {
        return "상품 ID: " + pro_id
                + ", 상품명: " + pro_name
                + ", 카테고리: " + pro_category
                + ", 가격: " + pro_price + "원"
                + ", 재고 수량: " + stock_count + "개"
                + ", 품절 여부: " + (sold_out ? "품절" : "판매중");
    }
}
