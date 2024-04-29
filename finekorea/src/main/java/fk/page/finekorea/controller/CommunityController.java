package fk.page.finekorea.controller;


import fk.page.finekorea.domain.entity.CommunityEntity;
import fk.page.finekorea.dto.CommunityDto;
import fk.page.finekorea.dto.UploadResultDto;
import fk.page.finekorea.service.CommunityService;
import fk.page.finekorea.service.SaveFile;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
public class CommunityController {

    @Value("${com.example.upload.path}")
    private String uploadPath;

    @Autowired
    private SaveFile saveFile;

    @Autowired
    private CommunityService communityService;

    @GetMapping("/guest/community")
    public String community(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        model.addAttribute("communityPage", communityService.selectList(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "communityId"))));
        return "announcement/announcement";
    }

    @GetMapping("/guest/community/writeform")
    public String writeform() {
        return "announcement/announcementWritingPage";
    }

    @PostMapping("/guest/community/write")
    public String communityWrite(MultipartFile[] uploadFiles, CommunityDto communityDto, Model model){
        String strNumber = "";
        for(MultipartFile uploadFile : uploadFiles){
            if(uploadFile.getSize() > 0){
                if(uploadFile.getContentType().startsWith("image") == false){
                    model.addAttribute("failText", "이미지 파일이 아닙니다.");
                    model.addAttribute("searchUrl", "/guest/community/writeform");
                    return "announcement/announcementWritingPage";
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
            communityDto.setFileList(strNumber);
        }
        communityService.saveCommunity(communityDto);
        return "redirect:/guest/community";
    }


    @GetMapping("/guest/community/view")
    public String slectCommunity(Model model, Long id){
        communityService.increaseViewCount(id);
        CommunityDto communityDto = communityService.selectCommunity(id);
        if(communityDto.getFileList() != null){
            List<UploadResultDto> uploadResultDtoList = saveFile.getFile(communityDto.getFileList());
            model.addAttribute("uploadResultDtoList", uploadResultDtoList);
        }
        model.addAttribute("community", communityDto);
        return "announcement/announcementDetailPage";
    }

    @GetMapping("/guest/community/keyword")
    @ResponseBody
    public Page<CommunityEntity> searchList(@RequestParam String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "communityId"));
        return communityService.searchList(keyword, pageable);
    }

    @RequestMapping("/guest/community/next")
    @ResponseBody
    public ResponseEntity<List<CommunityDto>> nextCommunity(HttpServletResponse response, @RequestParam(value = "id", required=false) Long id){
        return ResponseEntity.ok(communityService.nextCommunity(id));
    }

    @GetMapping("/guest/community/updateform")
    public String  updateform(Long id, Model model){
        model.addAttribute("community", communityService.selectCommunity(id));
        return "announcement/announcementUpdatePage";
    }

    @PostMapping("/guest/community/update")
    public String update(MultipartFile[] uploadFiles, CommunityDto communityDto, Model model){
        String strNumber = "";
        for(MultipartFile upfile : uploadFiles){
            log.info("upfile.getSize() : " + upfile.getSize());
            if(upfile.getSize() > 0){
                if(!communityDto.getFileList().equals("")){
                    List<UploadResultDto> uploadResultDtoList = saveFile.getFile(communityDto.getFileList());
                    for(UploadResultDto u : uploadResultDtoList){
                        if(saveFile.deleteFileInfo(uploadPath + "/uploadfiles", u.getChangeName()).equals("s")){
                            saveFile.deleteFile(u.getId());
                        }
                    }
                    communityDto.setFileList("");
                }
                if(upfile.getContentType().startsWith("image") == false){
                    model.addAttribute("failText", "이미지 파일이 아닙니다.");
                    model.addAttribute("searchUrl", "/guest/community/updateform");
                    return "announcement/announcementUpdatePage";
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
            communityDto.setFileList(strNumber);
        }
        communityService.saveCommunity(communityDto);
        return "redirect:/guest/community";
    }

    @GetMapping("/guest/community/delete")
    public String delete(Long id, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        communityService.delete(id);
        return "redirect:/guest/community";
    }
}
