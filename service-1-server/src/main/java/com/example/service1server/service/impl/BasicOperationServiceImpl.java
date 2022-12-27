package com.example.service1server.service.impl;

import com.example.objects.basic.request.ProductRequestDto;
import com.example.objects.basic.response.AmountResponseDto;
import com.example.objects.common.CoordinatesDto;
import com.example.objects.common.FilterDto;
import com.example.objects.common.LocationDto;
import com.example.objects.common.ProductDto;
import com.example.objects.exception.InvalidInputDataException;
import com.example.service1server.converter.Converter;
import com.example.service1server.model.Coordinates;
import com.example.service1server.model.Location;
import com.example.service1server.model.Person;
import com.example.service1server.model.Product;
import com.example.service1server.repositories.CoordinatesRepo;
import com.example.service1server.repositories.LocationRepo;
import com.example.service1server.repositories.PersonRepo;
import com.example.service1server.repositories.ProductRepo;
import com.example.service1server.service.BasicOperationService;
import org.jboss.ejb3.annotation.Pool;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Remote(value = BasicOperationService.class)
@Pool(value = "mypool")
public class BasicOperationServiceImpl implements BasicOperationService {

    @Inject
    private ProductRepo productRepo;

    @Inject
    private PersonRepo personRepo;

    @Inject
    private CoordinatesRepo coordinatesRepo;

    @Inject
    private LocationRepo locationRepo;

    @Inject
    private Converter converter;

    int secretValue = -67;


    @Override
    public ProductDto getProductById(Integer id) {
        Product product = productRepo.getById(id);
        if (product == null) {
            throw new InvalidInputDataException(String.format("Product with ID %d not found", id));
        }
        return converter.productToProductResponseDto(product);
    }

    @Override
    public List<ProductDto> getProductsByFilter(FilterDto filterRequestDto) {
        List<Product> productList = productRepo.findAll(filterRequestDto).stream()
                .filter(p -> {
                    if (filterRequestDto.getId() != null) {
                        return filterRequestDto.getId().equals(p.getId());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getCoordinateX() != null) {
                        return filterRequestDto.getCoordinateX().equals(p.getCoordinates().getX());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getCoordinateY() != null) {
                        return filterRequestDto.getCoordinateY().equals(p.getCoordinates().getY());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getPrice() != null) {
                        return filterRequestDto.getPrice().equals(p.getPrice());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getManufactureCost() != null) {
                        return filterRequestDto.getManufactureCost().equals(p.getManufactureCost());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getOwnerLocationX() != null) {
                        return filterRequestDto.getOwnerLocationX().equals(p.getOwner().getLocation().getX());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getOwnerLocationY() != null) {
                        return filterRequestDto.getOwnerLocationY().equals(p.getOwner().getLocation().getY());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getOwnerLocationZ() != null) {
                        return filterRequestDto.getOwnerLocationZ().equals(p.getOwner().getLocation().getZ());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getName() != null && !filterRequestDto.getName().isEmpty()) {
                        return filterRequestDto.getName().equals(p.getName());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getPartNumber() != null && !filterRequestDto.getPartNumber().isEmpty()) {
                        return filterRequestDto.getPartNumber().equals(p.getPartNumber());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getOwnerName() != null && !filterRequestDto.getOwnerName().isEmpty()) {
                        return filterRequestDto.getOwnerName().equals(p.getOwner().getName());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getOwnerPassportId() != null && !filterRequestDto.getOwnerPassportId().isEmpty()) {
                        return filterRequestDto.getOwnerPassportId().equals(p.getOwner().getPassportID());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getUnitOfMeasure() != null) {
                        return filterRequestDto.getUnitOfMeasure().equals(p.getUnitOfMeasure());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getOwnerEyeColor() != null) {
                        return filterRequestDto.getOwnerEyeColor().equals(p.getOwner().getEyeColor());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getOwnerHairColor() != null) {
                        return filterRequestDto.getOwnerHairColor().equals(p.getOwner().getHairColor());
                    }
                    return true;
                })
                .filter(p -> {
                    if (filterRequestDto.getOwnerNationality() != null) {
                        return filterRequestDto.getOwnerNationality().equals(p.getOwner().getNationality());
                    }
                    return true;
                })
                .collect(Collectors.toList());
        List<ProductDto> productResponseDtoList = productList.stream().map(p -> converter.productToProductResponseDto(p)).collect(Collectors.toList());

        if (filterRequestDto.getPageSize() == secretValue && filterRequestDto.getPage() == secretValue) {
            return productResponseDtoList;
        }
        // todo return size list
        return productResponseDtoList.subList(
                filterRequestDto.getPageSize()*filterRequestDto.getPage(),
                filterRequestDto.getPageSize()*(filterRequestDto.getPage()+1)
        );
    }

