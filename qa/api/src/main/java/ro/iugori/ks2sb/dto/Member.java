package ro.iugori.ks2sb.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

}
