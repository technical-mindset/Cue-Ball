package com.cueball.portal.service;



import com.cueball.portal.dto.BaseDTO;
import com.cueball.portal.utils.Constants;
import com.cueballdb.model.Bank;
import com.cueballdb.model.User;
import com.cueballdb.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Log4j2
public abstract class BaseService<E, D extends BaseDTO, R extends JpaRepository<E, Integer>> {

    protected final R repository;

    @Autowired
    UserRepository userRepository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    public D findById(int id) {

        return repository.findById(id).map(this::mapEntityToDto).orElse(null);
    }

    public List<D> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public D save(D dto) {

        //setting user ids
        int userId =getUserId();

        dto.setCreatedBy (userId);
        dto.setModifyBy(userId);


        var e = mapDtoToEntity(dto);
        e = repository.save(e);
        return mapEntityToDto(e);
    }

    @Transactional
    public List<D> save(List<D> dtos) {
        var e = dtos.stream().map(this::mapDtoToEntity).collect(Collectors.toList());
        e = repository.saveAll(e);
        return e.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    public E getOne(int id) {
        return repository.getReferenceById(id);
    }

    public E findOne(int id) {
        return repository.findById(id).orElse(null);
    }

//    public D disable(int id) {
//        D obj = findById(id);
//        obj.setEnable(false);
//        obj = this.save(obj);
//        return obj;
//    }

    public abstract D mapEntityToDto(E entity);

    public abstract E mapDtoToEntity(D dto);

    public Pageable pageable(Integer pageNumber, Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = Constants.MAX_PER_PAGE;
        }
        if (pageNumber == null || pageNumber <= 0) {
            pageNumber = Constants.DEFAUT_START_PAGENUMBER;
        }
        return PageRequest.of(pageNumber - 1, pageSize);
    }

    public static int getTotalPages(int total, int pageSize) {
        return total / pageSize;
    }

    public int totalPages(long[] count, Integer pageSize) {
        int totalPagesPre = (int) (count[0] / pageSize);
        int totalPages = (count[0] % pageSize) == 0 ? totalPagesPre : totalPagesPre + 1;
        return totalPages;
    }

    public static String formatDateToString(Date date, String dateFormat) {
        String mysqlDate = null;

        try {
            if (date != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                mysqlDate = simpleDateFormat.format(date);
            }
        } catch (Throwable var4) {
//            logger.error("Utility:: formatDateToString: Exception: ==>> ", var4);
        }

        return mysqlDate;
    }

    public static Date formantStringToDate(String date, String dateFormat) {
        Date stringDate = null;
        try {
            if (date != null && !date.isEmpty()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                stringDate = simpleDateFormat.parse(date);
            }
        } catch (Throwable var4) {
            System.out.println("Utility:: formatDateToString: Exception: ==>> "+ var4);
        }

        return stringDate;
    }

    public String confirmBox(D dto){
        if (dto.getId() > 0) {
            return "Updated Successfully!";
        } else {
            return "Added Successfully!";
        }
    }

    public  Integer  getUserId(){
        return this.getUser().getId ();
    }

    public User getUser(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        return user;
    }

    protected double timeCalculation(Date in, Date out){
        long diffInMillies = Math.abs(out.getTime() - in.getTime());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
        return minutes;

    }

}
