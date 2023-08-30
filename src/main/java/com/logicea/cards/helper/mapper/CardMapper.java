package com.logicea.cards.helper.mapper;

import com.logicea.cards.dtos.cards.CardDTO;
import com.logicea.cards.entities.cards.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mapping(target = "createdByUser", source = "createdByLink")
    CardDTO cardDTO(Card card);

    Card card(CardDTO cardDTO);
}