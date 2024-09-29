package ro.iugori.ks2sb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ro.iugori.ks2sb.domain.MemberEntity;
import ro.iugori.ks2sb.repository.MemberRepository;

@Component
public class StartupApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(StartupApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var memberRepository = event.getApplicationContext().getBean(MemberRepository.class);

        var member = new MemberEntity();
        member.setName("John Smith");
        member.setEmail("john.smith@mailinator.com");
        member.setPhoneNumber("2125551212");

        memberRepository.findByEmail(member.getEmail()).ifPresentOrElse(
                (m) -> log.info("Database successfully initialized"),
                () -> {
                    try {
                        memberRepository.saveAndFlush(member);
                        log.warn("Had to manually add John Smith!");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

}
