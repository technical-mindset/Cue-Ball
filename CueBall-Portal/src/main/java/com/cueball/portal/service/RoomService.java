package com.cueball.portal.service;


import com.cueball.portal.dto.OperatingUnitDTO;
import com.cueball.portal.dto.RoomDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.Game;
import com.cueballdb.model.Room;
import com.cueballdb.model.RoomCategory;
import com.cueballdb.repository.GameRepository;
import com.cueballdb.repository.RoomCategoryRepository;
import com.cueballdb.repository.RoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class RoomService extends BaseService<Room, RoomDTO, RoomRepository> {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    public RoomService(RoomRepository repository) {
        super(repository);
    }

    @Override
    public RoomDTO mapEntityToDto(Room entity) {
        RoomDTO dto = new RoomDTO();
        BeanUtils.copyProperties(entity, dto);


        /** Due to Mapping, Room-Category Id set into dto from entity's Room-Category's object  */
        dto.setRoomCategoryId(entity.getRoomCategory().getId());

        List<Integer> games = Arrays.asList(entity.getGames().split(","))
                .stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());

        dto.setGameIds(games);
        dto.setRoomCategoryName(entity.getRoomCategory().getName());
        dto.setCreatedDate(entity.getCreatedDate().getTime());
        dto.setModifyDate(entity.getModifyDate().getTime());
        return dto;
    }

    @Override
    public Room mapDtoToEntity(RoomDTO dto) {
        Room entity = new Room();

        BeanUtils.copyProperties(dto, entity);

        RoomCategory roomCategory = this.roomCategoryRepository.findById(dto.getRoomCategoryId()).get();
        entity.setRoomCategory(roomCategory);

        /**  Persisting the game ids by comma separated  */
        String gameIds = dto.getGameIds().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        entity.setGames(gameIds);

        List<Game> gameList = this.gameRepository.findAllById(dto.getGameIds());

        Double totalCharges = gameList.stream()
                .map(game -> this.chargesCalculation(game.getCharges()))
                .reduce(0.0, Double::sum);

        entity.setCharges(totalCharges);

        if (dto.getId() > 0) {
            entity.setModifyDate(new Date ( System.currentTimeMillis()));
            entity.setCreatedDate(new Date (dto.getCreatedDate()));
        } else {
            entity.setCreatedDate(new Date ( System.currentTimeMillis()));
            entity.setModifyDate(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }



    public ModelAndView findFindAllView(String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_VIEW_ALL);

        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<Room> lists = repository.findAllByFilters(search, enable,(pageNumber-1)*pageSize, pageSize, count);
        List<RoomDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_ROOM_VIEW_ALL_DETAIL);
        }

        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);
        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;
    }

    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_ADD_EDIT);

        List<RoomCategory> roomCategoryList = this.roomCategoryRepository.findAllByEnableTrue();
        List<Game> gameList = this.gameRepository.findAllByEnableTrue();


        mav.addObject("games", gameList);
        mav.addObject("roomCategoryList", roomCategoryList);
        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);

        //data access permission w.r.t. userLang
        String userLang= "ur";
        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
        mav.addObject("userLang",userLang);

        if (id != null && id > 0) {
            RoomDTO roomDTO = this.findById(id);
            mav.addObject(Constants.RA_DTO, roomDTO);
        }
        else {
            RoomDTO roomDTO = new RoomDTO();
            mav.addObject(Constants.RA_DTO, roomDTO);
        }

        return mav;
    }

    public ModelAndView addUpdate(RoomDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_ROOM_ADD_EDIT);

        List<RoomCategory> roomCategoryList = this.roomCategoryRepository.findAllByEnableTrue();
        List<Game> gameList = this.gameRepository.findAllByEnableTrue();

        mav.addObject("games", gameList);
        mav.addObject("roomCategoryList", roomCategoryList);
        mav.addObject("languageUrl",Constants.LANGUAGE_SERVICE_URL);

        //data access permission w.r.t. userLang
        String userLang= "ur";
        mav.addObject("userLanguageUrl",Constants.USER_LANGUAGE_SERVICE_URL);
        mav.addObject("userLang",userLang);

        if (result.hasErrors()) {
            result.reject("valid.remove.errors");
            return mav;
        }

        this.save(dto);
        String message = confirmBox(dto);
        redirectAttributes.addFlashAttribute("message", message);

        mav = new ModelAndView("redirect:/room/viewAll");
        return mav;
    }


    private double chargesCalculation(Double ... gameCharges){
        double result = 1.0;
        for (Double charge : gameCharges) {
            if (charge != null) {
                result *= charge;
            }
        }

        return result;

    }

}



