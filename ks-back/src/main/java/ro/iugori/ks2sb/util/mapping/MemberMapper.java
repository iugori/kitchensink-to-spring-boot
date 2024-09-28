package ro.iugori.ks2sb.util.mapping;

import ro.iugori.ks2sb.domain.MemberEntity;
import ro.iugori.ks2sb.dto.Member;

public class MemberMapper {

    public static void dto2entity(Member dto, MemberEntity entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
    }

    public static Member entity2dto(MemberEntity entity) {
        var dto = new Member();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return dto;
    }

}
