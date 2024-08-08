package com.example.onlineshopping.payment;

import com.example.onlineshopping.models.Order;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaypalService {

  private final APIContext apiContext;
    public Payment createPayment(
            Order order,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {

        BigDecimal totalAmount = order.getTotalAmount();

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(totalAmount.toString());

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment=new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution=new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext,paymentExecution);
    }
}
