package com.bank.app.usecase.approve;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.administrator.repository.ApproveRepository;

@Service
public class ApproveSearch {
    @Autowired
    private ApproveRepository approveRepository;

    public Approve getApproveById(String cpf) {
        Optional<Approve> borrowing = this.approveRepository.findById(cpf);
        return borrowing.isPresent() ? borrowing.get() : null;
    }

    public List<Approve> getAll() {
        return this.approveRepository.findAll();
    }
}
