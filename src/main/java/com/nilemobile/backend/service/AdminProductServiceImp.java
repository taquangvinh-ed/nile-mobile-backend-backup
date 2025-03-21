package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.Categories;
import com.nilemobile.backend.model.Product;
import com.nilemobile.backend.reponse.AdminProductDTO;
import com.nilemobile.backend.repository.CategoryRepository;
import com.nilemobile.backend.repository.ProductRepository;
import com.nilemobile.backend.request.AdminCreateProductRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminProductServiceImp implements AdminProductService{
    @Override
    public List<AdminProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new AdminProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getCategories().getParentCategory().getName(),
                        product.getScreenSize(),
                        product.getDisplayTech(),
                        product.getRefreshRate(),
                        product.getResolution(),
                        product.getFrontCamera(),
                        product.getBackCamera(),
                        product.getChipset(),
                        product.getCpu(),
                        product.getGpu(),
                        product.getBatteryCapacity(),
                        product.getChargingPort(),
                        product.getOs(),
                        product.getProductSize(),
                        product.getProductWeight(),
                        product.getDescription()))
                .toList();
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {

    }

    @Override
    public Product updateProduct(Long productId, Product updatedProduct) throws ProductException {
        return null;
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product with ID " + id + " not found"));
    }

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public AdminProductServiceImp(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(AdminCreateProductRequest request){
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

        return productRepository.save(product);

    }
}
