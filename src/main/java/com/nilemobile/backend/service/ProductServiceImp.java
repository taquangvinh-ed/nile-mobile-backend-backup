package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Categories;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.model.Variation;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.request.CreateProductRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImp(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        if (request == null || request.getFirstLevel() == null || request.getFirstLevel().trim().isEmpty()) {
            throw new ProductException("First level category is required");
        }

        String firstLevelName = request.getFirstLevel().trim();
        Optional<Categories> firstLevelOpt = categoryRepository.findByName(firstLevelName);
        Categories firstLevel = firstLevelOpt.orElseGet(() -> {
            Categories newFirstLevel = new Categories();
            newFirstLevel.setName(firstLevelName);
            newFirstLevel.setLevel(1);
            return categoryRepository.save(newFirstLevel);
        });

        String secondLevelName = request.getSecondLevel() != null ? request.getSecondLevel().trim() : null;
        Categories secondLevel = null;
        if (secondLevelName != null && !secondLevelName.isEmpty()) {
            Optional<Categories> secondLevelOpt = categoryRepository.findByNameAndParentCategory(secondLevelName, firstLevel);
            secondLevel = secondLevelOpt.orElseGet(() -> {
                Categories newSecondLevel = new Categories();
                newSecondLevel.setName(secondLevelName);
                newSecondLevel.setLevel(2);
                newSecondLevel.setParentCategory(firstLevel);
                return categoryRepository.save(newSecondLevel);
            });
        }

        String thirdLevelName = request.getThirdLevel() != null ? request.getThirdLevel().trim() : null;
        Categories thirdLevel = null;
        if (thirdLevelName != null && !thirdLevelName.isEmpty()) {
            Categories parent = secondLevel != null ? secondLevel : firstLevel;
            Optional<Categories> thirdLevelOpt = categoryRepository.findByNameAndParentCategory(thirdLevelName, parent);
            thirdLevel = thirdLevelOpt.orElseGet(() -> {
                Categories newThirdLevel = new Categories();
                newThirdLevel.setName(thirdLevelName);
                newThirdLevel.setLevel(3);
                newThirdLevel.setParentCategory(parent);
                return categoryRepository.save(newThirdLevel);
            });
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setScreenSize(request.getScreenSize());
        product.setDisplayTech(request.getDisplayTech());
        product.setResolution(request.getResolution());
        product.setRefreshRate(request.getRefreshRate());
        product.setFrontCamera(request.getFrontCamera());
        product.setBackCamera(request.getBackCamera());
        product.setChipset(request.getChipset());
        product.setCpu(request.getCpu());
        product.setGpu(request.getGpu());
        product.setBatteryCapacity(request.getBatteryCapacity());
        product.setChargingPort(request.getChargingPort());
        product.setOs(request.getOs());
        product.setProductSize(request.getProductSize());
        product.setProductWeight(request.getProductWeight());
        product.setCategories(thirdLevel != null ? thirdLevel : (secondLevel != null ? secondLevel : firstLevel));
        product.setCreateAt(LocalDateTime.now());

        // Xử lý variations
        if (request.getVariations() != null && !request.getVariations().isEmpty()) {
            for (Variation variation : request.getVariations()) {
                variation.setId(null); // Đảm bảo tạo mới
                variation.setProduct(product); // Liên kết với product
                product.getVariations().add(variation);
            }
        }

        return productRepository.save(product);
    }

    // Hàm parse số
    private Float parseFloat(String value, String fieldName) {
        try {
            return value != null && !value.trim().isEmpty() ? Float.parseFloat(value) : null;
        } catch (NumberFormatException e) {
            throw new ProductException(fieldName + " phải là số hợp lệ");
        }
    }

    private Integer parseInt(String value, String fieldName) {
        try {
            return value != null && !value.trim().isEmpty() ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            throw new ProductException(fieldName + " phải là số nguyên hợp lệ");
        }
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductException("Product with ID" + productId + "is not found");
        }

    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));

        if (product == null) {
            throw new ProductException("Update product cannot be null");
        }

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getScreenSize() != null) {
            existingProduct.setScreenSize(product.getScreenSize());
        }
        if (product.getDisplayTech() != null) {
            existingProduct.setDisplayTech(product.getDisplayTech());
        }
        if (product.getResolution() != null) {
            existingProduct.setResolution(product.getResolution());
        }
        if (product.getRefreshRate() != null) {
            existingProduct.setRefreshRate(product.getRefreshRate());
        }
        if (product.getFrontCamera() != null) {
            existingProduct.setFrontCamera(product.getFrontCamera());
        }
        if (product.getBackCamera() != null) {
            existingProduct.setBackCamera(product.getBackCamera());
        }
        if (product.getChipset() != null) {
            existingProduct.setChipset(product.getChipset());
        }

        if (product.getCpu() != null) {
            existingProduct.setChipset(product.getCpu());
        }

        if (product.getGpu() != null) {
            existingProduct.setChipset(product.getGpu());
        }

        if (product.getBatteryCapacity() != null) {
            existingProduct.setBatteryCapacity(product.getBatteryCapacity());
        }
        if (product.getChargingPort() != null) {
            existingProduct.setChargingPort(product.getChargingPort());
        }
        if (product.getOs() != null) {
            existingProduct.setOs(product.getOs());
        }
        if (product.getProductSize() != null) {
            existingProduct.setProductSize(product.getProductSize());
        }
        if (product.getProductWeight() != null) {
            existingProduct.setProductWeight(product.getProductWeight());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }

        //        // Xử lý cập nhật danh mục (Categories)
        //        if (product.getCategories() != null && product.getCategories().getCategories_id() != null) {
        //            Long categoryId = product.getCategories().getCategories_id();
        //            Categories category = categoryRepository.findById(categoryId)
        //                    .orElseThrow(() -> new ProductException("Category with ID " + categoryId + " not found"));
        //            existingProduct.setCategories(category);
        //        }


        return productRepository.save(existingProduct);

    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product with ID " + id + " not found"));
    }

    @Override
    public Product addVariation(Long productId, Variation variation) throws ProductException {
        Product product = findProductById(productId);

        if (variation == null) {
            throw new ProductException("Biến thể không thể null");
        }
        if (variation.getRam() == null || variation.getRom() == null || variation.getPrice() == null) {
            throw new ProductException("RAM, ROM và giá là bắt buộc cho biến thể");
        }
        if (variation.getStockQuantity() == null || variation.getStockQuantity() < 0) {
            throw new ProductException("Số lượng tồn kho phải >= 0");
        }

        product.getVariations().add(variation);
        return productRepository.save(product);
    }

    @Override
    public List<Product> findProductsByCategory(String categoryID) {
        return List.of();
    }

    @Override
    public Variation updateVariation(Long productId, Long variationId, Variation updatedVariation) throws ProductException {
        return null;
    }

    @Override
    public void deleteVariation(Long productId, Long variationId) throws ProductException {

    }

    @Override
    public List<Product> findProductsByVariation(String ram, String rom, String color) throws ProductException {
        return null;
    }


