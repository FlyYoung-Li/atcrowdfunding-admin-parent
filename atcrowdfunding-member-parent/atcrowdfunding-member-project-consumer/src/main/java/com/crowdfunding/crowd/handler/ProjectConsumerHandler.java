package com.crowdfunding.crowd.handler;

import com.crowd.constant.CrowdConstant;
import com.crowd.util.CrowdUtil;
import com.crowd.util.ResultEntity;
import com.crowdfunding.crowd.entity.po.ProjectPO;
import com.crowdfunding.crowd.entity.vo.*;
import com.crowdfunding.crowd.properties.OSSProperties;
import com.crowdfunding.crowd.service.api.MysqlRemoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: atcrowdfunding-admin-parent
 * @description
 * @author: lxy
 * @create: 2021-11-08 08:45
 **/
@Controller
public class ProjectConsumerHandler {
    @Autowired
    private OSSProperties ossProperties;
    @Autowired
    private MysqlRemoteService mysqlRemoteService;

    /**
     *  处理提交项目的请求
     * @param projectVO 封装基本参数
     * @param headerPicture 头图文
     * @param detailPictureList 详情图结合
     * @param session session，用于存储projectVO
     * @param model 返回错误信息
     * @return 第二阶段页面
     * @throws IOException
     */
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(
            // 接收除了图片之外的其他普通数据
            ProjectVO projectVO,
            // 接收上传的头图
            MultipartFile headerPicture ,
            // 接收上传的项目的详情图片
            List<MultipartFile> detailPictureList,
            // 将当前保存数据的projectVO对象存入session中（Redis中）
            HttpSession session,
            // 返回错误数据使用model
            Model model

    ) throws IOException {
        // 1.判断头图是否为空
        boolean isEmpty = headerPicture.isEmpty();

        // 2.为空，返回提交项目页面
        if(isEmpty){

            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_NULL_HEAD_PIC);

            return "project-launch";
        }

        // 不为空，上传到oss，返回访问路径
            ResultEntity<String>  uploadHeadPicResult = CrowdUtil.uploadFileToOss(
                    ossProperties.getEndpoint(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    headerPicture.getInputStream(),
                    Objects.requireNonNull(headerPicture.getOriginalFilename()));

            String result = uploadHeadPicResult.getResult();
            // 3.判断返回数据是否成功
            if(ResultEntity.SUCCESS.equals(result)){
                // 4.成功，获取文件返回路径
                String headPicPath = uploadHeadPicResult.getData();
                // 5.设置进OSS对象中
                projectVO.setHeaderPicturePath(headPicPath);
                // 6.失败，返回创建项目页，显示错误信息
            }else if(ResultEntity.FAILED.equals(result)){

                    model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_UPLOAD_PIC_INVALID);

                return "project-launch";
            }

        // 7.上传详情图片，创建DetailPicturePathList对象，存放oss返回的路径。（不能直接用，返回null）
        List<String> detailPicturePathList = new ArrayList<>();
            // 防止上传文件为null
        if(detailPictureList == null || detailPictureList.size() == 0){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_NULL_DETAIL_PIC);

