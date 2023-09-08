package com.bank.app.usecase.approve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.administrator.repository.ApproveRepository;


@Service
public class ApproveUpdate {
    @Autowired
    private ApproveRepository approveRepository;

    public Approve updateBorrowing(Approve approve){
        return approveRepository.save(approve);
    }
}
