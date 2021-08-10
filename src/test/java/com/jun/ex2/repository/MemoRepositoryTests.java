package com.jun.ex2.repository;

import com.jun.ex2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample...." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){
        //findById 사용 - findById 사용 순간에 SQL 처리완료

        //데이터베이스에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("================================");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2(){
        //getOne 사용 - 실제 객체를 사용하는 순간에 SQL 동작

        //데이터베이스에 존재하는 mno
        Long mno = 100L;

        //@Deprecated 되었음
        Memo memo = memoRepository.getOne(mno);

        System.out.println("================================");
        System.out.println(memo);
    }

    @Test
    public void testUpdate(){
        //save 사용 - Entity 객체 존재 확인(SELECT) 후 해당 @Id를 가진 Entity 객체가 있다면 update, 없다면 insert

        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete(){
        //deleteById 사용 - select 이후 delete
        //해당 데이터 존재하지 않으면 org.spring.framework.dao.EmptyDataAccessException 발생

        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    //페이징 처리
    //1. Oracle : 인 라인 뷰(inline view)
    //2. MySQL : limit
    //3. JPA : Dialect - JPA는 findAll() 메서드로 페이징 처리와 정렬을 합니다.
    //Spring Data JPA를 이용할 때 페이지 처리는 반드시 '0'부터 시작한다는 점
    @Test
    public void testPageDefault(){
        //1페이지 10개
        Pageable pageable = PageRequest.of(0,10 );

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("================================");
        System.out.println("Total Pages : " + result.getTotalPages());      //총 몇 페이지
        System.out.println("Total Count : " + result.getTotalElements());   //전체 갯수
        System.out.println("Page Number : " + result.getNumber());          //현재 페이지 번호
        System.out.println("Page Size : " + result.getSize());              //페이지당 데이터 갯수
        System.out.println("has next page? : " + result.hasNext());         //다음 페이지
        System.out.println("first page? : " + result.isFirst());            //시작 페이지(0) 여부
        System.out.println("================================");
        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
        System.out.println("================================");
    }

    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);    //and를 이용한 연결

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }
}
