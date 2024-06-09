package org.example.promenergosvet.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.promenergosvet.entity.user.User;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class RoleSetConverter implements AttributeConverter<Set<User.Role>, String> {

    @Override
    public String convertToDatabaseColumn(Set<User.Role> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<User.Role> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Set.of();
        }
        return Arrays.stream(dbData.split(","))
                .map(User.Role::valueOf)
                .collect(Collectors.toSet());
    }
}
