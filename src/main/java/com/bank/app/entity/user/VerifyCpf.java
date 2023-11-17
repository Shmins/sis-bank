package com.bank.app.entity.user;

import java.util.ArrayList;
import java.util.List;

public class VerifyCpf {
    public Boolean verifyCpfCode(String cpf) {
        List<Integer> list1 = this.multiplyCpf(dismemberCpf(cpf), 10, 2);
        int codeVerify1 = codeVerification(sumList(list1) % 11);
        System.out.print(codeVerify1);

        List<Integer> list2 = this.multiplyCpf(dismemberCpf(cpf), 11, 1);
        int codeVerify2 = codeVerification(sumList(list2) % 11);
        System.out.print(codeVerify2);
        return true;
    }

    private String[] dismemberCpf(String cpf) {
        return cpf.replace(".", "").replace("-", "").split("");

    }

    private List<Integer> multiplyCpf(String[] cpf, int weightList, int length) {
        List<Integer> r = new ArrayList<>();
        int weight = weightList;
        for (int i = 0; i < cpf.length - length; ++i) {
            r.add(Integer.parseInt(cpf[i]) * weight);
            if (weight >= 1) {
                --weight;
            }
        }
        return r;
    }

    private Integer sumList(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    private Integer codeVerification(int number) {
        if (number < 2) {
            return 0;
        } else {
            return 11 - number;
        }
    }
}
