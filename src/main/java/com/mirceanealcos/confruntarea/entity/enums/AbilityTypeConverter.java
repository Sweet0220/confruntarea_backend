package com.mirceanealcos.confruntarea.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AbilityTypeConverter implements AttributeConverter<AbilityType, String> {

    @Override
    public String convertToDatabaseColumn(AbilityType abilityType) {
        if(abilityType == null) {
            return null;
        }
        return abilityType.getCode();
    }

    @Override
    public AbilityType convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return Stream.of(AbilityType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