    @Override
    public ProductDto createProduct(ProductRequestDto productDto) {
        Person person = personRepo.findFirstByPassportID(productDto.getOwner().getPassportID());
        if (person == null) {
            Location location = getLocationFromLocationDto(productDto.getOwner().getLocation());
            person = new Person(productDto.getOwner().getPassportID(), productDto.getOwner().getName(), converter.colorDtoToColor(productDto.getOwner().getEyeColor()), converter.colorDtoToColor(productDto.getOwner().getHairColor()), converter.countryDtoToCountry(productDto.getOwner().getNationality()), location);
            personRepo.save(person);
        }
        Coordinates coordinates = getCoordinatesFromCoordinatesDto(productDto.getCoordinates());
        Product newProduct = new Product(productDto.getName(), coordinates, productDto.getPrice(), productDto.getPartNumber(), productDto.getManufactureCost(), converter.unitOfMeasureDtoToUnitOfMeasure(productDto.getUnitOfMeasure()), person);
        return converter.productToProductResponseDto(productRepo.save(newProduct));
    }

    @Override
    public ProductDto updateProductById(Integer id, ProductRequestDto productDto) {
        Product product = productRepo.getById(id);
        if (product == null) {
            throw new InvalidInputDataException(String.format("Product with ID %d not found", id));
        }

        Location location = getLocationFromLocationDto(productDto.getOwner().getLocation());

        Person person = personRepo.findFirstByPassportID(productDto.getOwner().getPassportID());
        if (person == null) {
            throw new InvalidInputDataException(String.format("Person with ID %d not found", id));
        }
        person.setNationality(converter.countryDtoToCountry(productDto.getOwner().getNationality()));
        person.setName(productDto.getOwner().getName());
        person.setHairColor(converter.colorDtoToColor(productDto.getOwner().getHairColor()));
        person.setEyeColor(converter.colorDtoToColor(productDto.getOwner().getEyeColor()));
        person.setLocation(location);
        personRepo.save(person);
        Coordinates coordinates = getCoordinatesFromCoordinatesDto(productDto.getCoordinates());
        product.setName(productDto.getName());
        product.setCoordinates(coordinates);
        product.setPrice(productDto.getPrice());
        product.setPartNumber(productDto.getPartNumber());
        product.setManufactureCost(productDto.getManufactureCost());
        product.setUnitOfMeasure(converter.unitOfMeasureDtoToUnitOfMeasure(productDto.getUnitOfMeasure()));
        product.setOwner(person);

        return converter.productToProductResponseDto(productRepo.save(product));
    }

    @Override
    public void deleteProductById(Integer id) {
        try {
            productRepo.deleteById(id);
        } catch (EJBTransactionRolledbackException ex) {//maybe EJBException or UnhandledException
            throw new InvalidInputDataException(String.format("Product with ID %d not found", id));
        }
    }

    private Coordinates getCoordinatesFromCoordinatesDto(CoordinatesDto coordinatesDto) {
        Coordinates coordinates = coordinatesRepo.findFirstByXAndY(coordinatesDto.getX(), coordinatesDto.getY());
        if (coordinates == null) {
            coordinates = converter.coordinatesRequestDtoToCoordinates(coordinatesDto);
            coordinatesRepo.save(coordinates);
        }
        return coordinates;
    }

    private Location getLocationFromLocationDto(LocationDto locationDto) {
        Location location = locationRepo.findFirstByXAndYAndZ(locationDto.getX(), locationDto.getY(), locationDto.getZ());
        if (location == null) {
            location = converter.locationRequestDtoToLocation(locationDto);
            locationRepo.save(location);
        }
        return location;
    }

    @Override
    public AmountResponseDto countProductsWherePriceHigher(Long price) {
        if (price == null || price < 1) {
            throw new InvalidInputDataException("Price should be more than 0. Invalid data request");
        }
        return new AmountResponseDto(productRepo.countAllByPriceAfter(price));
    }

    @Override
    public ArrayList<ProductDto> getArrayProductsWhereNameIncludeSubstring(String subString) {
        if (subString == null || subString.isEmpty()) {
            throw new InvalidInputDataException(String.format("subString should be not null and not empty. Your substring is %s", subString));
        }
        ArrayList<ProductDto> products = new ArrayList<>();
        for (Product product : productRepo.findAllByNameContaining(subString)) {
            products.add(converter.productToProductResponseDto(product));
        }
        return products;
    }

    @Override
    public ArrayList<String> getArrayProductsWhereNameUnique() {
        return personRepo.findAllPersonUniqueName();
    }

}
