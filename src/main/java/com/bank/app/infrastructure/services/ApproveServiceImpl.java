package com.bank.app.infrastructure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.entity.administrator.model.approve.ApproveAccount;
import com.bank.app.entity.administrator.model.approve.ApproveBorrowing;
import com.bank.app.entity.administrator.model.approve.ApproveCards;
import com.bank.app.entity.administrator.model.approve.ApproveOfficial;
import com.bank.app.entity.administrator.repository.ApproveRepository;
import com.bank.app.usecase.approve.ApproveService;

@Service
public class ApproveServiceImpl implements ApproveService {
    @Autowired
    private ApproveRepository approveRepository;

    @Override
    public Approve createApprove(Approve approve) {
        return this.approveRepository.insert(approve);
    }

    @Override
    public void insertMany(Iterable<Approve> approve) {
        this.approveRepository.saveAll(approve);
    }

    @Override
    public void deleteById(String id) {
        this.approveRepository.deleteById(id);
    }

    @Override
    public Approve getApproveById(String id) {
        Optional<Approve> borrowing = this.approveRepository.findById(id);
        return borrowing.isPresent() ? borrowing.get() : null;
    }

    @Override
    public List<Approve> getAll() {
        return this.approveRepository.findAll();
    }

    @Override
    public Approve updateApprove(Approve approve) {
        return approveRepository.save(approve);
    }

    @Override
    public List<ApproveBorrowing> getAllBorrowings() {
        return this.approveRepository.findAllBorrowing();
    }

    @Override
    public List<ApproveCards> getAllCards() {
        return this.approveRepository.findAllCards();
    }

    @Override
    public List<ApproveOfficial> getAllOfficial() {
        return this.approveRepository.findAllOfficial();

    }

    @Override
    public List<ApproveAccount> getAllAccount() {
        return this.approveRepository.findAllAccount();

    }

    @Override
    public List<ApproveBorrowing> getByBorrowing(String id) {
        return this.approveRepository.findByBorrowing(id);

    }

    @Override
    public List<ApproveCards> getByCards(String id) {
        return this.approveRepository.findByCards(id);

    }

    @Override
    public List<ApproveOfficial> getByOfficial(String id) {

        return this.approveRepository.findByOfficial(id);

    }

    @Override
    public List<ApproveAccount> getByAccount(String id) {
        return this.approveRepository.findByAccount(id);

    }
}
