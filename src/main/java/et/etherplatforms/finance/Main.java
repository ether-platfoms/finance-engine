package et.etherplatforms.finance;

import et.etherplatforms.finance.core.depreciation.DepreciationMethod;
import et.etherplatforms.finance.core.depreciation.DepreciationSchedule;
import et.etherplatforms.finance.core.depreciation.StraightLineDepreciation;
import et.etherplatforms.finance.core.money.CurrencyUnit;
import et.etherplatforms.finance.core.money.Money;
import et.etherplatforms.finance.core.period.AccountingPeriod;
import et.etherplatforms.finance.core.tax.DefaultTaxPolicy;
import et.etherplatforms.finance.core.tax.TaxContext;
import et.etherplatforms.finance.core.tax.TaxResult;
import et.etherplatforms.finance.core.FinanceEngineImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Depreciation Module Test ===");
        Money assetCost = new Money(BigDecimal.valueOf(10_000), CurrencyUnit.USD);
        Money residual = new Money(BigDecimal.valueOf(1_000), CurrencyUnit.USD);
        int usefulLife = 12;

        DepreciationMethod method = new StraightLineDepreciation();
        DepreciationSchedule schedule = new DepreciationSchedule(assetCost, residual, usefulLife, method);

        System.out.println("Straight-Line Depreciation Schedule:");
        schedule.getFullSchedule().forEach((month, amount) ->
                System.out.println("Month " + month + ": " + amount)
        );

        System.out.println("\n=== Accounting Period Test ===");
        AccountingPeriod period = new AccountingPeriod(
                "2025-12",
                LocalDate.of(2025, 12, 1),
                LocalDate.of(2025, 12, 31)
        );
        System.out.println("Period: " + period.getCode() + " Open? " + period.isOpen());

        System.out.println("\n=== Tax Calculation Test ===");
        FinanceEngineImpl engine = new FinanceEngineImpl(new DefaultTaxPolicy());

        Money netAmount = new Money(BigDecimal.valueOf(1_000), CurrencyUnit.USD);
        TaxContext vatContext = new TaxContext(BigDecimal.valueOf(0.18), CurrencyUnit.USD, "VAT");


        TaxResult taxResult = engine.calculateTaxResult(netAmount, vatContext);

        System.out.println("Net Amount: " + taxResult.getNetAmount());
        System.out.println("Calculated Tax (18% VAT): " + taxResult.getTaxAmount());
        System.out.println("Total Amount with Tax: " + taxResult.getTotalAmount());
        System.out.println("Tax Type: " + taxResult.getTaxType());
    }
}
