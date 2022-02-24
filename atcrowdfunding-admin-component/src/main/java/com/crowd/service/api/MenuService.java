package com.crowd.service.api;

import com.crowd.entity.Menu;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-16 00:12
 **/
public interface MenuService {
    List<Menu> getMenuList();

    void addMenu(Menu menu);

    void removeMenu(Integer id);

    void editMenu(Menu menu);
}
