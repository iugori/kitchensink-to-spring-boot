package ro.iugori.ks2sb.service;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.iugori.ks2sb.domain.MemberEntity;
import ro.iugori.ks2sb.dto.Member;
import ro.iugori.ks2sb.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void create() {
        var charlie = new Member();
        charlie.setName("Charlie");
        charlie.setEmail("charlie@example.com");
        charlie.setPhoneNumber("7778889999");

        var charlieEntity = new MemberEntity();
        charlieEntity.setId(10L);
        when(memberRepository.saveAndFlush(any(MemberEntity.class))).thenReturn(charlieEntity);

        var charlieId = memberService.create(charlie);

        assertThat(charlieId).isEqualTo(10L);
        verify(memberRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void createDuplicateEmail() {
        var charlie = new Member();
        charlie.setName("Charlie");
        charlie.setEmail("charlie@example.com");
        charlie.setPhoneNumber("7778889999");

        when(memberRepository.findByEmail(charlie.getEmail())).thenReturn(Optional.of(new MemberEntity()));

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> memberService.create(charlie))
                .withMessage("Unique Email Violation");
        verify(memberRepository, times(1)).findByEmail(charlie.getEmail());
    }

}