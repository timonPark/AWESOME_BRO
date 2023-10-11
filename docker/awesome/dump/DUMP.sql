create table user
(
    id              bigint auto_increment comment '계정 식별값'
        primary key,
    name            varchar(50)            not null comment '이름',
    email           varchar(50)            not null comment '이메일',
    nickname        varchar(50)            not null comment '닉네임',
    password        varchar(128)           null comment '비밀번호',
    phone_number    varchar(20)            null comment '연락처',
    login_type      varchar(30)            not null comment '로그인 타입',
    social_id       varchar(200)           null comment '소셜아이디',
    profile_picture varchar(200)           null comment '프로필 사진 URL',
    use_yn          varchar(1) default 'y' not null comment '사용여부',
    created_at      timestamp              not null comment '생성일',
    updated_at      timestamp              not null comment '수정일',
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email)
);



create table authority
(
    id         bigint auto_increment comment '권한 식별값'
        primary key,
    name       varchar(100)           not null comment '이름',
    label      varchar(100)           not null comment '라벨',
    use_yn     varchar(1) default 'y' not null comment '사용여부',
    created_at timestamp              not null comment '생성일',
    updated_at timestamp              not null comment '수정일'
);


insert into AWESOME_BRO_SCHEMA.authority (id, name, label, use_yn, created_at, updated_at)
values  (1, 'SYSTEM_MANAGER', '시스템관리자', 'y', '2023-09-11 12:52:04', '2023-09-11 12:52:11'),
        (2, 'OPERATION_MANAGER', '운영관리자', 'y', '2023-09-11 12:53:07', '2023-09-11 12:53:17'),
        (3, 'USER', '사용자', 'y', '2023-09-11 12:53:38', '2023-09-11 12:53:39');


create table user_authority
(
    id           bigint auto_increment comment '계정 권한 식별값'
        primary key,
    user_id      bigint                 not null comment '계정 식별값',
    authority_id bigint                 not null comment '권한 식별값',
    use_yn       varchar(1) default 'y' not null comment '사용여부',
    created_at   timestamp              not null comment '생성일',
    updated_at   timestamp              not null comment '수정일',
    constraint FK6ktglpl5mjosa283rvken2py5
        foreign key (authority_id) references authority (id),
    constraint FKmi78l4ksrn5jtqo75vwlyjqr9
        foreign key (user_id) references user (id)
);