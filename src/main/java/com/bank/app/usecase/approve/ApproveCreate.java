package com.bank.app.usecase.approve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.administrator.repository.ApproveRepository;


@Service
public class ApproveCreate {
    @Autowired
    private ApproveRepository approveRepository;

    public Approve createApprove(Approve approve) {
        return this.approveRepository.insert(approve);
    }

    public void insertMany(Iterable<Approve> approve) {
        this.approveRepository.saveAll(approve);
    }
}