            return "project-launch";
        }
        for (MultipartFile multipartFile : detailPictureList) {
            // 判断只要为空，返回创建项目页面
            if(multipartFile.isEmpty()){

                model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_NULL_DETAIL_PIC);

                return "project-launch";
            }
                // 每一个multipartFile，上传到oss，并返回路径
                ResultEntity<String> uploadDetailPicResult = CrowdUtil.uploadFileToOss(
                        ossProperties.getEndpoint(),
                        ossProperties.getBucketName(),
                        ossProperties.getBucketDomain(),
                        ossProperties.getAccessKeyId(),
                        ossProperties.getAccessKeySecret(),
                        multipartFile.getInputStream(),
                        Objects.requireNonNull(multipartFile.getOriginalFilename()));

                String uploadDetailPicResultResult = uploadDetailPicResult.getResult();
                // 如果上传成功，获取返回的路径，加入projectVO对象（注意这里可能会报null）
                if(ResultEntity.SUCCESS.equals(uploadDetailPicResultResult)){

                    String detailPicPath = uploadDetailPicResult.getData();

                    detailPicturePathList.add(detailPicPath);
                // 上传失败，返回原页面
                }else if(ResultEntity.FAILED.equals(uploadDetailPicResultResult)){

                    model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_UPLOAD_PIC_INVALID);

                    return "project-launch";
                }

        }
            // 将返回的图片途径List，封装进projectVO对象中
            projectVO.setDetailPicturePathList(detailPicturePathList);

            // 8.都完成了之后，存入session（Redis）
           session.setAttribute(CrowdConstant.ATTR_NAME_TEMPORARY_PROJECT,projectVO);

            // 9.因为是表单，所以重定向。
        return "redirect:http://www.crowd.com/project/project/return/page";
    }


    /**
     * 回报页面，点击图片，上传到oss上，
     * @param returnPic 返回路径
     * @return ResultEntity
     * @throws IOException
     */
    @RequestMapping("/create/upload/return/picture.json")
    @ResponseBody
    public ResultEntity<String> uploadOssAndGetPicPath( @RequestParam("returnPicture") MultipartFile returnPic) throws IOException {
        // 1.判断回报图片是否为空
        boolean returnPicIsEmpty = returnPic.isEmpty();

        // 2.为空，返回数据
        if(returnPicIsEmpty){

            return ResultEntity.failed(CrowdConstant.MESSAGE_NULL_RETURN_PIC);
        }

        // 不为空，上传到oss，返回访问路径
        ResultEntity<String>  uploadReturnPicResult = CrowdUtil.uploadFileToOss(
                ossProperties.getEndpoint(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPic.getInputStream(),
                Objects.requireNonNull(returnPic.getOriginalFilename()));
        // 这里，在工具类中，已经对这个上传到oss的方法做了处理，就是返回路径，所以直接返回就行
            return uploadReturnPicResult;

    }




    /**
     * 点击确定，页面添加一条回报信息。后端保存一条returnVO对象
     * @param returnVO 封装回报页面参数
     * @param session   存取projectVO
     * @return  ResultEntityt
     */
    @RequestMapping("/create/save/return.json")
    @ResponseBody
    public ResultEntity<String> saveReturnVO(ReturnVO returnVO,HttpSession session){

        try {
            // 判断session中取出project是否为空
            ProjectVO projectVO = (ProjectVO)session.getAttribute(CrowdConstant.ATTR_NAME_TEMPORARY_PROJECT);
            if(projectVO == null){

                ResultEntity.failed(CrowdConstant.MESSAGE_TEMPORARY_PROJECTVO_INVALID);
            }
            // 取出projectVO中的returnVOList
            List<ReturnVO> projectVOReturnVOList = projectVO.getReturnVOList();

            // 判断：projectVO对象是不是第一次添加returnVO对象
            if(projectVOReturnVOList == null || projectVOReturnVOList.size() == 0){
                // 如果是，创建returnVOList
                projectVOReturnVOList = new ArrayList<>();
                // 将修改后projectVO，存入session中
                projectVO.setReturnVOList(projectVOReturnVOList);

            }

            // 此时已经有returnVOList属性了,直接添加
            projectVOReturnVOList.add(returnVO);

            // 并且设置进redis的projectVO中
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPORARY_PROJECT,projectVO);

            return ResultEntity.successWithoutData();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResultEntity.failed(exception.getMessage());
        }
    }

    /**
     *  验证发起人信息，并保存到数据库
     * @param confirmInfoVO
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/create/memberConfirmInfoVO")
    public String saveConfirm(MemberConfirmInfoVO confirmInfoVO,HttpSession session,Model model){
        // 从session中获取projectVO
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPORARY_PROJECT);
        // 判断是否为空
        if(projectVO == null){
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPORARY_PROJECTVO_INVALID);

        }
        // 不为空，projectVO对象保存项目认证信息VO对象。
        projectVO.setMemberConfirmInfoVO(confirmInfoVO);

        // 从session中取出当前登陆的会员信息，取出memberId（MemberConfirmInfoPO需要）
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();

        // 调用mysql-provider的方法，存入projectVO（里面进行VO对象和PO对象的转换）
        ResultEntity<String> addProjectPOResult = mysqlRemoteService.saveProjectPORemote(projectVO,memberId);

        // 保存失败，返回验证页面
        String result = addProjectPOResult.getResult();
        if(ResultEntity.FAILED.equals(result)){
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,addProjectPOResult.getMessage());
            return "project-confirm";
        }
        // 保存成功，将projectVO对象信息，从session中移除
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPORARY_PROJECT);

        // 删除成功，重定向到成功页面
        return "redirect:http://www.crowd.com/project/project/success/page";
    }

    /**
     *  点击项目首页的一个项目，进行项目详情页，查询返回DetailProjectVO对象
     * @param id    项目id，通过参数路径发过来
     * @param modelMap  存入失败信息，返回到页面显示
     * @return
     */
    @RequestMapping("/show/detail/project/page/{projectId}")
    public String toDetailProjectPage(@PathVariable("projectId") Integer id, ModelMap modelMap){

        ResultEntity<DetailProjectVO> detailProjectVOResult = mysqlRemoteService.getDetailProjectVOById(id);

        String result = detailProjectVOResult.getResult();

        // 成功，获取数据
        if(ResultEntity.SUCCESS.equals(result)){
            DetailProjectVO detailProjectVO = detailProjectVOResult.getData();
            modelMap.addAttribute("project_detail_data",detailProjectVO);
        }
        // 无论是否返回详情页面所需要的数据，都返回到详情页
        return "project-portal-detail";
    }
}

