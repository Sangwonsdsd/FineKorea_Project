package fk.page.finekorea.service;


import fk.page.finekorea.domain.entity.ItemsEntity;
import fk.page.finekorea.domain.repository.ItemsFileRepository;
import fk.page.finekorea.domain.repository.ItemsRepository;
import fk.page.finekorea.dto.ItemsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemsService {

    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private ItemsFileRepository itemsFileRepository;

    public ItemsEntity saveItems(ItemsDto itemsDto) {
      ItemsEntity itemsEntity = itemsRepository.save(itemsDto.toEntity());
      return itemsEntity;
    }

    public List<ItemsDto> selectList(String items){
        List<ItemsEntity> itemsEntities = itemsRepository.findByItems(items);
        List<ItemsDto> itemsDtoList = new ArrayList<>();
        for(ItemsEntity itemsEntity : itemsEntities){
            ItemsDto itemsDto = itemsEntity.toEntity();
            itemsDtoList.add(itemsDto);
        }
        return itemsDtoList;
    }

    public ItemsDto selectItem(Long id) {

        ItemsEntity itemsEntity =  itemsRepository.findById(id).get();
        return itemsEntity.toEntity();
    }

    public void deleteItem(Long id){
        itemsRepository.deleteById(id);
    }

    public void deleteFileList(ItemsDto itemsDto) {
        itemsFileRepository.deleteById(itemsDto.getId());
    }

    public ItemsDto updateItems(ItemsDto itemsDto) {
       ItemsEntity itemsEntity = itemsRepository.save(itemsDto.toEntity());
        return itemsEntity.toEntity();
    }
}
