package com.examination.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.examination.bean.*;
import com.examination.service.ContentService;
import com.examination.service.PaperDetailsService;
import com.examination.service.PaperService;
import com.examination.service.QuestionService;
import com.examination.service.*;
import com.examination.utils.GlobalUserUtil;
import com.examination.utils.RandomUtil;
import com.examination.utils.StaticVariableUtil;
import com.examination.utils.StrOperateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Description
 * @Author he
 * @Data 2022/10/16 16:27
 */
@RequestMapping("/admin")
@Controller
@Slf4j
public class PaperController {
    @Autowired
    PaperService paperService;
    @Autowired
    PaperDetailsService paperDetailsService;
    @Autowired
    QuestionService questionService;
    @Autowired
    private  ScoreVMService scoreVMService;
    @Autowired
    private PoiService poiService;
    @Autowired
    ContentService contentService;

    @Autowired
    TypeService typeService;

    @RequestMapping("/toPaperList")
    public String getQuestionList(HttpServletRequest request,
                                  @RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                  @RequestParam(required = false, defaultValue = "", value = "searchName") String searchName,
                                  @RequestParam(required = false, defaultValue = "", value = "searchId") Integer searchId,
                                  Model model,
                                  Integer userId){
        userId = GlobalUserUtil.getUser().getId();
        if(userId == null){
            return "error/4xx";
        }
        Page<Paper> page=new Page(pn,15);
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        List<Paper> paperList = null;
        if("".equals(searchName) && searchId == null){
            BaseMapper<Paper> baseMapper = paperService.getBaseMapper();
            baseMapper.selectPage(page,wrapper);
            paperList = page.getRecords();
        }else if(!"".equals(searchName) && searchId == null){
            wrapper.like("p_name",searchName);
            BaseMapper<Paper> baseMapper = paperService.getBaseMapper();
            baseMapper.selectPage(page,wrapper);
            paperList = page.getRecords();
        }else if("".equals(searchName) && searchId != null){
            wrapper.eq("p_id",searchId);
            BaseMapper<Paper> baseMapper = paperService.getBaseMapper();
            baseMapper.selectPage(page,wrapper);
            paperList = page.getRecords();
        }else if(!"".equals(searchName) && searchId != null){
            wrapper.eq("p_id",searchId);
            wrapper.like("p_name",searchName);
            BaseMapper<Paper> baseMapper = paperService.getBaseMapper();
            baseMapper.selectPage(page,wrapper);
            paperList = page.getRecords();
        }
        request.setAttribute("page",page);
        request.setAttribute("jumpUrl","/admin/toPaperList?pn=");
        request.setAttribute("searchNameValue","");
        request.setAttribute("qId","");
        request.setAttribute("qName","");
        request.setAttribute("searchIdValue","");
        if(searchId == null && !"".equals(searchName)){
            request.setAttribute("qName","&searchName=");
            request.setAttribute("searchNameValue",searchName);
        }
        if ("".equals(searchName) && searchId != null){
            request.setAttribute("searchIdValue",searchId);
            request.setAttribute("qId","&searchId=");
        }
        model.addAttribute("paperList",paperList);
        // 显示管理员已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "admin/paper_list";
    }

    // 修改状态
    @ResponseBody
    @PostMapping("/paperStatusChange")
    public Object statusChange(@RequestBody String req){
        Paper paper = new Paper();
        //转化为json数据
        JSONObject jsonObject = (JSONObject) JSONObject.parse(req);
        //获取点击前的按钮状态，该修改的id
        Integer pId = Integer.parseInt(((String) jsonObject.get("id")));
        //转换为Integer
        Integer pStatus = (Integer) jsonObject.get("pStatus");
        pStatus = pStatus==0?1:0;
        UpdateWrapper<Paper> wrapper = new UpdateWrapper<>();
        wrapper.eq("p_id",pId);
        wrapper.set("p_status",pStatus);
        boolean update = paperService.update(wrapper);
        if (update){
            Map<String, Object> rep = new HashMap<String, Object>();
            rep.put("code", 200);
            rep.put("status",pStatus);//返回按钮状态，前端根据状态修改按钮的文本内容
            return rep;
        }
        else{
            return null;
        }
    }

    // 批量删除
    @RequestMapping("/deletePapers")
    public String deleteCount(@RequestParam String ids){//ids是复选框名字
        List<String> delList = new ArrayList<>();
        String[] strs = ids.split(",");
        for (String str : strs) {
            delList.add(str);
        }
        paperService.removeByIds(delList);
        for(int i=0;i<strs.length;i++){
            QueryWrapper<PaperDetails> detailsQueryWrapper = new QueryWrapper<>();
            detailsQueryWrapper.eq("p_id",strs[i]);
            paperDetailsService.remove(detailsQueryWrapper);
        }
        return "redirect:/admin/toPaperList";
    }

