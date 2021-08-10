package com.jun.ex2.repository;

import com.jun.ex2.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    //Memo 객체의 mno값 사이의 객체들을 구하고 mno의 역순으로 정렬하는 쿼리메서드
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    //쿼리메서드 + Pageable의 결합 : 좀 더 간단한 형태의 쿼리메서드 선언(정렬은 Pageable이 해줌)
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    //쿼리메서드 : deleteBy 삭제 처리
    void deleteMemoByMnoLessThan(Long num);

}
