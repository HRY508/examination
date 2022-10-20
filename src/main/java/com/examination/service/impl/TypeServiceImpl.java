package com.examination.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Type;
import com.examination.mapper.TypeMapper;
import com.examination.service.TypeService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author he
 * @Data 2022/10/20 8:47
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
}
