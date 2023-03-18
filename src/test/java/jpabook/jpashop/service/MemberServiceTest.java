package jpabook.jpashop.service;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepository;

    @Autowired
    EntityManager em;
    @Test
    public void 회원가입() throws Exception{
        //given
        Member member= new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1=new Member();
        member1.setName("kim");

        Member member2=new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);

        //then
        assertThatThrownBy(()->memberService.join(member2))
                .isInstanceOf(IllegalStateException.class);

    }

}