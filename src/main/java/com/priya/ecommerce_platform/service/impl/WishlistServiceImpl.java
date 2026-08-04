package com.priya.ecommerce_platform.service.impl;


import com.priya.ecommerce_platform.dto.response.WishlistResponse;
import com.priya.ecommerce_platform.entity.Product;
import com.priya.ecommerce_platform.entity.User;
import com.priya.ecommerce_platform.entity.Wishlist;
import com.priya.ecommerce_platform.entity.WishlistItem;
import com.priya.ecommerce_platform.repository.ProductRepository;
import com.priya.ecommerce_platform.repository.UserRepository;
import com.priya.ecommerce_platform.repository.WishlistItemRepository;
import com.priya.ecommerce_platform.repository.WishlistRepository;
import com.priya.ecommerce_platform.service.WishlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {


    private final WishlistRepository wishlistRepository;

    private final WishlistItemRepository wishlistItemRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;



    @Override
    public WishlistResponse addProductToWishlist(
            String email,
            Long productId
    ) {


        User user = getUser(email);


        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );


        Wishlist wishlist = getOrCreateWishlist(user);



        boolean exists =
                wishlistItemRepository
                        .findByWishlistAndProduct(
                                wishlist,
                                product
                        )
                        .isPresent();


        if(exists){

            throw new RuntimeException(
                    "Product already exists in wishlist"
            );

        }



        WishlistItem item =
                WishlistItem.builder()
                        .wishlist(wishlist)
                        .product(product)
                        .build();



        wishlistItemRepository.save(item);



        wishlist.getItems().add(item);


        wishlistRepository.save(wishlist);



        return mapToResponse(wishlist);

    }




    @Override
    @Transactional(readOnly = true)
    public WishlistResponse getWishlist(
            String email
    ) {


        User user = getUser(email);


        Wishlist wishlist =
                wishlistRepository.findByUser(user)
                        .orElseGet(() ->
                                createWishlist(user)
                        );



        return mapToResponse(wishlist);

    }




    @Override
    public void removeProductFromWishlist(
            String email,
            Long productId
    ) {


        User user = getUser(email);


        Wishlist wishlist =
                wishlistRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Wishlist not found"
                                )
                        );



        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"
                                )
                        );



        WishlistItem item =
                wishlistItemRepository
                        .findByWishlistAndProduct(
                                wishlist,
                                product
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not in wishlist"
                                )
                        );



        wishlistItemRepository.delete(item);

    }





    @Override
    public void clearWishlist(
            String email
    ) {


        User user = getUser(email);


        Wishlist wishlist =
                wishlistRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Wishlist not found"
                                )
                        );



        wishlistItemRepository
                .deleteAll(
                        wishlist.getItems()
                );


        wishlist.getItems().clear();


        wishlistRepository.save(wishlist);

    }





    private User getUser(String email){


        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

    }





    private Wishlist getOrCreateWishlist(
            User user
    ){

        return wishlistRepository
                .findByUser(user)
                .orElseGet(() ->
                        createWishlist(user)
                );

    }




    private Wishlist createWishlist(
            User user
    ){

        Wishlist wishlist =
                Wishlist.builder()
                        .user(user)
                        .build();


        return wishlistRepository.save(wishlist);

    }





    private WishlistResponse mapToResponse(
            Wishlist wishlist
    ){


        List<WishlistResponse.WishlistProductResponse> products =
                new ArrayList<>();


        if(wishlist.getItems()!=null){


            products =
                    wishlist.getItems()
                            .stream()
                            .map(item ->
                                    WishlistResponse
                                            .WishlistProductResponse
                                            .builder()
                                            .productId(
                                                    item.getProduct().getId()
                                            )
                                            .productName(
                                                    item.getProduct()
                                                            .getProductName()
                                            )
                                            .brand(
                                                    item.getProduct()
                                                            .getBrand()
                                            )
                                            .price(
                                                    item.getProduct()
                                                            .getPrice()
                                            )
                                            .imageUrl(
                                                    item.getProduct()
                                                            .getImageUrl()
                                            )
                                            .build()
                            )
                            .toList();

        }



        return WishlistResponse.builder()
                .wishlistId(
                        wishlist.getId()
                )
                .products(products)
                .build();

    }

}