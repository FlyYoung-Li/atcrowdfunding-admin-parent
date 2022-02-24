package com.crowdfunding.crowd.handler;

import com.crowd.constant.CrowdConstant;
import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.po.ProjectPO;
import com.crowdfunding.crowd.entity.vo.DetailProjectVO;
import com.crowdfunding.crowd.entity.vo.PortalTypeVO;
import com.crowdfunding.crowd.service.api.MysqlRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.transform.Result;
import java.util.List;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-10-22 15:16
 **/
@Controller
public class PortalHandler {
    @Autowired
    private MysqlRemoteService mysqlRemoteService;

    /**
     *  查出每一个分类的前四条数据，首页进行显示
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String showPortalPage(Model model) {
        // 1.调用接口，返回json数据
        ResultEntity<List<PortalTypeVO>> typeVOListResult = mysqlRemoteService.getPortalTypeVOList();
        // 2.判断，failed，返回页面，提示错误信息
        String result = typeVOListResult.getResult();

        if (ResultEntity.FAILED.equals(result)) {

            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_NULL_PROJECT_RECENTLY);

            return "portal";
        }
        // 3.成功，返回页面，也带数据PortalTypeVOList
        List<PortalTypeVO> data = typeVOListResult.getData();

        model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA, data);

        return "portal";
    }

}
