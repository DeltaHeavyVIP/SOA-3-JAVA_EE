package com.example.service1server.converter;

import com.example.objects.common.*;
import com.example.service1server.model.*;

import javax.ejb.Stateless;

@Stateless
public class Converter {

    public ProductDto productToProductResponseDto(Product product) {
        CoordinatesDto coordinatesRequestDto = new CoordinatesDto();
        coordinatesRequestDto.setX(product.getCoordinates().getX());
        coordinatesRequestDto.setY(product.getCoordinates().getY());

        LocationDto locationRequestDto = new LocationDto();
        locationRequestDto.setX(product.getOwner().getLocation().getX());
        locationRequestDto.setY(product.getOwner().getLocation().getY());
        locationRequestDto.setZ(product.getOwner().getLocation().getZ());

        PersonDto personRequestDto = new PersonDto();
        personRequestDto.setName(product.getOwner().getName());
        personRequestDto.setPassportID(product.getOwner().getPassportID());
        personRequestDto.setEyeColor(product.getOwner().getEyeColorDtoFromPerson());
        personRequestDto.setHairColor(product.getOwner().getHairColorDtoFromPerson());
        personRequestDto.setNationality(product.getOwner().getNationalityDtoFromPerson());
        personRequestDto.setLocation(locationRequestDto);

        ProductDto productResponseDto = new ProductDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setCoordinates(coordinatesRequestDto);
        productResponseDto.setCreationDate(product.getCreationDate());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setPartNumber(product.getPartNumber());
        productResponseDto.setManufactureCost(product.getManufactureCost());
        productResponseDto.setUnitOfMeasure(product.getUnitOfMeasureDtoFromProduct());
        productResponseDto.setOwner(personRequestDto);
        return productResponseDto;
    }

    public Location locationRequestDtoToLocation(LocationDto locationRequestDto) {
        Location location = new Location();
        location.setX(locationRequestDto.getX());
        location.setY(locationRequestDto.getY());
        location.setZ(locationRequestDto.getZ());
        return location;
    }

    public Coordinates coordinatesRequestDtoToCoordinates(CoordinatesDto coordinatesRequestDto) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(coordinatesRequestDto.getX());
        coordinates.setY(coordinatesRequestDto.getY());
        return coordinates;
    }

    public Color colorDtoToColor(ColorDto colorDto) {
        return Color.valueOf(colorDto.toString());
    }

    public Country countryDtoToCountry(CountryDto countryDto) {
        return Country.valueOf(countryDto.toString());
    }

    public UnitOfMeasure unitOfMeasureDtoToUnitOfMeasure(UnitOfMeasureDto unitOfMeasureDto) {
        return UnitOfMeasure.valueOf(unitOfMeasureDto.toString());
    }

}