    //删除
    @GetMapping("/deletePaper/{pId}")
    public String delete(@PathVariable("pId") Integer pId) {
        paperService.removeById(pId);
        QueryWrapper<PaperDetails> detailsQueryWrapper = new QueryWrapper<>();
        detailsQueryWrapper.eq("p_id",pId);
        paperDetailsService.remove(detailsQueryWrapper);
        return "redirect:/admin/toPaperList";
    }

    //进入试卷创建页面
    @RequestMapping("/paperCreate")
    public String createPaper(Model model){
        //查询所有的题型
        QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
        typeQueryWrapper.select("q_type","q_pool");
        List<Type> list = typeService.list(typeQueryWrapper);
        //设置model、返回视图
        model.addAttribute("typeList",list);
        // 显示管理员已登录
        model.addAttribute("userName", GlobalUserUtil.getUser().getUserName());
        return "admin/paper_create";
    }

    //试卷创建（随机创建）
    @ResponseBody
    @RequestMapping("/createPaperOperate")
    public Object creatPaperOpt(@RequestBody String request) throws ParseException {
        JSONObject jsonObject = JSONObject.parseObject(request);
        String paperName = (String) jsonObject.get("paperName");
        Integer singleSelectNumber = Integer.parseInt((String)jsonObject.get("singleSelectNumber"));
        Integer moreSelectNumber = Integer.parseInt((String)jsonObject.get("moreSelectNumber"));
        Integer judgeNumber = Integer.parseInt((String)jsonObject.get("judgeNumber"));
        String startTime = (String)jsonObject.get("startTime");
        String endTime = (String)jsonObject.get("endTime");
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        Paper paper = new Paper();
        paper.setPName(paperName);
        paper.setIsAuto(1);
        paper.setPStatus(0);
        paper.setSingleSelect(singleSelectNumber);
        paper.setMoreSelect(moreSelectNumber);
        paper.setStartTime(start);
        paper.setEndTime(end);
        LocalDateTime updateTime = LocalDateTime.parse(startTime);
        paper.setUpdateTime(updateTime);
        //储存返回给前端的数据
        Map map = new HashMap();
        //查询试卷，通过试卷名查询，试卷名需要保持唯一
        QueryWrapper<Paper> queryWrapper = new QueryWrapper();
        queryWrapper.select("p_id").eq("p_name",paperName);
        List<Paper> paperList = paperService.list(queryWrapper);
        //已经创建过，直接返回信息到前端
        if(paperList.size()>=1){
            map.put("code", 500);
            map.put("hasPaper",true);
            map.put("paperName",paperName);
            return map;
        }
        else {
            map.put("hasPaper",false);
        }
        //保存试卷信息
        boolean savePaper = paperService.save(paper);
        Integer pId = paperService.list(queryWrapper).get(0).getPId();
        //查询所有的单选题，在单选题中抽取指定数目的题：
        QueryWrapper<Question> questionQueryWrapperWrapper = new QueryWrapper();
        questionQueryWrapperWrapper.select("id").eq("question_type", StaticVariableUtil.singleSelectType);
        List<Question> list1 = questionService.list(questionQueryWrapperWrapper);
        //数组用来存放抽取的题目id，RandomUtil.random回从指定数组抽取指定个数的随机题目，且不重复
        Integer array[] = new Integer[list1.size()];
        for(int i = 0; i < array.length; i++){
           array[i] = list1.get(i).getId();
        }
        //设置单选题
        ArrayList<Integer> randomList = RandomUtil.random(singleSelectNumber, array);
        List<PaperDetails> list = new ArrayList<>();
        for(int i = 0; i < singleSelectNumber; i++){
            PaperDetails paperDetails = new PaperDetails();
            paperDetails.setNum(i+1);
            paperDetails.setQId(randomList.get(i));
            paperDetails.setPId(pId);
            list.add(paperDetails);
        }
        //查询所有的多选题，在多选题中抽取指定题：
        QueryWrapper<Question> moreWrapperWrapper = new QueryWrapper();
        moreWrapperWrapper.select("id").eq("question_type", StaticVariableUtil.moreSelectType);
        List<Question> list2 = questionService.list(moreWrapperWrapper);
        Integer array2[] = new Integer[list2.size()];
        for(int i = 0; i < array2.length; i++){
            array2[i] = list2.get(i).getId();
        }

        ArrayList<Integer> randomList2 = RandomUtil.random(moreSelectNumber, array2);
        for(int i = 0; i < moreSelectNumber; i++){
            PaperDetails paperDetails = new PaperDetails();
            paperDetails.setNum(list.size()+1);
            paperDetails.setQId(randomList2.get(i));
            paperDetails.setPId(pId);
            list.add(paperDetails);
        }
        //保存试卷
        boolean savePaperDetails = paperDetailsService.saveBatch(list);
        if(savePaper&&savePaperDetails){
            map.put("code",200);
            map.put("paperName",paperName);
            return map;
        }else {
            map.put("code", 500);
            map.put("paperName", paperName);
            map.put("hasError","保存试卷出错!");
            return map;
        }
    }

