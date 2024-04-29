package fk.page.finekorea.controller;


import fk.page.finekorea.domain.entity.QuestionEntity;
import fk.page.finekorea.dto.QuestionCommentDto;
import fk.page.finekorea.dto.QuestionDto;
import fk.page.finekorea.dto.UploadResultDto;
import fk.page.finekorea.service.QuestionService;
import fk.page.finekorea.service.SaveFile;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class QuestionController {

    @Value("${com.example.upload.path}")
    private String uploadPath;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private SaveFile saveFile;
    //질문과 답변

    @GetMapping("/guest/questionList")
    public String question(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size) {
        model.addAttribute("questionPage", questionService.getQuestionlist(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "num"))));
        return "question/question";
    }

    @GetMapping("/guest/question/keyword")
    @ResponseBody
    public Page<QuestionEntity> question(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "num"));
        return questionService.getQuestionlist(keyword, pageable);
    }

    @GetMapping("/guest/question/writeForm")
    public String writeForm() {
        return "question/questionWritingPage";
    }

    @GetMapping("/guest/question/view")
    public String questionView(Model model, Long num){
        questionService.increaseViewCount(num);
        QuestionDto questionDto =  questionService.questionView(num);
        if(questionDto.getFileList() != null){
            List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
            model.addAttribute("uploadResultDtoList", uploadResultDtoList);
        }
        model.addAttribute("question", questionDto);
        return "question/questionDetailPage";
    }
    @PostMapping("/guest/question/view")
    public String questionView(Model model, Long num, String password, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size){
        if(!password.equals("")){
            if(questionService.isPassword(num,password)){
                questionService.increaseViewCount(num);
                QuestionDto questionDto = questionService.questionView(num);
                if(questionDto.getFileList() != null){
                    List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
                    model.addAttribute("uploadResultDtoList", uploadResultDtoList);
                }
                model.addAttribute("question", questionDto);

                return "question/questionDetailPage";
            }else{
                model.addAttribute("questionPage", questionService.getQuestionlist(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "num"))));
                model.addAttribute("failText", "비밀번호가 틀렸습니다.");
                return "question/question";
            }
        }else{
            model.addAttribute("questionPage", questionService.getQuestionlist(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "num"))));
            model.addAttribute("failText", "비밀번호를 입력해주세요.");
            return "question/question";
        }
    }

    @RequestMapping("/guest/question/next")
    @ResponseBody
    public ResponseEntity<List<QuestionDto>> nextQuestion(HttpServletResponse response, @RequestParam(value = "id", required=false) Long id){
        return ResponseEntity.ok(questionService.nextQuestion(id));
    }

    @PostMapping("/guest/question/uploadQuestion")
    public String uploadFile(MultipartFile[] uploadFiles, QuestionDto questionDto, Model model){
        String strNumber = "";

        for(MultipartFile uploadFile : uploadFiles){
            if(uploadFile.getSize() > 0){
                if(uploadFile.getContentType().startsWith("image") == false){
                    model.addAttribute("failText", "이미지 파일이 아닙니다.");
                    model.addAttribute("searchUrl", "/guest/question/writeForm");
                    return "question/questionWritingPage";
                }
                String originalName = uploadFile.getOriginalFilename();
                String changeName = saveFile.getSaveFileInfo(uploadFile, uploadPath + "/uploadfiles");
                String filePath = "/image/uploadfiles/" + changeName;
                String folderPath = uploadPath + "/uploadfiles";
                Long size = uploadFile.getSize();
                strNumber += String.valueOf(saveFile.saveFile(new UploadResultDto(originalName, changeName, filePath, folderPath, size))) + ",";
            }
        }
        if (!strNumber.equals("") && strNumber.endsWith(",")){
            strNumber = strNumber.substring(0, strNumber.length() - 1);
            questionDto.setFileList(strNumber);
        }

        questionService.saveQuestion(questionDto);

        return "redirect:/guest/questionList";
    }

    @PostMapping("/guest/question/updateform")
    public String questionUpdateView(Model model, Long num, String password){
        if(!password.equals("")){
            if(questionService.isPassword(num,password)){
                questionService.increaseViewCount(num);
                QuestionDto questionDto = questionService.questionView(num);
                if(questionDto.getFileList() != null){
                    List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
                    model.addAttribute("uploadResultDtoList", uploadResultDtoList);
                }
                questionDto.setPassword(password);
                model.addAttribute("question", questionDto);
                return "question/questionUpdatePage";
            }else{
                QuestionDto questionDto = questionService.questionView(num);
                if(questionDto.getFileList() != null){
                    List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
                    model.addAttribute("uploadResultDtoList", uploadResultDtoList);
                }
                model.addAttribute("question", questionDto);
                model.addAttribute("failText", "비밀번호가 틀렸습니다.");
                return "question/questionDetailPage";
            }
        }else{
            QuestionDto questionDto = questionService.questionView(num);
            if(questionDto.getFileList() != null){
                List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
                model.addAttribute("uploadResultDtoList", uploadResultDtoList);
            }
            model.addAttribute("question", questionDto);
            model.addAttribute("failText", "비밀번호를 입력해주세요.");
            return "question/questionDetailPage";
        }
    }

    @PostMapping("/guest/question/update")
    public String questionUpdate(Model model, QuestionDto questionDto, MultipartFile[] upfiles){
        String strNumber = "";

        for(MultipartFile upfile : upfiles){
            if(upfile.getSize() > 0){
                if(!questionDto.getFileList().equals("")){
                    List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
                    for(UploadResultDto u : uploadResultDtoList){
                        if(saveFile.deleteFileInfo(uploadPath + "/uploadfiles", u.getChangeName()).equals("s")){
                            saveFile.deleteFile(u.getId());
                        }
                    }
                    questionDto.setFileList("");
                }
                if(upfile.getContentType().startsWith("image") == false){
                    model.addAttribute("failText", "이미지 파일이 아닙니다.");
                    model.addAttribute("searchUrl", "/guest/question/writeForm");
                    return "question/questionWritingPage";
                }
                String originalName = upfile.getOriginalFilename();
                String changeName = saveFile.getSaveFileInfo(upfile, uploadPath + "/uploadfiles");
                String filePath = "/image/uploadfiles/" + changeName;
                String folderPath = uploadPath + "/uploadfiles";
                Long size = upfile.getSize();
                strNumber += String.valueOf(saveFile.saveFile(new UploadResultDto(originalName, changeName, filePath, folderPath, size))) + ",";

            }
        }

        if (!strNumber.equals("") && strNumber.endsWith(",")){
            strNumber = strNumber.substring(0, strNumber.length() - 1);
            questionDto.setFileList(strNumber);
        }

        questionService.saveQuestion(questionDto);

        return "redirect:/guest/questionList";
    }

    @PostMapping("/guest/question/delete")
    public String questionDelete(Model model, Long num, String password, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size) {
        if (password.length() > 0) {
            if (questionService.isPassword(num, password)) {
                QuestionDto questionDto = questionService.questionView(num);
                if(questionDto.getFileList().length() > 0){
                    List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
                    for(UploadResultDto u : uploadResultDtoList) {
                        if (saveFile.deleteFileInfo(uploadPath + "/uploadfiles", u.getChangeName()).equals("s")) {
                            saveFile.deleteFile(u.getId());
                        }
                    }
                }
                questionService.deleteQuestion(num);
                model.addAttribute("failText", "게시물을 삭제하였습니다.");
                model.addAttribute("questionPage", questionService.getQuestionlist(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "num"))));
                return "question/question";
            }else{
                QuestionDto questionDto = questionService.questionView(num);
                if(questionDto.getFileList() != null){
                    List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
                    model.addAttribute("uploadResultDtoList", uploadResultDtoList);
                }
                model.addAttribute("failText", "비밀번호가 틀렸습니다.");
                model.addAttribute("question", questionDto);
                return "question/questionDetailPage";
            }
        }
            QuestionDto questionDto = questionService.questionView(num);
            if(questionDto.getFileList() != null){
                List<UploadResultDto> uploadResultDtoList = saveFile.getFile(questionDto.getFileList());
                model.addAttribute("uploadResultDtoList", uploadResultDtoList);
            }
            model.addAttribute("failText", "비밀번호를 입력하세요.");
            model.addAttribute("question", questionDto);
            return "question/questionDetailPage";
    }

    @RequestMapping("/guest/question/comment/view")
    @ResponseBody
    public ResponseEntity<List<QuestionCommentDto>> commentView(HttpServletResponse response, @RequestParam(value = "num", required=false) Long num){
        return ResponseEntity.ok(questionService.commentView(num));
    }


    @RequestMapping("/guest/question/comment/write")
    @ResponseBody
    public ResponseEntity<List<QuestionCommentDto>> commentWrite(HttpServletResponse response, @RequestParam(value = "num", required=false) Long num, @RequestBody QuestionCommentDto questionCommentDto){
        return ResponseEntity.ok(questionService.commentWrite(num, questionCommentDto));
    }

    @RequestMapping("/guest/question/comment/update")
    @ResponseBody
    public ResponseEntity<List<QuestionCommentDto>> commentUpdate(HttpServletResponse response, @RequestParam(value = "num", required=false) Long num, @RequestBody QuestionCommentDto questionCommentDto){
        return ResponseEntity.ok(questionService.commentUpdate(num, questionCommentDto, questionCommentDto.getPassword()));
    }

    @RequestMapping("/guest/question/comment/delete")
    @ResponseBody
    public ResponseEntity<List<QuestionCommentDto>> commentDelete(HttpServletResponse response, @RequestParam(value = "num", required=false) Long num, @RequestBody QuestionCommentDto questionCommentDto){
        return ResponseEntity.ok(questionService.commentDelete(num, questionCommentDto));
    }
}
