package com.example.objects.basic.request;

import com.example.objects.common.CoordinatesDto;
import com.example.objects.common.PersonDto;
import com.example.objects.common.UnitOfMeasureDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductRequestDto implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private CoordinatesDto coordinates; //Поле не может быть null
    private Long price; //Поле не может быть null, Значение поля должно быть больше 0
    private String partNumber; //Поле может быть null
    private Integer manufactureCost; //Поле не может быть null
    private UnitOfMeasureDto unitOfMeasure; //Поле может быть null
    private PersonDto owner; //Поле не может быть null
}
