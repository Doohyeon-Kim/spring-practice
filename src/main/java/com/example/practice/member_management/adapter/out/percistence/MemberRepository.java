package com.example.practice.member_management.adapter.out.percistence;


import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<MemberEntity, Integer>, QuerydslPredicateExecutor<MemberEntity> {

}
