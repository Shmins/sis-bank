package com.bank.app.entity.administrator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.administrator.model.approve.ApproveAccount;
import com.bank.app.entity.administrator.model.approve.ApproveBorrowing;
import com.bank.app.entity.administrator.model.approve.ApproveCards;
import com.bank.app.entity.administrator.model.approve.ApproveOfficial;

public interface ApproveRepository extends MongoRepository<Approve, String> {
    @Query(value = "{'typeApproved': 'borrowing', 'isApproved': false}" )
    List<ApproveBorrowing> findAllBorrowing();
    @Query(value = "{'typeApproved': 'cards', 'isApproved': false}" )
    List<ApproveCards> findAllCards();
    @Query(value = "{'typeApproved': 'official', 'isApproved': false}" )
    List<ApproveOfficial> findAllOfficial();
    @Query(value = "{'typeApproved': 'account', 'isApproved': false}" )
    List<ApproveAccount> findAllAccount();
    @Query(value = "{'typeApproved': 'borrowing', 'borrowing.id': 0?}" )
    List<ApproveBorrowing> findByBorrowing(String id);
    @Query(value = "{'typeApproved': 'cards', 'cards.numberCard': 0?}" )
    List<ApproveCards> findByCards(String id);
    @Query(value = "{'typeApproved': 'official', 'official.cpf': 0?}" )
    List<ApproveOfficial> findByOfficial(String id);
    @Query(value = "{'typeApproved': 'account', 'account.id': 0?}" )
    List<ApproveAccount> findByAccount(String id);
}
