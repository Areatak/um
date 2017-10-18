package com.areatak.gazette.entities;

import javax.persistence.*;

/**
 * Created by alirezaghias on 5/28/2017 AD.
 */
@Entity
@Table(name = "t_payment")
public class Payment extends BaseEntity {
    public enum ZarinGateType {
        Web,
        Zarin,
        Mobile,
        USSD
    }
    public enum PaymentStatus {
        Pending, //waiting to be paid
        Paid,// it has beed paid
        Error, // error
        Success // credit added to user
    }
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private ZarinGateType gateType;
    private Integer amount;
    private double ubit;
    private String description;
    private String authority;
    private Integer zarinStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private Long refId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ZarinGateType getGateType() {
        return gateType;
    }

    public void setGateType(ZarinGateType gateType) {
        this.gateType = gateType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getZarinStatus() {
        return zarinStatus;
    }

    public void setZarinStatus(Integer zarinStatus) {
        this.zarinStatus = zarinStatus;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public double getUbit() {
        return ubit;
    }

    public void setUbit(double ubit) {
        this.ubit = ubit;
    }

    public String getZarinStatusString() {
        switch (zarinStatus) {
            case -1:
                return "اطلاعات ارسال شده ناقص است.";
            case -2:
                return "IP و يا مرچنت كد پذيرنده صحيح نيست.";
            case -3:
                return "با توجه به محدوديت هاي شاپرك امكان پرداخت با رقم درخواست شده ميسر نمي باشد.";
            case -4:
                return "سطح تاييد پذيرنده پايين تر از سطح نقره اي است.";
            case -11:
                return "درخواست مورد نظر يافت نشد.";
            case -12:
                return "امكان ويرايش درخواست ميسر نمي باشد.";
            case -21:
                return "هيچ نوع عمليات مالي براي اين تراكنش يافت نشد.";
            case -22:
                return "تراكنش نا موفق ميباشد.";
            case -33:
                return "رقم تراكنش با رقم پرداخت شده مطابقت ندارد.";
            case -34:
                return "سقف تقسيم تراكنش از لحاظ تعداد يا رقم عبور نموده است";
            case -40:
                return "اجازه دسترسي به متد مربوطه وجود ندارد.";
            case -41:
                return "اطلاعات ارسال شده مربوط به AdditionalData غيرمعتبر ميباشد.";
            case -42:
                return "مدت زمان معتبر طول عمر شناسه پرداخت بايد بين 30 دقيه تا 45 روز مي باشد.";
            case -54:
                return "درخواست مورد نظر آرشيو شده است.";
            case 100:
                return "عمليات با موفقيت انجام گرديده است.";
            case 101:
                return "عمليات پرداخت موفق بوده و قبلا PaymentVerification تراكنش انجام شده است.";
            default:
                return "";
        }
    }
}
