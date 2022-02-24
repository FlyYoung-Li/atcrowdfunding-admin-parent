package com.crowd.service.impl;

import com.crowd.entity.Menu;
import com.crowd.entity.MenuExample;
import com.crowd.mapper.MenuMapper;
import com.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-09-16 00:13
 **/
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuList() {
        MenuExample menuExample = new MenuExample();
        List<Menu> menus = menuMapper.selectByExample(menuExample);
        return menus;
    }

    @Override
    public void addMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void editMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }
}
