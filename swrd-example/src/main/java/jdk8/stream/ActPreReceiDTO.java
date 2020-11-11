package jdk8.stream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ActPreReceiDTO {
    private String bizActNo;
    private String arActNo;
    private int inversSeq;
    private BigDecimal totalAmount;


    public static void main(String[] args) {
        ArrayList<ActPreReceiDTO> objects = new ArrayList<>();
        ActPreReceiDTO accountPreReceipt1 = new ActPreReceiDTO();
        accountPreReceipt1.setBizActNo("1");
        accountPreReceipt1.setInversSeq(1);
        accountPreReceipt1.setArActNo("CPC");
        accountPreReceipt1.setTotalAmount(new BigDecimal("2.2"));

        ActPreReceiDTO accountPreReceipt2 = new ActPreReceiDTO();
        accountPreReceipt2.setBizActNo("1");
        accountPreReceipt2.setInversSeq(2);
        accountPreReceipt2.setArActNo("CPC");
        accountPreReceipt2.setTotalAmount(new BigDecimal("-2.2"));

        ActPreReceiDTO accountPreReceipt3 = new ActPreReceiDTO();
        accountPreReceipt3.setBizActNo("2");
        accountPreReceipt3.setInversSeq(3);
        accountPreReceipt3.setArActNo("CASH");
        accountPreReceipt3.setTotalAmount(new BigDecimal("2.2"));

        objects.add(accountPreReceipt1);
        objects.add(accountPreReceipt2);
        objects.add(accountPreReceipt3);

        //
        Map<String, Set<Integer>> inverseseqmap = new HashMap<>();
        Map<String, BigDecimal> bizArActAmountMap = new HashMap<>();

        Map<String, Map<String, List<ActPreReceiDTO>>> bizArActMap =
                objects.stream().collect(
                        Collectors.groupingBy(ActPreReceiDTO::getBizActNo, Collectors.groupingBy(ActPreReceiDTO::getArActNo)
                        )
                );


        bizArActMap.forEach((outkey, outvalue) -> {
            outvalue.forEach((key, value) -> {
                value.stream().forEach(e ->
                                //财务账户、子账户拼接
                        {
                            Set<Integer> integers = inverseseqmap.get(outkey + "_" + key);
                            if (integers == null) {
                                integers = new HashSet<>();
                                inverseseqmap.put(outkey + "_" + key, integers);
                            }
                            integers.add(e.getInversSeq());
                        }
                        // inverseseqmap.put(outkey+"_"+key,e.getInversSeq());
                );

                BigDecimal reduce = value.stream().map(ActPreReceiDTO::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                bizArActAmountMap.put(outkey + "_" + key, reduce);
            });
        });

        System.out.println(bizArActAmountMap);
        System.out.println(inverseseqmap);

        /*objects.stream()
                .collect(Collectors
                        .groupingBy(
                                user -> new User(user.name, user.phone, user.address),
                                Collectors.summarizingLong(ActPreReceiDTO::getTotalAmount)
                        )
                )
                .forEach((k,v) -> {
                    k.scope = v.getSum();
                    System.out.println(k);
                });*/
        System.out.println();
        ///System.out.println(objects.stream().map(ActPreReceiDTO::getTotalAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getBizActNo() {
        return bizActNo;
    }

    public void setBizActNo(String bizActNo) {
        this.bizActNo = bizActNo;
    }

    public String getArActNo() {
        return arActNo;
    }

    public void setArActNo(String arActNo) {
        this.arActNo = arActNo;
    }

    public int getInversSeq() {
        return inversSeq;
    }

    public void setInversSeq(int inversSeq) {
        this.inversSeq = inversSeq;
    }
}
