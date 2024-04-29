package fk.page.finekorea.controller;


import fk.page.finekorea.domain.entity.ItemsEntity;
import fk.page.finekorea.domain.entity.ItemsFileEntity;
import fk.page.finekorea.dto.ItemsDto;
import fk.page.finekorea.dto.ItemsFileDto;
import fk.page.finekorea.service.ItemsService;
import fk.page.finekorea.service.SaveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class ItemsController {

    @Value("${com.example.upload.path}")
    private String uploadPath;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private SaveFile saveFile;

    @GetMapping("/guest/production")
    public String itemProductionForm(Model model){
        model.addAttribute("productionItems", itemsService.selectList("production"));
        return "hotel/productionItems";
    }

    @GetMapping("/guest/hotel")
    public String itemHotelForm(Model model){
        model.addAttribute("productionItems", itemsService.selectList("hotel"));
        return "hotel/hotelProductionItems";
    }

    @GetMapping("/guest/production/writeForm")
    public String writeform(){
        return "hotel/productionWritingPage";
    }

    @PostMapping("/guest/production/write")
    public String writeItem(MultipartFile[] uploadFiles, ItemsDto itemsDto, Model model){
        ItemsEntity itemsEntity = itemsService.saveItems(itemsDto);

        if(itemsDto.getItems().equals("hotel")){
            for(MultipartFile uploadFile : uploadFiles){
                if(uploadFile.getSize() > 0){
                    if(uploadFile.getContentType().startsWith("image") == false){
                        model.addAttribute("failText", "이미지 파일이 아닙니다.");
                        model.addAttribute("searchUrl", "/guest/community/writeform");
                        return "hotel/productionWritingPage";
                    }
                    String originalName = uploadFile.getOriginalFilename();
                    String changeName = saveFile.getSaveFileInfo(uploadFile, uploadPath + "/Items/호텔이불");
                    String filePath = "/image/Items/호텔이불/" + changeName;
                    String folderPath = uploadPath + "/Items/호텔이불";
                    Long size = uploadFile.getSize();
                    ItemsFileDto itemsFileDto = new ItemsFileDto(originalName, changeName, filePath, folderPath, size, itemsEntity);
                    saveFile.saveFile(itemsFileDto);
                }
            }
            return "redirect:/guest/hotel";
        }else{
            for(MultipartFile uploadFile : uploadFiles){
                if(uploadFile.getSize() > 0){
                    if(uploadFile.getContentType().startsWith("image") == false){
                        model.addAttribute("failText", "이미지 파일이 아닙니다.");
                        model.addAttribute("searchUrl", "/guest/community/writeform");
                        return "hotel/productionWritingPage";
                    }
                    String originalName = uploadFile.getOriginalFilename();
                    String changeName = saveFile.getSaveFileInfo(uploadFile, uploadPath + "/Items/차렵이불");
                    String filePath = "/image/Items/차렵이불/" + changeName;
                    String folderPath = uploadPath + "/Items/차렵이불";
                    Long size = uploadFile.getSize();
                    ItemsFileDto itemsFileDto = new ItemsFileDto(originalName, changeName, filePath, folderPath, size, itemsEntity);
                    saveFile.saveFile(itemsFileDto);
                }
            }
            return "redirect:/guest/production";
        }
    }

    @GetMapping("/guest/production/updateform")
    public String itemUpdateform(Long id, Model model){
        model.addAttribute("itemDto", itemsService.selectItem(id));
        return "hotel/productionUpdatePage";
    }

    @PostMapping("/guest/production/update")
    public String itemUpdate(MultipartFile[] uploadFiles, ItemsDto itemsDto, Model model){
        ItemsDto itemsDto1 = itemsService.updateItems(itemsDto);

        if(itemsDto.getItems().equals("hotel")){
            for(MultipartFile uploadFile : uploadFiles){
                if(uploadFile.getSize() > 0){
                    for(ItemsFileEntity itemsFileEntity : itemsDto.getItemsFileEntityList()){
                        if(saveFile.deleteFileInfo(uploadPath + "/Items/호텔이불", itemsFileEntity.getChangeName()).equals("s")){
                            itemsService.deleteFileList(itemsDto);
                        }
                    }
                    if(uploadFile.getContentType().startsWith("image") == false){
                        model.addAttribute("failText", "이미지 파일이 아닙니다.");
                        return "/guest/production/updateform?id=" + itemsDto.getId();
                    }
                    String originalName = uploadFile.getOriginalFilename();
                    String changeName = saveFile.getSaveFileInfo(uploadFile, uploadPath + "/Items/호텔이불");
                    String filePath = "/image/Items/호텔이불/" + changeName;
                    String folderPath = uploadPath + "/Items/호텔이불";
                    Long size = uploadFile.getSize();
                    ItemsFileDto itemsFileDto = new ItemsFileDto(originalName, changeName, filePath, folderPath, size, itemsDto1.toEntity());
                    saveFile.saveFile(itemsFileDto);
                }
            }
            model.addAttribute("productionItems", itemsService.selectList("hotel"));
            return "hotel/hotelProductionItems";
        }else{
            for(MultipartFile uploadFile : uploadFiles){
                if(uploadFile.getSize() > 0){
                    for(ItemsFileEntity itemsFileEntity : itemsDto.getItemsFileEntityList()){
                        if(saveFile.deleteFileInfo(uploadPath + "/Items/차렵이불", itemsFileEntity.getChangeName()).equals("s")){
                            itemsService.deleteFileList(itemsDto);
                        }
                    }
                    if(uploadFile.getContentType().startsWith("image") == false){
                        model.addAttribute("failText", "이미지 파일이 아닙니다.");
                        return "/guest/production/updateform?id=" + itemsDto.getId();
                    }
                    String originalName = uploadFile.getOriginalFilename();
                    String changeName = saveFile.getSaveFileInfo(uploadFile, uploadPath + "/Items/차렵이불");
                    String filePath = "/image/Items/차렵이불/" + changeName;
                    String folderPath = uploadPath + "/Items/차렵이불";
                    Long size = uploadFile.getSize();
                    ItemsFileDto itemsFileDto = new ItemsFileDto(originalName, changeName, filePath, folderPath, size, itemsDto1.toEntity());
                    saveFile.saveFile(itemsFileDto);
                }
            }
            model.addAttribute("productionItems", itemsService.selectList("production"));
            return "hotel/productionItems";
        }

    }
    @GetMapping("/guest/production/delete")
    public String deleteItem(Long id, Model model){
        ItemsDto itemsDto = itemsService.selectItem(id);

        if(itemsDto.getItems().equals("hotel")){
            if(itemsDto.getItemsFileEntityList().size() > 0){
                for(ItemsFileEntity itemsFileEntity : itemsDto.getItemsFileEntityList()){
                    if(saveFile.deleteFileInfo(uploadPath + "/Items/호텔이불", itemsFileEntity.getChangeName()).equals("s")){
                        itemsService.deleteItem(id);
                    }
                }
            }else{
                itemsService.deleteItem(id);
            }
            model.addAttribute("productionItems", itemsService.selectList("hotel"));
            return "hotel/hotelProductionItems";
        }else{
            if(itemsDto.getItemsFileEntityList().size() > 0){
                for(ItemsFileEntity itemsFileEntity : itemsDto.getItemsFileEntityList()){
                    if(saveFile.deleteFileInfo(uploadPath + "/Items/차렵이불", itemsFileEntity.getChangeName()).equals("s")){
                        itemsService.deleteItem(id);
                    }
                }
            }else{
                itemsService.deleteItem(id);
            }
            model.addAttribute("productionItems", itemsService.selectList("production"));
            return "hotel/productionItems";
        }
    }

}
