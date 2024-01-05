package com.example.practice;

import com.example.practice.member_management.adapter.out.percistence.MemberEntity;
import com.example.practice.member_management.adapter.out.percistence.MemberRepository;
import com.example.practice.member_management.adapter.out.percistence.QMemberEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
//@DataJpaTest
class PracticeApplicationTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager entityManager;
    JPAQueryFactory queryFactory;
    @Test
    @DisplayName("Basic Test")
    public void saveMember() {
        MemberEntity member = MemberEntity.builder().age(30).name("하이").build();
        MemberEntity savedMember = memberRepository.save(member);
        log.info("Original Member: {}", member);
        log.info("Saved Member: {}", savedMember);
        // JPA Proxy 관련 지연로딩 문제로 오브젝트 간 비교 시 fail, id를 불러와서 비교하면 success.
        assertThat(memberRepository.findById(savedMember.getId()).orElseThrow().getId()).isEqualTo(member.getId());
    }

    private List<MemberEntity> getMemberEntityList(){
        MemberEntity member01 = MemberEntity.builder().age(33).name("구마적").build();
        MemberEntity member02 = MemberEntity.builder().age(31).name("신마적").build();
        MemberEntity member03 = MemberEntity.builder().age(32).name("쌍칼").build();
        MemberEntity member04 = MemberEntity.builder().age(36).name("구마적").build();
        return Arrays.asList(member01, member02, member03, member04);
    }
    @Test
    @DisplayName("Querydsl Test")
    public void saveMemberWithQueryDsl() {
        memberRepository.saveAll(getMemberEntityList());
        queryFactory = new JPAQueryFactory(entityManager);
        QMemberEntity memberEntity = QMemberEntity.memberEntity;
        JPAQuery<MemberEntity> query = queryFactory.selectFrom(memberEntity).where(memberEntity.id.goe(2).and(memberEntity.name.eq("구마적")));
        List<MemberEntity> memberEntities = query.fetch();
        for(MemberEntity me: memberEntities ){
            log.info("[id]: {}", me.getId());
            log.info("[age]: {}", me.getAge());
            log.info("[name]: {}", me.getName());
        }
        assertThat(memberEntities.getFirst().getId()).isEqualTo(4);
    }
}

//import com.example.practice.member_management.adapter.out.percistence.MemberEntity;
//import com.example.practice.member_management.adapter.out.percistence.MemberRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import static org.mockito.Mockito.when;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//public class PracticeApplicationTests {
//
//    @MockBean
//    private MemberRepository memberRepository;
//
//    @Test
//    public void saveMember() {
//        MemberEntity member = MemberEntity.builder().age(30).name("하이").build();
//        when(memberRepository.save(member)).thenReturn(MemberEntity.builder().id(1L).age(30).name("하이").build());
//        MemberEntity savedMember = memberRepository.save(member);
//        log.info("Original Member: {}", member);
//        log.info("Saved Member: {}", savedMember);
//        // JPA Proxy 관련 지연로딩 문제로 오브젝트 간 비교 시 fail, id를 불러와서 비교하면 success.
//        assertThat(memberRepository.findById(savedMember.getId()).orElseThrow().getId()).isEqualTo(member.getId());
//    }
//}


