package ro.iugori.ks2sb.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ro.iugori.ks2sb.domain.MemberEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
class MemberRepositoryTest {

    private static final String JANE_EMAIL = "jane@mailinator.com";
    private static final String JOHN_EMAIL = "john.smith@mailinator.com";

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByEmail() {
        var retrievedMember = memberRepository.findByEmail(JOHN_EMAIL);
        assertThat(retrievedMember).isPresent();

        var member = retrievedMember.get();
        assertThat(member.getId()).isEqualTo(0L);
        assertThat(member.getName()).isEqualTo("John Smith");
        assertThat(member.getPhoneNumber()).isEqualTo("2125551212");
    }

    @Test
    void insertAndList() {
        var newMember = new MemberEntity();
        newMember.setName("Jane Doe");
        newMember.setEmail(JANE_EMAIL);
        newMember.setPhoneNumber("2125551234");

        newMember = memberRepository.saveAndFlush(newMember);
        assertThat(newMember.getId()).isGreaterThan(0L);

        var allMembers = memberRepository.findByOrderByNameAsc();
        assertThat(allMembers.size()).isGreaterThanOrEqualTo(2);

        var janeFound = false;
        for (var m : allMembers) {
            if (janeFound) {
                if (m.getEmail().equals(JOHN_EMAIL)) {
                    return;
                }
            } else {
                janeFound = m.getEmail().equals(JANE_EMAIL);
            }
        }
        fail("Entities were not retrieved in ascending order by name");
    }

}