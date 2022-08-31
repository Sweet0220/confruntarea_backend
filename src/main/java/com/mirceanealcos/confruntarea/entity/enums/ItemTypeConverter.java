package com.mirceanealcos.confruntarea.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ItemTypeConverter implements AttributeConverter<ItemType, String> {
    @Override
    public String convertToDatabaseColumn(ItemType itemType) {
        if(itemType == null) {
            return null;
        }
        return itemType.getCode();
    }

    @Override
    public ItemType convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }

        return Stream.of(ItemType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
