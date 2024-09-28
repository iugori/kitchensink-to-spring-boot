package ro.iugori.ks2sb.web;

import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.iugori.ks2sb.service.dto.MemberDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Validator validator;

    public MemberResourceRESTService(Validator validator) {
        this.validator = validator;
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
    public ResponseEntity<Map<String, String>> createMember(@RequestBody MemberDTO member) {
        var vResult = validator.validate(member);
        if (!vResult.isEmpty())  {
            var responseObj = new HashMap<String, String>();
            vResult.forEach(constraint -> responseObj.put(constraint.getPropertyPath().toString(), constraint.getMessage()));
            return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Map.of(), HttpStatus.OK);
    }

}
