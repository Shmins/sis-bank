package com.bank.app.usecase.approve;

import java.util.List;


import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.administrator.model.approve.ApproveAccount;
import com.bank.app.entity.administrator.model.approve.ApproveBorrowing;
import com.bank.app.entity.administrator.model.approve.ApproveCards;
import com.bank.app.entity.administrator.model.approve.ApproveOfficial;

public interface ApproveService {
    Approve createApprove(Approve approve);
    void insertMany(Iterable<Approve> approve);
    void deleteById(String cpf);
    Approve getApproveById(String cpf);
    List<Approve> getAll();
    Approve updateApprove(Approve approve);
    List<ApproveBorrowing> getAllBorrowings();
    List<ApproveCards> getAllCards();
    List<ApproveOfficial> getAllOfficial();
    List<ApproveAccount> getAllAccount();

}