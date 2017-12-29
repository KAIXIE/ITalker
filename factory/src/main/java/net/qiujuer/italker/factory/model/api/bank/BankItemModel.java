package net.qiujuer.italker.factory.model.api.bank;

/**
 * Created by Administrator on 2017/12/29.
 */

public class BankItemModel {
    private String cardType;
    private String bankName;
    private String id;
    private String cardNumber;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
