package ro.iugori.ks2sb.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.iugori.ks2sb.service.dto.MemberDTO;

import java.util.List;

@RestController
@RequestMapping("/rest/members")
public class MemberResourceRESTService {

    private static MemberDTO buildMemberDTO(long id) {
        if (id != 0) {
            return null;
        }
        var member = new MemberDTO();
        member.setId(0L);
        member.setName("John Smith");
        member.setEmail("john.smith@mailinator.com");
        member.setPhoneNumber("2125551212");
        return member;
    }

    @GetMapping()
    public ResponseEntity<List<MemberDTO>> listAllMembers() {
        return new ResponseEntity<>(List.of(buildMemberDTO(0L)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> lookupMemberById(@PathVariable("id") long id) {
        var member = buildMemberDTO(id);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createMember(@Valid @RequestBody MemberDTO member) {
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

}
