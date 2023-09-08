package com.bank.app.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.app.entity.administrator.model.approve.Approve;
import com.bank.app.usecase.approve.ApproveCreate;
import com.bank.app.usecase.approve.ApproveDelete;
import com.bank.app.usecase.approve.ApproveDto;
import com.bank.app.usecase.approve.ApproveSearch;
import com.bank.app.usecase.approve.ApproveUpdate;

@RestController
@RequestMapping("approve/v1")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ROLE_ADM') or hasRole('ROLE_BOSS')")
public class ApproveController {
    @Autowired
    private ApproveSearch approveSearch;
    @Autowired
    private ApproveCreate approveCreate;
    @Autowired
    private ApproveUpdate approveUpdate;
    @Autowired
    private ApproveDelete approveDelete;

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<?> saveAdministrator(@RequestBody ApproveDto data) {
        try {
            Approve approve = new Approve(null,
                    data.getBorrowingId(),
                    data.getCpfOfficial(),
                    data.getCpfOfClient(),
                    data.getQuantity(),
                    data.getCard(),
                    data.getDescription(),
                    data.getTypeApproved(),
                    data.getIsApproved(), 
                    null,
                    null);
            Approve result = this.approveCreate.createApprove(approve);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.valueOf(500));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("cpf") String id) {
        try {
            Approve clients = this.approveSearch.getApproveById(id);

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));

        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody ApproveDto data) {
        try {
            Approve approve = this.approveSearch.getApproveById(id);

            approve.setBorrowingId(data.getBorrowingId() != null ? data.getBorrowingId() : approve.getBorrowingId());
            approve.setCpfOfClient(data.getCpfOfClient() != null ? data.getCpfOfClient() : approve.getCpfOfClient());
            approve.setCpfOfficial(data.getCpfOfficial() != null ? data.getCpfOfficial() : approve.getCpfOfficial());
            approve.setCard(data.getCard() != null ? data.getCard() : approve.getCard());
            approve.setDescription(data.getDescription() != null ? data.getDescription() : approve.getDescription());
            approve.setIsApproved(data.getIsApproved() != null ? data.getIsApproved() : approve.getIsApproved());
            approve.setTypeApproved(data.getTypeApproved() != null ? data.getTypeApproved() : approve.getTypeApproved());
            approve.setQuantity(data.getQuantity() != approve.getQuantity() ? data.getQuantity() : approve.getQuantity());


            approve.setUpdateAt(LocalDateTime.now());

            Approve update = this.approveUpdate.updateBorrowing(approve);

            return new ResponseEntity<>(update, HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
    @PutMapping(value = "/approve/{id}")
    public ResponseEntity<?> approvedById(@PathVariable("id") String id){
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            this.approveDelete.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
