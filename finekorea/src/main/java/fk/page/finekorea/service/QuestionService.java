package fk.page.finekorea.service;


import fk.page.finekorea.domain.entity.QuestionComment;
import fk.page.finekorea.domain.entity.QuestionEntity;
import fk.page.finekorea.domain.repository.QuestionCommentRepository;
import fk.page.finekorea.domain.repository.QuestionRepository;
import fk.page.finekorea.dto.QuestionCommentDto;
import fk.page.finekorea.dto.QuestionDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionCommentRepository questionCommentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //질문과 문의 작성
    public void saveQuestion(QuestionDto questionDto){
        QuestionEntity questionEntity = QuestionEntity.builder()
                .num(questionDto.getNum())
                .ninkName(questionDto.getNinkName())
                .password(passwordEncoder.encode(questionDto.getPassword()))
                .title(questionDto.getTitle())
                .content(questionDto.getContent())
                .writingCheck(questionDto.isWritingCheck())
                .fileList(questionDto.getFileList())
                .build();
        questionRepository.save(questionEntity);
    }

    public Page<QuestionEntity> getQuestionlist(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public QuestionDto questionView(Long num) {
        QuestionEntity questionEntity = questionRepository.findById(num).get();
        QuestionDto questionDto = QuestionDto.builder()
                .num(questionEntity.getNum())
                .ninkName(questionEntity.getNinkName())
                .title(questionEntity.getTitle())
                .content(questionEntity.getContent())
                .view(questionEntity.getView())
                .writingCheck(questionEntity.isWritingCheck())
                .fileList(questionEntity.getFileList())
                .createdDate(questionEntity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifiedDate(questionEntity.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        return questionDto;
    }

    public void increaseViewCount(Long num){
        QuestionEntity questionEntity = questionRepository.findById(num).orElseThrow(() -> new EntityNotFoundException("question not found"));
        questionEntity.setView(questionEntity.getView() + 1);
        questionRepository.save(questionEntity);
    }

    public boolean isPassword(Long num,String password) {
        QuestionEntity questionEntity = questionRepository.findById(num).orElseThrow(() -> new EntityNotFoundException("question not found"));
        if(questionEntity != null && passwordEncoder.matches(password, questionEntity.getPassword())){
            return true;
        }else{
            return false;
        }
    }

    public Page<QuestionEntity> getQuestionlist(String keyword, Pageable pageable) {
        return questionRepository.findByTitleContaining(keyword, pageable);
    }

    public void deleteQuestion(Long num){
        questionRepository.deleteById(num);
    }

    public List<QuestionCommentDto> commentView(Long num){
        List<QuestionComment> questionCommentList = questionCommentRepository.findByQuestionEntityNum(num);
        List<QuestionCommentDto> questionCommentDtoList = new ArrayList<>();
        for(QuestionComment questionComment : questionCommentList){
            QuestionCommentDto questionCommentDto = questionComment.toEntity();
            questionCommentDto.setModifiedDate( questionComment.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            questionCommentDto.setCreatedDate( questionComment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            questionCommentDtoList.add(questionCommentDto);
        }
        return questionCommentDtoList;
    }

    public List<QuestionCommentDto> commentWrite(Long num, QuestionCommentDto questionCommentDto){
        QuestionEntity questionEntity = questionRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        QuestionComment questionComment = questionCommentDto.toEntity();
        questionComment.setPassword(passwordEncoder.encode(questionComment.getPassword()));
        questionComment.setQuestionEntity(questionEntity);
        questionCommentRepository.save(questionComment);

        List<QuestionCommentDto> questionCommentDtoList = new ArrayList<>();
        List<QuestionComment> questionCommentList = questionCommentRepository.findByQuestionEntityNum(num);
        for(QuestionComment q : questionCommentList){
            QuestionCommentDto qDto = q.toEntity();
            qDto.setCreatedDate(q.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            qDto.setModifiedDate(q.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            questionCommentDtoList.add(qDto);
        }
        return questionCommentDtoList;
    }

    public List<QuestionCommentDto> commentUpdate(Long num, QuestionCommentDto questionCommentDto, String password){
        QuestionEntity questionEntity = questionRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        QuestionComment questionComment = questionCommentRepository.findById(questionCommentDto.getId()).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        List<QuestionCommentDto> questionCommentDtoList = new ArrayList<>();
        List<QuestionComment> questionCommentList = questionCommentRepository.findByQuestionEntityNum(num);
        if(questionComment!= null && passwordEncoder.matches(password, questionComment.getPassword())){
            questionComment = questionCommentDto.toEntity();
            questionComment.setPassword(passwordEncoder.encode(questionComment.getPassword()));
            questionComment.setQuestionEntity(questionEntity);
            questionCommentRepository.save(questionComment);
            for(QuestionComment q : questionCommentList){
                QuestionCommentDto qDto = q.toEntity();
                qDto.setCreatedDate(q.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
                qDto.setModifiedDate(q.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
                questionCommentDtoList.add(qDto);
            }
        }

        return questionCommentDtoList;
    }

    public List<QuestionCommentDto> commentDelete(Long num, QuestionCommentDto questionCommentDto){
        QuestionEntity questionEntity = questionRepository.findById(num).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        QuestionComment questionComment = questionCommentRepository.findById(questionCommentDto.getId()).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if (questionComment != null && passwordEncoder.matches(questionCommentDto.getPassword(), questionComment.getPassword())) {
            questionCommentRepository.deleteById(questionCommentDto.getId());
        }
        List<QuestionCommentDto> questionCommentDtoList = new ArrayList<>();
        List<QuestionComment> questionCommentList = questionCommentRepository.findByQuestionEntityNum(num);
        for(QuestionComment q : questionCommentList){
            QuestionCommentDto qDto = q.toEntity();
            qDto.setCreatedDate(q.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            qDto.setModifiedDate(q.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            questionCommentDtoList.add(qDto);
        }

        return questionCommentDtoList;
    }

    public List<QuestionDto> nextQuestion(Long id) {
        List<QuestionDto> questionDtoList = new ArrayList<>();
        List<QuestionEntity> questionEntityList = questionRepository.findAll();
        for(int i = 0; i < questionEntityList.size(); i++){
            if(questionEntityList.get(i).getNum() == id && i > 0){
                questionDtoList.add(questionEntityList.get(i - 1).toEntity());
            }
            if(questionEntityList.get(i).getNum() == id && i < questionEntityList.size() - 1){
                questionDtoList.add(questionEntityList.get(i + 1).toEntity());
            }
        }
        return questionDtoList;
    }
}
