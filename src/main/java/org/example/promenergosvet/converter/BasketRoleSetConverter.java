package org.example.promenergosvet.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.promenergosvet.entity.basket.Basket;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class BasketRoleSetConverter implements AttributeConverter<Set<Basket.Role>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Basket.Role> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Basket.Role> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Set.of();
        }
        return Arrays.stream(dbData.split(","))
                .map(Basket.Role::valueOf)
                .collect(Collectors.toSet());
    }
}