//    @Override
//    public Page<Product> getAllProducts(String firstLevel, String secondLevel, String thirdLevel,
//                                        List<String> ram, List<String> rom, String os,
//                                        Integer minBattery, Integer maxBattery, Float minScreenSize, Float maxScreenSize,
//                                        Long minPrice, Long maxPrice, Integer minDiscount, String sort,
//                                        Integer pageNumber, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//        // Lấy danh sách sản phẩm từ repository
//        List<Product> products = productRepository.findAll();
//
//        // Lọc theo First Level Category (Loại sản phẩm: Điện thoại, Tai nghe, Tablet)
//        if (firstLevel != null && !firstLevel.trim().isEmpty()) {
//            products = products.stream()
//                    .filter(p -> p.getCategories() != null && firstLevel.equalsIgnoreCase(p.getCategories().getName()))
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo Second Level Category (Hãng: Samsung, Apple)
//        if (secondLevel != null && !secondLevel.trim().isEmpty()) {
//            products = products.stream()
//                    .filter(p -> p.getCategories() != null && p.getCategories().getParentCategory() != null
//                            && secondLevel.equalsIgnoreCase(p.getCategories().getParentCategory().getName()))
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo Third Level Category (Dòng sản phẩm: Galaxy S23, iPhone 14)
//        if (thirdLevel != null && !thirdLevel.trim().isEmpty()) {
//            products = products.stream()
//                    .filter(p -> p.getCategories() != null && p.getCategories().getParentCategory() != null
//                            && p.getCategories().getParentCategory().getParentCategory() != null
//                            && thirdLevel.equalsIgnoreCase(p.getCategories().getParentCategory().getParentCategory().getName()))
//                    .collect(Collectors.toList());
//        }
//
//
//        // Lọc theo RAM (ram trong Variation)
//        if (ram != null && !ram.isEmpty()) {
//            products = products.stream()
//                    .filter(p -> p.getVariations() != null && p.getVariations().stream()
//                            .anyMatch(v -> ram.stream().anyMatch(r -> r.equalsIgnoreCase(v.getRam()))))
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo ROM (rom trong Variation)
//        if (rom != null && !rom.isEmpty()) {
//            products = products.stream()
//                    .filter(p -> p.getVariations() != null && p.getVariations().stream()
//                            .anyMatch(v -> rom.stream().anyMatch(r -> r.equalsIgnoreCase(v.getRom()))))
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo hệ điều hành (os trong Product)
//        if (os != null && !os.trim().isEmpty()) {
//            products = products.stream()
//                    .filter(p -> p.getOs() != null && os.equalsIgnoreCase(p.getOs()))
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo dung lượng pin (batteryCapacity trong Product)
//        if (minBattery != null || maxBattery != null) {
//            products = products.stream()
//                    .filter(p -> {
//                        Integer battery = p.getBatteryCapacity();
//                        return (minBattery == null || battery != null && battery >= minBattery) &&
//                                (maxBattery == null || battery != null && battery <= maxBattery);
//                    })
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo kích thước màn hình (screenSize trong Product)
//        if (minScreenSize != null || maxScreenSize != null) {
//            products = products.stream()
//                    .filter(p -> {
//                        Float screen = p.getScreenSize();
//                        return (minScreenSize == null || screen != null && screen >= minScreenSize) &&
//                                (maxScreenSize == null || screen != null && screen <= maxScreenSize);
//                    })
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo giá (price trong Variation)
//        if (minPrice != null || maxPrice != null) {
//            products = products.stream()
//                    .filter(p -> p.getVariations() != null && p.getVariations().stream()
//                            .anyMatch(v -> {
//                                Long price = v.getPrice();
//                                return (minPrice == null || price != null && price >= minPrice) &&
//                                        (maxPrice == null || price != null && price <= maxPrice);
//                            }))
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo giảm giá (discountPercent trong Variation)
//        if (minDiscount != null) {
//            products = products.stream()
//                    .filter(p -> p.getVariations() != null && p.getVariations().stream()
//                            .anyMatch(v -> v.getDiscountPercent() >= minDiscount))
//                    .collect(Collectors.toList());
//        }
//
//        // Lọc theo tình trạng kho (stockQuantity trong Variation, chỉ lấy sản phẩm còn hàng)
//        products = products.stream()
//                .filter(p -> p.getVariations() != null && p.getVariations().stream()
//                        .anyMatch(v -> v.getStockQuantity() != null && v.getStockQuantity() > 0))
//                .collect(Collectors.toList());
//
//        // Sắp xếp (sort)
//        if (sort != null && !sort.trim().isEmpty()) {
//            switch (sort.toLowerCase()) {
//                case "price_asc":
//                    products.sort((p1, p2) -> {
//                        Long minPrice1 = p1.getVariations().stream()
//                                .mapToLong(Variation::getPrice)
//                                .min().orElse(Long.MAX_VALUE);
//                        Long minPrice2 = p2.getVariations().stream()
//                                .mapToLong(Variation::getPrice)
//                                .min().orElse(Long.MAX_VALUE);
//                        return minPrice1.compareTo(minPrice2);
//                    });
//                    break;
//                case "price_desc":
//                    products.sort((p1, p2) -> {
//                        Long minPrice1 = p1.getVariations().stream()
//                                .mapToLong(Variation::getPrice)
//                                .min().orElse(Long.MAX_VALUE);
//                        Long minPrice2 = p2.getVariations().stream()
//                                .mapToLong(Variation::getPrice)
//                                .min().orElse(Long.MAX_VALUE);
//                        return minPrice2.compareTo(minPrice1);
//                    });
//                    break;
//                case "newest":
//                    products.sort((p1, p2) -> p2.getCreateAt().compareTo(p1.getCreateAt()));
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        // Chuyển danh sách thành Page với phân trang
//        int startIndex = (int) pageable.getOffset();
//        int endIndex = Math.min((startIndex + pageable.getPageSize()), products.size());
//        List<Product> pagedProducts = products.subList(startIndex, endIndex);
//
//        return new PageImpl<>(pagedProducts, pageable, products.size());
//    }

    @Override
    public Page<Product> getAllProducts(String firstLevel, String secondLevel, String thirdLevel,
                                        List<String> ram, List<String> rom, String os,
                                        Integer minBattery, Integer maxBattery, Float minScreenSize, Float maxScreenSize,
                                        Long minPrice, Long maxPrice, Integer minDiscount, String sort,
                                        Integer pageNumber, Integer pageSize) {

        pageNumber = (pageNumber != null && pageNumber >= 0) ? pageNumber : 0;
        pageSize = (pageSize != null && pageSize > 0) ? pageSize : 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return productRepository.filterProducts(
                firstLevel != null && !firstLevel.trim().isEmpty() ? firstLevel : null,
                secondLevel != null && !secondLevel.trim().isEmpty() ? secondLevel : null,
                thirdLevel != null && !thirdLevel.trim().isEmpty() ? thirdLevel : null,
                ram != null && !ram.isEmpty() ? ram : null,
                rom != null && !rom.isEmpty() ? rom : null,
                os != null && !os.trim().isEmpty() ? os : null,
                minBattery,
                maxBattery,
                minScreenSize,
                maxScreenSize,
                minPrice,
                maxPrice,
                minDiscount,
                sort,
                pageable
        );
    }

    @Override
    public List<String> getAllThirdLevels() {
        return productRepository.findDistinctThirdLevels();
    }

    @Override
    public List<String> getAllSecondLevels() {
        return productRepository.findDistinctSecondLevels();
    }

    @Override
    public List<String> getThirdLevels(String secondLevel) {
        return productRepository.findThirdLevelsBySecondLevel(secondLevel);
    }
}