    //自定义创建试卷
    @ResponseBody
    @RequestMapping("/createPaperDiy")
    public Object createPaperDiy(@RequestBody String request){
        JSONObject jsonObject = JSONObject.parseObject(request);
        String paperName = (String) jsonObject.get("paperName");
        String startTime = (String)jsonObject.get("startTime");
        String endTime = (String)jsonObject.get("endTime");
        JSONObject moreNumArray = jsonObject.getJSONObject("moreNumArray");
        JSONObject morePoolArray = jsonObject.getJSONObject("morePoolArray");
        JSONObject singleNumArray = jsonObject.getJSONObject("singleNumArray");
        JSONObject singlePoolArray = jsonObject.getJSONObject("singlePoolArray");
        //获取单选、多选题数
        Integer singleSelect = 0;
        Integer moreSelect = 0;
        for(int i = 0; i < singleNumArray.size(); i++){
            singleSelect+=Integer.parseInt((String) singleNumArray.get(String.valueOf(i)));
        }
        for(int i = 0; i < moreNumArray.size(); i++){
            moreSelect+=Integer.parseInt((String) morePoolArray.get(String.valueOf(i)));
        }
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        Paper paper = new Paper();
        paper.setPName(paperName);
        paper.setIsAuto(1);
        paper.setPStatus(0);
        paper.setSingleSelect(singleSelect);
        paper.setMoreSelect(moreSelect);
        paper.setStartTime(start);
        paper.setEndTime(end);
        LocalDateTime updateTime = LocalDateTime.parse(startTime);
        paper.setUpdateTime(updateTime);
        //储存返回给前端的数据
        Map map = new HashMap();
        //查询试卷，判断试卷是否已经存在，通过试卷名查询，试卷名需要保持唯一
        QueryWrapper<Paper> queryWrapper = new QueryWrapper();
        queryWrapper.select("p_id").eq("p_name",paperName);
        List<Paper> paperList = paperService.list(queryWrapper);
        //已经创建过，直接返回信息到前端
        if(paperList.size()>=1){
            map.put("code", 500);
            map.put("hasPaper",true);
            map.put("paperName",paperName);
            return map;
        }
        else {
            map.put("hasPaper",false);
        }
        //试卷不存在保存试卷信息
        boolean savePaper = paperService.save(paper);
        Integer pId = paperService.list(queryWrapper).get(0).getPId();
        //试卷详情list，所有筛选的题目信息存入
        List<PaperDetails> paperDetailsList = new ArrayList<>();
        //单选：查出所有符合条件的单选题
        for(int i =0;i<singlePoolArray.size();i++){
            //先查出所有符合pool条件的--->再由random查出指定数目的-->添加道总和的paperDetailsList中
            // 查询条件：question_pool
            QueryWrapper<Question> questionQueryWrapperWrapper = new QueryWrapper();
            questionQueryWrapperWrapper.select("id").eq("question_type", StaticVariableUtil.singleSelectType)
                    .eq("question_pool",Integer.parseInt((String) singlePoolArray.get(String.valueOf(i))));
            //查出满足某种题型、某种类型的题，并将该题的id存放在array中
            List<Question> questionList = questionService.list(questionQueryWrapperWrapper);
            Integer array[] = new Integer[questionList.size()];
            for(int j = 0; j < array.length; j++){
                array[j] = questionList.get(j).getId();
            }
            //限定这种题型的数量
            Integer number = Integer.parseInt((String) singleNumArray.get(String.valueOf(i)));
            ArrayList<Integer> randomList = RandomUtil.random(number, array);
            for(int m = 0; m < number; m++){
                PaperDetails paperDetails = new PaperDetails();
                paperDetails.setNum(paperDetailsList.size()+1);
                paperDetails.setQId(randomList.get(m));
                paperDetails.setPId(pId);
                //添加道到和list中
                paperDetailsList.add(paperDetails);
            }
        }
        for(int i =0;i<morePoolArray.size();i++){
            //先查出所有符合pool条件的--->再由random查出指定数目的-->添加道总和的paperDetailsList中
            // 查询条件：question_pool
            QueryWrapper<Question> questionQueryWrapperWrapper = new QueryWrapper();
            questionQueryWrapperWrapper.select("id").eq("question_type", StaticVariableUtil.moreSelectType)
                    .eq("question_pool",Integer.parseInt((String) morePoolArray.get(String.valueOf(i))));
            //查出满足某种题型、某种类型的题，并将该题的id存放在array中
            List<Question> questionList = questionService.list(questionQueryWrapperWrapper);
            Integer array[] = new Integer[questionList.size()];
            for(int j = 0; j < array.length; j++){
                array[j] = questionList.get(j).getId();
            }
            //限定这种题型的数量
            Integer number = Integer.parseInt((String) moreNumArray.get(String.valueOf(i)));
            ArrayList<Integer> randomList = RandomUtil.random(number, array);
            for(int m = 0; m < number; m++){
                PaperDetails paperDetails = new PaperDetails();
                paperDetails.setNum(paperDetailsList.size()+1);
                paperDetails.setQId(randomList.get(m));
                paperDetails.setPId(pId);
                //添加到总和list中
                paperDetailsList.add(paperDetails);
            }
        }
        //保存试卷
        boolean savePaperDetails = paperDetailsService.saveBatch(paperDetailsList);
        if(savePaper&&savePaperDetails){
            map.put("code",200);
            map.put("paperName",paperName);
            return map;
        }else {
            map.put("code", 500);
            map.put("paperName", paperName);
            map.put("hasError","保存试卷出错!");
            return map;
        }


    }


