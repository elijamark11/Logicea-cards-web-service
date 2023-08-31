package com.logicea.cards.services.cards;

import com.logicea.cards.dtos.cards.CardDTO;
import com.logicea.cards.entities.CardStatus;
import com.logicea.cards.entities.cards.Card;
import com.logicea.cards.entities.users.User;
import com.logicea.cards.exceptions.BadRequestException;
import com.logicea.cards.exceptions.ResourceNotFoundException;
import com.logicea.cards.helper.HelperUtils;
import com.logicea.cards.helper.ResponseStatus;
import com.logicea.cards.helper.dto.APIResponse;
import com.logicea.cards.helper.mapper.CardMapper;
import com.logicea.cards.repositories.CardsRepository;
import com.logicea.cards.repositories.UserRepository;
import com.logicea.cards.repositories.specifications.CardsSpecification;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class CardsServiceImpl implements CardsService {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ROOT);
    private final CardsRepository cardsRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;
    private final Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");


    public CardsServiceImpl(CardsRepository cardsRepository, UserRepository userRepository, CardMapper cardMapper) {
        this.cardsRepository = cardsRepository;
        this.userRepository = userRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public APIResponse create(CardDTO cardDTO) throws Exception {
        var loggedInUser = new HelperUtils(userRepository).currentlyLoggedInUser();

        Card card = validateCard(cardDTO, loggedInUser);
        card.setStatus(CardStatus.TO_DO.getStatus());

        cardsRepository.save(card);

        return new APIResponse<>(ResponseStatus.SUCCESS.getResponseCode(),
                ResponseStatus.SUCCESS.getDescription(), cardMapper.cardDTO(card));
    }

    @Override
    public APIResponse update(CardDTO cardDTO) throws Exception {
        var loggedInUser = new HelperUtils(userRepository).currentlyLoggedInUser();

        var existingCard = getCardByUser(cardDTO.getId());

        var card = validateCard(cardDTO, loggedInUser);
        card.setCreatedBy(existingCard.getCreatedBy());
        card.setCreatedByLink(existingCard.getCreatedByLink());
        card.setCreatedOn(existingCard.getCreatedOn());

        card = cardsRepository.save(card);

        return new APIResponse<>(ResponseStatus.SUCCESS.getResponseCode(),
                ResponseStatus.SUCCESS.getDescription(), cardMapper.cardDTO(card));
    }

    @Override
    public APIResponse delete(long id) throws Exception {
        var card = getCardByUser(id);

        cardsRepository.deleteById(card.getId());

        return new APIResponse<>(ResponseStatus.SUCCESS.getResponseCode(),
                ResponseStatus.SUCCESS.getDescription(), "Deleted");
    }

    @Override
    public APIResponse getById(long id) throws Exception {
        var card = getCardByUser(id);

        return new APIResponse<>(ResponseStatus.SUCCESS.getResponseCode(),
                ResponseStatus.SUCCESS.getDescription(), cardMapper.cardDTO(card));
    }

    @Override
    public APIResponse pagedFetching(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String filterValue, boolean asc) throws Exception {
        if (!validSortBy(sortBy)) {
            throw new BadRequestException("Invalid Sort-by field! It either name or color or createdOn or status");
        }

        if (!validSortBy(filterBy)) {
            throw new BadRequestException("Invalid Filter-by field! It either name or color or createdOn or status");
        }

        if (filterBy.equalsIgnoreCase("createdOn") && !isDateValid(filterValue)) {
            throw new BadRequestException("Invalid Date! Date format should be: MM/dd/yyyy");
        }

        var loggedInUser = new HelperUtils(userRepository).currentlyLoggedInUser();

        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<CardDTO> cards;
        cards = cardsRepository
                .findAll(
                        CardsSpecification.filterCards(filterBy, filterValue, loggedInUser),
                        pageable
                ).map(cardMapper::cardDTO);

        return new APIResponse<>
                (ResponseStatus.SUCCESS.getResponseCode(), ResponseStatus.SUCCESS.getDescription(),
                        cards);
    }

    private Card validateCard(CardDTO cardDTO, User loggedInUser) throws Exception {
        var card = cardMapper.card(cardDTO);
        if (Strings.isEmpty(card.getName())) {
            throw new BadRequestException("Name is required");
        }

        if (!Strings.isEmpty(cardDTO.getColor()) && !isColorValid(cardDTO.getColor())) {
            throw new BadRequestException("Invalid color code");
        }

        if (!(cardDTO.getStatus().equals("To Do") || cardDTO.getStatus().equals("In Progress") || cardDTO.getStatus().equals("Done"))) {
            throw new BadRequestException("Invalid card status");
        }

        if (card.getId() == null || card.getId() == 0) {
            card.createdOn(loggedInUser.getId());
            card.setCreatedByLink(loggedInUser);
        } else {
            card.updatedOn(loggedInUser.getId());
        }
        return card;
    }

    private Card getCardByUser(long id) throws Exception {
        var loggedInUser = new HelperUtils(userRepository).currentlyLoggedInUser();

        if (loggedInUser.getUserType().getName().equalsIgnoreCase("Member")) {
            return cardsRepository.findByIdAndCreatedBy(id, loggedInUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Card not found!"));
        } else {
            return cardsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found!"));
        }
    }

    private boolean isColorValid(String color) {
        if (!color.startsWith("#")) {
            return false;
        }
        var colorCode = color.substring(1);

        return pattern.matcher(colorCode).matches() && colorCode.length() == 6;
    }

    private boolean validSortBy(String sortBy) {
        return sortBy.equalsIgnoreCase("name") || sortBy.equalsIgnoreCase("color") ||
                sortBy.equalsIgnoreCase("createdOn") || sortBy.equalsIgnoreCase("status");
    }

    private boolean isDateValid(String dateString) {
        try {
            formatter.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}