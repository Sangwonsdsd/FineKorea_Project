package fk.page.finekorea.service;


import fk.page.finekorea.domain.entity.CommunityEntity;
import fk.page.finekorea.domain.repository.CommunityRepository;
import fk.page.finekorea.dto.CommunityDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveCommunity(CommunityDto communityDto){
        CommunityEntity community = communityDto.toEntity();
        communityRepository.save(community);
    }

    public Page<CommunityEntity> selectList(Pageable pageable){
        return communityRepository.findAll(pageable);
    }

    public void increaseViewCount(Long id) {
        CommunityEntity community = communityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("community not found"));
        community.setView(community.getView() + 1);
        communityRepository.save(community);
    }

    public CommunityDto selectCommunity(Long id) {
        CommunityEntity community = communityRepository.findById(id).get();
        return community.toEntity();
    }

    public Page<CommunityEntity> searchList(String keyword, Pageable pageable) {
        return communityRepository.findByTitleContaining(keyword, pageable);
    }

    public void delete(Long id) {
        communityRepository.deleteById(id);

    }

    public List<CommunityDto> nextCommunity(Long id) {
        List<CommunityDto> communityDtoList = new ArrayList<>();
        List<CommunityEntity> communityEntityList = communityRepository.findAll(Sort.by(Sort.Direction.ASC, "communityId"));

        for (int i = 0; i < communityEntityList.size(); i++) {
            if (communityEntityList.get(i).getCommunityId() == id && i > 0) {
                communityDtoList.add(communityEntityList.get(i - 1).toEntity()); // 현재 글의 이전 글 추가
            }
            if (communityEntityList.get(i).getCommunityId() == id && i < communityEntityList.size() - 1) {
                communityDtoList.add(communityEntityList.get(i + 1).toEntity()); // 현재 글의 다음 글 추가
            }
        }

        return communityDtoList;
    }
}
