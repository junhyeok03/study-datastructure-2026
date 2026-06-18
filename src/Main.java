import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductManager manager = new ProductManager();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int menu = inputInt("메뉴 선택: ");

            switch (menu) {
                case 1:
                    registerProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    updateProductPrice();
                    break;
                case 4:
                    manager.printAllProducts();
                    break;
                case 5:
                    searchProductByName();
                    break;
                case 6:
                    printProductsByCategory();
                    break;
                case 7:
                    setSoldOut();
                    break;
                case 8:
                    manager.printProductsOrderByPrice();
                    break;
                case 9:
                    printProductsByStockLimit();
                    break;
                case 10:
                    manager.printAveragePriceByCategory();
                    break;
                case 11:
                    searchProductById();
                    break;
                case 12:
                    updateStockCount();
                    break;
                case 13:
                    manager.printSoldOutProducts();
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 메뉴입니다.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== 상품 재고 관리 시스템 =====");
        System.out.println("1. 상품 추가");
        System.out.println("2. 상품 삭제");
        System.out.println("3. 상품 가격 수정");
        System.out.println("4. 전체 상품 정보 출력");
        System.out.println("5. 상품명으로 검색");
        System.out.println("6. 카테고리별 상품 정보 출력");
        System.out.println("7. 품절 여부 설정");
        System.out.println("8. 가격 낮은 순으로 정렬해서 출력");
        System.out.println("9. 재고가 n개 이하인 상품 정보 출력");
        System.out.println("10. 카테고리별 상품 가격 평균 출력");
        System.out.println("11. 상품 ID로 검색");
        System.out.println("12. 재고 수량 수정");
        System.out.println("13. 품절 상품만 출력");
        System.out.println("0. 종료");
    }

    private static void registerProduct() {
        System.out.print("상품 ID: ");
        String proId = scanner.nextLine();

        System.out.print("상품명: ");
        String proName = scanner.nextLine();

        System.out.print("카테고리: ");
        String proCategory = scanner.nextLine();

        int proPrice = inputInt("가격: ");
        int stockCount = inputInt("재고 수량: ");
        boolean soldOut = inputBoolean("품절 여부(true/false): ");

        Product product = new Product(proId, proName, proCategory, proPrice, stockCount, soldOut);
        boolean result = manager.registerProduct(product);

        if (result) {
            System.out.println("상품이 추가되었습니다.");
        } else {
            System.out.println("이미 등록된 상품 ID 또는 상품명입니다.");
        }
    }

    private static void deleteProduct() {
        System.out.print("삭제할 상품 ID: ");
        String productId = scanner.nextLine();

        Product deletedProduct = manager.deleteProduct(productId);
        if (deletedProduct == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
        } else {
            System.out.println("삭제된 상품: " + deletedProduct);
        }
    }

    private static void updateProductPrice() {
        System.out.print("가격을 수정할 상품 ID: ");
        String productId = scanner.nextLine();

        int newPrice = inputInt("수정할 가격: ");
        boolean result = manager.updateProductPrice(productId, newPrice);

        if (result) {
            System.out.println("상품 가격이 수정되었습니다.");
        } else {
            System.out.println("해당 상품을 찾을 수 없거나 가격이 올바르지 않습니다.");
        }
    }

    private static void searchProductByName() {
        System.out.print("검색할 상품명: ");
        String productName = scanner.nextLine();

        Product product = manager.searchProductByName(productName);
        if (product == null) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            System.out.println(product);
        }
    }

    private static void printProductsByCategory() {
        System.out.print("카테고리: ");
        String category = scanner.nextLine();
        manager.printProductsByCategory(category);
    }

    private static void setSoldOut() {
        System.out.print("품절 여부를 설정할 상품 ID: ");
        String productId = scanner.nextLine();

        if (!manager.existsProductId(productId)) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        boolean soldOut = inputBoolean("품절 여부(true/false): ");
        manager.setSoldOut(productId, soldOut);

        System.out.println("품절 여부가 변경되었습니다.");
    }

    private static void printProductsByStockLimit() {
        int stockLimit = inputInt("재고 기준 n: ");
        manager.printProductsByStockLimit(stockLimit);
    }

    private static void searchProductById() {
        System.out.print("검색할 상품 ID: ");
        String productId = scanner.nextLine();

        Product product = manager.searchProductById(productId);
        if (product == null) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            System.out.println(product);
        }
    }

    private static void updateStockCount() {
        System.out.print("재고를 수정할 상품 ID: ");
        String productId = scanner.nextLine();

        if (!manager.existsProductId(productId)) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        int newStockCount = inputInt("새 재고 수량: ");
        boolean result = manager.updateStockCount(productId, newStockCount);

        if (result) {
            System.out.println("재고 수량이 수정되었습니다.");
        } else {
            System.out.println("재고 수량은 0개 이상이어야 합니다.");
        }
    }

    private static int inputInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
            }
        }
    }

    private static boolean inputBoolean(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("true")) {
                return true;
            }
            if (input.equalsIgnoreCase("false")) {
                return false;
            }

            System.out.println("true 또는 false를 입력하세요.");
        }
    }
}
