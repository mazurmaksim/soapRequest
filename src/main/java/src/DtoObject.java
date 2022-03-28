package src;

import java.util.StringJoiner;

public class DtoObject {

    private int id;
    private String description;
    private String amount;
    private String currency;
    private String code;
    private String referenceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public void setDelimiterToReferenceCode() {
        StringJoiner joiner = new StringJoiner("|");
            referenceCode = String.valueOf(joiner
                    .add(referenceCode.substring(0,4))
                    .add(referenceCode));
    }
}
