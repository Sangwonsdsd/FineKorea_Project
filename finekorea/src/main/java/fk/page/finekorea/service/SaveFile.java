package fk.page.finekorea.service;


import fk.page.finekorea.domain.entity.ItemsFileEntity;
import fk.page.finekorea.domain.entity.UploadEntity;
import fk.page.finekorea.domain.repository.ItemsFileRepository;
import fk.page.finekorea.domain.repository.SaveFileRepository;
import fk.page.finekorea.dto.ItemsFileDto;
import fk.page.finekorea.dto.UploadResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SaveFile {
    @Autowired
    private SaveFileRepository saveFileRepository;

    @Autowired
    private ItemsFileRepository itemsFileRepository;


    public String getSaveFileInfo(MultipartFile upfile, String path) {
        // 파일명 수정 후 서버 업로드 시키기("이미지저장용 (2).jpg" => 20231109102712345.jpg)
        // 년월일시분초 + 랜덤숫자 5개 + 확장자

        // 원래 파일명
        String originName = upfile.getOriginalFilename();

        // 시간정보 (년월일시분초)
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // 랜덤숫자 5자리
        int ranNum = (int) (Math.random() * 90000) + 10000;

        // 확장자
        String ext = originName.substring(originName.lastIndexOf("."));

        // 변경된이름
        String changeName = currentTime + ranNum + ext;
        String saveName = path + File.separator + changeName;
        log.info("saveName :" + saveName);
        // 첨부파일 저장할 폴더의 물리적인 경우
        Path savePath = Paths.get(saveName);
        try {
            upfile.transferTo(savePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return changeName;
    }

    public String deleteFileInfo(String path, String changeName){
        String filePath = path + File.separator + changeName;
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                log.info("파일이 삭제되었습니다.");
                return "s";
            } else {
                log.info("파일 삭제에 실패했습니다.");
                return "f";
            }
        } else {
            log.info("파일이 존재하지 않습니다.");
            return "f";
        }
    }
    public void deleteFile(Long id){
        saveFileRepository.deleteById(id);
    }

    public Long saveFile(UploadResultDto uploadResultDto){
        UploadEntity uploadEntity = UploadEntity.builder()
                .originalName(uploadResultDto.getOriginalName())
                .changeName(uploadResultDto.getChangeName())
                .filePath(uploadResultDto.getFilePath())
                .folderPath(uploadResultDto.getFolderPath())
                .size(uploadResultDto.getSize())
                .build();
        UploadEntity upload = saveFileRepository.save(uploadEntity);
        return upload.getId();
    }


    public List<UploadResultDto> getFile(String fileList) {
        String[] fList = fileList.split(",");
        List<Long> numbers = new ArrayList<>();
        List<UploadEntity> uploadEntities = new ArrayList<>();
        for(String numberStr : fList){
            Long id = Long.parseLong(numberStr.trim());
            Optional<UploadEntity> uploadOptional = saveFileRepository.findById(id);
            if (uploadOptional.isPresent()) {
                UploadEntity upload = uploadOptional.get();
                uploadEntities.add(upload);
            }else{
                log.info("file 불러오기 에러");
            }
        }

        List<UploadResultDto> uploadResultDtos = new ArrayList<>();
        for(UploadEntity uploadEntity : uploadEntities){
            UploadResultDto uploadResultDto = UploadResultDto.builder()
                    .id(uploadEntity.getId())
                    .originalName(uploadEntity.getOriginalName())
                    .changeName(uploadEntity.getChangeName())
                    .filePath(uploadEntity.getFilePath())
                    .folderPath(uploadEntity.getFolderPath())
                    .size(uploadEntity.getSize())
                    .build();

            uploadResultDtos.add(uploadResultDto);
        }
        return uploadResultDtos;
    }

    public Long saveFile(ItemsFileDto itemsFileDto){
        ItemsFileEntity itemsFileEntity = itemsFileDto.toEntity();
        ItemsFileEntity itemsFileEntity1 = itemsFileRepository.save(itemsFileEntity);
        return itemsFileEntity1.getId();
    }

    public List<ItemsFileDto> getItmesFile(String fileList) {
        String[] fList = fileList.split(",");
        List<Long> numbers = new ArrayList<>();
        List<ItemsFileEntity> uploadEntities = new ArrayList<>();
        for(String numberStr : fList){
            Long id = Long.parseLong(numberStr.trim());
            Optional<ItemsFileEntity> uploadOptional = itemsFileRepository.findById(id);
            if (uploadOptional.isPresent()) {
                ItemsFileEntity upload = uploadOptional.get();
                uploadEntities.add(upload);
            }else{
                log.info("file 불러오기 에러");
            }
        }

        List<ItemsFileDto> itemsFileDtos = new ArrayList<>();
        for(ItemsFileEntity uploadEntity : uploadEntities){
            ItemsFileDto itemsFileDto = uploadEntity.toEntity();
            itemsFileDtos.add(itemsFileDto);
        }

        return itemsFileDtos;
    }

}
