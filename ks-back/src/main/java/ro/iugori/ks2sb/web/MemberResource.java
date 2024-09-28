package ro.iugori.ks2sb.web;

import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import ro.iugori.ks2sb.config.REST;
import ro.iugori.ks2sb.dto.Member;
import ro.iugori.ks2sb.service.MemberService;
import ro.iugori.ks2sb.util.mapping.MemberMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(REST.PATH_MEMBER)
public class MemberResource {

    private final Validator validator;
    private final MemberService memberService;

    public MemberResource(Validator validator, MemberService memberService) {
        this.validator = validator;
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<List<Member>> listAllMembers() {
        var entities = memberService.findAllOrderedByName();
        var dtoList = entities.stream().map(MemberMapper::entity2dto).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> lookupMemberById(@PathVariable("id") long id) {
        var member = memberService.findById(id);
        if (member == null) {
            return new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(MemberMapper.entity2dto(member), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createMember(@RequestBody Member member) {
        var vResult = validator.validate(member);
        if (!vResult.isEmpty()) {
            var responseObj = new HashMap<String, String>();
            vResult.forEach(constraint -> responseObj.put(constraint.getPropertyPath().toString(), constraint.getMessage()));
            return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
        }
        try {
            var memberId = memberService.create(member);
            var headers = new LinkedMultiValueMap<String, String>();
            headers.put("Location", List.of(REST.PATH_MEMBER + "/" + memberId));
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (ValidationException ex) {
            var responseObj = new HashMap<String, String>();
            responseObj.put("email", "Email taken");
            return new ResponseEntity<>(responseObj, HttpStatus.CONFLICT);
        } catch (Exception ex) {
            var responseObj = new HashMap<String, String>();
            responseObj.put("error", ex.getMessage());
            return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
        }
    }

}
