package com.chengxi.fitday.service.impl;

import com.chengxi.fitday.entity.Question;
import com.chengxi.fitday.mapper.QuestionMapper;
import com.chengxi.fitday.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

}
