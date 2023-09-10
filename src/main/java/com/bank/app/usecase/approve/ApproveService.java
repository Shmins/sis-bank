package com.bank.app.usecase.approve;

import java.util.List;


import com.bank.app.entity.administrator.model.approve.Approve;

public interface ApproveService {
    Approve createApprove(Approve approve);
    void insertMany(Iterable<Approve> approve);
    void deleteById(String cpf);
    Approve getApproveById(String cpf);
    List<Approve> getAll();
    Approve updateApprove(Approve approve);
}