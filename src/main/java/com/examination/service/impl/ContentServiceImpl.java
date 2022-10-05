package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Content;
import com.examination.bean.Question;
import com.examination.mapper.ContentMapper;
import com.examination.mapper.QuestionMapper;
import com.examination.service.ContentService;
import com.examination.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author he
 * @Data 2022/10/5 2:08
 */
@Service
public class ContentServiceImpl  extends ServiceImpl<ContentMapper, Content> implements ContentService {
}
