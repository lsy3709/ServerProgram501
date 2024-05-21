package com.busanit501.samplejsp501.menu.service;

import com.busanit501.samplejsp501.menu.dao.MenuDAO;
import com.busanit501.samplejsp501.menu.domain.MenuVO;
import com.busanit501.samplejsp501.menu.dto.MenuDTO2;
import com.busanit501.samplejsp501.todo.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum MenuService {
  INSTANCE;

  private MenuDAO menuDAO;
  private ModelMapper modelMapper;

  MenuService() {
    menuDAO = new MenuDAO();
    modelMapper = MapperUtil.INSTANCE.get();
  }

  public void register2(MenuDTO2 menuDTO2) throws Exception {

    MenuVO menuVO = modelMapper.map(menuDTO2, MenuVO.class);

    log.info("menuVO : " + menuVO);
    menuDAO.insert(menuVO);
  }

  public List<MenuDTO2> listAll() throws Exception {

    List<MenuVO> sampleList = menuDAO.selectAll();
    log.info("MenuService , 확인1, sampleList : " + sampleList);

    List<MenuDTO2> sampleDtoList = sampleList.stream()
        .map(vo -> modelMapper.map(vo, MenuDTO2.class))
        .collect(Collectors.toList());

    return sampleDtoList;

  }

  // 하나 조회

  // 수정

  // 삭제

}
