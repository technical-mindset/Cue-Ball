package com.cueball.portal.service;


import com.cueball.portal.dto.InventoryDTO;
import com.cueball.portal.dto.tuckBuyingDTO;
import com.cueball.portal.response.GenericResponse;
import com.cueball.portal.response.TuckBuyingMenu;
import com.cueball.portal.response.TuckBuyingResponse;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.*;
import com.cueballdb.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class TuckBuyingService extends BaseService<TucBuying, tuckBuyingDTO, tuckBuyingRepository> {

    public TuckBuyingService(tuckBuyingRepository repository) {
        super(repository);
    }

    @Override
    public tuckBuyingDTO mapEntityToDto(TucBuying entity) {
        tuckBuyingDTO dto = new tuckBuyingDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setCreatedAt(entity.getCreatedAt().getTime());
        dto.setModifiedAt(entity.getModifiedAt().getTime());
        return dto;
    }

    @Override
    public TucBuying mapDtoToEntity(tuckBuyingDTO dto) {
        TucBuying entity = new TucBuying();

        BeanUtils.copyProperties(dto, entity);

        if (dto.getId() > 0) {
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
            entity.setCreatedAt(new Date (dto.getCreatedAt()));
        } else {
            entity.setCreatedAt(new Date ( System.currentTimeMillis()));
            entity.setModifiedAt(new Date ( System.currentTimeMillis()));
        }
        return entity;
    }


    public TuckBuyingResponse Checkout(List <TuckBuyingMenu> tuckItemJson){
        TuckBuyingResponse tuckBuyingResponse = new TuckBuyingResponse(GenericResponse.SUCCESS);
        ModelAndView mav = new ModelAndView(Constants.RA_PAGE_TUCK_SHOP_SUB_MENU_VIEW_ALL);
        try{

            TucBuying tucBuying = new TucBuying();

            Integer menuItemsEntries = tuckItemJson.size();

            System.out.println(":::::::::::: menuItemsEntries :::::::::::::::" + menuItemsEntries);
            System.out.println(":::::::::::: menuItemsEntries :::::::::::::::" + menuItemsEntries);
            System.out.println(":::::::::::: menuItemsEntries :::::::::::::::" + menuItemsEntries);
            System.out.println(":::::::::::: menuItemsEntries :::::::::::::::" + menuItemsEntries);
            System.out.println(":::::::::::: menuItemsEntries :::::::::::::::" + menuItemsEntries);

            if(menuItemsEntries != null && menuItemsEntries > 0){
                for (int i = 0; i < menuItemsEntries; i++) {
                    tucBuying.setCustomerId("0");
                    tucBuying.setInventoryId(tuckItemJson.get(i).getId().toString());
                    tucBuying.setPrice(tuckItemJson.get(i).getActualPrice());
                    tucBuying.setQuantity(1);
                    tucBuying.setCreatedAt(new Date ( System.currentTimeMillis()));
                    tucBuying.setModifiedAt(new Date ( System.currentTimeMillis()));
                    tucBuying.setEnable(true);
                    tucBuying.setCreatedBy(1);
                    tucBuying.setModifyBy(1);
                    this.repository.save(tucBuying);
                }

            }

            tuckBuyingResponse.setStatus(200);
            tuckBuyingResponse.setMessage("Purchase Successful");

        }
        catch(Exception e){
            System.out.println("Qurbani::HomeController::PostCall:: Error" + e);
            tuckBuyingResponse.setStatus(500);
            tuckBuyingResponse.setMessage("something went wrong !");
        }

        return tuckBuyingResponse;
    }
}



