create table if not exists user
(
    id              bigint auto_increment
    primary key comment '계정 식별값',
    name            varchar(50)  not null comment '이름',
    email           varchar(50)  not null comment '이메일',
    nickname        varchar(50)  not null comment '닉네임',
    password        varchar(128) null comment '비밀번호',
    phone_number    varchar(20)  not null comment '연락처',
    login_type      varchar(30)  not null comment '로그인 타입',
    social_id       varchar(200) null comment '소셜아이디',
    profile_picture varchar(200) null comment '프로필 사진 URL',
    use_yn          varchar(1)   not null comment '사용여부',
    created_at      TIMESTAMP  not null comment '생성일',
    updated_at      TIMESTAMP  not null comment '생성일',
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
    unique (email)
    );
