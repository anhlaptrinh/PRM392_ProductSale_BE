package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.dto.response.ProductResponse;
import com.prm.productsale.entity.CategoryEntity;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.ProductMapper;
import com.prm.productsale.repository.CategoryRepo;
import com.prm.productsale.repository.ProductRepo;
import com.prm.productsale.repository.ReviewRepo;
import com.prm.productsale.repository.WishlistRepo;
import com.prm.productsale.services.LoginServices;
import com.prm.productsale.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImp implements ProductServices {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private WishlistRepo wishlistRepo;
    @Autowired
    private LoginServices loginServices;

    // =========================
    // 1. Các method "Read" / "List" (GET)
    // =========================

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductEntity> productEntityList = productRepo.findAll();
        if(productEntityList.isEmpty()) throw new AppException(ErrorCode.PRODUCT_LIST_NOT_EXIST_EXCEPTION);
        return productMapper.toListProductResponse(productEntityList);
    }

    @Override
    public ProductResponse getProductByID(int productID) {
        ProductEntity entity = productRepo.findById(productID)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));
        ProductResponse response = productMapper.toProductResponse(entity);

        // Tính trung bình của rating
        List<ReviewEntity> reviewEntities = reviewRepo.findByProduct_IdAndIsDeletedFalseOrderByCreatedAtDesc(productID);
        Float ratingAverage = 0f;
        if (reviewEntities != null && !reviewEntities.isEmpty()) {
            int total = 0;
            for (ReviewEntity item : reviewEntities) {
                total += item.getRating();
            }
            ratingAverage = (float) total / reviewEntities.size();
        }
        response.setRatingAverage(ratingAverage);

        return response;
    }
    @Override
    public List<ProductResponse> filterProducts(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sort) {
        List<ProductEntity> products = productRepo.findAll();

        // ✅ Kiểm tra category có tồn tại không
        if (categoryId != null) {
            boolean categoryExists = categoryRepo.existsById(categoryId);
            if (!categoryExists) {
                throw new AppException(ErrorCode.CATEGORY_NOT_EXIST_EXCEPTION);
            }
            products = products.stream()
                    .filter(p -> p.getCategory().getCategoryID() == categoryId)
                    .toList();
        }

        // ✅ Kiểm tra min/max price không âm
        if (minPrice != null) {
            if (minPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new AppException(ErrorCode.INVALID_PRICE_RANGE);
            }
            products = products.stream()
                    .filter(p -> p.getPrice().compareTo(minPrice) >= 0)
                    .toList();
        }

        if (maxPrice != null) {
            if (maxPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new AppException(ErrorCode.INVALID_PRICE_RANGE);
            }
            products = products.stream()
                    .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                    .toList();
        }

        // ✅ Kiểm tra sort type hợp lệ
        if (sort != null && !sort.isBlank()) {
            switch (sort.toLowerCase()) {
                case "price_asc" -> products = products.stream()
                        .sorted(Comparator.comparing(ProductEntity::getPrice))
                        .toList();
                case "price_desc" -> products = products.stream()
                        .sorted(Comparator.comparing(ProductEntity::getPrice).reversed())
                        .toList();
                case "rating_asc" -> products = products.stream()
                        .sorted(Comparator.comparing(p -> calculateRatingAverage(p.getId())))
                        .toList();
                case "rating_desc" -> products = products.stream()
                        .sorted(Comparator.comparing((ProductEntity p) -> calculateRatingAverage(p.getId())).reversed())
                        .toList();
                default -> throw new AppException(ErrorCode.INVALID_SORT_TYPE);
            }
        }

        UserEntity user = loginServices.getUser();
        List<ProductResponse> responseList = products.stream()
                .map(product -> {
                    ProductResponse response = productMapper.toProductResponse(product);
                    response.setRatingAverage(calculateRatingAverage(product.getId()));

                    wishlistRepo.findByUserIdAndProductId(user.getId(), product.getId())
                            .ifPresentOrElse(
                                    wishlist -> {
                                        response.setFavorite(true);
                                        response.setWishlistId(wishlist.getId());
                                    },
                                    () -> {
                                        response.setFavorite(false);
                                        response.setWishlistId(-1);
                                    }
                            );
                    return response;
                }).toList();

        return responseList;
    }
    private float calculateRatingAverage(int productId) {
        List<ReviewEntity> reviews = reviewRepo.findByProduct_IdAndIsDeletedFalseOrderByCreatedAtDesc(productId);
        if (reviews == null || reviews.isEmpty()) return 0f;

        int sum = reviews.stream().mapToInt(ReviewEntity::getRating).sum();
        return (float) sum / reviews.size();
    }
    // =========================
    // 2. Các method "Create" (POST)
    // =========================

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        ProductEntity productEntity = productMapper.toProductEntity(request);

        CategoryEntity categoryEntity = categoryRepo.findById(request.getCategoryID())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_EXCEPTION));

        productEntity.setCategory(categoryEntity);

        ProductEntity saved = productRepo.save(productEntity);
        return productMapper.toProductResponse(saved);
    }

    // =========================
    // 3. Các method "Update" (PUT/PATCH)
    // =========================

    public ProductResponse updateProduct(int productID, ProductRequest request) {
        ProductEntity existing = productRepo.findById(productID)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));

        productMapper.updateProductFromRequest(request, existing);

        CategoryEntity category = categoryRepo.findById(request.getCategoryID())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_EXCEPTION));

        existing.setCategory(category);

        ProductEntity updated = productRepo.save(existing);

        return productMapper.toProductResponse(updated);
    }

    // =========================
    // 4. Các method "Delete" (DELETE) hoặc đặc biệt
    // =========================

    @Override
    public void deleteProduct(int productId) {
        Optional<ProductEntity> product = productRepo.findById(productId);
        if(product.isEmpty()) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION);
        productRepo.deleteById(productId);
    }

    // =========================
    // 5. Các method "Utility" (private helpers)
    // =========================
}

