package org.awesomeboro.awesome_bro.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum LoginType {
    NORMAL(0, "일반","normal"),
    KAKAO(1, "카카오","kakao"),
    NAVER(2, "네이버","naver"),
    GOOGLE(3, "구글","google");
    private final Integer code;
    private final String type;
    private final String name;

}
