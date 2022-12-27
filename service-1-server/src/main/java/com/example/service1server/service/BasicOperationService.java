package com.example.service1server.service;

import com.example.objects.basic.request.ProductRequestDto;
import com.example.objects.basic.response.AmountResponseDto;
import com.example.objects.common.FilterDto;
import com.example.objects.common.ProductDto;

import java.util.ArrayList;
import java.util.List;

public interface BasicOperationService {

    public ProductDto getProductById(Integer id);

    public List<ProductDto> getProductsByFilter(FilterDto filterRequestDto);

    public ProductDto createProduct(ProductRequestDto productDto);

    public ProductDto updateProductById(Integer id, ProductRequestDto productDto);

    public void deleteProductById(Integer id);

    public AmountResponseDto countProductsWherePriceHigher(Long price);

    public ArrayList<ProductDto> getArrayProductsWhereNameIncludeSubstring(String subString);

    public ArrayList<String> getArrayProductsWhereNameUnique();
}
