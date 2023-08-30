package com.logicea.cards.controllers;

import com.logicea.cards.dtos.cards.CardDTO;
import com.logicea.cards.helper.dto.APIResponse;
import com.logicea.cards.services.cards.CardsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cards")
public class CardsController {
    private final CardsService cardsService;

    public CardsController(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @PostMapping
    public APIResponse createCard(@RequestBody CardDTO cardDTO) throws Exception {
        return cardsService.create(cardDTO);
    }

    @PatchMapping
    public APIResponse updateCard(@RequestBody CardDTO cardDTO) throws Exception {
        return cardsService.update(cardDTO);
    }

    @DeleteMapping
    public APIResponse deleteCard(@RequestParam long cardId) throws Exception {
        return cardsService.delete(cardId);
    }

    @GetMapping
    public APIResponse getCardById(@RequestParam long cardId) throws Exception {
        return cardsService.getById(cardId);
    }

    @GetMapping("/paged")
    public APIResponse pagedFetching(@RequestParam(defaultValue = "0") int pageNo,
                                     @RequestParam(defaultValue = "10") int pageSize,
                                     @RequestParam(defaultValue = "createdOn") String sortBy,
                                     @RequestParam(defaultValue = "createdOn") String filterBy,
                                     @RequestParam(defaultValue = "createdOn") String filterValue,
                                     @RequestParam(defaultValue = "true") boolean asc) throws Exception {
        return cardsService.pagedFetching(pageNo, pageSize, sortBy, filterBy, filterValue, asc);
    }
}