package com.examination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.examination.bean.Question;
import com.examination.bean.Type;
import com.examination.mapper.QuestionMapper;
import com.examination.mapper.TypeMapper;
import com.examination.service.TypeService;
import com.examination.utils.StaticVariableUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author he
 * @Data 2022/10/20 8:47
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private QuestionMapper questionMapper;

    @Override
    public Page<Type> pageBySearchName(Page<Type> page, String searchName) {
        LambdaQueryWrapper<Type> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Type::getQType,searchName);
        Page<Type> typePage = typeMapper.selectPage(page, queryWrapper);
        return typePage;
    }

    @Override
    public Integer selectPoolByIdAndDelete(int id) {
        LambdaQueryWrapper<Type> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Type::getQPool).eq(Type::getId,id);
        Type type = typeMapper.selectOne(queryWrapper);
        if (typeMapper.deleteById(id) != 0){
            if (type != null){
                Integer qPool = type.getQPool();
                LambdaUpdateWrapper<Question> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(Question::getQuestionPool,0).eq(Question::getQuestionPool,id);
                if (questionMapper.update(null,updateWrapper) != 0){
                    return StaticVariableUtil.SUCCESSCODE;
                }else{
                    return StaticVariableUtil.HALFSUCCESSCODE;
                }
            }
        }
        return StaticVariableUtil.FAILCODE;
    }
}
