package com.bank.app.usecase.approve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.repository.ApproveRepository;


@Service
public class ApproveDelete {
    @Autowired
    private ApproveRepository approveRepository;

    public void deleteById(String cpf) {
        this.approveRepository.deleteById(cpf);
    }
}
