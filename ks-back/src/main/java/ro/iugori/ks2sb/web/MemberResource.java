package ro.iugori.ks2sb.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.iugori.ks2sb.service.dto.MemberDTO;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class MemberResource {

    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> getAllUsers() {
        var member = new MemberDTO();
        member.setId(0L);
        member.setName("John Smith");
        member.setEmail("john.smith@mailinator.com");
        member.setPhoneNumber("2125551212");
        return new ResponseEntity<>(List.of(member), HttpStatus.OK);
    }

}
