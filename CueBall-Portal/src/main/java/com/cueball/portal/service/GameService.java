package com.cueball.portal.service;


import com.cueball.portal.dto.GameDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.Game;
import com.cueballdb.repository.GameRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService extends BaseService<Game, GameDTO, GameRepository> {

    public GameService(GameRepository repository) {
        super(repository);
    }

    public ModelAndView findFindAllView(String search ,Integer enable,Integer pageSize, Integer pageNumber, boolean ajax) {
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_GAME_VIEW_ALL);


        if(pageSize == null || pageSize <= 0 ) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }

        long []count = {0};
        List<Game> lists = this.repository.findAllByFilters(search,enable,(pageNumber-1)*pageSize,pageSize,count);
        List<GameDTO> DTOs = lists
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        if (ajax) {
            mav = new ModelAndView(Constants.RA_PAGE_GAME_VIEW_ALL_DETAIL);
        }

        mav.addObject(Constants.RA_PAGE_NUMBER, pageNumber);
        mav.addObject(Constants.RA_PAGE_SIZE, pageSize);
        mav.addObject(Constants.RA_TOTAL_PAGES, totalPages(count,pageSize));
        mav.addObject("totalCount", count[0]);
        mav.addObject(Constants.RA_LIST, DTOs);
        return mav;
    }

    public ModelAndView getView(Integer id, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_GAME_ADD_EDIT);
        if (id != null && id > 0) {
            GameDTO gameDTO = this.findById(id);
            mav.addObject(Constants.RA_DTO, gameDTO);
        }
        else {
            GameDTO gameDTO = new GameDTO ();
            mav.addObject(Constants.RA_DTO, gameDTO);
        }
        return mav;
    }

    public ModelAndView addUpdate(GameDTO gameDTO, BindingResult result, RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_GAME_ADD_EDIT);

        /**  Fetching Games against the addition / updation of Game if exists then throws an error **/
        Game game = repository.findByTitle(gameDTO.getTitle());
        if(game != null && gameDTO.getId() != game.getId()){
            result.rejectValue("title", "title.root", "Game title already exists!");
        }

        if (result.hasErrors()) {
            return mav;
        }

        this.save(gameDTO);
        String message = confirmBox(gameDTO);
        redirectAttributes.addFlashAttribute("message", message);
        mav = new ModelAndView("redirect:/game/viewAll");
        return mav;
    }




    @Override
    public GameDTO mapEntityToDto(Game entity) {
        GameDTO dto = new GameDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.setCreatedAt(entity.getCreatedAt().getTime());
        return dto;
    }

    @Override
    public Game mapDtoToEntity(GameDTO dto) {
        Game entity = new Game();
        BeanUtils.copyProperties(dto, entity);

        if (dto.getId() > 0) {
            entity.setModifiedAt(new Date( System.currentTimeMillis()));
            entity.setCreatedAt(new Date(dto.getCreatedAt()));
        } else {
            entity.setCreatedAt(new Date ( System.currentTimeMillis()));
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }
}
