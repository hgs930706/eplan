package com.lcm.web.rest;

import com.lcm.domain.Menu;
import com.lcm.repository.MenuRepository;
import com.lcm.service.dto.MenuDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuRepository menuRepository;
    private final String activeMenu = "Y";

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping
    public ResponseEntity<MenuDTO> index(String functionId) {
        Menu menu = menuRepository.findOneByFunctionId(functionId);
        MenuDTO menuDTO = menuToDto(menu);
        for (Menu menu1 : menu.getChildMenu()) {
            menuDTO.getChildMenu().add(menuToDto(menu1));
        }
        return ResponseEntity.ok(menuDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuDTO>> all(@RequestParam(value="language") String language, HttpSession session) {
        //获取角色集合
        Map<String,String>  roleMap = (Map<String,String>)session.getAttribute("roleMap");
        List<MenuDTO> dtos = new ArrayList<>();
        List<Menu> menus = menuRepository.findByLanguageAndActiveFlagAndParentMenuIsNullOrderByDisplaySeq(language,activeMenu);
        for (Menu menu : menus) {
            if (menu.getChildMenu().size() > 0) {
                menu.getChildMenu().sort(Comparator.comparing(Menu::getDisplaySeq));
            }
            MenuDTO menuDTO = menuToDto(menu);
            for (Menu menu1 : menu.getChildMenu()) {
                if (activeMenu.equals(menu1.getActiveFlag())) {
                    menuDTO.getChildMenu().add(menuToDto(menu1));
                    ;
                }
            }
            dtos.add(menuDTO);
        }
        if(roleMap != null){
            for(MenuDTO menuDTO : dtos) {//菜单权限
                List<MenuDTO> mDto = menuDTO.getChildMenu();
                List<MenuDTO> collect = mDto.stream().filter(m -> !m.getFunctionId().equals(roleMap.get(m.getFunctionId()))).collect(Collectors.toList());
                if (!collect.isEmpty()) {
                    mDto.removeAll(collect);
                }
            }
        }

        return ResponseEntity.ok(dtos);
    }

    private MenuDTO menuToDto(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setDisplaySeq(menu.getDisplaySeq());
        menuDTO.setFunctionPath(menu.getFunctionPath());
        menuDTO.setFunctionName(menu.getFunctionName());
        menuDTO.setFunctionId(menu.getFunctionId());
        menuDTO.setSite(menu.getSite());
        return menuDTO;
    }

}