    //显示试卷
    @ResponseBody
    @RequestMapping("/lookPaperDetails")
    public Object lookPaperDetails(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.parseObject(request);
        Integer pId = Integer.parseInt((String) jsonObject.get("pId"));
        QueryWrapper<Paper> paperWrapper = new QueryWrapper<>();
        paperWrapper.select("p_name").eq("p_id",pId);
        String pName = paperService.getOne(paperWrapper).getPName();
        //查出所有pId为指定id的数据
        QueryWrapper<PaperDetails> wrapper = new QueryWrapper<>();
        wrapper.select("q_id").eq("p_id",pId);
        List<PaperDetails> list = paperDetailsService.list(wrapper);
        List<Integer> qIdList = new ArrayList<>();
        for(int i=0;i< list.size();i++){
            qIdList.add(list.get(i).getQId());
        }
        BaseMapper<Content> contentBaseMapper = contentService.getBaseMapper();
        //获取正文对象
        List<Content> contentObjectList = contentBaseMapper.selectBatchIds(qIdList);
        ArrayList<String> contentList = new ArrayList<>();
        //获取正文对象中的content属性，封装了关于题的信息
        for(int i = 0; i < contentObjectList.size(); i++){
            contentList.add(contentObjectList.get(i).getContent());
            System.out.println(contentObjectList.get(i).getContent());
        }
        //将所有信息转为QuestionObject对象
        ArrayList<QuestionObject> questionObjectList = new ArrayList<>();
        for(int i = 0; i < contentList.size(); i++){
            QuestionObject questionObject = JSONObject.parseObject(contentList.get(i), QuestionObject.class);
            questionObject.setTitleContent(StrOperateUtil.removeTag(questionObject.getTitleContent()));
            questionObjectList.add(questionObject);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("paperName",pName);
        map.put("questionObjectList",questionObjectList);
        return map;
    }


    // 通过卷子id查找分数
    @ResponseBody
    @PostMapping("/queryMark")
    public Object toSelectMark(@RequestBody String req){
        Integer pId = Integer.parseInt(JSONObject.parseObject(req).get("id").toString());
        List<ScoreVM> scoreVMS = scoreVMService.searchMark(pId);
        Map map = new HashMap();
        map.put("code",200);
       if (scoreVMS.size() == 0){
           map.put("code",500);
       }
        map.put("scoreList",scoreVMS);
        map.put("pid",pId);
        return map;
    }

    //导出excel文件
    @ResponseBody
    @PostMapping("/dowmLoadExcel")
    public Object downLoadExcel(@RequestBody String req,HttpServletResponse response) throws IOException {
        Integer pId = Integer.parseInt(JSONObject.parseObject(req).get("id").toString());
        List<ScoreVM> scoreVMS = scoreVMService.searchMark(pId);
        poiService.downLoadExcel(scoreVMS);
        HashMap<Object,Object> map = new HashMap();
        map.put("code",200);
        return map;
    }
}
