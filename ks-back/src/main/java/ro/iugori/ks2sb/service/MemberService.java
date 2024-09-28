package ro.iugori.ks2sb.service;


import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.iugori.ks2sb.domain.MemberEntity;
import ro.iugori.ks2sb.dto.Member;
import ro.iugori.ks2sb.repository.MemberRepository;
import ro.iugori.ks2sb.util.mapping.MemberMapper;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long create(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new ValidationException("Unique Email Violation");
        }
        var entity = new MemberEntity();
        MemberMapper.dto2entity(member, entity);
        entity = memberRepository.saveAndFlush(entity);
        return entity.getId();
    }

    public List<MemberEntity> findAllOrderedByName() {
        return memberRepository.findByOrderByNameAsc();
    }

    public MemberEntity findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

}
