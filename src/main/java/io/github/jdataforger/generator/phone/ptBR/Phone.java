package io.github.jdataforger.generator.phone.ptBR;

import io.github.jdataforger.generator.abstraction.IForgery;
import io.github.jdataforger.generator.phone.ptBR.model.PhoneModel;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.jdataforger.generator.phone.ptBR.PhoneUtil.DDD;

/**
 * Generate telhpone numbers of Brazil
 *
 * @author Hadil
 */
public class Phone implements IForgery<String> {

    @Override
    public String fake() {
        return generateModel().toString();
    }

    public PhoneModel getRandomPhone() {
        return generateModel();
    }

    private PhoneModel generateModel() {
        PhoneModel model = new PhoneModel();
        model.setDdd(generateDDD());
        model.setType(generatePhoneType());
        model.setNumber(generateNumber(model.getType(), model.getDdd()));

        return model;
    }

    private int generateDDD() {
        int dddIndex = ThreadLocalRandom.current().ints(1, 0, DDD.length).findFirst().getAsInt();
        return DDD[dddIndex];
    }

    private PhoneType generatePhoneType() {
        int dddIndex = ThreadLocalRandom.current().ints(1, 0, 2).findFirst().getAsInt();
        return PhoneType.values()[dddIndex];
    }

    private String generateNumber(PhoneType type, int ddd) {
        String firstNumber = "";
        if (type == PhoneType.MOBILE) {
            //DDD de São Paulo
            if (ddd == 11) {
                firstNumber += "9";
            }
            firstNumber += Integer.toString(ThreadLocalRandom.current()
                    .ints(1, 4, 10)
                    .findFirst().getAsInt());
        } else {
            firstNumber += Integer.toString(ThreadLocalRandom.current()
                    .ints(1, 2, 4)
                    .findFirst().getAsInt());
        }

        return firstNumber + ThreadLocalRandom.current()
                .ints(7, 0, 10)
                .mapToObj(d -> Integer.toString(d))
                .reduce("", (ps, e) -> ps + e);
    }
/*
    private String generateMobileNumber(){
        int firstNumber = ThreadLocalRandom.current().ints(1,2,10).findFirst().getAsInt();
        return "9"+firstNumber+ThreadLocalRandom.current()
                         .ints(7,0,10)
                         .mapToObj(d -> Integer.toString(d))
                         .reduce("",(ps,e)-> ps+e);
    }

    private String generateFixedNumber(){
        int firstNumber = ThreadLocalRandom.current().ints(1,2,10).findFirst().getAsInt();
        int size = ThreadLocalRandom.current().ints(1,6,8).findFirst().getAsInt();
        return ""+ firstNumber + ThreadLocalRandom.current()
                .ints(size,0,10)
                .mapToObj(d -> Integer.toString(d))
                .reduce("",(ps,e)-> ps+e);
    }*/
}